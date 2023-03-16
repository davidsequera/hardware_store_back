package com.hardware.user.presentation;

import com.hardware.user.domain.entities.User;
import com.hardware.user.domain.inputs.UserInput;
import com.hardware.user.domain.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
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

    /**
     * Retorna un usuario por su id.
     * @param user_id Identificador del usuario
     * @return Mono<user>
     */
    @QueryMapping //
    public Mono<User> getById(@Argument String id) {
        return userService.findById(id);
    }
    /**
     * Retorna un usuario por su correo electronico.
     *
     * @param email Correo electronico del usuario
     * @return Mono<user>
     */
    @QueryMapping
    public Mono<User> getByEmail(@Argument String email) {
        return userService.findByEmail(email);
    }


    /**
     * Actualiza un usuario por su id.
     * @return Mono<User>
     *
     */
    @MutationMapping
    public Mono<User> updateUser(@Argument UserInput input) {
        return userService.updateUser(input.toUser());
    }

    /**
     * Elimina un usuario por su id.
     * @param id Identificador del usuario
     * @return Mono<user>
     */
    @MutationMapping
    public Mono<User> deleteUser(@Argument String id) {
        return userService.deleteUser(id);
    }

    /**
     * Crea un usuario.
     * @param input Usuario a crear
     * @return Mono<user>
     */
    @MutationMapping
    public Mono<User> createUser(@Argument UserInput input) {
        System.out.println("CreateUSER: "+input);
        return userService.createUser(input.toUser());
    }

}
