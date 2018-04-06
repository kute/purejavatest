package com.kute.java8;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * created by kute on 2018/04/06 10:36
 */
public class JavaScriptTest {

    public static void main(String[] args) throws ScriptException {


        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName( "JavaScript" );

        System.out.println( engine.getClass().getName() );
        System.out.println( "Result:" + engine.eval( "function f() { return 1; }; f() + 1;" ) );

    }
}
