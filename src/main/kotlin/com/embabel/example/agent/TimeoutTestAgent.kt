package com.embabel.example.agent

import com.embabel.agent.api.annotation.*
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.config.models.OpenAiModels
import com.embabel.agent.domain.io.UserInput
import com.embabel.agent.domain.library.HasContent
import com.embabel.common.ai.model.LlmOptions
import com.embabel.common.ai.model.ModelSelectionCriteria
import java.time.Duration


data class Writeup(
    override val content: String,
) : HasContent

/**
 * Test agent for demonstrating LLM timeout functionality
 */
@Agent(
    name = "KotlinTimeoutTestAgent",
    description = "Test agent for demonstrating LLM timeout functionality",
    beanName = "kotlinTimeoutTestAgent"
)
class TimeoutTestAgent {
    /**
     * Test action with a medium timeout (5 seconds)
     */
    @Action(cost = 0.1)
    fun testMediumTimeout(context: OperationContext, userInput: UserInput?): Writeup {
        val llm = LlmOptions.fromCriteria(
            ModelSelectionCriteria.firstOf("qwen3:0.6b")
        ).withTemperature(0.7).withTimeout(Duration.ofSeconds(10))

        val prompt = if (userInput?.content != null && userInput.content.trim().isNotEmpty()) {
            userInput.content
        } else {
            "Write a brief summary of artificial intelligence trends in 2024"
        }

        return context.ai().withLlm(llm).createObject(
            """
                    Take the following Poetry idea $prompt and write up something
                    amusing in 50 words. 
                    """.trimIndent(),
            Writeup::class.java
        )
    }

    /**
     * Main goal action that demonstrates different timeout scenarios
     */
    @AchievesGoal(
        description = "Test various LLM timeout configurations with user input to understand timeout behavior",
        export = Export(
            remote = true,
            name = "kotlinTimeoutTest",
            startingInputTypes = [UserInput::class]
        )
    )
    @Action
    fun runTimeoutTests(writeup:Writeup): String {
        val results = StringBuilder()

        results.append("# LLM Timeout Test Results:\n\n")
        results.append(writeup.content)

        return results.toString()
    }
}
