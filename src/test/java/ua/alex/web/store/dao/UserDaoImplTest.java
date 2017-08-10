package ua.alex.web.store.dao;

import org.junit.jupiter.api.Test;
import ua.alex.web.store.dao.jdbc.UserDaoImpl;
import ua.alex.web.store.entity.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {
    static final UserDaoImpl userDao = new UserDaoImpl();

    @Test
    void getEntityByKey() {
        assertEquals(userDao.getEntityByKey(1L).getLastName(), "Marley");

    }

    @Test
    void create() throws IOException {
        InputStream picture = Files.newInputStream(Paths.get("D:\\MyWork\\Projects\\GitHubProjects\\WebStore0.2\\src\\test\\testRecources\\Lighthouse.jpg"));
        byte[] bytes = new byte[picture.available()];
        picture.read(bytes);

        Long aLong = userDao.create(new User(-3L, "TestName", "TestLastName", 300.0, LocalDate.of(2000, 07, 17),bytes));
        assertEquals(userDao.getEntityByKey(aLong).getId(), aLong);
        userDao.delete(aLong);
    }

    @Test
    void update() {
        User testUser = new User(-4L, "TestName2", "TestLastName2", 300.0, LocalDate.of(2000, 07, 17));
        User testUpdaterUser = new User(-4L, "TestName21", "TestLastName2", 300.0, LocalDate.of(2000, 07, 17));
        Long aLong = userDao.create(testUser);
        assertTrue(userDao.update(aLong, testUpdaterUser));
        assertNotEquals(userDao.getEntityByKey(aLong).getFirstName(), testUser.getFirstName());
        userDao.delete(-4L);

    }

    @Test
    void delete() {
        User testUser = new User(-5L, "TestName2", "TestLastName2", 300.0, LocalDate.of(2000, 07, 17));
        Long aLong = userDao.create(testUser);
        assertTrue(userDao.delete(aLong));
    }

    @org.junit.jupiter.api.Test
    void getAll() {
        for (User user : userDao.getAll()) {
            System.out.println(user);
        }

    }

}