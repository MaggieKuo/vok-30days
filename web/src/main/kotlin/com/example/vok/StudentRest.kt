package com.example.vok

import io.javalin.Javalin
import io.javalin.http.NotFoundResponse

fun Javalin.studentRest(){

    get("/rest/student/:id"){ ctx ->
        val id = ctx.pathParam("id").toLong()
        Student.findById(id)?.let { ctx.json(it) }?: throw NotFoundResponse("No student with id:$id")
    }

    get("/rest/students"){ctx ->
        ctx.json(Student.findAll())
    }

    get("/rest/student/:id/grade"){ctx->
        val id = ctx.pathParam("id").toLong()
        val student = Student.findById(id) ?: throw NotFoundResponse("No student with id:$id")
        ctx.json(student.grades.fetch())
    }

}
