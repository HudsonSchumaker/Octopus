package br.com.schumaker.octopus.framework.reflection;

public class MethodReflection {
    private static final MethodReflection INSTANCE = new MethodReflection();

    private MethodReflection() {}

    public static MethodReflection getInstance() {
        return INSTANCE;
    }
}
