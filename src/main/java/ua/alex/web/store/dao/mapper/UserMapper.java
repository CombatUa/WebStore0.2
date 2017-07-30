package ua.alex.web.store.dao.mapper;

import ua.alex.web.store.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements AbstractMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet) {
        User user = new User();
        try {
            user.setId(resultSet.getLong("ID"));
            user.setFirstName(resultSet.getString("FIRST_NAME"));
            user.setLastName(resultSet.getString("LAST_NAME"));
            user.setSalary(resultSet.getDouble("SALARY"));
            user.setDateOfBirth(resultSet.getDate("DATE_OF_BIRTH").toLocalDate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}