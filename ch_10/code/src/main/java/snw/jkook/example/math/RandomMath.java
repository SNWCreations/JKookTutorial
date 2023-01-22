package snw.jkook.example.math;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Random;
import java.util.function.Supplier;

public class RandomMath {
    // --- Constants ---
    private static final ScriptEngine JS_ENGINE;

    static {
        // I don't know how to implement basic calculation in Java :(
        // So I choose JavaScript engine!
        JS_ENGINE = new ScriptEngineManager().getEngineByName("js");
    }
    
    // --- Member variables ---
    private final Random random;
    private final int[] parts;
    private final int[] opCodes;
    private final String result;
    private final String calcString;
    
    // --- Methods ---

    public RandomMath() {
        random = new Random();
        parts = new int[5 + random.nextInt(5)];
        opCodes = new int[parts.length - 1];
        initData();
        calcString = initCalcString();
        result = calcResult();
    }


    private void initData() {
        fill(parts, () -> 1 + random.nextInt(10));
        fill(opCodes, () -> 1 + random.nextInt(4));
        // check / operations
        for (int i = 0; i < opCodes.length; i++) {
            if (opCodes[i] == 4) { // if divide
                while (parts[i] % parts[i + 1] != 0) {
                    // regenerate parts
                    parts[i] = 1 + random.nextInt(10);
                    parts[i + 1] = 1 + random.nextInt(10);
                }
                if (i != opCodes.length - 1 // if not final round
                        && opCodes[i + 1] == 4 // if next is still divide
                ) { // prevent x/y/z situations
                    opCodes[i + 1] = (random.nextBoolean() ? 1 : 2);
                }
            }
        }
    }

    private String calcResult() {
        try {
            return JS_ENGINE.eval(getCalcString()).toString();
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    private String initCalcString() {
        StringBuilder builder = new StringBuilder();
        boolean t = false;
        for (int i = 0; i < parts.length; i++) {
            builder.append(parts[i]);
            if (i < opCodes.length) { // if this is false, the round is ended after this statement
                builder.append(getOperatorString(opCodes[i]));
            }
        }
        return builder.toString();
    }

    public String getResult() {
        return result;
    }

    public String getCalcString() {
        return calcString;
    }

    public static void fill(int[] arr, Supplier<Integer> supplier) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = supplier.get();
        }
    }

    public static String getOperatorString(int data) {
        switch (data) {
            case 1:
                return "+";
            case 2:
                return "-";
            case 3:
                return "*";
            case 4:
                return "/";
        }
        throw new IllegalArgumentException("Unsupported operator type");
    }
}
