package com.example.springboottestcodebasis.archunit

import com.example.TestEnvironment
import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import com.tngtech.archunit.library.Architectures.layeredArchitecture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


@TestEnvironment
@DisplayName("ArchUnit Sample")
internal class ArchSampleTest {

    private lateinit var importedClasses: JavaClasses

    @BeforeEach
    fun init() {

    }

    @Test
    @DisplayName("레이어드 아키텍처의 controller -> service -> repository 의 단방향을 검사한다.")
    fun layeredArchTest() {

        // 역참조나 중간에 참조를 끼우면 안된다. : 기본 룰
        importedClasses = ClassFileImporter()
            .importPackages("com.example.springboottestcodebasis.archunit.layerd")

        val rule = layeredArchitecture()
            .layer("Controller").definedBy("..controller..")
            .layer("Service").definedBy("..service..")
            .layer("Repository").definedBy("..repository..")
            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
            .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service");

        rule.check(importedClasses)
    }

    @Test
    @DisplayName("util package -> layerd.service 패키지로 호출을 금지한다. : 역참조")
    fun backReferenceTest() {

        // AppUtilKt 파일에서 주석처리된 내용을 찾는다.
        importedClasses = ClassFileImporter()
            .importPackages("com.example.springboottestcodebasis.archunit")

        val rule = noClasses()
            .that().resideInAPackage("..util..")
            .should().accessClassesThat().resideInAPackage("..service..")

        rule.check(importedClasses)
    }
}