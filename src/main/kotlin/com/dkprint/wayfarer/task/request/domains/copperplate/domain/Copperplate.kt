package com.dkprint.wayfarer.task.request.domains.copperplate.domain

import com.dkprint.wayfarer.task.request.domains.common.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "material_copperplate")
class Copperplate(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0L,

    @Column(name = "copperplate_name")
    var copperplateName: String,

    @Column(name = "code_id")
    var codeId: Long = 0L,

    @Column(name = "vendor_id")
    var vendorId: Long = 0L,

    @Column(name = "fabric_width")
    var fabricWidth: Int,

    @Column(name = "chromaticity")
    var chromaticity: Int,

    @Column(name = "comment")
    var comment: String? = null,

    @Column(name = "copperplate_circumference")
    var location: String,

    @Column(name = "copperplate_length")
    var copperplateLength: String,

    @Column(name = "copperplate_image")
    var copperplateImage: String,

    @Column(name = "general_comment")
    var generalComment: String,
) : BaseTimeEntity()

