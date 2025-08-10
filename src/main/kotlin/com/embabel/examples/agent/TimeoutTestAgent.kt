package com.embabel.examples.agent

import com.embabel.agent.api.annotation.*
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.config.models.OpenAiModels
import com.embabel.agent.domain.io.UserInput
import com.embabel.common.ai.model.LlmOptions
import com.embabel.common.ai.model.ModelSelectionCriteria
import java.time.Duration

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
     * Test action with a very short timeout (1 second) to demonstrate timeout behavior
     */
    @Action
    fun testShortTimeout(context: OperationContext, userInput: UserInput?): String {
        val llm = LlmOptions.fromCriteria(
            ModelSelectionCriteria.firstOf(OpenAiModels.GPT_41_MINI)
        ).withTemperature(0.9).withTimeout(Duration.ofSeconds(1))

        val prompt = if (userInput?.content != null && userInput.content.trim().isNotEmpty()) {
            userInput.content
        } else {
            "Write a detailed analysis of quantum computing principles and their applications in modern cryptography. " +
                    "Include mathematical formulas, historical context, and future predictions. " +
                    "Make this response very comprehensive and detailed."
        }

        return context.ai().withLlm(llm).createObject(prompt, String::class.java)
    }

    /**
     * Test action with a medium timeout (5 seconds)
     */
    @Action
    fun testMediumTimeout(context: OperationContext, userInput: UserInput?): String {
        val llm = LlmOptions.fromCriteria(
            ModelSelectionCriteria.firstOf(OpenAiModels.GPT_41_MINI)
        ).withTemperature(0.7).withTimeout(Duration.ofSeconds(5))

        val prompt = if (userInput?.content != null && userInput.content.trim().isNotEmpty()) {
            userInput.content
        } else {
            "Write a brief summary of artificial intelligence trends in 2024"
        }

        return context.ai().withLlm(llm).createObject(prompt, String::class.java)
    }

    /**
     * Test action with a longer timeout (30 seconds) - should complete successfully
     */
    @Action
    fun testLongTimeout(context: OperationContext, userInput: UserInput?): String {
        val llm = LlmOptions.fromCriteria(
            ModelSelectionCriteria.firstOf(OpenAiModels.GPT_41_MINI)
        ).withTemperature(0.5).withTimeout(Duration.ofSeconds(30))

        val prompt = if (userInput?.content != null && userInput.content.trim().isNotEmpty()) {
            userInput.content
        } else {
            "Write a short poem about technology"
        }

        return context.ai().withLlm(llm).createObject(prompt, String::class.java)
    }

    /**
     * Test action using default LLM timeout for comparison
     */
    @Action
    fun testDefaultTimeout(context: OperationContext, userInput: UserInput?): String {
        val prompt = if (userInput?.content != null && userInput.content.trim().isNotEmpty()) {
            userInput.content
        } else {
            "Write a haiku about programming"
        }

        return context.ai().withDefaultLlm().createObject(prompt, String::class.java)
    }

    /**
     * Test action with custom timeout and temperature for creative writing
     */
    @Action
    fun testCreativeWithTimeout(context: OperationContext, userInput: UserInput?): String {
        val llm = LlmOptions.fromCriteria(
            ModelSelectionCriteria.firstOf(OpenAiModels.GPT_41_MINI)
        ).withTemperature(0.9).withTimeout(Duration.ofSeconds(3))

        val prompt = if (userInput?.content != null && userInput.content.trim().isNotEmpty()) {
            userInput.content
        } else {
            "Create a creative story about a robot learning to paint. " +
                    "Make it imaginative and detailed with dialogue and descriptions."
        }

        return context.ai().withLlm(llm).createObject(prompt, String::class.java)
    }

    /**
     * Test action with custom timeout duration parsed from user input
     */
    @Action
    fun testCustomTimeout(context: OperationContext, userInput: UserInput?): String {
        if (userInput?.content == null || userInput.content.trim().isEmpty()) {
            return "Please provide input in format: 'timeout:5 your prompt here'"
        }

        val input = userInput.content.trim()
        var timeoutSeconds = 10 // default
        var prompt = "Write something interesting."

        // Parse input format: "timeout:5 explain quantum computing"
        if (input.startsWith("timeout:")) {
            try {
                val parts = input.split(" ", limit = 2)
                timeoutSeconds = parts[0].substring(8).toInt()
                if (parts.size > 1) {
                    prompt = parts[1]
                }
            } catch (e: NumberFormatException) {
                return "Invalid timeout format. Use: 'timeout:5 your prompt here'"
            }
        } else {
            prompt = input
        }

        val llm = LlmOptions.fromCriteria(
            ModelSelectionCriteria.firstOf(OpenAiModels.GPT_41_MINI)
        ).withTemperature(0.8).withTimeout(Duration.ofSeconds(timeoutSeconds.toLong()))

        return context.ai().withLlm(llm).createObject(prompt, String::class.java)
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
    fun runTimeoutTests(context: OperationContext, userInput: UserInput?): String {
        val results = StringBuilder()
        results.append("# LLM Timeout Test Results\n\n")

        // Test 1: Short timeout
        try {
            results.append("## Test 1: Short Timeout (1 second)\n")
            val result = testShortTimeout(context, userInput)
            results.append("**Result:** $result\n\n")
        } catch (e: Exception) {
            results.append("**Timeout occurred as expected:** ${e.message}\n\n")
        }

        // Test 2: Medium timeout
        try {
            results.append("## Test 2: Medium Timeout (5 seconds)\n")
            val result = testMediumTimeout(context, userInput)
            results.append("**Result:** $result\n\n")
        } catch (e: Exception) {
            results.append("**Error:** ${e.message}\n\n")
        }

        // Test 3: Long timeout
        try {
            results.append("## Test 3: Long Timeout (30 seconds)\n")
            val result = testLongTimeout(context, userInput)
            results.append("**Result:** $result\n\n")
        } catch (e: Exception) {
            results.append("**Error:** ${e.message}\n\n")
        }

        // Test 4: Default timeout
        try {
            results.append("## Test 4: Default Timeout\n")
            val result = testDefaultTimeout(context, userInput)
            results.append("**Result:** $result\n\n")
        } catch (e: Exception) {
            results.append("**Error:** ${e.message}\n\n")
        }

        // Test 5: Creative with timeout
        try {
            results.append("## Test 5: Creative Writing with Timeout (3 seconds)\n")
            val result = testCreativeWithTimeout(context, userInput)
            results.append("**Result:** $result\n\n")
        } catch (e: Exception) {
            results.append("**Error:** ${e.message}\n\n")
        }

        // Test 6: Custom timeout
        try {
            results.append("## Test 6: Custom Timeout (User-defined)\n")
            val result = testCustomTimeout(context, userInput)
            results.append("**Result:** $result\n\n")
        } catch (e: Exception) {
            results.append("**Error:** ${e.message}\n\n")
        }

        return results.toString()
    }

    @AchievesGoal(
        description = "Test a specific timeout duration with custom prompt",
        export = Export(
            remote = true,
            name = "kotlinTestSpecificTimeout",
            startingInputTypes = [UserInput::class]
        )
    )
    @Action
    fun testSpecificTimeout(context: OperationContext, userInput: UserInput?): String {
        if (userInput?.content == null || userInput.content.trim().isEmpty()) {
            return "Please provide input in format: 'timeout:5 prompt:your prompt here'"
        }

        val input = userInput.content.trim()
        var timeoutSeconds = 10 // default
        var prompt = "Write something interesting."

        // Parse input format: "timeout:5 prompt:explain quantum computing"
        if (input.startsWith("timeout:")) {
            try {
                val parts = input.split(" ", limit = 2)
                timeoutSeconds = parts[0].substring(8).toInt()
                if (parts.size > 1 && parts[1].startsWith("prompt:")) {
                    prompt = parts[1].substring(7)
                } else if (parts.size > 1) {
                    prompt = parts[1]
                }
            } catch (e: NumberFormatException) {
                return "Invalid timeout format. Use: 'timeout:5 prompt:your prompt here'"
            }
        } else {
            prompt = input
        }

        val llm = LlmOptions.fromCriteria(
            ModelSelectionCriteria.firstOf(OpenAiModels.GPT_41_MINI)
        ).withTemperature(0.8).withTimeout(Duration.ofSeconds(timeoutSeconds.toLong()))

        return try {
            val result = context.ai().withLlm(llm).createObject(prompt, String::class.java)
            "**Timeout: $timeoutSeconds seconds**\n**Prompt:** $prompt\n\n**Result:** $result"
        } catch (e: Exception) {
            "**Timeout: $timeoutSeconds seconds**\n**Prompt:** $prompt\n\n**Error:** ${e.message}"
        }
    }
}
