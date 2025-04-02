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
        private const val PAGE_SIZE = 20
    }

    fun search(searchRequest: SearchRequest): Page<TaskRequest> {
        val pageable: Pageable = PageRequest.of(searchRequest.page, PAGE_SIZE)
        val filteredByKeyword: Page<TaskRequest> = filterByKeyword(searchRequest, pageable)
        val filteredByDate: Page<TaskRequest> = filterByDate(searchRequest, filteredByKeyword, pageable)
        return intersect(filteredByKeyword, filteredByDate, pageable)
    }

    private fun filterByKeyword(searchRequest: SearchRequest, pageable: Pageable): Page<TaskRequest> {
        return when (searchRequest.searchType) {
            SearchType.PRODUCT_NAME.description -> {
                val taskRequestIds: List<Long> =
                    detailsService.findByProductName(searchRequest.page, searchRequest.keyword!!)
                        .map { it.taskRequestId }
                        .toList()

                taskRequestService.findByIdIn(taskRequestIds, pageable)
            }

            SearchType.PRODUCT_STANDARD.description -> {
                val taskRequestIds: List<Long> =
                    detailsService.findByProductStandard(searchRequest.page, searchRequest.keyword!!)
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

                val taskRequestIds: List<Long> = detailsService.findByVendorId(vendorId)
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
        filteredKeyword: Page<TaskRequest>,
        pageable: Pageable,
    ): Page<TaskRequest> {
        if (isDateNull(searchRequest.startDate, searchRequest.endDate)) {
            return filteredKeyword
        }

        val start: LocalDateTime = searchRequest.startDate!!.atStartOfDay()
        val end: LocalDateTime = searchRequest.endDate!!.atTime(LocalTime.MAX)

        return when (searchRequest.dateType) {
            DateType.ORDER.description -> {
                val taskRequestIds: List<Long> = detailsService.findByOrderDateBetween(
                    searchRequest.startDate,
                    searchRequest.endDate,
                ).map { it.taskRequestId }
                    .toList()

                return taskRequestService.findByIdIn(taskRequestIds, pageable)
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
    }

    private fun isDateNull(startDate: LocalDate?, endDate: LocalDate?): Boolean {
        return startDate == null && endDate == null
    }

    private fun intersect(
        filteredByKeyword: Page<TaskRequest>,
        filteredDate: Page<TaskRequest>,
        pageable: Pageable,
    ): Page<TaskRequest> {
        return if (filteredByKeyword.isEmpty) {
            filteredDate
        } else {
            val intersection: List<TaskRequest> = filteredByKeyword.intersect(filteredDate)
                .sortedBy { it.id }

            PageImpl(intersection, pageable, pageable.pageSize.toLong())
        }
    }
}
