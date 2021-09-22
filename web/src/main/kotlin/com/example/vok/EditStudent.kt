package com.example.vok

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.router.BeforeEvent
import com.vaadin.flow.router.HasUrlParameter
import com.vaadin.flow.router.Route

@Route("edit-student", layout = MainLayout::class)
class EditStudent : KComposite(), HasUrlParameter<Long> {
    private lateinit var studentEditorComponent: StudentEditorComponent
    private val root = ui {
        verticalLayout {
            h1("學生資料修改")
            studentEditorComponent = studentEditorComponent()
        }
    }

    override fun setParameter(event: BeforeEvent?, studentId: Long?) {
        studentEditorComponent.student = Student.getById(studentId!!)
    }

    companion object{
        fun navigateTo(studentId: Long) = navigateToView(EditStudent::class, studentId)
    }
}

