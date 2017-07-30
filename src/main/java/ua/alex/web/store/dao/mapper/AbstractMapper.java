package ua.alex.web.store.dao.mapper;

import java.sql.ResultSet;

public interface AbstractMapper<E> {
    E mapRow(ResultSet resultSet);
}
