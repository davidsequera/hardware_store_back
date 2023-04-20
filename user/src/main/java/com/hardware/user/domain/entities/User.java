package com.hardware.user.domain.entities;

// Para escribir un salto de linea en un comentario, usa <BR>
// Para cambiar el color de un comentario, usa <font color="red">texto</font>

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.lang.String;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    @Getter @Setter private String id;
    @Getter @Setter private String name;
    @Getter @Setter private String last_name;
    @Getter @Setter private String username;
    @Getter @Setter private String birthday;
    @Getter @Setter private String city_birth;
    @Getter @Setter private String status ;
    @Field(targetType = FieldType.OBJECT_ID)
    @Getter @Setter private List<String> credentials;

    /**
     * Constructor de la clase user.
     * @param id Identificador del usuario
     * @param name Nombre del usuario
     * @param last_name Apellido del usuario
     * @param birthday Cumpleaños del usuario.
     * @param city_birth Ciudad de nacimiento del usuario.
     */
    public User(String id, String name,String last_name, String username, String birthday, String city_birth) {
        this(name, last_name, username, birthday, city_birth);
        this.id = id;
    }

    public User(String name, String last_name, String username, String birthday, String city_birth) {
        this.name = name;
        this.username = username;
        this.last_name = last_name;
        this.birthday = birthday;
        this.city_birth = city_birth;
    }
}
