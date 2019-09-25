/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.scripting.repl.js

import org.jetbrains.kotlin.cli.common.repl.ReplCompileResult.*
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.ir.backend.js.utils.NameTables
import org.jetbrains.kotlin.ir.util.SymbolTable
import kotlin.script.experimental.api.*


// `JsScriptCompiler` is used to compile .kts files
class JsScriptCompiler(environment: KotlinCoreEnvironment) : ScriptCompiler {
    val nameTables = NameTables(emptyList())
    val symbolTable = SymbolTable()
    val dependencies: List<ModuleDescriptor> = readLibrariesFromConfiguration(environment.configuration)
    private val compiler = JsCoreScriptingCompiler(environment, nameTables, symbolTable, dependencies)

    override suspend fun invoke(
        script: SourceCode,
        scriptCompilationConfiguration: ScriptCompilationConfiguration
    ): ResultWithDiagnostics<CompiledScript<*>> {
        val compileResult = compiler.compile(makeReplCodeLine(0, script.text))
        return when (compileResult) {
            is CompiledClasses -> ResultWithDiagnostics.Success(
                CompiledToJsScript(compileResult.data as String, scriptCompilationConfiguration)
            )
            is Incomplete -> ResultWithDiagnostics.Failure(
                ScriptDiagnostic("Incomplete code")
            )
            is Error -> ResultWithDiagnostics.Failure(
                ScriptDiagnostic(
                    message = compileResult.message,
                    severity = ScriptDiagnostic.Severity.ERROR
                )
            )
        }
    }
}
