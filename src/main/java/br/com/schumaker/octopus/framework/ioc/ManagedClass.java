package br.com.schumaker.octopus.framework.ioc;

public interface ManagedClass<T> {

    static <T> T castObject(Object obj, Class<T> type) {
        return type.cast(obj);
    }

    String getFqn();
    Object getInstance();
}
