package com.dkprint.wayfarer.task.request.domain.details.application

import com.dkprint.wayfarer.task.request.domain.details.dao.DetailsRepository
import com.dkprint.wayfarer.task.request.domain.details.domain.Details
import com.dkprint.wayfarer.task.request.domain.details.dto.DetailsDto
import java.time.LocalDate
import kotlin.jvm.optionals.getOrNull
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class DetailsService(
    private val detailsRepository: DetailsRepository,
    // private val vendorSdk: VendorSdk,
) {
    companion object {
        private const val WIDTH_INDEX: Int = 0
        private const val LENGTH_INDEX: Int = 1
        private const val HEIGHT_INDEX: Int = 2
    }

    fun create(taskRequestId: Long, detailsDto: DetailsDto) {
        val vendorId: Long = 1L // vendorSdk.findIdByName(detailsDto.vendorName)
        val details: Details = Details.of(
            taskRequestId,
            vendorId,
            detailsDto,
        )
        detailsRepository.save(details)
    }

    fun delete(taskRequestId: Long) {
        detailsRepository.deleteById(taskRequestId)
    }

    fun find(taskRequestId: Long): Details {
        return detailsRepository.findById(taskRequestId).getOrNull()
            ?: throw IllegalArgumentException("작업 의뢰서 id: $taskRequestId 조회 오류")
    }

    fun findByProductName(productName: String): List<Details> {
        return detailsRepository.findByProductNameContainingIgnoreCase(productName)
    }

    fun findByProductStandard(keyword: String): List<Details> {
        val standardValues: List<Int> = keyword.split("*")
            .map { it.trim().toInt() }

        return detailsRepository.findByStandardWidthAndStandardLengthAndStandardHeight(
            standardValues[WIDTH_INDEX],
            standardValues[LENGTH_INDEX],
            standardValues[HEIGHT_INDEX],
        )
    }

    fun findByVendorId(vendorId: Long): List<Details> {
        return detailsRepository.findByVendorId(vendorId)
    }

    fun findByOrderDateBetween(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<Details> {
        return detailsRepository.findByOrderDateBetween(startDate, endDate)
    }
}
