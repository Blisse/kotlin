// !LANGUAGE: +InlineClasses
// TARGET_BACKEND: JVM
// WITH_RUNTIME

inline class R(private val r: Int) {
    fun test() = ok()

    companion object {
        @JvmStatic
        private fun ok() = "OK"
    }
}

fun box() = R(0).test()