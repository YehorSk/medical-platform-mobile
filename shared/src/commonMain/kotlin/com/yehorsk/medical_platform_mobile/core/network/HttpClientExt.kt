package com.yehorsk.medical_platform_mobile.core.network

import co.touchlab.kermit.Logger
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.core.util.ValidationErrorsDto
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse

expect suspend fun <T> platformSafeCall(
    execute: suspend () -> HttpResponse,
    handleResponse: suspend (HttpResponse) -> Result<T, DataError.Remote>
): Result<T, DataError.Remote>

suspend inline fun <reified Request, reified Response: Any> HttpClient.post(
    route: String,
    body: Request,
    queryParams: Map<String, Any> = mapOf(),
    crossinline builder: HttpRequestBuilder.() -> Unit = {}
): Result<Response, DataError.Remote> {
    return safeCall {
        post {
            url(constructRoute(route))
            queryParams.forEach { (key, value) ->
                parameter(key, value)
            }
            setBody(body)
            builder()
        }
    }
}

suspend inline fun <reified Response: Any> HttpClient.get(
    route: String,
    queryParams: Map<String, Any> = mapOf(),
    crossinline builder: HttpRequestBuilder.() -> Unit = {}
): Result<Response, DataError.Remote> {
    return safeCall {
        get {
            url(constructRoute(route))
            queryParams.forEach { (key, value) ->
                parameter(key, value)
            }
            builder()
        }
    }
}

suspend inline fun <reified Response: Any> HttpClient.delete(
    route: String,
    queryParams: Map<String, Any> = mapOf(),
    crossinline builder: HttpRequestBuilder.() -> Unit = {}
): Result<Response, DataError.Remote> {
    return safeCall {
        delete {
            url(constructRoute(route))
            queryParams.forEach { (key, value) ->
                parameter(key, value)
            }
            builder()
        }
    }
}

suspend inline fun <reified Request, reified Response: Any> HttpClient.put(
    route: String,
    queryParams: Map<String, Any> = mapOf(),
    body: Request,
    crossinline builder: HttpRequestBuilder.() -> Unit = {}
): Result<Response, DataError.Remote> {
    return safeCall {
        put {
            url(constructRoute(route))
            queryParams.forEach { (key, value) ->
                parameter(key, value)
            }
            setBody(body)
            builder()
        }
    }
}

suspend inline fun <reified T> safeCall(
    noinline execute: suspend () -> HttpResponse
): Result<T, DataError.Remote> {
    return platformSafeCall(execute = execute) { response ->
        responseToResult(response)
    }
}

suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, DataError.Remote> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Failure(DataError.Remote.Status.SERIALIZATION)
            }
        }
        400 -> Result.Failure(DataError.Remote.Status.BAD_REQUEST)
        401 -> Result.Failure(DataError.Remote.Status.UNAUTHORIZED)
        403 -> Result.Failure(DataError.Remote.Status.FORBIDDEN)
        404 -> Result.Failure(DataError.Remote.Status.NOT_FOUND)
        408 -> Result.Failure(DataError.Remote.Status.REQUEST_TIMEOUT)
        409 -> Result.Failure(DataError.Remote.Status.CONFLICT)
        413 -> Result.Failure(DataError.Remote.Status.PAYLOAD_TOO_LARGE)
        422 -> {
            val errors = try {
                response.body<ValidationErrorsDto>()
            } catch (e: NoTransformationFoundException) {
                null
            }
            Result.Failure(DataError.Remote.ValidationError(errors))
        }
        429 -> Result.Failure(DataError.Remote.Status.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Failure(DataError.Remote.Status.SERVER_ERROR)
        else -> Result.Failure(DataError.Remote.Status.UNKNOWN)
    }
}

fun constructRoute(route: String): String {
    val result = when {
        route.contains(UrlConstants.BASE_URL_HTTP) -> route
        route.startsWith("/") -> "${UrlConstants.BASE_URL_HTTP}$route"
        else -> "${UrlConstants.BASE_URL_HTTP}/$route"
    }
    Logger.withTag("HttpClientExt").e { result }

    return result
}