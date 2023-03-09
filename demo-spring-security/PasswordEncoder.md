# <span style="color: red"> PasswordEncoder </span>

```java
class MyPasswordEncoder {
    @Bean
    PasswordEncoder encoder() {
        //Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        //Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        //SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8();
        //new BCryptPasswordEncoder(16);
        return NoOpPasswordEncoder.getInstance();
    }
}
```
