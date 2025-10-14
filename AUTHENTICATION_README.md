# Banking App - JWT Authentication System

## Overview
This Banking App now includes a secure JWT-based authentication system with email and password login/registration.

## Security Features Implemented

### 1. JWT Authentication Components
- **JwtService.java**: Handles JWT token generation, validation, and extraction
- **JwtAuthFilter.java**: Intercepts HTTP requests to validate JWT tokens
- **JwtAuthenticationEntryPoint.java**: Handles authentication errors
- **SecurityConfig.java**: Configures Spring Security with JWT

### 2. User Authentication
- Email and password-based authentication
- BCrypt password encryption
- Role-based access control (ADMIN, USER)
- Stateless session management

## API Endpoints

### Public Endpoints (No Authentication Required)

#### Register New User
```
POST /api/auth/register
Content-Type: application/json

{
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "password": "securePassword123",
  "phoneNumber": "+1234567890",
  "address": "123 Main St, City, Country"
}

Response (201 Created):
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "john.doe@example.com",
  "fullName": "John Doe",
  "role": "USER"
}
```

#### Login
```
POST /api/auth/login
Content-Type: application/json

{
  "email": "john.doe@example.com",
  "password": "securePassword123"
}

Response (200 OK):
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "john.doe@example.com",
  "fullName": "John Doe",
  "role": "USER"
}
```

### Protected Endpoints (Authentication Required)

All other endpoints require a valid JWT token in the Authorization header:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Admin Endpoints

Endpoints under `/api/admin/**` require ADMIN role.

## Configuration

### JWT Settings (application.properties)
```properties
# JWT Configuration
jwt.secret=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
jwt.expiration=86400000  # 24 hours in milliseconds
```

**IMPORTANT**: For production, change the `jwt.secret` to a strong, unique secret key. You can generate one using:
```bash
openssl rand -base64 64
```

## Security Features

### 1. Password Encryption
- Passwords are encrypted using BCrypt before storage
- Never stored in plain text

### 2. JWT Token Security
- Tokens expire after 24 hours (configurable)
- Signed with HMAC-SHA256 algorithm
- Contains user email and authorities

### 3. Role-Based Access Control
- **USER**: Default role for registered users
- **ADMIN**: Administrative privileges

### 4. Stateless Authentication
- No server-side session storage
- JWT tokens contain all necessary information

### 5. CORS and CSRF
- CSRF disabled (stateless JWT authentication)
- Configure CORS as needed for your frontend

## Testing the Authentication

### Using cURL

#### Register:
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Test User",
    "email": "test@example.com",
    "password": "password123",
    "phoneNumber": "+1234567890",
    "address": "123 Test St"
  }'
```

#### Login:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

#### Access Protected Endpoint:
```bash
curl -X GET http://localhost:8080/api/accounts \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
```

### Using Postman

1. **Register/Login**: Send POST request to register or login endpoint
2. **Copy the token** from the response
3. **For protected endpoints**: 
   - Go to Authorization tab
   - Select "Bearer Token"
   - Paste your JWT token

## Database Schema Update

The User entity now implements `UserDetails` interface for Spring Security integration. The database schema remains the same, but passwords will be stored as BCrypt hashes.

## Error Handling

### Common Error Responses

- **400 Bad Request**: Invalid input or email already exists
- **401 Unauthorized**: Invalid credentials or expired token
- **403 Forbidden**: Insufficient permissions

## Best Practices

1. **Always use HTTPS in production** to protect tokens in transit
2. **Store tokens securely** on the client side (not in localStorage for sensitive apps)
3. **Implement token refresh** mechanism for better UX
4. **Add rate limiting** to prevent brute force attacks
5. **Validate input** on both client and server side
6. **Use strong passwords** and consider implementing password policies

## Next Steps

Consider implementing:
- Password reset functionality
- Email verification
- Refresh token mechanism
- Account lockout after failed login attempts
- Two-factor authentication (2FA)
- OAuth2 integration (Google, Facebook, etc.)

## Dependencies Added

```xml
<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- JWT -->
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

## Troubleshooting

### Issue: "User not found" error
- Ensure the user is registered in the database
- Check email spelling

### Issue: "Unauthorized" on protected endpoints
- Verify JWT token is included in Authorization header
- Check token hasn't expired
- Ensure token format is: `Bearer <token>`

### Issue: "Email already registered"
- User with that email already exists
- Try logging in instead or use a different email
