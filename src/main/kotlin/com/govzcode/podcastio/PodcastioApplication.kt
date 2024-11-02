package com.govzcode.podcastio

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
class PodcastioApplication

fun main(args: Array<String>) {
	runApplication<PodcastioApplication>(*args)
}
