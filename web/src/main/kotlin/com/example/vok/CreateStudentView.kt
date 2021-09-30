package com.example.vok

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.router.Route
import eu.vaadinonkotlin.security.AllowRoles

@Route("create-student", layout = MainLayout::class)
@AllowRoles("administrator")
class CreateStudentView: KComposite() {
    private lateinit var editorComponent: StudentEditorComponent
    private val root = ui {
        verticalLayout {
            h1("新增學生資料")
            editorComponent = studentEditorComponent {
                student = Student()
            }
        }
    }
}