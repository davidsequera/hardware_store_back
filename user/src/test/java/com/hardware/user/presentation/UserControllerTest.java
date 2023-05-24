package com.hardware.user.presentation;

import com.hardware.user.domain.entities.User;
import com.hardware.user.domain.inputs.UserInput;
import com.hardware.user.domain.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(Flux.just(new User(), new User()));

        Flux<User> users = userController.getAllUsers();

        Assertions.assertNotNull(users);
        Assertions.assertEquals(2, users.count().block());
    }

    @Test
    void testGetById() {
        when(userService.findById(any())).thenReturn(Mono.just(new User()));

        Mono<User> user = userController.getById("123");

        Assertions.assertNotNull(user);
    }

    @Test
    void testGetByEmail() {
        when(userService.findByEmail(any())).thenReturn(Mono.just(new User()));

        Mono<User> user = userController.getByEmail("test@example.com");

        Assertions.assertNotNull(user);
    }

    @Test
    void testUpdateUser() {
        UserInput userInput = new UserInput("123", "Jhon", "Doe", "jhon", "jhon@mail.com", "124","23-12-2023","Bogota");

        when(userService.updateUser(any())).thenReturn(Mono.just(new User()));

        Mono<User> updatedUser = userController.updateUser(userInput);

        Assertions.assertNotNull(updatedUser);
    }

    @Test
    void testDeleteUser() {
        when(userService.deleteUser(any())).thenReturn(Mono.just(new User()));

        Mono<User> deletedUser = userController.deleteUser("123");

        Assertions.assertNotNull(deletedUser);
    }

    @Test
    void testCreateUser() {
        UserInput userInput = new UserInput("123", "Jhon", "Doe", "jhon", "jhon@mail.com", "124","23-12-2023","Bogota");

        when(userService.createUser(any())).thenReturn(Mono.just(new User()));

        Mono<User> createdUser = userController.createUser(userInput);

        Assertions.assertNotNull(createdUser);
    }
}
