package com.example.vok

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.Text
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.router.BeforeEvent
import com.vaadin.flow.router.HasUrlParameter
import com.vaadin.flow.router.Route
import com.vaadin.flow.router.RouterLink

@Route("student", layout = MainLayout::class)
class StudentView: KComposite(), HasUrlParameter<Long> {
    private lateinit var editLink: RouterLink
    private lateinit var name: Text
    private lateinit var gender: Text
    private lateinit var birthday: Text
    private lateinit var gradeView: GradeViewComponent
    private lateinit var gradeEditor: GradeEditorComponent
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
            gradeView = grandsViewComponent()
            gradeEditor = gradeEditorComponent {
                gradeCreatedListener = { gradeView.refresh() }
            }
            editLink = routerLink(VaadinIcon.EDIT,null)
        }
    }

    override fun setParameter(event: BeforeEvent?, studentId: Long) {

        val student = Student.getById(studentId)
        gradeView.studentId = studentId
        gradeEditor.student = student
        name.text = student.name
        gender.text = student.gender.toString()
        birthday.text = student.birthday.toString()
        editLink.setRoute(EditStudent::class, student.id!!)
    }

    companion object {
        fun navigateTo(studentId: Long) = navigateToView(StudentView::class, studentId)
    }
}