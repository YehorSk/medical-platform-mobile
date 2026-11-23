package com.yehorsk.medical_platform_mobile.core.data.lifecycle

actual fun elapsedRealtimeMillis(): Long = System.nanoTime() / 1_000_000
