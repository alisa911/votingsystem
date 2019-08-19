package com.plotva.votingsystem.service;

import com.plotva.votingsystem.model.Role;
import com.plotva.votingsystem.model.User;


import com.plotva.votingsystem.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static com.plotva.votingsystem.UserTestData.*;


public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void create() {
        User newUser = new User(
                null,
                "Nata",
                LocalDateTime.now(),
                "qe@gmail.com",
                "qe",
                true,
                Collections.singleton(Role.ROLE_USER
                ));
        User created = service.create(newUser);
        newUser.setId(created.getId());
        assertMatch(service.getAll(), newUser, FIRST_USER, SECOND_USER);
    }


    @Test
    public void delete() {
        service.delete(FIRST_USER_ID);
        assertMatch(service.getAll(), SECOND_USER);
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(9999));
    }

    @Test
    public void get() {
        User user = service.get(FIRST_USER_ID);
        assertMatch(user, FIRST_USER);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(999));
    }

    @Test
    public void getByEmail() {
        User user = service.getByEmail(FIRST_USER_EMAIL);
        assertMatch(user, FIRST_USER);
    }

    @Test
    public void getByEmailNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByEmail("unknownEmail@email.com"));
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(), FIRST_USER, SECOND_USER);
    }

    @Test
    public void update() {
        User user = new User(FIRST_USER);
        user.setEmail("munoon@gmail.com");
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        service.update(user);
        assertMatch(service.get(FIRST_USER_ID), user);
    }
}