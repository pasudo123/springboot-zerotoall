package com.example.springbootswaggerbasis.presentation.team

import com.example.springbootswaggerbasis.presentation.team.model.TeamResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("teams")
class TeamController {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun findAll(): ResponseEntity<List<TeamResponse>> {
        val teamResponses: MutableList<TeamResponse> = mutableListOf()

        repeat(10) { seq ->
            teamResponses.add(
                TeamResponse(id = seq.toLong(), name = "드림팀-$seq")
            )
        }

        return ResponseEntity.ok(teamResponses)
    }
}