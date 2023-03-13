    package com.hardware.user.persistence;

    import com.hardware.user.domain.entities.User;
    import org.springframework.data.mongodb.repository.Query;
    import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
    import reactor.core.publisher.Flux;
    import reactor.core.publisher.Mono;

    /**
     * Repositorio de la entidad user que expone los servicios CRUD para la entidad user
     * @author Sebastian Vergara, David Sequera
     * @version 1.0
     * @since 1.0
     * @see User
     */
    public interface userRepository extends ReactiveMongoRepository<User, String> {
        @Query("{ 'email' : ?0 }")
        /**
         * Retorna un usuario por su correo electronico.
         * @param email Correo electronico del usuario
         * @return Mono<user>
         */
        Mono<User> findByEmail(String email);

        /**
         * Retorna un usuario por su id.
         *
         * @param user_id Identificador del usuario
         * @return Mono<user>
         */
        @Query("{ '_id' : ?0 }")
        Flux<User> findAllById(String user_id);

        /**
         * Retorna todos los usuarios.
         * @return Flux<user>
         */
        @Query("{}")
        Flux<User> findAllUsers();
    }
