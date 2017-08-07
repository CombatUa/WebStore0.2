package ua.alex.web.store.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface GenericMapper<E> {
    E mapRow(ResultSet resultSet) throws SQLException;
}
