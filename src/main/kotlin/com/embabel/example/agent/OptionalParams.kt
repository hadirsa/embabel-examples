package com.embabel.example.agent

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent

@Agent(description = "Test with optional parameters")
class OptionalParams {
    @Action
    fun start(): Project = Project()
    
    @Action
    fun process(
        project: Project,
        config: Config
    ): Result = Result()

    @Action
    @AchievesGoal(description = "Process with optional config")
    fun complete(result: Result): Success = Success()
}

data class Config(var dummy: Boolean = false)
