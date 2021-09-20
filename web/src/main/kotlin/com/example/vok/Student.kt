package com.example.vok

import com.github.vokorm.KEntity
import com.gitlab.mvysny.jdbiorm.Dao
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.*

data class Student(
    override var id: Long? = null,

    @field:NotNull
    @field:Size(min = 2, max = 10)
    var name: String? = null,

    @field:NotNull
    @field:Past
    var birthday: LocalDate? = null,
    var created: Date? = null,

    @field:NotNull
    var gender : Gender? = null,

    @field:NotNull
    @field:DecimalMin(value = "100.0", message = "需至少100公分以上")
    @field:DecimalMax(value = "200.0")
    var height: Double? = null,

    @field:NotNull
    @field:DecimalMin(value = "40.0")
    @field:DecimalMax(value = "150.0")
    var weight: Double? = null,

    var student_id : String? = null
): KEntity<Long>{
    companion object :Dao<Student, Long>(Student::class.java)
}

enum class Gender {
    Female,
    Male,
    Custom
}
