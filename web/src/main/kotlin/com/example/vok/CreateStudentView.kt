package com.example.vok

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.router.Route

@Route("create-student", layout = MainLayout::class)
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