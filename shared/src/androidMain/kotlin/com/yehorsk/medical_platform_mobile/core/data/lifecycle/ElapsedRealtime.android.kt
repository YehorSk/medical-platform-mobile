package com.yehorsk.medical_platform_mobile.core.data.lifecycle

import android.os.SystemClock

actual fun elapsedRealtimeMillis(): Long = SystemClock.elapsedRealtime()
