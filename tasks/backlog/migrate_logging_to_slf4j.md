USER STORY: Migrate Logging to SLF4J
As a developer, I want to migrate from custom logging to SLF4J + Logback, so that we use a standard and flexible logging framework.

Raw Notes:
- Migrate from custom logging to SLF4J + Logback:
  - Add SLF4J API and Logback Classic dependencies to Gradle.
  - Refactor code to use `org.slf4j.LoggerFactory` instead of `gen.ai.core.logging`.
  - Remove `gen.ai.core.logging` package (ConsoleLogger.kt, Logger.kt). 