package com.embabel.example.agent

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.annotation.Condition

@Agent(description = "Test agent with self-sufficient action")
class SelfSufficient {
    @Action(pre = ["initialized"])
    fun selfSufficient(): Output {
        // This action provides its own precondition
        return Output()
    }
    
    @Condition(name = "initialized")
    fun initialized(output: Output?): Boolean = output == null

    @Action
    @AchievesGoal(description = "Complete self-sufficient task")
    fun complete(output: Output): Success = Success()
}

data class Output(
    val data: String = "Initial output" // Customize as needed
)
