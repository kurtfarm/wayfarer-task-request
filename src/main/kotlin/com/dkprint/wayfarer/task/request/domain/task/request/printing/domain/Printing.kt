package com.dkprint.wayfarer.task.request.domain.task.request.printing.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "task_request_printing")
class Printing(
    @Column(name = "number_of_inks")
    private var numberOfInks: String,

    @Column(name = "width")
    private var width: Int,

    @Column(name = "height")
    private var height: Int,

    @Column(name = "due_date")
    private var dueDate: LocalDate,

    @Column(name = "printing_type")
    private var printingType: String,

    @Column(name = "supervision_date")
    private var supervisionDate: LocalDateTime,

    @Column(name = "printing_direction")
    private var printingDirection: Int
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long = 0L

    init {
        validate()
    }

    private fun validate() {
        require(width > 0) {
            "너비는 0보다 커야 합니다."
        }
        require(height > 0) {
            "높이는 0보다 커야 합니다."
        }
        require(dueDate.isAfter(LocalDate.now())) {
            "납기일은 현재 날짜 이후여야 합니다."
        }
    }
}
