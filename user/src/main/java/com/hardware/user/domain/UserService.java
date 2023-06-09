package com.hardware.user.domain;

import com.hardware.user.domain.entities.User;
import com.hardware.user.persistence.UserRepository;
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
 * @see UserRepository
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Retorna todos los usuarios.
     * @return Flux<User>
     */
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retorna un usuario por su id.
     * @param id Identificador del usuario
     * @return Flux<user>
     */
    public Mono<User> findById(String id) {
        return userRepository.findById(id);
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
     * @param user Usuario a actualizar
     * @return Mono<user> Usuario actualizado
     */
    public Mono<User> updateUser(User user) {
        return userRepository.findById(user.getId())
                .flatMap(existingUser -> {
                    if (user.getName() != null && !user.getName().isEmpty()) {
                        existingUser.setName(user.getName());
                    }
                    if (user.getLast_name() != null && !user.getLast_name().isEmpty()) {
                        existingUser.setLast_name(user.getLast_name());
                    }
                    if (user.getUsername() != null && !user.getUsername().isEmpty()) {
                        existingUser.setLast_name(user.getUsername());
                    }
                    if (user.getBirthday() != null) {
                        existingUser.setBirthday(user.getBirthday());
                    }
                    if (user.getCity_birth() != null && !user.getCity_birth().isEmpty()) {
                        existingUser.setCity_birth(user.getCity_birth());
                    }
                    if(user.getCredentials() != null && !user.getCredentials().isEmpty()){
                        existingUser.setCredentials(user.getCredentials());
                    }
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
                    existingUser.setStatus("inactive");
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
