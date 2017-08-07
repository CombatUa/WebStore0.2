package ua.alex.web.store.dao;

import java.util.List;

public interface GenericDao<E, K> {
    List<E> getAll();

    E getEntityByKey(K key);

    K create(E entity);

    boolean update(K key, E entity);

    boolean delete(K key);

}
