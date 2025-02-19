package com.dkprint.wayfarer.task.request.domains.task.request.processing.domain

import com.dkprint.wayfarer.task.request.domains.task.request.domain.TaskRequest
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "task_request_processing")
class Processing(
    @Id
    @OneToOne
    @JoinColumn(name = "task_request_id")
    private var taskRequest: TaskRequest,

    @Column(name = "vendor_id")
    private var vendorId: Long,

    @Column(name = "task_type")
    private var taskType: String,

    @Column(name = "quantity")
    private var quantity: Int,

    @Column(name = "side")
    private var side: String,

    @Column(name = "due_date")
    private var dueDate: LocalDate,

    @Column(name = "perforation")
    private var perforation: String,

    @Column(name = "upper_part")
    private var upperPart: String,

    @Column(name = "notch")
    private var notch: String,

    @Column(name = "plain_box")
    private var plain_box: String,

    @Column(name = "round")
    private var round: String,

    @Column(name = "zipper")
    private var zipper: String,

    @Column(name = "stand")
    private var stand: String,

    @Column(name = "opening_direction")
    private var opening_direction: String,
) {

}
