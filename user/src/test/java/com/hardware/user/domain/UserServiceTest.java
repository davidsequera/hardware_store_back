package com.hardware.user.domain;

import com.hardware.user.domain.entities.User;
import com.hardware.user.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetAllUsers() {
        // Arrange
        User user1 = new User("1", "John", "Doe", "jhon404","12/12/2001", "Duitama");
        User user2 = new User("2", "Jane", "Smith", "jane.smith@example.com", "12/12/2001", "Duitama");
        when(userRepository.findAll()).thenReturn(Flux.just(user1, user2));

        // Act
        Flux<User> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.count().block());
    }

    @Test
    public void testFindById() {
        // Arrange
        String userId = "1";
        User expectedUser = new User("1", "John", "Doe", "jhon404","12/12/2001", "Duitama");
        when(userRepository.findById(userId)).thenReturn(Mono.just(expectedUser));

        // Act
        Mono<User> result = userService.findById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUser, result.block());
    }

    @Test
    public void testFindByEmail() {
        // Arrange
        String email = "john.doe@example.com";
        User expectedUser = new User("1", "John", "Doe", "jhon404","12/12/2001", "Duitama");
        when(userRepository.findByEmail(email)).thenReturn(Mono.just(expectedUser));

        // Act
        Mono<User> result = userService.findByEmail(email);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUser, result.block());
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        User user = new User("1", "John", "Doe", "jhon404","12/12/2001", "Duitama");
        when(userRepository.findById(user.getId())).thenReturn(Mono.just(user));
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        // Act
        Mono<User> result = userService.updateUser(user);

        // Assert
        assertNotNull(result);
        assertEquals(user, result.block());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        String userId = "1";
        User user = new User("1", "John", "Doe", "jhon404","12/12/2001", "Duitama");
        when(userRepository.findById(userId)).thenReturn(Mono.just(user));
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        // Act
        Mono<User> result = userService.deleteUser(userId);

        // Assert
        assertNotNull(result);
        assertEquals("inactive", result.block().getStatus());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testCreateUser() {
        // Arrange
        User user = new User("1", "John", "Doe", "jhon404","12/12/2001", "Duitama");
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        // Act
        Mono<User> result = userService.createUser(user);

        // Assert
        assertNotNull(result);
        assertEquals(user, result.block());
        verify(userRepository, times(1)).save(user);
    }
}
