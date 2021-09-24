package com.example.vok

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.Text
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.router.BeforeEvent
import com.vaadin.flow.router.HasUrlParameter
import com.vaadin.flow.router.Route
import com.vaadin.flow.router.RouterLink

@Route("student", layout = MainLayout::class)
class StudentView: KComposite(), HasUrlParameter<Long> {
    private lateinit var student: Student
    private lateinit var editLink: RouterLink
    private lateinit var name: Text
    private lateinit var gender: Text
    private lateinit var birthday: Text
    private lateinit var gradeDiv: Div
    private val gradeBinder = beanValidationBinder<Grade>()
    private lateinit var addGradeButton: Button
    private val root = ui {
        verticalLayout {
            routerLink(VaadinIcon.ARROW_LEFT, null, viewType = AllStudentsView::class)
            div {
                strong("姓名 : "); this@StudentView.name = text("")
            }
            div {
                strong("性別 : "); this@StudentView.gender = text("")
            }
            div {
                strong("生日 : "); this@StudentView.birthday = text("")
            }
            h2("成績")
            gradeDiv = div()
            p("新增成績")

            textField("學期"){
                bind(gradeBinder).bind(Grade::description)
                placeholder = "第一學期"
            }
            numberField("國文"){
                bind(gradeBinder).bind(Grade::mandarin)
            }
            numberField("英文"){
                bind(gradeBinder).bind(Grade::english)
            }
            numberField("數學"){
                bind(gradeBinder).bind(Grade::math)
            }
            numberField("體育"){
                bind(gradeBinder).bind(Grade::pe)
            }
            addGradeButton = button("新增"){
                onLeftClick { addGrade() }
            }
            editLink = routerLink(VaadinIcon.EDIT,null)
        }
    }

    private fun addGrade() {
        val grade = Grade()
        if (gradeBinder.validate().isOk && gradeBinder.writeBeanIfValid(grade)){
            grade.student_id = student.id
            grade.save()
            refreshGrades()
            gradeBinder.readBean(Grade())
        }
    }

    private fun refreshGrades() {
        gradeDiv.removeAll()
        student.grades.fetch().forEach {
            gradeDiv.html("<p>" +
                    "<strong>學期:</strong>${it.description}&nbsp;" +
                    "<strong>國文:</strong>${it.mandarin}&nbsp;" +
                    "<strong>英文:</strong>${it.english}&nbsp;" +
                    "<strong>數學:</strong>${it.math}&nbsp;" +
                    "<strong>體育:</strong>${it.pe}" +
                    "</p>")
        }
    }

    override fun setParameter(event: BeforeEvent?, studentId: Long?) {
        student = Student.getById(studentId!!)
        name.text = student.name
        gender.text = student.gender.toString()
        birthday.text = student.birthday.toString()
        editLink.setRoute(EditStudent::class, student.id!!)
    }

    companion object {
        fun navigateTo(studentId: Long) = navigateToView(StudentView::class, studentId)
    }
}