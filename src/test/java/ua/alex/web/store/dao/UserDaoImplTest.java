package ua.alex.web.store.dao;

import org.junit.jupiter.api.Test;
import ua.alex.web.store.dao.ojdbc.UserDaoImpl;
import ua.alex.web.store.entity.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {
    static final UserDaoImpl userDao = new UserDaoImpl();

    @Test
    void getEntityByKey() {
        assertEquals(userDao.getEntityByKey(1L).getLastName(), "Marley");

    }

    @Test
    void create() {
        Long aLong = userDao.create(new User(-3L, "TestName", "TestLastName", 300.0, LocalDate.of(2000, 07, 17)));
        assertEquals(-3L, aLong.longValue());
        userDao.delete(-3L);
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