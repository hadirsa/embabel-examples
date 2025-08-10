package com.embabel.example.agent

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action

//@Agent(description = "Test agent with multiple paths")
class MultiPathAgent {
    @Action
    fun pathA(): TypeA = TypeA()
    
    @Action
    fun pathB(): TypeB = TypeB()
    
    @Action
    fun convergeA(a: TypeA): Result = Result()
    
    @Action
    fun convergeB(b: TypeB): Result = Result()

    @Action
    @AchievesGoal(description = "Reach goal via multiple paths")
    fun goal(result: Result): Goal = Goal()
}

data class TypeA(
    val value: String = "Path A result"
)

data class TypeB(
    val value: String = "Path B result"
)

data class Result(
    val combined: String = "Converged result"
)

data class Goal(
    val message: String = "Goal achieved through one of the paths"
)
