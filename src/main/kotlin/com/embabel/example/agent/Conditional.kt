package com.embabel.example.agent

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Condition

//@Agent(description = "Test agent with conditions")
class Conditional {
    companion object {
        const val NEEDS_BUILD = "needsBuild"
        const val BUILD_SUCCESS = "buildSuccess"
    }
    
    @Action
    fun start(): Project = Project()
    
    @Action(post = [NEEDS_BUILD])
    fun modifyCode(project: Project): CodeChange = CodeChange()
    
    @Action(pre = [NEEDS_BUILD], post = [BUILD_SUCCESS])
    fun buildProject(change: CodeChange): BuildResult = BuildResult()
    
    @Condition(name = NEEDS_BUILD)
    fun needsBuild(change: CodeChange): Boolean = true
    
    @Condition(name = BUILD_SUCCESS)
    fun buildSuccess(result: BuildResult): Boolean = true
    
    @Action(pre = [BUILD_SUCCESS])
    @AchievesGoal(description = "Complete code modification")
    fun complete(result: BuildResult): Success = Success()
}


data class Project(
    val id: String = "defaultProjectId"
)

data class CodeChange(
    val description: String = "Code changes applied"
)

data class BuildResult(
    val success: Boolean = true,
    val output: String = "Build completed successfully"
)

data class Success(
    val message: String = "Goal achieved"
)
