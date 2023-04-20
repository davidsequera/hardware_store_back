package com.hardware.user.domain.inputs;

import com.hardware.user.domain.entities.User;
import lombok.AllArgsConstructor;

/**
 * Esta clase representa la entrada de usuario. Aqui se definen los atributos de la entrada.
 * Utiliza el allargsconstructor de lombok para crear el constructor con todos los atributos.
 * @author Sebastian Vergara, David Sequera
 * @version 1.0
 * @since 1.0
 */
@AllArgsConstructor
public class UserInput {
    public String id;
    public String name;
    public String last_name;
    public String username;
    public String email;
    public String password;
    public String birthday;
    public String city_birth;

    /**
     * Metodo que convierte la entrada de usuario a la entidad de usuario.
     * @return user: Nueva entidad de usuario, con los atributos de la entrada.
     */
    public User toUser() {
        if (id == null) {
            return new User(name,username, last_name, birthday, city_birth);
        }
        return new User(id, name, last_name, username, birthday, city_birth);
    }

    /**
     * Metodo que retorna la representacion en string de la entrada de usuario.
     * @return String: Representacion en string de la entrada de usuario.
     */
    @Override
    public String toString(){
        return "createUserInput{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthday='" + birthday + '\'' +
                ", city_birth='" + city_birth + '\'' +
                '}';
    }

}
