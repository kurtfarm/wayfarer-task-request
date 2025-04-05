package com.dkprint.wayfarer.task.request.task.request.api.dto.response

import com.dkprint.wayfarer.task.request.copperplate.mapping.dto.FabricDto
import com.dkprint.wayfarer.task.request.details.dto.DetailsDto
import com.dkprint.wayfarer.task.request.etc.dto.EtcDto
import com.dkprint.wayfarer.task.request.lamination.dto.LaminationDto
import com.dkprint.wayfarer.task.request.printing.dto.PrintingDto
import com.dkprint.wayfarer.task.request.processing.dto.ProcessingDto

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
