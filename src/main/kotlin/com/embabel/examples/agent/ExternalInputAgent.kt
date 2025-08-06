package com.embabel.examples.agent

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.domain.io.UserInput

@Agent(description = "Test agent with external input")
class ExternalInputAgent {
    @Action
    fun processUserInput(userInput: UserInput): ProcessedInput = ProcessedInput()
    
    @Action
    fun generateCode(input: ProcessedInput): GeneratedCode = GeneratedCode()

    @Action
    @AchievesGoal(description = "Generate code from user input")
    fun completeGeneration(code: GeneratedCode, userInput: UserInput, dummyInput: DummyInput): CompletedCode = CompletedCode()
}

data class ProcessedInput(val dummy: Boolean = false)
data class GeneratedCode(val dummy: Boolean = false)
data class CompletedCode(val dummy: Boolean = false)
data class DummyInput(val dummy: Boolean = false)
