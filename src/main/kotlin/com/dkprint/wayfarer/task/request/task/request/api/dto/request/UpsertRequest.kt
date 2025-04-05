package com.dkprint.wayfarer.task.request.task.request.api.dto.request

import com.dkprint.wayfarer.task.request.copperplate.mapping.dto.FabricDto
import com.dkprint.wayfarer.task.request.details.dto.DetailsDto
import com.dkprint.wayfarer.task.request.etc.dto.EtcDto
import com.dkprint.wayfarer.task.request.lamination.dto.LaminationDto
import com.dkprint.wayfarer.task.request.printing.dto.PrintingDto
import com.dkprint.wayfarer.task.request.processing.dto.ProcessingDto
import com.dkprint.wayfarer.task.request.slitting.dto.SlittingDto

data class UpsertRequest(
    val isPrint: Boolean,
    val isLamination: Boolean,
    val isProcessing: Boolean,
    val isSlitting: Boolean,
    val isEtc1: Boolean,
    val isEtc2: Boolean,

    val generalComment: String?,
    val fabricComment: String?,
    val printingComment: String?,
    val laminationComment: String?,
    val slittingComment: String?,
    val etc1Comment: String?,
    val etc2Comment: String?,
    val processingComment: String?,

    val detailsDto: DetailsDto,
    val fabricDtos: List<FabricDto>,
    val printingDto: PrintingDto?,
    val laminationDtos: List<LaminationDto>?,
    val slittingDto: SlittingDto?,
    val etc1Dto: EtcDto?,
    val etc2Dto: EtcDto?,
    val processingDto: ProcessingDto?,
)
