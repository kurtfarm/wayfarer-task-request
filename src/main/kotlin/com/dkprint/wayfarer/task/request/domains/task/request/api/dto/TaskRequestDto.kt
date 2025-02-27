package com.dkprint.wayfarer.task.request.domains.task.request.api.dto

import com.dkprint.wayfarer.task.request.domains.details.dto.DetailsDto
import com.dkprint.wayfarer.task.request.domains.etc.dto.EtcDto
import com.dkprint.wayfarer.task.request.domains.fabric.mapping.dto.FabricDto
import com.dkprint.wayfarer.task.request.domains.lamination.dto.LaminationDto
import com.dkprint.wayfarer.task.request.domains.printing.dto.PrintingDto
import com.dkprint.wayfarer.task.request.domains.processing.dto.ProcessingDto
import com.dkprint.wayfarer.task.request.domains.slitting.dto.SlittingDto
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.web.multipart.MultipartFile

data class TaskRequestDto(
    @field:NotNull val isPrint: Boolean,
    @field:NotNull val isLamination: Boolean,
    @field:NotNull val isProcessing: Boolean,
    @field:NotNull val isSlitting: Boolean,
    @field:NotNull val isEtc1: Boolean,
    @field:NotNull val isEtc2: Boolean,

    val generalComment: String?,
    val fabricComment: String?,
    val printingComment: String?,
    val laminationComment: String?,
    val slittingComment: String?,
    val etc1Comment: String?,
    val etc2Comment: String?,
    val processingComment: String?,

    @field:NotNull val detailsDto: DetailsDto,
    @field:NotNull val fabricDtos: List<FabricDto>,
    val printingDto: PrintingDto?,
    val laminationDtos: List<LaminationDto>?,
    val slittingDto: SlittingDto?,
    val etc1Dto: EtcDto?,
    val etc2Dto: EtcDto?,
    val processingDto: ProcessingDto?,

    @field:Size(max = 5) var printDesigns: List<MultipartFile>?,
)
