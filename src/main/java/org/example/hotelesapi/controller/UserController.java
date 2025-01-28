package org.example.hotelesapi.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.hotelesapi.models.User;
import org.example.hotelesapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
@Tag(name = "Usuarios", description = "Gestión de usuarios")
// Se pueden definir las respuestas a nivel de clase en lugar de en cada función.
// El cuerpo de cada respuesta si no se indica se genera automáticamente, escogiendo por defecto el ejemplo
// indicado en la clase modelo.
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación correcta"),
        // Pero si especificas el contenido puedes sobreescribir el ejemplo, en este caso para que esté vacío.
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content(examples = @ExampleObject(value = ""))),
        // También se puede dejar vacío el content y tampoco se vería nada.
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Permiso denegado", content = @Content),
})
public class UserController {
    private final UserService service;

    public UserController(UserService userService) {
        this.service = userService;
    }

    @PostMapping("login")
    @Operation(summary = "Inicio de sesión", description = "El usuario inicia sesión")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        try {
            User user = service.findByUsernameAndPassword(username, DigestUtils.sha256Hex(password));
            if (user == null) {
                return new ResponseEntity<>("Usuario o contraseña incorrectas", HttpStatus.NOT_FOUND);
            }
            String token = getJWTToken(user.getUsername());
            user.setToken(token);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al guardar el usuario", e);
        }
    }

    @PostMapping("register")
    @Operation(summary = "Registra un usuario", description = "Guarda un usuario en la base de datos")
    // @Valid valída el body antes de que el controlador lo reciba, los criterios son definidos en la clase modelo,
    // como las anotaciones @NotNull
    public User register(@RequestBody @Valid User user) {
        try {
            // Si el usuario ya existe da un error 403
            user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
            return service.save(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al guardar el usuario", e);
        }
    }

    //Utilizamos el método getJWTToken(...) para construir el token,
    // delegando en la clase de utilidad Jwts que incluye información sobre su expiración
    // y un objeto de GrantedAuthority de Spring que, como veremos más adelante,
    // usaremos para autorizar las peticiones a los recursos protegidos.
    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
