package gen.ai.core.logging

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ConsoleLogger : Logger {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")

    private fun log(level: String, message: String, throwable: Throwable? = null) {
        val timestamp = LocalDateTime.now().format(formatter)
        println("[$timestamp] [$level] - $message")
        throwable?.printStackTrace()
    }

    override fun info(message: String) {
        log("INFO", message)
    }

    override fun debug(message: String) {
        log("DEBUG", message) // Simple for now, could add a debug level check
    }

    override fun warn(message: String) {
        log("WARN", message)
    }

    override fun error(message: String, throwable: Throwable?) {
        log("ERROR", message, throwable)
    }
} 