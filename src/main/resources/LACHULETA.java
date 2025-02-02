package com.erikat.practica_busqueda_hoteles_jwt;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.jsonwebtoken.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class LACHULETA {

//Chuleta con cositas:
/*------------------------------------------------------------------------------------------------------------------------------------------*/

    /*
    ESTRUCTURA DE MVC CON SpringBoot
        - MODELOS (Clases que va a usar)
        - DTO (Records que forman los modelos sin relaciones ni identidades)
        - CONTROLLERS (Guardan todos los accesos a los EndPoints)
        - SERVICES (Se conectan a los Controllers y les pasan información de los repositorios)
        - REPOSITORY (Se conectan a los servicios y se pasan los datos de la base de datos a estos, son interfaces que extienden de JPARepository<K, V>)
     */

    ///Controllers Empiezan con:
    //@RestController @Tag(name = "nombre que queremos ver en Swagger", description = "breve descripción")
    //@RequestMapping("principio del endpoint")
    @RestController
    @Tag(name = "Habitaciones", description = "Controles de habitaciones")
    @RequestMapping("/api/habitaciones")
    public class HabitacionController {
        //Con AutoWired nos ahorramos el paso de crear el constructor, pero todas las etiquetas tienen que estar bien nombradas
        @Autowired
        private HabitacionService habitacionService;
    }

    //Clase Servicio
    //Se pone @Service arriba y se vincula un repositorio. Las funciones actúan sobre el repositorio.
    @Service
    public class HabitacionService {
        @Autowired
        private HabitacionRepository habitacionRepository;
    }
    //Clase Repositorio
    //Se pone @Repository y de qué hereda
    @Repository
    public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {
        //Aqui dentro pueden ir funciones propias generadas por el repositorio.
        //También se pueden hacer queries personalizadas con SQL y JPQL
        @Query("SELECT h from Hotel h where h.habitaciones.size > 1")
        public List<Hotel> listHotelesWithHabitacionesJPQL();

        @Query("SELECT * from hotel h where (SELECT count(*) from habitacion where hotel = h.id)>0")
        public List<Hotel> listHotelesWithHabitacionesSQL();
    }

    /*------------------------------------------------------------------------------------------------------------------------------------------*/

    //Mappings
    @GetMapping("endpoint")
    @Operation(summary = "descripción de la operación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "codigo", description = "Descripción de qué hace", content = @Content (array = @ArraySchema(schema = @Schema(implementation = String.class)))),
            @ApiResponse(responseCode = "codigo", description = "Descripción", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "codigo", description = "Descripción"),
    })
    public ResponseEntity<?> funcion(@PathVariable("id") int id, @RequestBody UserDTO dto, @RequestParam(name = "username") String username){
        return new ResponseEntity<String>("Objeto_A_Devolver_Aqui", HttpStatus.OK);
    }
/*
Se puede usar en cualquier Mapping (GetMapping, PostMapping, PutMapping, DeleteMapping) y hacen referencia a lo siguiente:
- @Operation y @ApiResponses es para dar una personalización de swagger,
    - summary en @Operation carga una
    - ApiResponses se divide en etiquetas ApiResponse
        - responseCodee es el codigo que devuelve la respuesta que estas apuntando (200, 300, 401..)
        - description es una breve descripción de qué sucede
        - content (no es obligatorio) sirve para poner la estructura de lo que devuelve
            - @Content(schema = @Schema(implementation = NombreClase.class)) sirve para mostrar que devuelve un único objeto de una clase
            - @Content(array = @ArraySchema(schema = @Schema(implementation = NombreClase.class))) sirve para mostrar que devuelve una lista de objetos de esa clase

Con ResponseEntity lo que hacemos es formar una respuesta de una peticion HTTP, donde podemos pasar tanto el codigo de respuesta como el propio objeto de respuesta.

Además de esto, hay varios parámetros con los que poder trabajar:
    - PathVariable("nombre") -> Un objeto que se recibe en la URL
    - RequestBody -> Un objeto que se recibe por el cuerpo de la petición
    - RequestParam("nombre") -> Un objeto que se recibe de un "formulario" formato www-xx-raw
 */

/*------------------------------------------------------------------------------------------------------------------------------------------*/

    //Entidad de una tabla de la BD.
    //Se pone @Entity y @Table(name = "nombreTablaEnBD)
    @Entity
    @Table(name = "user")
    public class User {
        //Identificador
        //Se pone @Id @GeneratedValue(strategy = GenerationType.IDENTITY) para marcar que se autoincrementa la clave primaria.
        @Id
        @GeneratedValue
        @Column(name = "username")
        private String username;

        //En todas las columnas se puede poner la etiqueta @Column(name = "nombreColumnaEnTabla")
        //Digo se puede porque, si la columna se llama igual que el atributo, no es necesario
        @Column(name = "password")
        private String password;

        /*
        Cosas que SIEMPRE y digo SIEMPRE, tiene que tener los objetos de Hibernate:
            1. Constructor Vacío
            2. Constructor Completo
            3. Getters y Setters
         */

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public User() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

/*------------------------------------------------------------------------------------------------------------------------------------------*/

    /*
    RELACIONES:
    Vamos a imaginar que tenemos dos clases con información ligada, específicamente con una relación 1:N. En Hibernate se haría lo siguiente:
    HOTEL - HABITACIÓN -> 1 Hotel tiene varias habitaciones y una habitación tiene solo un hotel:
     */
    @Entity
    @Table(name="hotel")
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    class Hotel{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        int id;

        //En la relacion 1:N este es el (1,1), se escribe:
        //La etiqueta @OneToMany(mappedBy = "nombre del objeto de la otra clase", cascade = CascadeType.ALL)
        //Además, se añade JsonManagedReference para evitar que haya problemas al crear el JSON
        @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
        @JsonManagedReference
        public List<Habitacion> habitaciones;
    }

    @Entity
    @Table(name = "habitacion")
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    class Habitacion{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        int id;

        //En la Relación 1:N este es el (1,N). Se pone:
        //@ManyToOne
        //@JoinColumn(name = "nombre de la tabla de referencia", referencedColumnName = "nombre del id al que mira")
        //Además se añade JsonBackReference para que no haya problemas con bucles infinitos de mostrar información en el item
        @ManyToOne
        @JoinColumn(name = "hotel", referencedColumnName = "id")
        @JsonBackReference
        private Hotel hotel;
    }
/*------------------------------------------------------------------------------------------------------------------------------------------*/

    /*
    CLASES DTO:
    Imaginemos por un momento que tenemos una clase con relaciones en la base de datos, o que tenemos en esa clase más o menos información de la que físicamente podemos hacer.
    Pues en este caso, para no usar esa clase para la obtención de datos, se crea una clase que tiene su mismo nombre y atributos pero con DTO
     */

    //Un ejemplo con usuario, tenemos arriba la clase User, con un nombre y una contraseña,
    //pero queremos meter un token para pasar toda la información al usuario
    //Pero el token no pinta nada en la tabla de la BD. Pues se haria una clase como la siguiente:
    public record UserDTO(String userName, String password, String token){}

    //Es un Record, una especie de Data Class que deja guardar y usar valores, pero nunca sobreescribirlos. Para estas cosas está bastante bien

/*------------------------------------------------------------------------------------------------------------------------------------------*/

    //Función de obtención de JWT:
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

    //Luego para devolver el token puede ser algo asi:
    public UserDTO login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password){
        try{
            User u = new User(); //Como lo saques aquí
            if (u.getPassword().equals(password)){
                return new UserDTO(u.getUsername(), u.getPassword(), getJWTToken(u.getUsername()));
            }else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error de Contraseña");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error de búsqueda", e);
        }
    }

/*------------------------------------------------------------------------------------------------------------------------------------------*/

    ///Clase interna de WebSecurity. Importante poner @EnableWebSecurity para que funcione y @Configuration
    @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        //Lista de URLs autorizadas. Estas tienen que estar siempre para que funcione Swagger como debería
        private static final String[] AUTH_WHITELIST = {
                // -- Swagger UI v2
                "/v2/api-docs",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                // -- Swagger UI v3 (OpenAPI)
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/doc/**"
                // other public endpoints of your API may be appended to this array
        };

        //Funcón de configuración de seguridad Http.
        //Se habilitan urls abiertas con .antMatchers(metodo, url).permitAll() y se prohiben con .authenticated().
        //Normalmente se acaba poniendo .anyRequest().authenticated() para prohibir las no escritas arriba.
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/user").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/hoteles/localidad/{loc}").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/hoteles/categoria/{cat}").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/hoteles/hotel/{id}/habitaciones/tamEntre/{mintam}:{maxtam}").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/hoteles/hotel/{id}/habitaciones/precioEntre/{minprice}:{maxprice}").permitAll()
                    .antMatchers(AUTH_WHITELIST).permitAll()
                    // CUALQUIER SOLICITUD NO PUESTA ARRIBA DEBE SER AUTENTICADA, DE LO CONTRARIO DEVOLVERA UNA RESPUESTA 401
                    .anyRequest().authenticated();

        }

    }

/*------------------------------------------------------------------------------------------------------------------------------------------*/

    //Clase de Autorización de JWT
    public class JWTAuthorizationFilter extends OncePerRequestFilter {
        //Cabecera de JWT
        private final String HEADER = "Authorization";
        //Prefijo
        private final String PREFIX = "Bearer ";
        //Clasve secreta
        private final String SECRET = "mySecretKey";

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
            try {

                if (checkJWTToken(request, response)) {
                    Claims claims = validateToken(request);
                    if (claims.get("authorities") != null) {
                        setUpSpringAuthentication(claims);
                    } else {
                        SecurityContextHolder.clearContext();
                    }
                } else {
                    SecurityContextHolder.clearContext();
                }
                chain.doFilter(request, response);
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
                return;
            }
        }

        private Claims validateToken(HttpServletRequest request) {
            String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
            return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
        }

        private void setUpSpringAuthentication(Claims claims) {
            @SuppressWarnings("unchecked")
            List<String> authorities = (List<String>) claims.get("authorities");

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                    authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            SecurityContextHolder.getContext().setAuthentication(auth);

        }

        private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
            String authenticationHeader = request.getHeader(HEADER);
            if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
                return false;
            return true;
        }

    }
/*------------------------------------------------------------------------------------------------------------------------------------------*/

    /*
    POSIBLE ENTRADA EN EL EXAMEN: RELACIÓN MANY TO MANY:
    Imaginemos que tenemos dos tablas: Una de alumnos y otra de profesores, y queremos hacer la relación Many to Many de esas tablas
     */

    @Entity
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Data
    @Table(name = "alumno")
    class Alumno{
        @Id
        @Column(name = "dnialum")
        private String dni;

        @Column(name = "name")
        private String name;

        @Column(name = "clase")
        private String clase;

        //ojo la siguiente definicion va toda junta
        @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
                CascadeType.REFRESH }, fetch = FetchType.LAZY)
        @JoinTable(name = "alumno_profesor", //OJO es donde le digo que se crea la tabla
                joinColumns = @JoinColumn(name="dnialum"), //Columna de esta tabla
                inverseJoinColumns = @JoinColumn(name="dniprofe")) //Columna de la otra tabla
        @JsonManagedReference
        private List<Profesor> profesores;
    }

    @Entity
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Data
    @Table(name = "profesor")
    class Profesor{
        @Id
        @Column(name = "dniprofe")
        private String dni;

        @Column(name = "name")
        private String name;

        @Column(name = "age")
        private int age;

        //ojo la siguiente definicion va toda junta
        @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
                CascadeType.REFRESH }, fetch = FetchType.LAZY)
        @JoinTable(name = "alumno_profesor", //OJO es donde le digo que se crea la tabla
                joinColumns = @JoinColumn(name="dniprofe"), //Columna de esta tabla
                inverseJoinColumns = @JoinColumn(name="dnialum")) //Columna de la otra tabla
        @JsonManagedReference
        private List<Alumno> alumnos;
    }
}