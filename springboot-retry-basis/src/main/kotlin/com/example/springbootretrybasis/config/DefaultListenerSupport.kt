package com.example.springbootretrybasis.config

import org.slf4j.LoggerFactory
import org.springframework.retry.RetryCallback
import org.springframework.retry.RetryContext
import org.springframework.retry.listener.RetryListenerSupport

class DefaultListenerSupport : RetryListenerSupport() {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * (1-1) retryTemplate 메소드가 호출되는 시점에 호출
     * (2-1) @Retryable 메소드가 호출되는 시점에 호출
     */
    override fun <T, E : Throwable?> open(context: RetryContext?, callback: RetryCallback<T, E>?): Boolean {
        log.info("==> open")
        return super.open(context, callback)
    }

    /**
     * (1-2) retryTemplate 메소드 내에 에러가 발생하는 시점에 호출
     * (2-2) @Retryable 이 붙은 메소드에서 에러가 발생할 때 호출
     */
    override fun <T, E : Throwable?> onError(
        context: RetryContext?, callback: RetryCallback<T, E>?,
        throwable: Throwable?
    ) {
        log.info("==> onError")
        super.onError(context, callback, throwable)
    }

    /**
     * (1-3) retryTemplate 메소드가 최대 시도까지 완료한 시점에 호출 (성공/실패 여부 상관없이)
     * (2-3) @Retryable 메소드가 최대 시도(혹은 리커버리) 까지 완료한 시점에 호출 (성공/실패 여부 상관없이)
     */
    override fun <T, E : Throwable?> close(
        context: RetryContext?, callback: RetryCallback<T, E>?,
        throwable: Throwable?
    ) {
        log.info("==> close")
        super.close(context, callback, throwable)
    }
}