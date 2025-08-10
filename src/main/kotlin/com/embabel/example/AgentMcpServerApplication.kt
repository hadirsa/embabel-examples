package com.embabel.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import com.embabel.agent.config.annotation.EnableAgentMcpServer
import com.embabel.agent.config.annotation.EnableAgents
import com.embabel.agent.config.annotation.McpServers
import io.modelcontextprotocol.server.McpServer
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableAgentMcpServer
@EnableAgents(
    mcpServers = [McpServers.DOCKER_DESKTOP, McpServers.DOCKER],
)
class AgentMcpServerApplication

fun main(args: Array<String>) {
    runApplication<AgentMcpServerApplication>(*args)
}
