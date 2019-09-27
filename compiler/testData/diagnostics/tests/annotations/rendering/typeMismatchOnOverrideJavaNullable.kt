// !RENDER_DIAGNOSTICS_MESSAGES

// FILE: A.java

import org.jetbrains.annotations.NotNull;

@An
public interface A {
    @NotNull
    @An
    String foo();
}

// FILE: k.kt

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE, AnnotationTarget.CLASS,  AnnotationTarget.PROPERTY,  AnnotationTarget.VALUE_PARAMETER)
annotation class An


class B : A {
    override fun foo(): <!RETURN_TYPE_MISMATCH_ON_OVERRIDE("foo", "@NotNull public abstract fun foo(): String defined in A")!>String?<!> = null
}
