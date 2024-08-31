package br.com.schumaker.octopus.framework.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * This interface represents a Model View Mapper.
 * It is a functional interface.
 *
 * @param <S> the source type
 * @param <T> the target type
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public interface ModelViewMapper<S, T> {

    /**
     * This method converts a source object to a target object.
     *
     * @param source the source object
     * @return the target object
     */
    default T from(S source) {
        throw new UnsupportedOperationException("from(S) is not implemented");
    }

    /**
     * This method converts a list of source objects to a list of target objects.
     *
     * @param sources the list of source objects
     * @return the list of target objects
     */
    default List<T> from(Iterable<S> sources) {
        return from(sources, (s, t) -> {});
    }

    /**
     * This method converts a list of source objects to a list of target objects.
     *
     * @param sources the list of source objects
     * @param postProcessor the post processor
     * @return the list of target objects
     */
    default List<T> from(Iterable<S> sources, BiConsumer<S, T> postProcessor) {
        List<T> targetList = new ArrayList<>();
        for (S source : sources) {
            try {
                T target = from(source);
                postProcessor.accept(source, target);
                targetList.add(target);
            } catch (RuntimeException ex) {
                System.out.println("Can not map.");
            }
        }
        return targetList;
    }


    /**
     * This method converts a target object to a source object.
     *
     * @param source the source object
     * @param target the target object
     */
    default void map(S source, T target) {
        throw new UnsupportedOperationException("map(S, T) is not implemented");
    }
}
