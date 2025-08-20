package com.embabel.example.agent

import com.dataagent.core.service.DataAgentService
import com.embabel.agent.api.annotation.*
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.domain.io.UserInput
import com.embabel.agent.domain.library.HasContent
import com.embabel.common.ai.model.LlmOptions
import com.embabel.common.ai.model.ModelSelectionCriteria
import java.time.Duration


data class SqlOutput(
    override val content: String,
) : HasContent

/**
 * Test agent for demonstrating using data-agent framework functionality
 */
@Agent(
    name = "SqlGeneratorAgent",
    description = "Test agent for convert userInput to sql functionality using data-agent framework",
    beanName = "SqlGeneratorAgent"
)
class SqlConvertorAgent (private val dataAgentService: DataAgentService){

    @Action(cost = 0.1)
    fun sqlQueryGenerator(context: OperationContext, userInput: UserInput?): SqlOutput {
        val schemas = dataAgentService.getAllLearnedSchemas()
        val summary = dataAgentService.getSchemaSummary()

        val llm = LlmOptions.fromCriteria(
            ModelSelectionCriteria.firstOf("deepseek-r1:1.5b")
        ).withTemperature(1.0)

        val prompt = if (userInput?.content != null && userInput.content.trim().isNotEmpty()) {
            """
                You are an expert SQL query generator. 
                Your task is to convert user requests in natural language into valid SQL queries.

                You will be given:
                1. A database schema, described as a list of entities (tables) with their fields (columns) and relationships.
                2. A natural language query from the user.

                Rules:
                - Always base your SQL queries only on the provided schema. Do not assume extra tables or fields.
                - Ensure the query is syntactically correct SQL.
                - Use proper JOINs based on relationships between entities if necessary.
                - If the user’s request is ambiguous, make reasonable assumptions and add a comment explaining them in the SQL.
                - Only output SQL (with optional inline comments), no explanations outside of SQL.

                Input format:
                Schema: $schemas
                Schema summary: $summary
                User request: $userInput
                
                Output format:
                <generated SQL code>
      
            """.trimIndent()
        } else {
            ""
        }

        return context.ai().withLlm(llm).createObject(
            """
                        $prompt
                    """.trimIndent(),
            SqlOutput::class.java
        )
    }

    /**
     * Main goal action that demonstrates userInput to sql convertor functionality
     */
    @AchievesGoal(
        description = "Test agent for convert user request to sql functionality using data-agent framework",
        export = Export(
            remote = true,
            name = "SqlGenerator",
            startingInputTypes = [UserInput::class]
        )
    )
    @Action
    fun runToSql(sqlOutput:SqlOutput): String {
        val results = StringBuilder()

        results.append("# Equivalent SQL:\n\n")
        results.append(sqlOutput.content)

        return results.toString()
    }
}
