package com.dkprint.wayfarer.task.request.domain.task.request.api.dto.response

import com.dkprint.wayfarer.task.request.domain.details.dto.DetailsDto
import com.dkprint.wayfarer.task.request.domain.etc.dto.EtcDto
import com.dkprint.wayfarer.task.request.domain.fabric.mapping.dto.FabricDto
import com.dkprint.wayfarer.task.request.domain.lamination.dto.LaminationDto
import com.dkprint.wayfarer.task.request.domain.printing.dto.PrintingDto
import com.dkprint.wayfarer.task.request.domain.processing.dto.ProcessingDto

data class ReadResponse(
    val detailsDto: DetailsDto,
    val fabricDtos: List<FabricDto>,
    val printingDto: PrintingDto,
    val laminationDtos: List<LaminationDto>,
    val etc1Dto: EtcDto?,
    val etc2Dto: EtcDto?,
    val processingDto: ProcessingDto,
    val printingDesigns: List<String>,
)
