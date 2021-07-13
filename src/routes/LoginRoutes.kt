package com.mynote.routes

import com.mynote.data.checkPasswordForEmail
import com.mynote.data.requests.AccountRequest
import com.mynote.data.responses.SimpleRespons
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import javax.security.auth.callback.ConfirmationCallback.OK

fun Route.loginRoute() {
    route("/login") {
        post {
            val request = try {
                call.receive<AccountRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val isPasswordCorrect = checkPasswordForEmail(request.email, request.password)
            if (isPasswordCorrect) {
                call.respond(HttpStatusCode.OK, SimpleRespons(true, "You are now logged in!"))
            } else {
                call.respond(HttpStatusCode.OK, SimpleRespons(false, "The email or password is incorrect"))
            }
        }
    }
}