package com.example.vok

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.Text
import com.vaadin.flow.router.BeforeEvent
import com.vaadin.flow.router.HasUrlParameter
import com.vaadin.flow.router.Route

@Route("student", layout = MainLayout::class)
class StudentView: KComposite(), HasUrlParameter<Long> {
    private lateinit var name: Text
    private lateinit var gender: Text
    private lateinit var birthday: Text
    private val root = ui {
        verticalLayout {
            div {
                strong("姓名 : "); this@StudentView.name = text("")
            }
            div {
                strong("性別 : "); this@StudentView.gender = text("")
            }
            div {
                strong("生日 : "); this@StudentView.birthday = text("")
            }
        }
    }

    override fun setParameter(event: BeforeEvent?, studentId: Long?) {
        val student = Student.getById(studentId!!)
        name.text = student.name
        gender.text = student.gender.toString()
        birthday.text = student.birthday.toString()
    }

    companion object {
        fun navigateTo(studentId: Long) = navigateToView(StudentView::class, studentId)
    }
}