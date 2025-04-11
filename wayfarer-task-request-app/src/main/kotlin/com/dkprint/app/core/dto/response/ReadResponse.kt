package com.dkprint.app.core.dto.response

import com.dkprint.app.core.dto.CacheableDto
import com.dkprint.app.details.dto.DetailsDto
import com.dkprint.app.etc.dto.EtcDto
import com.dkprint.app.fabric.mapping.dto.FabricDto
import com.dkprint.app.lamination.dto.LaminationDto
import com.dkprint.app.printing.dto.PrintingDto
import com.dkprint.app.processing.dto.ProcessingDto

data class ReadResponse(
    val detailsDto: DetailsDto,
    val fabricDtos: List<FabricDto>,
    val printingDto: PrintingDto,
    val laminationDtos: List<LaminationDto>,
    val etc1Dto: EtcDto?,
    val etc2Dto: EtcDto?,
    val processingDto: ProcessingDto,
    val printingDesigns: List<String>,
) : CacheableDto
