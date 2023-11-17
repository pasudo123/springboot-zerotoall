package com.example.springdocumenttraining.chapter02

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils

@Component
class Chapter02Runner(
    private val singleTonSampleBean1th: SingleTonSampleBean1th,
    private val singleTonSampleBean2th: SingleTonSampleBean2th
): ApplicationRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(args: ApplicationArguments?) {
        log.info("args runner")
        log.info("(1) prototype : ${ObjectUtils.getIdentityHexString(singleTonSampleBean1th.createProtoType())}")
        log.info("(2) prototype : ${ObjectUtils.getIdentityHexString(singleTonSampleBean1th.createProtoType())}")
        log.info("(3) prototype : ${ObjectUtils.getIdentityHexString(singleTonSampleBean1th.createProtoType())}")

        log.info("--")
        log.info("(1) prototype : ${ObjectUtils.getIdentityHexString(singleTonSampleBean2th.createProtoType())}")
        log.info("(2) prototype : ${ObjectUtils.getIdentityHexString(singleTonSampleBean2th.createProtoType())}")
        log.info("(3) prototype : ${ObjectUtils.getIdentityHexString(singleTonSampleBean2th.createProtoType())}")
    }
}