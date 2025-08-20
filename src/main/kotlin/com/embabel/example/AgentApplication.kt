package com.embabel.example

import com.dataagent.core.annotation.EnableDataAgent
import com.embabel.agent.config.annotation.EnableAgentShell
import com.embabel.agent.config.annotation.EnableAgents
import com.embabel.agent.config.annotation.LoggingThemes
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@EnableDataAgent(
    packages = "com.embabel.example.domain.*",
    autoDiscover = true,
)
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableAgents(
    loggingTheme = LoggingThemes.SEVERANCE,
)
@EnableAgentShell
class CodingAgentApplication

fun main(args: Array<String>) {
    runApplication<CodingAgentApplication>(*args)
}
