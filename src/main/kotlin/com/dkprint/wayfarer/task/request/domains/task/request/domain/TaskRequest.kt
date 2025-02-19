package com.dkprint.wayfarer.task.request.domains.task.request.domain

import com.dkprint.wayfarer.task.request.domains.model.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "task_request")
class TaskRequest(
    @Column(name = "is_print")
    private var isPrint: Boolean,

    @Column(name = "is_lamination")
    private var isLamination: Boolean,

    @Column(name = "is_processing")
    private var isProcessing: Boolean,

    @Column(name = "is_slitting")
    private var isSlitting: Boolean,

    @Column(name = "is_etc1")
    private var isEtc1: Boolean,

    @Column(name = "is_etc2")
    private var isEtc2: Boolean,

    @Column(name = "comment")
    private var comment: String,

    @Column(name = "fabric_comment")
    private var fabricComment: String,

    @Column(name = "printing_comment")
    private var printingComment: String,

    @Column(name = "lamination_comment")
    private var laminationComment: String,

    @Column(name = "slitting_comment")
    private var slittingComment: String,

    @Column(name = "etc1_comment")
    private var etc1Comment: String,

    @Column(name = "etc2_comment")
    private var etc2Comment: String,

    @Column(name = "processing_comment")
    private var processingComment: String,

    @Column(name = "print_design")
    private var printDesign: String,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long = 0L
}
