package com.embabel.example.agent

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action

//@Agent(description = "Agent with an unreachable goal")
class BrokenAgent {

    @Action
    fun start(): A = A()

    // Action that requires input B, but no action produces B
    @Action
    fun combine(a: A, b: B): C = C()

    @Action
    @AchievesGoal(description = "Achieve goal with C")
    fun complete(c: C): Done = Done()
}

data class A(val dummy: Boolean = false)
data class B(val dummy: Boolean = false)
data class C(val dummy: Boolean = false)
data class Done(val dummy: Boolean = false)
