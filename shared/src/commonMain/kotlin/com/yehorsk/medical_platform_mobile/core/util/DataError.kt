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
            // Password reset: the code is gone, the user has to request a new one
            RESET_CODE_EXPIRED,
            RESET_CODE_TOO_MANY_ATTEMPTS,
            // Schedule edit would leave upcoming appointments outside working hours
            SCHEDULE_CONFLICTS_WITH_APPOINTMENTS,
            UNKNOWN
        }

        data class ValidationError(
            val errors: ValidationErrorsDto?
        ) : Remote

        // Business-rule failure whose server message is meant for the user (e.g. INVALID_SCHEDULE)
        data class ServerMessage(
            val message: String
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

class DataErrorException(val error: DataError.Remote) : Exception()

@Serializable
data class ValidationErrorsDto(
    val message: String,
    val errors: List<ValidationError>
)

@Serializable
data class ValidationError(
    val field: String,
    val message: String
)