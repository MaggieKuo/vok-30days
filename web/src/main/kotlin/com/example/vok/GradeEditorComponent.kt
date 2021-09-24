package com.example.vok

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.Button

class GradeEditorComponent : KComposite() {
    var gradeCreatedListener: () -> Unit = {}
    lateinit var student: Student
    private val gradeBinder = beanValidationBinder<Grade>()
    private lateinit var addGradeButton: Button
    private val root = ui {
        verticalLayout {
            text("新增成績")

            textField("學期") {
                bind(gradeBinder).bind(Grade::description)
                placeholder = "第一學期"
            }
            numberField("國文") {
                bind(gradeBinder).bind(Grade::mandarin)
            }
            numberField("英文") {
                bind(gradeBinder).bind(Grade::english)
            }
            numberField("數學") {
                bind(gradeBinder).bind(Grade::math)
            }
            numberField("體育") {
                bind(gradeBinder).bind(Grade::pe)
            }
            addGradeButton = button("新增") {
                onLeftClick { addGrade() }
            }
        }
    }

    private fun addGrade() {
        val grade = Grade()
        if (gradeBinder.validate().isOk && gradeBinder.writeBeanIfValid(grade)) {
            grade.student_id = student.id
            grade.save()
            gradeBinder.readBean(Grade())
            gradeCreatedListener()
        }
    }
}

fun HasComponents.gradeEditorComponent(block: GradeEditorComponent.() -> Unit = {}) =
    init(GradeEditorComponent(), block)
