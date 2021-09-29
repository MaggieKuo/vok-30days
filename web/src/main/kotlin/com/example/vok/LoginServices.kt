package com.example.vok

import com.github.javafaker.Bool
import com.github.vokorm.KEntity
import com.gitlab.mvysny.jdbiorm.Dao
import com.vaadin.flow.component.UI
import eu.vaadinonkotlin.security.simple.HasPassword
import eu.vaadinonkotlin.vaadin10.Session
import java.io.Serializable

class LoginServices : Serializable {
    var currentUser: User? = null
        private set
    val isLoggedIn get() = currentUser != null

    fun login(username: String, password: String): Boolean {
        //verify username and password
//        currentUser = User(username)
        val user = User.findByUsername(username) ?: return false
        if (!user.passwordMatches(password)) return false
        currentUser = user
        UI.getCurrent().page.reload()
        return true
    }

    fun logout() {
        Session.current.close()
        UI.getCurrent().navigate("")
        UI.getCurrent().page.reload()
    }

    fun getCurrentUserRoles(): Set<String>{
        val roles: String = currentUser?.roles ?: return setOf()
        return roles.split(",").toSet()
    }

    fun isUserInRole(role: String): Boolean = getCurrentUserRoles().contains(role)

    fun isAdmin(): Boolean = isUserInRole("administrator")
}

val Session.loginService: LoginServices
    get() = getOrPut { LoginServices() }

data class User(
    override var id: Long? = null,
    var username: String = "",
    override var hashedPassword: String = "",
    var roles: String = ""
) : KEntity<Long>, HasPassword {
    companion object : Dao<User, Long>(User::class.java) {
        fun findByUsername(username: String): User? = findOneBy("username = :username") { query ->
            query.bind("username", username)
        }
    }
}