package com.yehorsk.medical_platform_mobile.core.util

import com.yehorsk.medical_platform_mobile.util.UiText
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.*

fun DataError.toUiText(): UiText {
    val resource = when(this) {
        DataError.Local.DISK_FULL -> UiRes.string.error_disk_full
        DataError.Local.NOT_FOUND -> UiRes.string.error_not_found
        DataError.Local.UNKNOWN -> UiRes.string.error_unknown
        DataError.Remote.Status.BAD_REQUEST -> UiRes.string.error_bad_request
        DataError.Remote.Status.REQUEST_TIMEOUT -> UiRes.string.error_request_timeout
        DataError.Remote.Status.UNAUTHORIZED -> UiRes.string.error_unauthorized
        DataError.Remote.Status.FORBIDDEN -> UiRes.string.error_forbidden
        DataError.Remote.Status.NOT_FOUND -> UiRes.string.error_not_found
        DataError.Remote.Status.CONFLICT -> UiRes.string.error_conflict
        DataError.Remote.Status.TOO_MANY_REQUESTS -> UiRes.string.error_too_many_requests
        DataError.Remote.Status.NO_INTERNET -> UiRes.string.error_no_internet
        DataError.Remote.Status.PAYLOAD_TOO_LARGE -> UiRes.string.error_payload_too_large
        DataError.Remote.Status.SERVER_ERROR -> UiRes.string.error_server
        DataError.Remote.Status.SERVICE_UNAVAILABLE -> UiRes.string.error_service_unavailable
        DataError.Remote.Status.SERIALIZATION -> UiRes.string.error_serialization
        DataError.Remote.Status.UNKNOWN -> UiRes.string.error_unknown
        DataError.Connection.NOT_CONNECTED -> UiRes.string.error_no_internet
        DataError.Connection.MESSAGE_SEND_FAILED -> UiRes.string.error_unable_to_send_message
        is DataError.Remote.ValidationError -> UiRes.string.validation_error
    }
    return UiText.Resource(resource)
}