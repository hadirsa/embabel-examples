package com.embabel.example.agent

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action

//@Agent(description = "Test agent with valid linear chain")
class LinearChain {

    @Action
    fun loadProject(): SoftwareProject = SoftwareProject("DemoProject")

    @Action
    fun analyzeProject(project: SoftwareProject): Analysis =
        Analysis("Analysis of ${project.name}")

    @Action
    fun generateReport(analysis: Analysis): Report =
        Report("Report based on: ${analysis.details}")

    @AchievesGoal(description = "Generate project report")
    fun finalReport(report: Report): FinalReport =
        FinalReport("Final report content: ${report.content}")
}

// ✅ Proper data classes with at least one property
data class SoftwareProject(val name: String)
data class Analysis(val details: String)
data class Report(val content: String)
data class FinalReport(val summary: String)
