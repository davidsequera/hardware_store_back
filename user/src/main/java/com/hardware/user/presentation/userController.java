package com.hardware.user.presentation;

import com.hardware.user.domain.entities.User;
import com.hardware.user.domain.inputs.UserInput;
import com.hardware.user.domain.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador de la entidad user que expone los servicios GraphQL para la entidad user
 * @author Sebastian Vergara, David Sequera
 * @version 1.0
 * @since 1.0
 * @see User
 * @see userService
 *
 */
@Controller
public class userController {
    @Autowired
    private userService userService;

    public userController(userService userService) {
        this.userService = userService;
    }
    @QueryMapping
    /**
     * Retorna todos los usuarios.
     * @return Flux<user>
     */
    public Flux<User> findAllUsers() {
        return userService.findAllUsers();
    }
    @QueryMapping
    /**
     * Retorna un usuario por su id.
     * @param user_id Identificador del usuario
     * @return Mono<user>
     */
    public Mono<User> findById(@Argument String user_id) {
        return userService.findById(user_id);
    }
    @QueryMapping
    /**
     * Retorna un usuario por su correo electronico.
     *
     * @param email Correo electronico del usuario
     * @return Mono<user>
     */
    public Mono<User> findByEmail(@Argument String email) {
        return userService.findByEmail(email);
    }
    @SchemaMapping(typeName = "user", field = "id")
    /**
     * Retorna un usuario por su id.
     * @param user_id Identificador del usuario
     * @return Mono<user>
     */
    public Mono<User> user(@Argument String user_id) {
        return userService.findById(user_id);
    }


    @MutationMapping
    /**
     * Actualiza un usuario por su id.
     * @param user_id Identificador del usuario
     *                user Usuario a actualizar
     * @return Mono<user>
     *
     */
    public Mono<User> updateUser(@Argument String user_id, @Argument User user) {
        return userService.updateUser(user_id, user);
    }

    @MutationMapping
    /**
     * Elimina un usuario por su id.
     * @param user_id Identificador del usuario
     * @return Mono<user>
     */
    public Mono<User> deleteUser(@Argument String user_id) {
        return userService.deleteUser(user_id);
    }

    @MutationMapping
    /**
     * Crea un usuario.
     * @param user Usuario a crear
     * @return Mono<user>
     */
    public Mono<User> createUser(@Argument UserInput input) {
        System.out.println("CreateUSER: "+input);
        return userService.createUser(input.toUser());
    }

}
