# Spring Web + Data

Aquecendo meu Spring Boot pelo [curso da Udemy](https://www.udemy.com/course/spring-boot-expert/).

* Spring Web
* Spring Data

Não fiz "cópia exata", fui brincando por conta própria (e apanhando muito).

Ao começar um projeto, dar uma passada de olho pelas anotações nos comentários do código-fonte. Aqui tratamos:

* Configurações
  * `@Configuration`
  * `@Bean`
  * `@Profile`
  
* Controladores REST
  * `@RestController` (= `@Controller` + `@ResponseBody` na classe)
  * `@RequestMapping`, `@GetMapping`, `@PostMapping`, `@...Mapping`: URLs
  * `@ResponseStatus`: status em caso de sucesso
  * `@RequestParam`: parâmetros GET, POST...
  * `@PathVariable`: ex.: `/clientes/{id}`
  * `@RequestBody`: objeto de entrada
  * `ResponseEntity`: controle da resposta (objeto + código e cabeçalhos HTTP)
* Validações
  * **O starter nas versões mais novas usa `jakarta.validation` ao invés de `javax.validation`!**
  * `@Valid`: marca os objetos validados no controlador
    * também marca atributos desses objetos (validação em cascata)
  * `@NotNull`, `@NotEmpty` (para Strings)
  * `@CPF`: já vem pronto!
  * `@Min`, `@Max`, `@DecimalMin`, `@DecimalMax`
* Tratamento de erros
  * no controller
  * geral via `ControllerAdvice`
* Tipos DTO para requisições e respostas
* `stream()` e programação "funcional" com Java
* JPA
  * **Nas versões novas do Hibernate usamos `jakarta.persistence` ao invés de `javax.persistence`!**
  * **`@OneToMany` não persiste os registros filhos automaticamente, deve haver um repositório e um comando explícito para salvá-los!**
  * Query methods
  * Custom queries
  * Otimizações
    * Logamos queries e detectamos casos de N+1
    * Usamos `JOIN FETCH`
  * `EntityManager` tratado em https://github.com/EdyKnopfler/spring-boot-udemy-spring-data-entity-manager
  * `@Transactional`
* `MessageSource`: leitura dos `messages.properties` de forma internacionalizada
  
