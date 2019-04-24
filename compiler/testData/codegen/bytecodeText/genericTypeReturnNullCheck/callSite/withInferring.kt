// !LANGUAGE: +GenerateNullChecksForGenericTypeReturningFunctions
// TARGET_BACKEND: JVM

fun <T> foo(): T = null as T

fun main() {
    val x: String = foo()
}

// 1 IFNONNULL
// 1 INVOKESTATIC kotlin/jvm/internal/Intrinsics.throwNpe