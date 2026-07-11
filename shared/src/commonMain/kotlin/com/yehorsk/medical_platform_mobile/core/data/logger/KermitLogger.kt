package com.yehorsk.medical_platform_mobile.core.data.logger

import co.touchlab.kermit.Logger
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger

object KermitLogger: MainLogger {

    override fun debug(message: String) {
        Logger.d(message)
    }

    override fun info(message: String) {
        Logger.i(message)
    }

    override fun warn(message: String) {
        Logger.w(message)
    }

    override fun error(message: String, throwable: Throwable?) {
        Logger.e(message, throwable)
    }
}