package com.dkprint.wayfarer.task.request.domain.task.request.dao

import com.dkprint.wayfarer.task.request.domain.copperplate.mapping.domain.QCopperplateMapping
import com.dkprint.wayfarer.task.request.domain.details.domain.QDetails
import com.dkprint.wayfarer.task.request.domain.fabric.mapping.domain.QFabricMapping
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestSearchRequest
import com.dkprint.wayfarer.task.request.domain.task.request.domain.DateType
import com.dkprint.wayfarer.task.request.domain.task.request.domain.QTaskRequest
import com.dkprint.wayfarer.task.request.domain.task.request.domain.SearchType
import com.dkprint.wayfarer.task.request.domain.task.request.domain.TaskRequest
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class TaskRequestRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory,
) : TaskRequestRepositoryCustom {
    private val taskRequest = QTaskRequest.taskRequest
    private val details = QDetails.details
    private val fabricMapping = QFabricMapping.fabricMapping
    private val copperplateMapping = QCopperplateMapping.copperplateMapping

    companion object {
        private const val WIDTH_INDEX: Int = 0
        private const val LENGTH_INDEX: Int = 1
        private const val HEIGHT_INDEX: Int = 2
        private const val MINIMUM_STANDARD_COUNT: Int = 2
        private const val PAGE_SIZE: Int = 20
    }

    override fun search(taskRequestSearchRequest: TaskRequestSearchRequest): Page<TaskRequest> {
        val pageable: Pageable = PageRequest.of(taskRequestSearchRequest.page, PAGE_SIZE)
        val builder = BooleanBuilder()

        if (isEmptySearch(taskRequestSearchRequest)) {
            return fetchAll(pageable)
        }

        addSearchTypeFilter(builder, taskRequestSearchRequest)
        addDateTypeFilter(builder, taskRequestSearchRequest)

        return createPage(builder, pageable)
    }

    private fun isEmptySearch(taskRequestSearchRequest: TaskRequestSearchRequest): Boolean {
        return taskRequestSearchRequest.keyword.isNullOrBlank() && taskRequestSearchRequest.dateType.isNullOrBlank()
    }

    private fun fetchAll(pageable: Pageable): Page<TaskRequest> {
        val result: List<TaskRequest> = queryFactory
            .selectFrom(taskRequest)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(taskRequest.id.asc())
            .fetch()

        val count: Long = result.size.toLong()

        return PageImpl(result, pageable, count)
    }

    private fun addSearchTypeFilter(builder: BooleanBuilder, taskRequestSearchRequest: TaskRequestSearchRequest) {
        val keyword: String = taskRequestSearchRequest.keyword
            ?: return

        when (taskRequestSearchRequest.searchType) {
            SearchType.PRODUCT_NAME.description -> {
                addProductNameFilter(builder, keyword)
            }

            SearchType.PRODUCT_STANDARD.description -> {
                addProductStandardFilter(builder, keyword)
            }

            SearchType.PRODUCT_CODE.description -> {
                addProductCodeFilter(builder, keyword)
            }

            SearchType.TASK_REQUEST_NUMBER.description -> {
                addTaskRequestNumberFilter(builder, keyword)
            }

            SearchType.VENDOR.description -> {
                addVendorFilter(builder, keyword)
            }
        }
    }

    private fun addProductNameFilter(builder: BooleanBuilder, keyword: String) {
        val selectedTaskRequestIds: List<Long> = queryFactory
            .select(details.taskRequestId)
            .from(details)
            .where(details.productName.contains(keyword))
            .fetch()

        builder.and(taskRequest.id.`in`(selectedTaskRequestIds))
    }

    private fun addProductStandardFilter(builder: BooleanBuilder, keyword: String) {
        val standardValues: List<Int> = keyword.split("*")
            .map { it.trim().toInt() }

        val condition: BooleanBuilder = createProductStandardCondition(standardValues)

        val selectedTaskRequestIds: List<Long> = queryFactory
            .select(details.taskRequestId)
            .from(details)
            .where(condition)
            .fetch()

        builder.and(taskRequest.id.`in`(selectedTaskRequestIds))
    }

    private fun createProductStandardCondition(standardValues: List<Int>): BooleanBuilder {
        return BooleanBuilder().apply {
            if (standardValues.size >= MINIMUM_STANDARD_COUNT) {
                and(details.standardWidth.eq(standardValues[WIDTH_INDEX]))
                and(details.standardLength.eq(standardValues[LENGTH_INDEX]))
            }

            if (standardValues.size > MINIMUM_STANDARD_COUNT) {
                and(details.standardHeight.eq(standardValues[HEIGHT_INDEX]))
            }
        }
    }

    private fun addProductCodeFilter(builder: BooleanBuilder, keyword: String) {
        val codeId: Long = 1L // codeSdk.findIdByCode(keyword)

        val selectedTaskRequestIds: List<Long> = queryFactory
            .select(taskRequest.id)
            .from(taskRequest)
            .where(taskRequest.codeId.eq(codeId))
            .fetch()

        builder.and(taskRequest.id.`in`(selectedTaskRequestIds))
    }

    private fun addTaskRequestNumberFilter(builder: BooleanBuilder, keyword: String) {
        val selectedTaskRequestIds: List<Long> = queryFactory
            .select(taskRequest.id)
            .from(taskRequest)
            .where(taskRequest.taskRequestNumber.eq(keyword))
            .fetch()

        builder.and(taskRequest.id.`in`(selectedTaskRequestIds))
    }

    private fun addVendorFilter(builder: BooleanBuilder, keyword: String) {
        val vendorId: Long = 1L // vendorSdk.findIdByName(keyword)

        val selectedTaskRequestIds: List<Long> = queryFactory
            .select(details.taskRequestId)
            .from(details)
            .where(details.vendorId.eq(vendorId))
            .fetch()

        builder.and(taskRequest.id.`in`(selectedTaskRequestIds))
    }

    private fun addDateTypeFilter(builder: BooleanBuilder, searchRequest: TaskRequestSearchRequest) {
        val dateType: String = searchRequest.dateType
            ?: return

        val startDate: LocalDate = searchRequest.startDate
            ?: return

        val endDate: LocalDate = searchRequest.endDate
            ?: return

        when (dateType) {
            DateType.ORDER.description -> {
                addOrderDateFilter(builder, startDate, endDate)
            }

            DateType.FABRIC.description -> {
                addFabricDateFilter(builder)
            }

            DateType.COPPERPLATE.description -> {
                addCopperplateDateFilter(builder)
            }
        }
    }

    private fun addOrderDateFilter(
        builder: BooleanBuilder,
        startDate: LocalDate,
        endDate: LocalDate,
    ) {
        val startDateTime: LocalDateTime = startDate.atStartOfDay()
        val endDateTime: LocalDateTime = endDate.atTime(LocalTime.MAX)

        val selectedTaskRequestIds: List<Long> = queryFactory
            .select(taskRequest.id)
            .from(taskRequest)
            .where(taskRequest.createdAt.between(startDateTime, endDateTime))
            .fetch()

        builder.and(taskRequest.id.`in`(selectedTaskRequestIds))
    }

    private fun addFabricDateFilter(builder: BooleanBuilder) {
        val expectedArrivalDates: List<LocalDate> = listOf(
            LocalDate.now(),
            LocalDate.now().plusDays(1),
            LocalDate.now().plusDays(2),
        ) // fabricSdk.findExpectedArrivalDateByDateRange(startDate, endDate)

        val latestExpectedArrivalDate = expectedArrivalDates.max()
        val latestFabricId = 1L // fabricSdk.findIdByExpectedArrivalDate(latestExpectedArrivalDate)

        val selectedTaskRequestIds: List<Long> = queryFactory
            .select(fabricMapping.taskRequestId)
            .from(fabricMapping)
            .where(fabricMapping.fabricId.eq(latestFabricId))
            .fetch()

        builder.and(taskRequest.id.`in`(selectedTaskRequestIds))
    }

    private fun addCopperplateDateFilter(builder: BooleanBuilder) {
        val expectedArrivalDates: List<LocalDate> = listOf(
            LocalDate.now(),
            LocalDate.now().plusDays(1),
            LocalDate.now().plusDays(2),
        ) // copperplateSdk.findExpectedArrivalDateByDateRange(startDate, endDate)

        val latestExpectedArrivalDate = expectedArrivalDates.max()
        val latestCopperplateId = 1L // copperplateSdk.findIdByExpectedArrivalDate(latestExpectedArrivalDate)

        val selectedTaskRequestIds = queryFactory
            .select(copperplateMapping.taskRequestId)
            .from(copperplateMapping)
            .where(copperplateMapping.copperplateId.eq(latestCopperplateId))
            .fetch()

        builder.and(taskRequest.id.`in`(selectedTaskRequestIds))
    }

    private fun createPage(
        builder: BooleanBuilder,
        pageable: Pageable,
    ): PageImpl<TaskRequest> {
        val result: List<TaskRequest> = fetchWithFilters(builder, pageable)
        val count: Long = result.size.toLong()

        return PageImpl(result, pageable, count)
    }

    private fun fetchWithFilters(builder: BooleanBuilder, pageable: Pageable): List<TaskRequest> {
        return queryFactory
            .selectFrom(taskRequest)
            .where(builder)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(taskRequest.id.asc())
            .fetch()
    }
}
