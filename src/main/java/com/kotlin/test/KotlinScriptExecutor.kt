package com.kotlin.test

import javax.script.ScriptEngineManager

class KotlinScriptExecutor {
    private val engine = ScriptEngineManager().getEngineByExtension("kts")

    fun eval(script: String, bindings: Map<String, Any> = emptyMap()): Any? {
        return engine.let {
            // 添加绑定变量
            bindings.forEach { (key, value) -> it.put(key, value) }
            // 执行脚本
            it.eval(script)
        }
    }
}