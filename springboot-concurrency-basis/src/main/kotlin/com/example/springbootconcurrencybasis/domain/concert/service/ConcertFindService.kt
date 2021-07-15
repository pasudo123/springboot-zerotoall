package com.example.springbootconcurrencybasis.domain.concert.service

import com.example.springbootconcurrencybasis.domain.concert.model.Concert
import com.example.springbootconcurrencybasis.domain.concert.repository.ConcertCustomRepository
import com.example.springbootconcurrencybasis.domain.concert.repository.ConcertRepository
import com.example.springbootconcurrencybasis.global.exception.ErrorCode
import com.example.springbootconcurrencybasis.global.exception.detail.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ConcertFindService(
    private val concertRepository: ConcertRepository,
    private val concertCustomRepository: ConcertCustomRepository
) {

    fun findOneByIdOrThrow(id: Long) : Concert {
        return concertRepository.findByIdOrNull(id)
            ?: throw EntityNotFoundException(ErrorCode.E100, "Concert[$id] 를 찾을 수 없습니다.")
    }
}