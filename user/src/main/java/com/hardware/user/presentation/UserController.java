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
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.http.HttpRequest;

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
    /**
     * Retorna todos los usuarios.
     * @return Flux<user>
     */
    public Flux<User> getAllUsers(DataFetchingEnvironment env) {
        GraphQLContext context = env.getGraphQlContext();
//        HttpRequest request = context.get(HttpRequest.class);
        System.out.println(context.toString());
        return userService.getAllUsers();
    }

    /**
     * Retorna un usuario por su id.
     * @param id Identificador del usuario
     * @return Mono<user>
     */
    @QueryMapping //
    public Mono<User> getById(@Argument String id, DataFetchingEnvironment env) {
        GraphQLContext context = env.getGraphQlContext();
//        HttpRequest request = context.get(HttpRequest.class);
        System.out.println(context);
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

    static class ErrorMessage {
        private final String message;

        public ErrorMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}
