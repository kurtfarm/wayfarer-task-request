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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0L,

    @Column(name = "is_print")
    var isPrint: Boolean = false,

    @Column(name = "is_lamination")
    var isLamination: Boolean = false,

    @Column(name = "is_processing")
    var isProcessing: Boolean = false,

    @Column(name = "is_slitting")
    var isSlitting: Boolean = false,

    @Column(name = "is_etc1")
    var isEtc1: Boolean = false,

    @Column(name = "is_etc2")
    var isEtc2: Boolean = false,

    @Column(name = "general_comment")
    var generalComment: String? = null,

    @Column(name = "fabric_comment")
    var fabricComment: String? = null,

    @Column(name = "printing_comment")
    var printingComment: String? = null,

    @Column(name = "lamination_comment")
    var laminationComment: String? = null,

    @Column(name = "slitting_comment")
    var slittingComment: String? = null,

    @Column(name = "etc1_comment")
    var etc1Comment: String? = null,

    @Column(name = "etc2_comment")
    var etc2Comment: String? = null,

    @Column(name = "processing_comment")
    var processingComment: String? = null,
) : BaseEntity()
