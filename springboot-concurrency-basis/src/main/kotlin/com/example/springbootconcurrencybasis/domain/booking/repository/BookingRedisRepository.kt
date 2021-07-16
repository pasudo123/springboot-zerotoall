package com.example.springbootconcurrencybasis.domain.booking.repository

import com.example.springbootconcurrencybasis.domain.booking.model.Booking
import com.example.springbootconcurrencybasis.domain.config.redis.MyRedisConnectionInfo
import com.example.springbootconcurrencybasis.global.exception.ErrorCode
import com.example.springbootconcurrencybasis.global.exception.detail.SystemRuntimeException
import mu.KLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class BookingRedisRepository(
    @Qualifier(value = MyRedisConnectionInfo.BOOKING_TEMPLATE)
    private val redisTemplate: RedisTemplate<String, String>,
    @Qualifier(value = MyRedisConnectionInfo.BOOKING_TX_TEMPLATE)
    private val redisTxTemplate: RedisTemplate<String, String>,
) {

    companion object : KLogging()

    /**
     * incr 을 통한 동시성 처리
     */
    fun increaseBooking(booking: Booking, bookingCount: Int): Long {
        val key = generateKey(booking)
        redisTemplate.opsForValue().setIfAbsent(key, bookingCount.toString(), Duration.ofMinutes(5))
        return redisTemplate.opsForValue()
            .increment(key) ?: throw SystemRuntimeException(ErrorCode.SR100, "레디스 incr command 상에서 문제가 발생하였습니다.")
    }

    fun decreaseBooking(booking: Booking): Long? {
        val key = generateKey(booking)
        return redisTemplate.opsForValue()
            .decrement(key)
    }

    fun get(booking: Booking): Int {
        val key = generateKey(booking)
        return redisTemplate.opsForValue()
            .get(key)?.toInt() ?: 0
    }

    /**
     * watch 를 통한 동시성 처리 : watch 는 레디스 트랜잭션에서 동시성처리 (cas : check & set) 을 위해 사용한다.
     * https://redis.io/commands/setex
     * https://redis.io/topics/transactions
     */
    fun watchBookingForTest(booking: Booking): Boolean {
        val key = generateKey(booking)

        val result = redisTxTemplate.execute { redisConnection ->
            try {
                redisConnection.watch(key.toByteArray())
                val value = redisConnection.get(key.toByteArray())
                val assign = value?.toString()?.toInt() ?: 0

                redisConnection.multi()
                redisConnection.setEx(key.toByteArray(), 60 * 5, (assign + 1).toString().toByteArray())
                redisConnection.exec()
            } catch (exception: Exception) {
                logger.error { "redis watch exception : ${exception.message}" }
                redisConnection.discard()
                redisConnection.exec()
            }
        }

        logger.info { "redis exec result : $result" }
        return result?.first()?.toString()?.toBoolean() ?: false
    }

    private fun generateKey(booking: Booking): String {
        return "BOOKING-CONCERT:${booking.concert!!.id}:BOOKING-TICKET:${booking.ticket!!.id}"
    }
}