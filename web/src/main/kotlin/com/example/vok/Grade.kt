package com.example.vok

import com.github.vokorm.KEntity
import com.gitlab.mvysny.jdbiorm.Dao
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class Grade(
    override var id: Long? = null,
    @field:NotNull
    var student_id: Long? = null,

    @field:NotNull
    @field:Size(min = 4)
    var description: String? = null,

    @field:NotNull
    @field:DecimalMin(value = "0.0")
    @field:DecimalMax(value = "100.0")
    var english: Double? = null,

    @field:NotNull
    @field:DecimalMin(value = "0.0")
    @field:DecimalMax(value = "100.0")
    var math: Double? = null,

    @field:NotNull
    @field:DecimalMin(value = "0.0")
    @field:DecimalMax(value = "100.0")
    var mandarin:Double? = null,

    @field:NotNull
    @field:DecimalMin(value = "0.0")
    @field:DecimalMax(value = "100.0")
    var pe: Double? = null
): KEntity<Long> {
    companion object: Dao<Grade, Long>(Grade::class.java)
    val student: Student?
        get() = student_id?.let { Student.getById(student_id!!)} ?: null
}
