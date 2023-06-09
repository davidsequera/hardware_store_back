package com.hardware.user.presentation;

import com.hardware.user.domain.entities.User;
import com.hardware.user.domain.inputs.UserInput;
import com.hardware.user.domain.UserService;
import graphql.GraphQLContext;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador de la entidad user que expone los servicios GraphQL para la entidad user
 * @author Sebastian Vergara, David Sequera
 * @version 1.0
 * @since 1.0
 * @see User
 * @see UserService
 *
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;



    public UserController(UserService userService) {
        this.userService = userService;
    }
    @QueryMapping
    // TODO: cambiar a isAuthenticated
    /**
     * Retorna todos los usuarios.
     * @return Flux<User>
     */
    public Flux<User> getAllUsers() {
//        HttpRequest request = context.get(HttpRequest.class);
        return userService.getAllUsers();
    }

    /**
     * Retorna un usuario por su id.
     * @param id Identificador del usuario
     * @param env DataFetchingEnvironment
     * @return Mono<User>
     */
    @QueryMapping
    @PreAuthorize("isAuthenticated")
    public Mono<User> getById(@Argument String id) {
//        HttpRequest request = context.get(HttpRequest.class);
        return userService.findById(id);
    }
    /**
     * Retorna un usuario por su correo electronico.
     *
     * @param email Correo electronico del usuario
     * @return Mono<User>
     */
    @QueryMapping
    @PreAuthorize("isAuthenticated")
    public Mono<User> getByEmail(@Argument String email) {
        return userService.findByEmail(email);
    }


    /**
     * Actualiza un usuario por su id.
     * @return Mono<User>
     *
     */
    @MutationMapping
    @PreAuthorize("isAuthenticated")
    public Mono<User> updateUser(@Argument UserInput input) {
        return userService.updateUser(input.toUser());
    }

    /**
     * Elimina un usuario por su id.
     * @param id Identificador del usuario
     * @return Mono<User>
     */
    @MutationMapping
    @PreAuthorize("isAuthenticated")
    public Mono<User> deleteUser(@Argument String id) {
        return userService.deleteUser(id);
    }

    /**
     * Crea un usuario.
     * @param input Usuario a crear
     * @return Mono<User>
     */
    @MutationMapping
    @PreAuthorize("isAuthenticated")
    public Mono<User> createUser(@Argument UserInput input) {
        System.out.println("CreateUSER: "+input);
        return userService.createUser(input.toUser());
    }

}
