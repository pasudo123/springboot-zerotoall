package com.example.springdocumenttraining.chapter03

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

class AnnotationBasedAutowiredSample

@Component
class MovieRecommender {

    private lateinit var catalog: MovieCatalog
    private lateinit var preference: MoviePreference

    @Autowired
    fun prepare(
        catalog: MovieCatalog,
        preference: MoviePreference
    ) {
        // println("autowired prepare")
        this.catalog = catalog
        this.preference = preference
    }
}

@Component
class MovieCatalog

@Component
class MoviePreference

