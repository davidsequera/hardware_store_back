    package com.hardware.user.persistence;

    import com.hardware.user.domain.entities.User;
    import org.springframework.data.mongodb.repository.Query;
    import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
    import reactor.core.publisher.Mono;

    /**
     * Repositorio de la entidad user que expone los servicios CRUD para la entidad user
     * @author Sebastian Vergara, David Sequera
     * @version 1.0
     * @since 1.0
     * @see User
     */
    public interface UserRepository extends ReactiveMongoRepository<User, String> {
        /**
         * Retorna un usuario por su correo electronico.
         * @param email Correo electronico del usuario
         * @return Mono<user>
         */
        @Query("{ 'email' : ?0 }")
        Mono<User> findByEmail(String email);

    }
