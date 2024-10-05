package br.com.schumaker.force.framework.ioc.managed;

import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.jdbc.SqlCrudImpl;
import br.com.schumaker.force.framework.jdbc.SqlCrudInterceptor;

import java.lang.reflect.ParameterizedType;

/**
 * The ManagedRepository class represents a managed repository within the IoC container.
 * It implements the ManagedClass interface and provides methods to retrieve the fully qualified name (FQN)
 * and the instance of the managed repository.
 *
 * @see ManagedClass
 *
 * @author Hudson Schumaker
 * @version 1.1.0
 */
public final class ManagedRepository implements ManagedClass<ManagedRepository> {
    private static final String SQL_CRUD_INTERFACE = "br.com.schumaker.force.framework.jdbc.SqlCrud";
    private final String fqn;
    private Object instance;

    private ManagedRepository(String fqn) {
        this.fqn = fqn;
    }

    /**
     * Creates a new ManagedRepository instance using the specified repository class.
     * The instance is created using reflection.
     *
     * @param repository the repository class.
     * @return a new ManagedRepository instance.
     */
    public static ManagedRepository builder(Class<?> repository) {
        var fqn = repository.getName();
        var managedRepository = new ManagedRepository(fqn);

        var genericInterfaces = repository.getGenericInterfaces();
        for (var genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType parameterizedType) {
                if (parameterizedType.getRawType().getTypeName().equals(SQL_CRUD_INTERFACE)) {
                    var pk = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                    var clazz = (Class<?>) parameterizedType.getActualTypeArguments()[1];
                    var sqlCrudImpl = SqlCrudImpl.create(pk, clazz);

                    var proxy = SqlCrudInterceptor.createProxy(sqlCrudImpl, repository);
                    managedRepository.instance = repository.cast(proxy);
                    return managedRepository;
                }
            }
        }

        throw new ForceException("Repository class does not have a parameterized superinterface of type SqlCrud.");
    }

    @Override
    public String getFqn() {
        return fqn;
    }

    @Override
    public Object getInstance() {
        return instance;
    }
}
