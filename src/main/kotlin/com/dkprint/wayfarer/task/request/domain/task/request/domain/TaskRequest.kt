package com.dkprint.wayfarer.task.request.domain.task.request.domain

import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.request.SaveRequest
import com.dkprint.wayfarer.task.request.common.model.BaseTimeEntity
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
    val id: Long = 0L,

    @Column(name = "code_id")
    var codeId: Long = 0L,

    @Column(name = "task_request_number")
    var taskRequestNumber: String = "",

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
) : BaseTimeEntity() {
    fun update(taskRequest: TaskRequest) {
        this.isPrint = taskRequest.isPrint
        this.isLamination = taskRequest.isLamination
        this.isProcessing = taskRequest.isProcessing
        this.isSlitting = taskRequest.isSlitting
        this.isEtc1 = taskRequest.isEtc1
        this.isEtc2 = taskRequest.isEtc2
        this.generalComment = taskRequest.generalComment
        this.fabricComment = taskRequest.fabricComment
        this.printingComment = taskRequest.printingComment
        this.laminationComment = taskRequest.laminationComment
        this.slittingComment = taskRequest.slittingComment
        this.etc1Comment = taskRequest.etc1Comment
        this.etc2Comment = taskRequest.etc2Comment
        this.processingComment = taskRequest.processingComment
    }

    companion object {
        fun from(saveRequest: SaveRequest): TaskRequest {
            return TaskRequest(
                isPrint = saveRequest.isPrint,
                isLamination = saveRequest.isLamination,
                isProcessing = saveRequest.isProcessing,
                isSlitting = saveRequest.isSlitting,
                isEtc1 = saveRequest.isEtc1,
                isEtc2 = saveRequest.isEtc2,

                generalComment = saveRequest.generalComment,
                fabricComment = saveRequest.fabricComment,
                printingComment = saveRequest.printingComment,
                laminationComment = saveRequest.laminationComment,
                slittingComment = saveRequest.slittingComment,
                etc1Comment = saveRequest.etc1Comment,
                etc2Comment = saveRequest.etc2Comment,
                processingComment = saveRequest.processingComment,
            )
        }
    }
}
