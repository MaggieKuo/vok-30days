package com.example.vok

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.div
import com.github.mvysny.karibudsl.v10.routerLink
import com.github.mvysny.karibudsl.v10.text
import com.vaadin.flow.component.page.Viewport
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.BeforeEnterObserver
import com.vaadin.flow.router.RouterLayout
import eu.vaadinonkotlin.vaadin10.Session

@Viewport(Viewport.DEVICE_DIMENSIONS)
class MainLayout: KComposite(), RouterLayout, BeforeEnterObserver {
    private val root = ui {
        div {
            setSizeFull()
        }
    }

    override fun beforeEnter(event: BeforeEnterEvent) {
        if (event.navigationTarget != LoginView::class.java && !Session.loginService.isLoggedIn){
            event.rerouteTo(LoginView::class.java)
        }
    }

}