package gen.ai.core.logging

interface Logger {
    fun info(message: String)
    fun debug(message: String)
    fun warn(message: String)
    fun error(message: String, throwable: Throwable? = null)

    companion object {
        // Basic factory, can be expanded later for different logger implementations
        private var _instance: Logger = ConsoleLogger() // Default to ConsoleLogger

        val INSTANCE: Logger
            get() = _instance

        fun setLogger(logger: Logger) {
            _instance = logger
        }
    }
} 