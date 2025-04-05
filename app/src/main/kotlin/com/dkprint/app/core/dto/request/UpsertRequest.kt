package com.dkprint.app.core.dto.request

import com.dkprint.app.copperplate.mapping.dto.FabricDto
import com.dkprint.app.details.dto.DetailsDto
import com.dkprint.app.etc.dto.EtcDto
import com.dkprint.app.lamination.dto.LaminationDto
import com.dkprint.app.printing.dto.PrintingDto
import com.dkprint.app.processing.dto.ProcessingDto
import com.dkprint.app.slitting.dto.SlittingDto

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
