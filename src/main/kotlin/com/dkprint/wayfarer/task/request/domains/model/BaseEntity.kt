package com.dkprint.wayfarer.task.request.domains.model

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@MappedSuperclass
abstract class BaseEntity {
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private lateinit var createdAt: LocalDateTime

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private lateinit var updatedAt: LocalDateTime

    @Column(name = "is_deleted")
    private var isDeleted: Boolean = false
}
