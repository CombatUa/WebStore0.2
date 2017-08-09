package ua.alex.web.store.dao.jdbc;

import ua.alex.web.store.dao.UserDao;
import ua.alex.web.store.dao.mapper.UserMapper;
import ua.alex.web.store.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao<User, Long> {
    private static final UserMapper USER_MAPPER = new UserMapper();
    private static final String SQL_SELECT_ALL_USERS = "SELECT ID,FIRST_NAME,LAST_NAME,SALARY,DATE_OF_BIRTH FROM USERS";
    //    private static final String SQL_CREATE_USER = "INSERT INTO USERS (ID,FIRST_NAME,LAST_NAME,SALARY,DATE_OF_BIRTH) values (?,?,?,?,?)";
    private static final String SQL_CREATE_USER = "INSERT INTO USERS (ID,FIRST_NAME,LAST_NAME,SALARY,DATE_OF_BIRTH) VALUES (seq_users.nextval,?,?,?,?)";
    private static final String SQL_UPDATE_USER = "UPDATE USERS SET FIRST_NAME=?,LAST_NAME=?,SALARY=?,DATE_OF_BIRTH=? WHERE ID = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM USERS WHERE ID = ?";
    private static final String SQL_SELECT_USERS = "SELECT ID,FIRST_NAME,LAST_NAME,SALARY,DATE_OF_BIRTH FROM USERS WHERE ID=?";
    private static final String USER_COLS[] = {"ID", "FIRST_NAME", "LAST_NAME", "SALARY", "DATE_OF_BIRTH"};
    private Connection connection;

    public UserDaoImpl() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@//192.168.1.70:1521/orcl", "web_store", "web_store");
            System.err.println("Is generated keys supported:" + connection.getMetaData().supportsGetGeneratedKeys());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_USERS);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(USER_MAPPER.mapRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User getEntityByKey(Long key) {
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS)) {
            preparedStatement.setLong(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = USER_MAPPER.mapRow(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Long create(User entity) {
        boolean isSucess=true;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_USER, USER_COLS)) {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setDouble(3, entity.getSalary());
            preparedStatement.setDate(4, Date.valueOf(entity.getDateOfBirth()));
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }

        } catch (SQLException e) {
            isSucess=false;
            e.printStackTrace();
        }
        return isSucess?entity.getId():null;
    }

    @Override
    public boolean update(Long key, User entity) {
        boolean isSucsess = true;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)
        ) {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setDouble(3, entity.getSalary());
            preparedStatement.setDate(4, Date.valueOf(entity.getDateOfBirth()));
            preparedStatement.setLong(5, key);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            isSucsess = false;
            e.printStackTrace();
        }
        return isSucsess;
    }

    @Override
    public boolean delete(Long key) {
        boolean isSucsess = true;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER)) {
            preparedStatement.setLong(1, key);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            isSucsess = false;
            e.printStackTrace();
        }
        return isSucsess;
    }
}
