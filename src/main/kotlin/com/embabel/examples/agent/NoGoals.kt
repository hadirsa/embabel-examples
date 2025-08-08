package com.embabel.examples.agent

import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent

@Agent(description = "Test agent with no goals")
class NoGoals {
    @Action
    fun doSomething(): String = "Action without goal"
    
    @Action
    fun doSomethingElse(input: String): String = "Another action"
}
