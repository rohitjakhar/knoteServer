package com.mynote.routes

import com.mynote.data.checkIfUserExists
import com.mynote.data.collections.User
import com.mynote.data.registerUser
import com.mynote.data.requests.AccountRequest
import com.mynote.data.responses.SimpleRespons
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.registerRoute() {
    route("/register") {
        post {
            val request = try {
                call.receive<AccountRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val usersExist = checkIfUserExists(request.email)
            if (!usersExist) {
                if (registerUser(User(request.email, request.password))) {
                    call.respond(HttpStatusCode.OK, SimpleRespons(true, "Successfully created"))
                } else {
                    call.respond(HttpStatusCode.OK, SimpleRespons(false, "An unknown error occured"))
                }
            } else {
                call.respond(HttpStatusCode.OK, SimpleRespons(false, "A user with that Email already registered"))
            }
        }
    }
}