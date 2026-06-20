package com.yehorsk.medical_platform_mobile.core.util

import kotlinx.serialization.Serializable

sealed interface DataError : Error {
    sealed interface Remote : DataError {
        enum class Status : Remote {
            BAD_REQUEST,
            REQUEST_TIMEOUT,
            UNAUTHORIZED,
            FORBIDDEN,
            NOT_FOUND,
            CONFLICT,
            TOO_MANY_REQUESTS,
            NO_INTERNET,
            PAYLOAD_TOO_LARGE,
            SERVER_ERROR,
            SERVICE_UNAVAILABLE,
            SERIALIZATION,
            UNKNOWN
        }

        data class ValidationError(
            val errors: ValidationErrorsDto?
        ) : Remote
    }

    enum class Local : DataError {
        DISK_FULL,
        NOT_FOUND,
        UNKNOWN
    }

    enum class Connection : DataError {
        NOT_CONNECTED,
        MESSAGE_SEND_FAILED
    }
}

@Serializable
data class ValidationErrorsDto(
    val message: String,
    val errors: Map<String, List<String>>
)