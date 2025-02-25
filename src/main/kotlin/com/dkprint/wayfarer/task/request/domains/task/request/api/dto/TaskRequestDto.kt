package com.dkprint.wayfarer.task.request.domains.task.request.api.dto

import com.dkprint.wayfarer.task.request.domains.details.dto.DetailsDto
import com.dkprint.wayfarer.task.request.domains.etc.dto.EtcDto
import com.dkprint.wayfarer.task.request.domains.fabric.mapping.dto.FabricDto
import com.dkprint.wayfarer.task.request.domains.lamination.dto.LaminationDto
import com.dkprint.wayfarer.task.request.domains.printing.dto.PrintingDto
import com.dkprint.wayfarer.task.request.domains.processing.dto.ProcessingDto
import com.dkprint.wayfarer.task.request.domains.slitting.dto.SlittingDto
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

    val printDesigns: List<MultipartFile>?,
)
