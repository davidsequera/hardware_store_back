package com.hardware.user.domain;

import com.hardware.user.domain.entities.User;
import com.hardware.user.persistence.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Esta clase representa el servicio de la entidad user. Aqui se definen los metodos que se pueden realizar con la entidad user.
 * @author Sebastian Vergara, David Sequera
 * @version 1.0
 * @since 1.0
 * @see User
 * @see userRepository
 */
@Service
public class userService {
    @Autowired
    private userRepository userRepository;

    /**
     * Retorna todos los usuarios.
     * @return Flux<user>
     */
    public Flux<User> findAllUsers() {
        System.out.println("findAllUsers");
        return userRepository.findAll();
    }

    /**
     * Retorna un usuario por su id.
     * @param user_id Identificador del usuario
     * @return Flux<user>
     */
    public Mono<User> findById(String user_id) {
        return userRepository.findById(user_id);
    }

    /**
     * Retorna un usuario por su correo electronico.
     * @param email Correo electronico del usuario
     * @return Flux<user>
     */
    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Actualiza un usuario por su id.
     * @param id Identificador del usuario
     * @param user Usuario a actualizar
     * @return Mono<user> Usuario actualizado
     */
    public Mono<User> updateUser(String id, User user) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setLastName(user.getLastName());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setBirthday(user.getBirthday());
                    existingUser.setCity_birth(user.getCity_birth());
                    return userRepository.save(existingUser);
                });
    }

    /**
     * Basado en el id del usuario, borra el usuario. El borrado es un Soft Delete, es decir, el usuario no se elimina de la base de datos, sino que se cambia el estado de su atributo active a false.
     * @param id Identificador del usuario
     * @return Mono<Boolean> true si el usuario fue borrado, false si no se pudo borrar el usuario
     */
    public Mono<User> deleteUser(String id) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setActive(false);
                    return userRepository.save(existingUser);
                });
    }

    /**
     * Crea un usuario
     * @param user Usuario a crear
     * @return Mono<user> Usuario creado
     */
    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }
}
