package com.yehorsk.medical_platform_mobile.core.data.push

import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class PushTokenProvider {

    actual suspend fun getToken(): String? {
        return FirebaseMessaging.getInstance().token.awaitResult()
    }

    // Invalidates the token at FCM, so pushes sent to it are no longer delivered to this device.
    actual suspend fun deleteToken() {
        FirebaseMessaging.getInstance().deleteToken().awaitResult()
    }

}

private suspend fun <T> Task<T>.awaitResult(): T =
    suspendCancellableCoroutine { cont ->
        addOnSuccessListener { cont.resume(it) }
        addOnFailureListener { cont.resumeWithException(it) }
        addOnCanceledListener { cont.cancel() }
    }
