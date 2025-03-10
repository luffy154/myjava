package com.kotlin.test;

import java.util.Map;

/**
 * @ClassName:ScriptManager
 * @author: qm
 * @Description:
 * @date:2025-02-19
 */
public class ScriptManager {
    private final KotlinScriptExecutor executor = new KotlinScriptExecutor();

    public Object executeScript(String script, Map<String, Object> params) {
        try {
            return executor.eval(script, params);
        } catch (Exception e) {
            throw new RuntimeException("Script execution failed", e);
        }
    }
}
