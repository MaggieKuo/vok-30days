package com.example.vok

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.login.LoginForm
import com.vaadin.flow.component.login.LoginI18n
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.BeforeEnterObserver
import com.vaadin.flow.router.Route
import eu.vaadinonkotlin.vaadin10.Session

@Route("login")
class LoginView: KComposite(), BeforeEnterObserver {
    private lateinit var loginForm: LoginForm

    private val root = ui{
        verticalLayout {
            setSizeFull(); isPadding = false; content { center() }
            val loginI18n: LoginI18n = loginI18n {
                header.title = "您好!"
                with(form){
                    title = "登入"
                    username = "帳號"
                    password = "密碼"
                    submit = "登入"
                    forgotPassword = "忘記密碼"
                }
                additionalInformation = "請登入"

            }
            loginForm = loginForm(loginI18n){
                addLoginListener {event ->
                    isError = !Session.loginService.login(event.username, event.password)
                }
            }
        }
    }

    override fun beforeEnter(event: BeforeEnterEvent) {
        if (Session.loginService.isLoggedIn){
            event.rerouteTo("")
        }
    }

}