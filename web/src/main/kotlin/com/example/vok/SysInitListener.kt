package com.example.vok

import com.vaadin.flow.server.ServiceInitEvent
import com.vaadin.flow.server.VaadinServiceInitListener
import eu.vaadinonkotlin.vaadin10.Session
import eu.vaadinonkotlin.vaadin10.VokSecurity

class SysInitListener : VaadinServiceInitListener {
    override fun serviceInit(event: ServiceInitEvent) {
        event.source.addUIInitListener { uiInitEvent ->
            uiInitEvent.ui.addBeforeEnterListener { beforeEnterEvent ->
                if (!Session.loginService.isLoggedIn && beforeEnterEvent.navigationTarget != LoginView::class.java) {
                    beforeEnterEvent.rerouteTo(LoginView::class.java)
                } else {
                    VokSecurity.checkPermissionsOfView(beforeEnterEvent.navigationTarget)
                }
            }
        }
    }
}