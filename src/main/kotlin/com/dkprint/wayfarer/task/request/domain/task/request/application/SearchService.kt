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
import org.springframework.stereotype.Service

@Service
class SearchService(
    private val taskRequestService: TaskRequestService,
    private val detailsService: DetailsService,
    private val fabricMappingService: FabricMappingService,
    private val copperplateMappingService: CopperplateMappingService,
) {
    fun search(searchRequest: SearchRequest): List<TaskRequest> {
        val filteredByKeyword: List<TaskRequest> = filterByKeyword(searchRequest)
        val filteredByDate: List<TaskRequest> = filterByDate(searchRequest, filteredByKeyword)
        return intersect(filteredByKeyword, filteredByDate)
    }

    private fun filterByKeyword(searchRequest: SearchRequest): List<TaskRequest> {
        return when (searchRequest.searchType) {
            SearchType.PRODUCT_NAME.description -> {
                val taskRequestIds: List<Long> =
                    detailsService.findByProductName(searchRequest.lastId, searchRequest.keyword!!)
                        .map { it.taskRequestId }
                        .toList()

                taskRequestService.findByIdIn(searchRequest.lastId, taskRequestIds)
            }

            SearchType.PRODUCT_STANDARD.description -> {
                val taskRequestIds: List<Long> =
                    detailsService.findByProductStandard(searchRequest.lastId, searchRequest.keyword!!)
                        .map { it.taskRequestId }
                        .toList()

                taskRequestService.findByIdIn(searchRequest.lastId, taskRequestIds)
            }

            SearchType.PRODUCT_CODE.description -> {
                val codeId: Long = 1L // taskRequestSdk.findIdByProductCode(keyword, pageable)
                taskRequestService.findByCodeId(searchRequest.lastId, codeId)
            }

            SearchType.TASK_REQUEST_NUMBER.description -> {
                taskRequestService.findByTaskRequestNumber(searchRequest.lastId, searchRequest.keyword!!)
            }

            SearchType.VENDOR.description -> {
                val vendorId: Long = 1L // detailsSdk.findIdByVendorName(keyword, pageable)

                val taskRequestIds: List<Long> = detailsService.findByVendorId(searchRequest.lastId, vendorId)
                    .map { it.taskRequestId }
                    .toList()

                taskRequestService.findByIdIn(searchRequest.lastId, taskRequestIds)
            }

            else -> {
                listOf()
            }
        }
    }

    private fun filterByDate(searchRequest: SearchRequest, filteredKeyword: List<TaskRequest>): List<TaskRequest> {
        if (isDateNull(searchRequest.startDate, searchRequest.endDate)) {
            return filteredKeyword
        }

        val start: LocalDateTime = searchRequest.startDate!!.atStartOfDay()
        val end: LocalDateTime = searchRequest.endDate!!.atTime(LocalTime.MAX)

        return when (searchRequest.dateType) {
            DateType.ORDER.description -> {
                val taskRequestIds: List<Long> = detailsService.findByOrderDateBetween(
                    searchRequest.lastId,
                    searchRequest.startDate,
                    searchRequest.endDate,
                ).map { it.taskRequestId }
                    .toList()

                return taskRequestService.findByIdIn(searchRequest.lastId, taskRequestIds)
            }

            DateType.FABRIC.description -> {
                val fabricIds: List<Long> = listOf(1L) // fabricSdk.findIdByCreatedAtBetween(start, end, pageable)

                val taskRequestIds: List<Long> = fabricMappingService.findByFabricIdIn(fabricIds)
                    .map { it.taskRequestId }
                    .toList()

                taskRequestService.findByIdIn(searchRequest.lastId, taskRequestIds)
            }

            DateType.COPPERPLATE.description -> {
                val copperplateIds: List<Long> =
                    listOf(1L) // copperplateSdk.findIdByCreatedAtBetween(start, end, pageable)

                val taskRequestIds: List<Long> = copperplateMappingService.findByCopperplateIdIn(copperplateIds)
                    .map { it.taskRequestId }
                    .toList()

                taskRequestService.findByIdIn(searchRequest.lastId, taskRequestIds)
            }

            else -> {
                listOf()
            }
        }
    }

    private fun isDateNull(startDate: LocalDate?, endDate: LocalDate?): Boolean {
        return startDate == null && endDate == null
    }

    private fun intersect(
        filteredByKeyword: List<TaskRequest>, filteredDate: List<TaskRequest>,
    ): List<TaskRequest> {
        return if (filteredByKeyword.isEmpty()) {
            filteredDate
        } else {
            filteredByKeyword.intersect(filteredDate.toSet())
                .sortedBy { it.id }
                .toList()
        }
    }
}
