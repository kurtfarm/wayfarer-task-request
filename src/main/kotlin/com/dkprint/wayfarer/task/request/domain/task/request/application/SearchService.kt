package com.dkprint.wayfarer.task.request.domain.task.request.application

import com.dkprint.wayfarer.task.request.domain.copperplate.mapping.application.CopperplateMappingService
import com.dkprint.wayfarer.task.request.domain.details.application.DetailsService
import com.dkprint.wayfarer.task.request.domain.fabric.mapping.application.FabricMappingService
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.request.SearchRequest
import com.dkprint.wayfarer.task.request.domain.task.request.domain.DateType
import com.dkprint.wayfarer.task.request.domain.task.request.domain.SearchType
import com.dkprint.wayfarer.task.request.domain.task.request.domain.TaskRequest
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SearchService(
    private val taskRequestService: TaskRequestService,
    private val detailsService: DetailsService,
    private val fabricMappingService: FabricMappingService,
    private val copperplateMappingService: CopperplateMappingService,
) {
    companion object {
        private const val PAGE_SIZE = 20
    }

    fun search(searchRequest: SearchRequest): Page<TaskRequest> {
        val pageable: Pageable = PageRequest.of(searchRequest.page, PAGE_SIZE)

        val filteredByKeyword: List<Long>? =
            filteringByKeyword(searchRequest.searchType, searchRequest.keyword)

        val filteredByDate: List<Long>? =
            filteringByDate(searchRequest.dateType, searchRequest.startDate, searchRequest.endDate)

        return intersect(filteredByKeyword, filteredByDate, pageable)
    }

    private fun filteringByKeyword(
        searchType: String?,
        keyword: String?,
    ): List<Long>? {
        if (isKeywordNull(searchType, keyword)) {
            return null
        }

        return when (searchType) {
            SearchType.PRODUCT_NAME.description -> {
                detailsService.findByProductName(keyword!!)
                    .map { it.taskRequestId }
            }

            SearchType.PRODUCT_STANDARD.description -> {
                detailsService.findByProductStandard(keyword!!)
                    .map { it.taskRequestId }
            }

            SearchType.PRODUCT_CODE.description -> {
                val codeId: Long = 1L // TODO: taskRequestSdk.findIdByProductCode(keyword, pageable)
                taskRequestService.findByCodeId(codeId)
                    .map { it.id }
            }

            SearchType.TASK_REQUEST_NUMBER.description -> {
                taskRequestService.findByTaskRequestNumber(keyword!!)
                    .map { it.id }
            }

            SearchType.VENDOR.description -> {
                val vendorId: Long = 1L // TODO: detailsSdk.findIdByVendorName(keyword, pageable)
                detailsService.findByVendorId(vendorId)
                    .map { it.taskRequestId }
            }

            else -> {
                emptyList()
            }
        }
    }

    private fun isKeywordNull(searchType: String?, keyword: String?): Boolean {
        return searchType == null || keyword == null
    }

    private fun filteringByDate(
        dateType: String?,
        startDate: LocalDate?,
        endDate: LocalDate?,
    ): List<Long>? {
        if (isDateNull(dateType, startDate, endDate)) {
            return null
        }

        val start: LocalDateTime = startDate!!.atStartOfDay()
        val end: LocalDateTime = endDate!!.atTime(LocalTime.MAX)

        return when (dateType) {
            DateType.ORDER.description -> {
                detailsService.findByOrderDateBetween(startDate, endDate)
                    .map { it.taskRequestId }
            }

            DateType.FABRIC.description -> {
                val fabricIds: List<Long> = listOf(1L) // TODO: fabricSdk.findIdByCreatedAtBetween(start, end, pageable)
                fabricMappingService.findByFabricIdIn(fabricIds)
                    .map { it.taskRequestId }
            }

            DateType.COPPERPLATE.description -> {
                val copperplateIds: List<Long> =
                    listOf(1L) // TODO: copperplateSdk.findIdByCreatedAtBetween(start, end, pageable)
                copperplateMappingService.findByCopperplateIdIn(copperplateIds)
                    .map { it.taskRequestId }
            }

            else -> {
                emptyList()
            }
        }
    }

    private fun isDateNull(
        dateType: String?,
        startDate: LocalDate?,
        endDate: LocalDate?,
    ): Boolean {
        return dateType == null || startDate == null || endDate == null
    }

    private fun intersect(
        filteredByKeyword: List<Long>?,
        filteredByDate: List<Long>?,
        pageable: Pageable,
    ): Page<TaskRequest> {
        return when {
            filteredByKeyword == null && filteredByDate == null -> {
                Page.empty()
            }

            filteredByKeyword != null && filteredByDate == null -> {
                taskRequestService.findByIdIn(filteredByKeyword, pageable)
            }

            filteredByKeyword == null && filteredByDate != null -> {
                taskRequestService.findByIdIn(filteredByDate, pageable)
            }

            else -> {
                val intersection: List<Long> = filteredByKeyword!!.intersect(filteredByDate!!.toSet())
                    .toList()
                taskRequestService.findByIdIn(intersection, pageable)
            }
        }
    }
}
