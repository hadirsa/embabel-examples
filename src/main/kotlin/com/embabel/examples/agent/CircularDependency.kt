package com.embabel.examples.agent

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent

@Agent(description = "Test agent with circular dependencies")
class CircularDependency {
    @Action
    fun actionA(resultB: ResultB): ResultA = ResultA()
    
    @Action
    fun actionB(resultA: ResultA): ResultB = ResultB()
    
    @AchievesGoal(description = "Achieve something")
    fun goal(resultA: ResultA): String = "Goal"
}

data class ResultA(val dummy: Boolean = true)
data class ResultB(val dummy: Boolean = true)
