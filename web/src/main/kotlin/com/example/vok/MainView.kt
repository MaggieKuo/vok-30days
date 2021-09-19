package com.example.vok

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.router.Route
import org.h2.engine.Right

@Route("")
class MainView: KComposite() {
    private val root = ui{
        verticalLayout {
            content { align(center, top) }
            h1("2021 iThome鐵人賽")
            h2("使用 Kotlin 快速開發 Web 程式 -- Vaadin系列")
        }
    }
}