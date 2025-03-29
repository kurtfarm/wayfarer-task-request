package com.dkprint.wayfarer.task.request.domain.task.request.application

import com.dkprint.wayfarer.task.request.domain.copperplate.mapping.application.CopperplateMappingService
import com.dkprint.wayfarer.task.request.domain.details.application.DetailsService
import com.dkprint.wayfarer.task.request.domain.fabric.mapping.application.FabricMappingService
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.request.SearchRequest
import com.dkprint.wayfarer.task.request.domain.task.request.domain.DateType
import com.dkprint.wayfarer.task.request.domain.task.request.domain.SearchType
import com.dkprint.wayfarer.task.request.domain.task.request.domain.TaskRequest
import java.time.LocalDateTime
import java.time.LocalTime
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
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
        private const val PAGE_SIZE: Int = 20
    }

    fun search(searchRequest: SearchRequest): Page<TaskRequest> {
        val pageable: Pageable = PageRequest.of(searchRequest.page, PAGE_SIZE)

        val filteredResultsByKeyword: Page<TaskRequest> = filterByKeyword(searchRequest, pageable)
        val filteredResultsByDate: Page<TaskRequest> = filterByDate(searchRequest, pageable, filteredResultsByKeyword)

        return combineResults(filteredResultsByKeyword, filteredResultsByDate, pageable)
    }

    private fun filterByKeyword(searchRequest: SearchRequest, pageable: Pageable): Page<TaskRequest> {
        return when (searchRequest.searchType) {
            SearchType.PRODUCT_NAME.description -> {
                val taskRequestIds: List<Long> =
                    detailsService.findByProductName(searchRequest.keyword!!, pageable)
                        .map { it.taskRequestId }
                        .toList()

                taskRequestService.findByIdIn(taskRequestIds, pageable)
            }

            SearchType.PRODUCT_STANDARD.description -> {
                val taskRequestIds: List<Long> = detailsService.findByProductStandard(searchRequest.keyword!!, pageable)
                    .map { it.taskRequestId }
                    .toList()

                taskRequestService.findByIdIn(taskRequestIds, pageable)
            }

            SearchType.PRODUCT_CODE.description -> {
                val codeId: Long = 1L // taskRequestSdk.findIdByProductCode(keyword, pageable)
                taskRequestService.findByCodeId(codeId, pageable)
            }

            SearchType.TASK_REQUEST_NUMBER.description -> {
                taskRequestService.findByTaskRequestNumber(searchRequest.keyword!!, pageable)
            }

            SearchType.VENDOR.description -> {
                val vendorId: Long = 1L // detailsSdk.findIdByVendorName(keyword, pageable)

                val taskRequestIds: List<Long> = detailsService.findByVendorId(vendorId, pageable)
                    .map { it.taskRequestId }
                    .toList()

                taskRequestService.findByIdIn(taskRequestIds, pageable)
            }

            else -> {
                Page.empty()
            }
        }
    }

    private fun filterByDate(
        searchRequest: SearchRequest,
        pageable: Pageable,
        filteredResultsByKeyword: Page<TaskRequest>,
    ): Page<TaskRequest> {
        if (searchRequest.startDate == null && searchRequest.endDate == null) {
            return filteredResultsByKeyword
        }

        val start: LocalDateTime = searchRequest.startDate!!.atStartOfDay()
        val end: LocalDateTime = searchRequest.endDate!!.atTime(LocalTime.MAX)

        val filteredResultsByDate = when (searchRequest.dateType) {
            DateType.ORDER.description -> {
                taskRequestService.findByCreatedAtBetween(start, end, pageable)
            }

            DateType.FABRIC.description -> {
                val fabricIds: List<Long> = listOf(1L) // fabricSdk.findIdByCreatedAtBetween(start, end, pageable)

                val taskRequestIds: List<Long> = fabricMappingService.findByFabricIdIn(fabricIds)
                    .map { it.taskRequestId }
                    .toList()

                taskRequestService.findByIdIn(taskRequestIds, pageable)
            }

            DateType.COPPERPLATE.description -> {
                val copperplateIds: List<Long> =
                    listOf(1L) // copperplateSdk.findIdByCreatedAtBetween(start, end, pageable)

                val taskRequestIds: List<Long> = copperplateMappingService.findByCopperplateIdIn(copperplateIds)
                    .map { it.taskRequestId }
                    .toList()

                taskRequestService.findByIdIn(taskRequestIds, pageable)
            }

            else -> {
                Page.empty()
            }
        }

        return if (filteredResultsByKeyword.isEmpty) {
            filteredResultsByDate
        } else {
            filteredResultsByKeyword
        }
    }

    private fun combineResults(
        filteredResultsByKeyword: Page<TaskRequest>,
        filteredResultsByDate: Page<TaskRequest>,
        pageable: Pageable,
    ): Page<TaskRequest> {
        if (filteredResultsByKeyword.isEmpty) {
            return filteredResultsByDate
        }

        if (filteredResultsByDate.isEmpty) {
            return filteredResultsByKeyword
        }

        val combined: List<TaskRequest> = filteredResultsByKeyword.content
            .intersect(filteredResultsByDate)
            .toList()

        return PageImpl(combined, pageable, combined.size.toLong())
    }
}
