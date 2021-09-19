package com.example.vok

import com.github.vokorm.KEntity
import com.gitlab.mvysny.jdbiorm.Dao
import java.time.LocalDate
import java.util.*

data class Student(
    override var id: Long? = null,
    var name: String? = null,
    var birthday: LocalDate? = null,
    var created: Date? = null,
    var gender : Gender? = null,
    var height: Double? = null,
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
