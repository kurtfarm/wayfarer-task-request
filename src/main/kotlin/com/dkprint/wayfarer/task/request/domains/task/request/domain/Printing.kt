package com.dkprint.wayfarer.task.request.domains.task.request.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "task_request_printing")
class Printing(
    @Id
    @Column(name = "task_request_id")
    var taskRequestId: Long,

    @Column(name = "number_of_inks")
    var numberOfInks: String,

    @Column(name = "standard_width")
    var standardWidth: Int,

    @Column(name = "standard_length")
    var standardLength: Int,

    @Column(name = "due_date")
    var dueDate: LocalDate,

    @Column(name = "printing_type")
    var printingType: String,

    @Column(name = "supervision_datetime")
    var supervisionDatetime: LocalDateTime,

    @Column(name = "printing_direction")
    var printingDirection: Int,

    @Column(name = "copperplate_id")
    var copperplateId: Long,

    @Column(name = "is_matte")
    var isMatte: Boolean,
)
