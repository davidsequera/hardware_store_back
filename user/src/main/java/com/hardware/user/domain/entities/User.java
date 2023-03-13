package com.hardware.user.domain.entities;

// Para escribir un salto de linea en un comentario, usa <BR>
// Para cambiar el color de un comentario, usa <font color="red">texto</font>

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Esta clase representa la entidad de usuario. Aqui se definen los atributos de la entidad.
 * @functions
 * Constructor de la clase user:<BR>
 * <strong><font color="red">public</font></strong> <strong><font color="white">user</font></strong><font color="gray">(</font><strong>
 * <font color="white">String</font> </strong><font color="#00FF00">id</font><font color="gray">,</font> <strong>
 *     <font color="white">String</font> </strong><font color="#00FF00">name</font><font color="gray">,</font> <strong>
 *         <font color="white">String</font> </strong><font color="#00FF00">lastName</font><font color="gray">,</font> <strong>
 *             <font color="white">String</font> </strong><font color="#00FF00">email</font><font color="gray">,</font> <strong>
 *                 <font color="white">String</font> </strong><font color="#00FF00">password</font><font color="gray">,</font> <strong>
 *                     <font color="white">String</font> </strong><font color="#00FF00">birthday</font><font color="gray">,</font> <strong>
 *                         <font color="white">String</font> </strong><font color="#00FF00">city_birth</font><font color="gray">)</font>
 *                         <BR>
 * Constructor de la clase user, sin parametros:<BR>
 *
 *
 * @author Sebastian Vergara, David Sequera
 * @version 1.0
 * @since 1.0
 *
 *
 *
 *
 */
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String birthday;
    private String city_birth;
    private boolean active = true;

    /**
     * Constructor de la clase user.
     * @param id Identificador del usuario
     * @param name Nombre del usuario
     * @param lastName Apellido del usuario
     * @param email Correo electronico del usuario
     * @param password Contraseña del usuario (Temporal)
     * @param birthday Cumpleaños del usuario.
     * @param city_birth Ciudad de nacimiento del usuario.
     */
    public User(String id, String name, String lastName, String email, String password, String birthday, String city_birth) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.city_birth = city_birth;
    }

    public User(String name, String lastName, String email, String password, String birthday, String city_birth) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.city_birth = city_birth;
    }

    /**
     * Retorna el id del usuario.
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Asigna el id del usuario.
     * @param id Identificador del usuario
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retorna el nombre del usuario.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Asigna el nombre del usuario.
     * @param name Nombre del usuario
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna el apellido del usuario.
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Asigna el apellido del usuario.
     * @param lastName Apellido del usuario
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retorna el correo electronico del usuario.
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Asigna el correo electronico del usuario.
     * @param email Correo electronico del usuario
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna la contraseña del usuario. Importante: Esta contraseña es temporal. Esta es una implementacion temprana, y se debe cambiar por una mas segura con
     * el modulo de asignacion.
     * // TODO: Cambiar la asignacion de la contraseña por una mas segura.
     * @deprecated Esta contraseña es temporal. Esta es una implementacion temprana, y se debe cambiar por una mas segura con el modulo de asignacion.
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Asigna la contraseña del usuario. Importante: Esta contraseña es temporal. Esta es una implementacion temprana, y se debe cambiar por una mas segura con
     * el modulo de asignacion.
     * // TODO: Cambiar la asignacion de la contraseña por una mas segura.
     * @deprecated Esta contraseña es temporal. Esta es una implementacion temprana, y se debe cambiar por una mas segura con el modulo de asignacion.
     * @param password Contraseña del usuario (Temporal)
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retorna el cumpleaños del usuario.
     * @return birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * Asigna el cumpleaños del usuario.
     * @param birthday Cumpleaños del usuario.
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * Retorna la ciudad de nacimiento del usuario.
     * @return city_birth
     */
    public String getCity_birth() {
        return city_birth;
    }

    /**
     * Asigna la ciudad de nacimiento del usuario.
     * @param city_birth Ciudad de nacimiento del usuario.
     */
    public void setCity_birth(String city_birth) {
        this.city_birth = city_birth;
    }

    /**
     * Retorna el estado del usuario.
     * @return active
     */
    public boolean isActive() {
        return active;
    }
    /**
     * Asigna el estado del usuario.
     * @param active Estado del usuario.
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
