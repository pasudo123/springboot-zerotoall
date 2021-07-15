package com.example.springbootconcurrencybasis.domain.concert.model

import com.example.springbootconcurrencybasis.domain.BaseEntity
import com.example.springbootconcurrencybasis.domain.concert.api.dto.ConcertCreateDto
import com.example.springbootconcurrencybasis.domain.ticket.model.Ticket
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonManagedReference
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "concert")
class Concert private constructor (
    @Column(name = "name", columnDefinition = "VARCHAR(255)", nullable = false)
    val name: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "performance_date", columnDefinition = "DATETIME comment '공연일시'", nullable = false)
    val performanceDate: LocalDateTime,

    @Column(name = "viewing_time", columnDefinition = "BIGINT comment '관림시간(분단위)'", nullable = false)
    val viewingTime: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "grade", columnDefinition = "VARCHAR(50)", nullable = false)
    val grade: Grade
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @JsonManagedReference
    @OneToMany(mappedBy = "concert", fetch = FetchType.LAZY, targetEntity = Ticket::class)
    val tickets: MutableList<Ticket> = mutableListOf()

    enum class Grade(desc: String) {
        G_RATED("전체관람가"),
        PG_12("12세 이상"),
        PG_15("15세 이상"),
        X_RATED("18세 이상")
    }

    fun addTicket(ticket: Ticket) {
        tickets.removeIf { currentTicket -> currentTicket.id == ticket.id }
        tickets.add(ticket)
        ticket.set(this)
    }

    companion object {
        fun from(concertCreateDto: ConcertCreateDto): Concert {
            return concertCreateDto.run {
                   Concert(
                       name = this.name,
                       performanceDate = this.performanceDate,
                       viewingTime = this.viewingTime,
                       grade = this.grade
                   )
            }
        }
    }
}