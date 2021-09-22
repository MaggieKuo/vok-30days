package com.example.vok

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.HasComponents

class StudentEditorComponent: KComposite() {
    private val binder = beanValidationBinder<Student>()
    var student: Student? = null
        set(value) {
            field = value
            value?.let { binder.readBean(value) }
        }
    private val root = ui {
        verticalLayout {
            isMargin = false
            textField("姓名 : "){
                bind(binder).bind(Student::name)
            }
            comboBox<Gender>("性別 : "){
                setItems(*Gender.values())
                bind(binder).bind(Student::gender)
            }
            datePicker("生日 : "){
                bind(binder).bind(Student::birthday)
            }
            numberField("身高"){
                bind(binder).bind(Student::height)
            }
            numberField("體重"){
                bind(binder).bind(Student::weight)
            }
            button("儲存"){
                onLeftClick {
                    val student = student!!
                    if (binder.validate().isOk && binder.writeBeanIfValid(student)){
                        student.save()
                        StudentView.navigateTo(student.id!!)
                    }
                }
            }
            routerLink(null, "返回", AllStudentsView::class)
        }
    }
}

fun HasComponents.studentEditorComponent(block: StudentEditorComponent.()->Unit = {}) = init(StudentEditorComponent(), block)