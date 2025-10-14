# JWT Authentication Quick Reference

## 🚀 Quick Setup Checklist

### 1. Add Dependencies (pom.xml)
```xml
<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- JWT (All 3 required) -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
```

### 2. Configure application.properties
```properties
# JWT Configuration
jwt.secret=YOUR_BASE64_SECRET_KEY_HERE
jwt.expiration=86400000

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Create Required Files (in order)

#### Step 1: Entity
- `User.java` - implements `UserDetails`

#### Step 2: Repository
- `UserRepository.java` - with `findByEmail()` method

#### Step 3: DTOs
- `RegisterRequest.java`
- `LoginRequest.java`
- `AuthResponse.java`

#### Step 4: Security Components
- `JwtService.java` - Token generation & validation
- `JwtAuthFilter.java` - Filter for each request
- `JwtAuthenticationEntryPoint.java` - 401 error handler
- `SecurityConfig.java` - Main security configuration

#### Step 5: Service Layer
- `AuthService.java` - Interface
- `AuthServiceImpl.java` - Implementation

#### Step 6: Controller
- `AuthController.java` - REST endpoints

---

## 📋 File Templates

### User Entity (Key Points)
```java
@Entity
@Table(name="users")
public class User implements UserDetails {
    // Must implement:
    // - getAuthorities() -> returns roles
    // - getUsername() -> return email
    // - getPassword() -> return password
    // - isAccountNonExpired() -> return true
    // - isAccountNonLocked() -> return true
    // - isCredentialsNonExpired() -> return true
    // - isEnabled() -> return true
}
```

### SecurityConfig (Key Points)
```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()  // Public
                .anyRequest().authenticated()                 // Protected
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
```

### JwtService (Key Methods)
```java
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;
    
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    
    // Key methods:
    // - generateToken(UserDetails)
    // - extractUsername(String token)
    // - isTokenValid(String token, UserDetails)
}
```

### JwtAuthFilter (Key Logic)
```java
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(...) {
        // 1. Extract token from "Authorization: Bearer <token>"
        // 2. Extract username from token
        // 3. Load user from database
        // 4. Validate token
        // 5. Set authentication in SecurityContext
        // 6. Continue filter chain
    }
}
```

---

## 🔑 API Endpoints

### Register
```http
POST /api/auth/register
Content-Type: application/json

{
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": "SecurePass123!",
  "phoneNumber": "1234567890",
  "address": "123 Main St"
}
```

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "SecurePass123!"
}
```

### Response Format
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "john@example.com",
  "fullName": "John Doe",
  "role": "USER"
}
```

### Using the Token
```http
GET /api/protected-endpoint
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## 🛠️ Testing Commands

### Generate JWT Secret
```bash
openssl rand -base64 64
```

### Test with cURL

**Register:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Test User",
    "email": "test@example.com",
    "password": "Password123!",
    "phoneNumber": "1234567890",
    "address": "123 Test St"
  }'
```

**Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "Password123!"
  }'
```

**Access Protected Endpoint:**
```bash
curl -X GET http://localhost:8080/api/your-endpoint \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

---

## ⚠️ Common Errors & Fixes

| Error | Cause | Solution |
|-------|-------|----------|
| 401 on /api/auth/login | Auth endpoints not public | Add `.requestMatchers("/api/auth/**").permitAll()` |
| Circular dependency | JwtAuthFilter dependency | Use `@Lazy` on JwtAuthFilter in SecurityConfig |
| Invalid JWT signature | Wrong secret key | Check jwt.secret in application.properties |
| Token not validated | Missing "Bearer " prefix | Ensure header is `Bearer <token>` |
| User not found | UserDetailsService issue | Implement `findByEmail()` in repository |

---

## 📦 Complete Package Structure

```
com.yourapp/
├── controller/
│   └── AuthController.java
├── dto/
│   ├── AuthResponse.java
│   ├── LoginRequest.java
│   └── RegisterRequest.java
├── entity/
│   └── User.java
├── repository/
│   └── UserRepository.java
├── security/
│   ├── JwtAuthFilter.java
│   ├── JwtAuthenticationEntryPoint.java
│   ├── JwtService.java
│   └── SecurityConfig.java
└── service/
    ├── AuthService.java
    └── serviceImpl/
        └── AuthServiceImpl.java
```

---

## 🔐 Security Best Practices

1. ✅ **Never commit secrets** - Use environment variables
2. ✅ **Use strong JWT secrets** - Minimum 256 bits (32 bytes)
3. ✅ **Set token expiration** - Default: 24 hours (86400000 ms)
4. ✅ **Hash passwords** - BCrypt with default strength
5. ✅ **Validate input** - Add `@Valid` annotations
6. ✅ **Use HTTPS** - In production
7. ✅ **Implement refresh tokens** - For better UX
8. ✅ **Add rate limiting** - Prevent brute force

---

## 🎯 Role-Based Access

### In SecurityConfig:
```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/api/auth/**").permitAll()
    .requestMatchers("/api/admin/**").hasRole("ADMIN")
    .requestMatchers("/api/user/**").hasRole("USER")
    .anyRequest().authenticated()
)
```

### In Controller:
```java
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/admin/users")
public ResponseEntity<?> getAllUsers() { ... }
```

---

## 📝 JWT Token Structure

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNjk5OTk5OTk5LCJleHAiOjE3MDAwODYzOTl9.signature
│                                      │                                                                                    │
│         Header (Algorithm)           │                    Payload (Claims)                                                │  Signature
```

**Decoded Payload:**
```json
{
  "sub": "user@example.com",
  "iat": 1699999999,
  "exp": 1700086399
}
```

---

## 🚀 Production Checklist

- [ ] Move secrets to environment variables
- [ ] Enable HTTPS
- [ ] Add input validation (`@Valid`, `@NotBlank`, etc.)
- [ ] Implement refresh tokens
- [ ] Add email verification
- [ ] Implement password reset
- [ ] Add rate limiting
- [ ] Set up logging and monitoring
- [ ] Implement token blacklisting for logout
- [ ] Add CORS configuration if needed
- [ ] Write unit and integration tests
- [ ] Document API with Swagger/OpenAPI

---

**Last Updated:** October 2025  
**Spring Boot:** 3.5.6 | **Java:** 21 | **JWT:** 0.12.3
