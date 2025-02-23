package com.dkprint.wayfarer.task.request.domains.task.request.api.dto

import org.springframework.web.multipart.MultipartFile

data class TaskRequestDto(
    val isPrint: Boolean,
    val isLamination: Boolean,
    val isProcessing: Boolean,
    val isSlitting: Boolean,
    val isEtc1: Boolean,
    val isEtc2: Boolean,

    val generalComment: String,
    val fabricComment: String,
    val printingComment: String,
    val laminationComment: String,
    val slittingComment: String,
    val etc1Comment: String,
    val etc2Comment: String,
    val processingComment: String,

    val detailsDto: DetailsDto,
    val fabricDtos: List<FabricDto>,
    val printingDto: PrintingDto?,
    val laminationDtos: List<LaminationDto>?,
    val slittingDto: SlittingDto?,
    val etc1Dto: EtcDto?,
    val etc2Dto: EtcDto?,
    val processingDto: ProcessingDto?,

    val printDesigns: List<MultipartFile>
)
