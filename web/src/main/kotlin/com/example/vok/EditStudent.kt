package com.example.vok

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.router.BeforeEvent
import com.vaadin.flow.router.HasUrlParameter
import com.vaadin.flow.router.Route

@Route("edit-student", layout = MainLayout::class)
class EditStudent : KComposite(), HasUrlParameter<Long> {
    private val binder = beanValidationBinder<Student>()
    private var student: Student? = null
    private val root = ui {
        verticalLayout {
            h1("學生資料修改")
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

    override fun setParameter(event: BeforeEvent?, studentId: Long?) {
        binder.readBean(Student.getById(studentId!!))
    }

    companion object{
        fun navigateTo(studentId: Long) = navigateToView(EditStudent::class, studentId)
    }
}

