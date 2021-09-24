package com.example.vok

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.dependency.StyleSheet
import com.vaadin.flow.component.orderedlayout.VerticalLayout

@CssImport("frontend://table.css")
class GradeViewComponent : KComposite() {
    var studentId: Long = 0
        set(value) {
            field = value
            refresh()
        }
    private lateinit var grades: VerticalLayout
    private val root = ui {
        verticalLayout {
            isMargin = false
            h2("成績")
            grades = verticalLayout()
        }
    }

    fun refresh() {
        grades.removeAll()
        grades.apply {
            div("table") {
                div("tr") {
                    div("td") { strong("學期") }
                    div("td") { strong("國文") }
                    div("td") { strong("英文") }
                    div("td") { strong("數學") }
                    div("td") { strong("體育") }
                }
            }
        }
        Student.getById(studentId).grades.fetch().forEach {
            grades.apply {
                div("table") {
                    div("tr") {
                        div("td") { label("${it.description}") }
                        div("td") { label("${it.mandarin}") }
                        div("td") { label("${it.english}") }
                        div("td") { label("${it.math}") }
                        div("td") { label("${it.pe}") }

//                    html("<p>" +
//                            "<strong>學期:</strong>${it.description}&nbsp;" +
//                            "<strong>國文:</strong>${it.mandarin}&nbsp;" +
//                            "<strong>英文:</strong>${it.english}&nbsp;" +
//                            "<strong>數學:</strong>${it.math}&nbsp;" +
//                            "<strong>體育:</strong>${it.pe}" +
//                            "</p>")
                    }
                }
            }
        }
    }
}

fun HasComponents.grandsViewComponent(block: GradeViewComponent.() -> Unit = {}) = init(GradeViewComponent(), block)