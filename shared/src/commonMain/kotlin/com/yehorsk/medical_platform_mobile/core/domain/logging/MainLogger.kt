package com.yehorsk.medical_platform_mobile.core.domain.logging

interface MainLogger {
    fun debug(message: String)
    fun info(message: String)
    fun warn(message: String)
    fun error(message: String, throwable: Throwable? = null)
}