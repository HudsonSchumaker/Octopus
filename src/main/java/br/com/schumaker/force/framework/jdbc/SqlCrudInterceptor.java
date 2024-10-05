package br.com.schumaker.force.framework.jdbc;

import br.com.schumaker.force.framework.ioc.annotations.db.Query;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The SqlCrudInterceptor class.
 * It is responsible for intercepting the CRUD operations.
 *
 * @param <K> entity key.
 * @param <T> entity type.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class SqlCrudInterceptor<K, T> implements InvocationHandler {
    private final SqlCrudImpl<K, T> target;

    public SqlCrudInterceptor(SqlCrudImpl<K, T> target) {
        this.target = target;
    }

    /**
     * Invokes the method.
     *
     * @param proxy  the proxy.
     * @param method the method.
     * @param args   the arguments.
     * @return the result.
     * @throws Throwable if an error occurs.
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        var list = Arrays.stream(target.getClass().getMethods()).map(Method::getName).toList();
        List<?> result;

        if (list.contains(method.getName())) {
            return method.invoke(target, args);
        } else {
            if (method.isAnnotationPresent(Query.class)) {
                var rawQuery = method.getAnnotation(Query.class).value();
                System.out.println("SQL: " + rawQuery);
                var query  = QueryBuilder.replacePlaceholders(rawQuery, args);
                result = SqlExecutor.executeQuery(query, target.getEntityClass());
            } else {
                var tableName = target.getTableName();
                var query = QueryBuilder.buildQuery(tableName, method, args);
                result = SqlExecutor.executeQuery(query, target.getEntityClass());
            }

            return handleReturnType(method, result);
        }
    }

    /**
     * Handles the return type of the method.
     *
     * @param method the method.
     * @param result the result.
     * @return the result wrapped.
     */
    private Object handleReturnType(Method method, List<?> result) {
        if (method.getReturnType().equals(List.class)) {
            return result;
        } else {
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }

    /**
     * Creates a proxy instance.
     *
     * @param target        the target.
     * @param interfaceType the interface type.
     * @param <T>           the type.
     * @return the proxy instance.
     */
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(T target, Class<? extends T> interfaceType) {
        return (T) Proxy.newProxyInstance(
                interfaceType.getClassLoader(),
                new Class<?>[]{interfaceType},
                new SqlCrudInterceptor<>((SqlCrudImpl<?, ?>)target)
        );
    }
}
