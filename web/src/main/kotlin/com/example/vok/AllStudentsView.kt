package com.example.vok

import com.github.javafaker.Faker
import com.github.mvysny.karibudsl.v10.*
import com.github.vokorm.dataloader.dataLoader
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.IconFactory
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.data.converter.LocalDateToDateConverter
import com.vaadin.flow.data.renderer.ComponentRenderer
import com.vaadin.flow.router.AfterNavigationEvent
import com.vaadin.flow.router.AfterNavigationObserver
import com.vaadin.flow.router.Route
import eu.vaadinonkotlin.security.AllowAllUsers
import eu.vaadinonkotlin.security.AllowRoles
import eu.vaadinonkotlin.security.LoggedInUserResolver
import eu.vaadinonkotlin.vaadin10.Session
import eu.vaadinonkotlin.vaadin10.VokSecurity
import eu.vaadinonkotlin.vaadin10.vokdb.setDataLoader
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Route("students", layout = MainLayout::class)
@AllowAllUsers
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
//                addColumn(NativeButtonRenderer("Show", {StudentView.navigateTo(it.id!!)}))
//                addColumn(NativeButtonRenderer("Edit", {EditStudent.navigateTo(it.id!!)}))
//                addColumn(NativeButtonRenderer("Delete") {
//                    confirmDialog(text = "是否確定刪除${it.name}的資料？") {
//                        it.delete()
//                        this.refresh()
//                    }
//                })
                addButtonColumn(VaadinIcon.EYE, "view") { StudentView.navigateTo(it.id!!) }
                addButtonColumn(VaadinIcon.EDIT, "edit") { EditStudent.navigateTo(it.id!!) }
                if (Session.loginService.isUserInRole("administrator")) {
                    addButtonColumn(VaadinIcon.TRASH, "delete") {
                        confirmDialog(text = "是否確定刪除${it.name}的資料？") {
                            it.delete()
                            this.refresh()
                        }
                    }
                }
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

fun <T> Grid<T>.addButtonColumn(icon: IconFactory, key: String, clickListener: (T) -> Unit): Grid.Column<T> {
    val renderer = ComponentRenderer<Button, T> { data: T ->
        val button = Button(icon.create())
        button.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL)
        button.onLeftClick { clickListener(data) }
        button
    }
    val column: Grid.Column<T> = addColumn(renderer).setKey(key).setWidth("50px")
    column.isExpand = false
    return column
}