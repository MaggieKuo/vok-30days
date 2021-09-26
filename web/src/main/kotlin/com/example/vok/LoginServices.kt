package com.example.vok

import com.vaadin.flow.component.UI
import eu.vaadinonkotlin.vaadin10.Session
import java.io.Serializable

class LoginServices: Serializable {
    var currentUser: User? = null
        private set
    val isLoggedIn get() = currentUser != null

    fun login(username: String, password: String): Boolean{
        // TODO verify username and password
        currentUser = User(username)
        UI.getCurrent().page.reload()
        return true
    }

    fun logout(){
        Session.current.close()
        UI.getCurrent().navigate("")
        UI.getCurrent().page.reload()
    }

}

val Session.loginService: LoginServices
    get() = getOrPut { LoginServices() }

data class User(val name: String) : Serializable