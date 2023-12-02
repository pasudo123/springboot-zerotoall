package com.example.springdocumenttraining.chapter03

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

class AnnotationBasedAutowiredSample03

class Coffee

@Service
class CoffeeService {
    @Autowired
    var coffee: Coffee? = null
}

