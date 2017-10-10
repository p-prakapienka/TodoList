package com.gpsolutions.todolist.repository;

import static com.gpsolutions.todolist.data.TestData.USER;

import com.gpsolutions.todolist.model.User;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void testFindByUsername() {
        val user = repository.findByUsername(USER.getUsername());

        Assert.assertEquals(USER, user);

        log.info("\nUser found: \n{}", user);
    }

}
