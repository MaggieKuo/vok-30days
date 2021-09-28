package com.example.vok

import com.github.mvysny.vokdataloader.DataLoader
import com.github.mvysny.vokdataloader.withFilter
import com.github.vokorm.KEntity
import com.github.vokorm.dataloader.dataLoader
import com.github.vokorm.deleteBy
import com.gitlab.mvysny.jdbiorm.Dao
import com.google.gson.annotations.Expose
import eu.vaadinonkotlin.vaadin10.VokDataProvider
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.*

data class Student(
    @Expose
    override var id: Long? = null,

    @field:NotNull
    @field:Size(min = 2, max = 10)
    @Expose
    var name: String? = null,

    @field:NotNull
    @field:Past
    @Expose
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
    val grades : DataLoader<Grade>
        get() = Grade.dataLoader.withFilter {
            Grade::student_id eq id
        }

    override fun delete() {
        Grade.deleteBy { Grade::student_id eq id }
        super.delete()
    }
}

enum class Gender {
    Female,
    Male,
    Custom
}
