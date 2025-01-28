package org.example.hotelesapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
// Lo que se defina aquí en @Schema se verá en los ejemplos de los request body
// Para que el ejemplo del body se muestre correctamente hay que hacerlo aquí, no en el @Parameter
@Schema(example = """
        {
        "username": "juan",
        "password": "juan"
        }""")
public class User {
    // Lo que se defina aquí en @Schema se verá en el apartado de Schemas de la documentación
    @Schema(description = "Identificador del usuario", example = "1111", requiredMode = Schema.RequiredMode.REQUIRED)
    @Id
    // Si no hay getter, no se muestra en el ejemplo de la documentación.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "Nombre de usuario", example = "angel", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Column(nullable = false)
    @Getter
    private String username;

    @Schema(description = "Contraseña del usuario", example = "!9aF#54g*3uI", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Getter
    @Column(nullable = false)
    private String password;

    @Transient
    @Getter
    @Setter
    private String token;
}
