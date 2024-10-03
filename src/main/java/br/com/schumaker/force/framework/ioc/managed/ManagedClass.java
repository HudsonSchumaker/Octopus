package br.com.schumaker.force.framework.ioc.managed;

/**
 * The ManagedClass interface defines the contract for managed classes within the IoC container.
 * It provides methods to retrieve the fully qualified name (FQN) and the instance of the managed class.
 *
 * @param <T> the type of the managed class
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public interface ManagedClass<T> {

    /**
     * Casts the given object to the specified type.
     *
     * @param obj the object to cast.
     * @param type the class of the type to cast to.
     * @param <T> the type to cast to.
     * @return the cast object.
     */
    static <T> T castObject(Object obj, Class<T> type) {
        return type.cast(obj);
    }

    /**
     * Returns the fully qualified name (FQN) of the managed class.
     *
     * @return the fully qualified name of the managed class.
     */
    String getFqn();

    /**
     * Returns the instance of the managed class.
     *
     * @return the instance of the managed class.
     */
    Object getInstance();
}
