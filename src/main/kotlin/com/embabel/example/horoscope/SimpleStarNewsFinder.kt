package com.embabel.example.horoscope

import com.embabel.agent.api.annotation.*
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.config.models.OpenAiModels
import com.embabel.agent.domain.io.UserInput
import com.embabel.common.ai.model.LlmOptions
import org.springframework.beans.factory.annotation.Value

/**
 * Simplified horoscope agent that works with MCP inspector.
 * This version follows the working pattern from TimeoutTestAgent.
 */
@Agent(
    name = "SimpleStarNewsFinder",
    description = "Find news based on a person's star sign - simplified version",
    beanName = "simpleStarNewsFinder"
)
class SimpleStarNewsFinder(
    private val horoscopeService: HoroscopeService,
    @param:Value("\${star-news-finder.model:gpt-4.1-mini}")
    private val model: String = OpenAiModels.GPT_41_NANO,
) {

    /**
     * Main action that processes user input and generates a horoscope-based writeup.
     * This follows the simple pattern that works with MCP inspector.
     */
    @Action(cost = 0.1)
    fun processHoroscopeRequest(context: OperationContext, userInput: UserInput?): Writeup {
        val prompt = if (userInput?.content != null && userInput.content.trim().isNotEmpty()) {
            userInput.content
        } else {
            "Generate a horoscope for Aries"
        }

        // Extract star sign from the prompt (simple approach)
        val starSign = extractStarSign(prompt)
        val horoscope = horoscopeService.dailyHoroscope(starSign)

        val llm = LlmOptions.withModel(model).withTemperature(0.7)

        return context.ai().withLlm(llm).createObject(
            """
            Based on the following horoscope for $starSign, create an amusing and personalized writeup:
            
            <horoscope>$horoscope</horoscope>
            
            User request: $prompt
            
            Create a fun, engaging response that incorporates the horoscope reading in about 100 words.
            Make it personal and entertaining.
            """.trimIndent(),
            Writeup::class.java
        )
    }

    /**
     * Main goal action that demonstrates the horoscope functionality.
     * This is the entry point for MCP inspector calls.
     */
    @AchievesGoal(
        description = "Create a personalized horoscope-based writeup from user input",
        export = Export(
            remote = true,
            name = "simpleStarNewsWriteup",
            startingInputTypes = [UserInput::class]
        )
    )
    @Action
    fun generateHoroscopeWriteup(writeup: Writeup): String {
        val results = StringBuilder()
        results.append("# Horoscope-Based Writeup:\n\n")
        results.append(writeup.content)
        return results.toString()
    }

    /**
     * Simple helper method to extract star sign from text
     */
    private fun extractStarSign(text: String): String {
        val starSigns = listOf(
            "aries", "taurus", "gemini", "cancer", "leo", "virgo",
            "libra", "scorpio", "sagittarius", "capricorn", "aquarius", "pisces"
        )
        
        val lowerText = text.lowercase()
        for (sign in starSigns) {
            if (lowerText.contains(sign)) {
                return sign.capitalize()
            }
        }
        
        // Default to Aries if no sign found
        return "Aries"
    }
} 
