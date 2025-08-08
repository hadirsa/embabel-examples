package com.embabel.examples.agent

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent

// This agent should fail at GOAP planning stage
@Agent(description = "Agent where goal conflicts with available state")
class ConflictingStateAgent {

    @Action
    fun start(): TypeA = TypeA()

    @Action
    fun createConflict(a: TypeA): TypeB = TypeB()

    // The goal action needs TypeB to be FALSE, but createConflict sets it to TRUE
    // This creates a logical conflict that even your optimistic validation can't resolve
    @Action
    @AchievesGoal(description = "Goal with conflicting state requirement")  
    fun complete(a: TypeA): Done {
        // In the agent metadata, this might be interpreted as needing TypeB = FALSE
        // But createConflict produces TypeB = TRUE
        // The GOAP planner should fail to find a plan
        return Done()
    }
}

// Better example - agent where the goal literally cannot be satisfied
@Agent(description = "Agent with impossible goal state")
class ImpossibleGoalAgent {

    @Action
    fun start(): TypeA = TypeA()

    @Action  
    fun setTrue(a: TypeA): TypeB = TypeB()  // This sets some condition to TRUE

    // Goal action that somehow requires the same condition to be FALSE
    // (This depends on how your metadata extraction works)
    @Action
    @AchievesGoal(description = "Impossible goal")
    fun complete(a: TypeA, notB: SomeOtherType): Done = Done()
    
    // If SomeOtherType represents the negation of TypeB, 
    // then we have TypeB=TRUE and TypeB=FALSE required simultaneously
}

// Most reliable failing case - action chain that doesn't connect to goal
@Agent(description = "Completely disconnected goal")
class DisconnectedGoalAgent {

    // Chain 1: start -> middle (produces TypeB)
    @Action
    fun start(): TypeA = TypeA()

    @Action
    fun middle(a: TypeA): TypeB = TypeB()

    // Chain 2: Completely separate goal that needs TypeC
    // TypeC will be set as external input, but goal needs Done to be TRUE
    // The goal action produces Done, but needs TypeC
    // Even if TypeC=TRUE, the goal action needs to RUN to produce Done=TRUE
    // But your current goal logic uses effects as preconditions!
    @Action
    @AchievesGoal(description = "Disconnected goal")
    fun unreachableGoal(b: TypeB): Done = Done()
}

data class TypeC(val dummy: Boolean = false)
data class SomeOtherType(val dummy: Boolean = false)

