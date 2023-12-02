package com.example.springdocumenttraining.chapter03

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

class AnnotationBasedAutowiredSample02

interface Milk

@Component
class ChocoMilk : Milk

@Component
class OrangeMilk : Milk

@Component
class BasicMilk : Milk

@Component
class MilkStorage(
    @Autowired
    val milkGroup: Map<String, Milk>
)