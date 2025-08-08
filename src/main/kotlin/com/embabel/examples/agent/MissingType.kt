package com.embabel.examples.agent

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent

@Agent(description = "Test with missing intermediate type")
class MissingType {
    @Action
    fun start(): TypeA = TypeA()
    
    // Missing action that produces TypeB
    
    @Action
    fun combine(a: TypeA, b: TypeB): Result = Result()

    @Action
    @AchievesGoal(description = "Combine types")
    fun goal(result: Result): Goal = Goal()
}
