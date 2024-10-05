package br.com.schumaker.force.framework.jdbc;

import br.com.schumaker.force.framework.web.view.Page;
import br.com.schumaker.force.framework.web.view.Pageable;

import java.util.Optional;

/**
 * The SqlCrud interface.
 * It is responsible for CRUD operations.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * @Repository
 * public interface MyRepository extends DbCrud<Long, MyEntity> {}
 *
 * @Service
 * public class MyEntityService {
 *
 *     private final MyRepository repository;
 *
 *     public ProductService(MyRepository repository) {
 *         this.MyRepository = repository;
 *     }
 *
 *     public Long count() {
 *         return repository.count();
 *     }
 *
 *     public MyEntity getById(BigInteger id) {
 *         return repository.findById(id).orElse(null);
 *     }
 *
 *     public List<MyEntity> list() {
 *         return repository.findAll();
 *     }
 *
 *     public Long save(MyEntity myEntity) {
 *        return repository.save(myEntity).orElse(null);
 *     }
 * }}
 * </pre>
 *
 * @param <K> entity key.
 * @param <T> entity type.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public interface SqlCrud <K, T> {
    /**
     * Gets the table entity class type T.
     *
     * @return the class type.
     */
    Class<T> getEntityClass();

    /**
     * Gets the table name.
     *
     * @return the table name.
     */
    String getTableName();

    /**
     * Counts the number of records.
     *
     * @return the number of records.
     */
    Long count();

    /**
     * Finds an entity by its ID.
     *
     * @param id the entity ID.
     * @return an optional entity.
     */
    Optional<T> findById(K id);

    /**
     * Finds all entities.
     *
     * @return a page of entities.
     */
    Page<T> findAll();

    /**
     * Finds all entities.
     *
     * @param pageable the pageable.
     * @return a page of entities.
     */
    Page<T> findAll(Pageable pageable);

    /**
     * Saves an entity.
     *
     * @param entity the entity.
     * @return an optional entity.
     */
    Optional<K> save(T entity);

    /**
     * Updates an entity.
     *
     * @param entity the entity.
     */
    void update(T entity);

    /**
     * Deletes an entity.
     *
     * @param entity the entity.
     */
    void delete(T entity);

    /**
     * Deletes an entity by its ID.
     *
     * @param id the entity ID.
     */
    void deleteById(K id);
}
