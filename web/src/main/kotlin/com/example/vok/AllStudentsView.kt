package com.example.vok

import com.github.javafaker.Faker
import com.github.mvysny.karibudsl.v10.*
import com.github.vokorm.dataloader.dataLoader
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.data.converter.LocalDateToDateConverter
import com.vaadin.flow.router.AfterNavigationEvent
import com.vaadin.flow.router.AfterNavigationObserver
import com.vaadin.flow.router.Route
import eu.vaadinonkotlin.vaadin10.vokdb.setDataLoader
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Route("students", layout = MainLayout::class)
class AllStudentsView: KComposite(), AfterNavigationObserver {
    private lateinit var grid: Grid<Student>
    private val root = ui {
        verticalLayout {
            setSizeFull()
            h1("學生資料")
            button("Generate testing data"){
                onLeftClick {
                    val faker = Faker(Locale("zh-TW"))
                    (1..20).forEach { i ->
                        LocalDateToDateConverter().convertToPresentation(faker.date().birthday(20, 21), null)
                        Student(
                            name = faker.name().fullName(),
                            birthday = LocalDateToDateConverter().convertToPresentation(faker.date().birthday(20, 21), null),
                            gender = Gender.values().random(),
                            height = faker.number().randomDouble(1, 155, 190),
                            weight = faker.number().randomDouble(1, 45, 80)
                        ).save()
                    }
                    grid.refresh()
                }
            }
            grid = grid {
                isExpand = true
                setDataLoader(Student.dataLoader)
                addColumnFor(Student::id).setHeader("序號")
                addColumnFor(Student::name).setHeader("姓名")
                addColumnFor(Student::gender).setHeader("性別")
                addColumnFor(Student::birthday).setHeader("生日")
                addColumnFor(Student::height).setHeader("身高")
                addColumnFor(Student::weight).setHeader("體重")
            }
        }
    }

    private fun @VaadinDsl Button.generateStudentsData() {
        onLeftClick {
            val faker = Faker(Locale("zh-TW"))
            (1..20).forEach { i ->
                Student(
                    name = faker.name().fullName(),
                    birthday = LocalDateTime.ofInstant(
                        faker.date().birthday(20, 21).toInstant(),
                        ZoneId.of("Asia/Taipei")
                    ).toLocalDate(),

                    gender = Gender.values().random(),
                    height = faker.number().randomDouble(1, 155, 190),
                    weight = faker.number().randomDouble(1, 45, 80)
                ).save()
            }
            grid.refresh()
        }
    }

    override fun afterNavigation(event: AfterNavigationEvent?) {
        grid.refresh()
    }
}