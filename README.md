# spring-security-in-action-demo

## Description

A Spring Boot application demonstrating OAuth2 Authorization Server and Resource Server capabilities using JSON Web Tokens (JWTs) with RSA signing. This project showcases how to issue tokens and protect API endpoints within a single application.

## Prerequisites

- Java 17 or higher
- Maven 3.6.x or higher
- OpenSSL (for generating RSA key pairs if you need to create new ones)

## Getting Started

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    cd spring-security-in-action-demo
    ```

2.  **Generate RSA Key Pair (if not already present):**
    The required RSA keys (`private.pem` and `public.pem`) are expected in the `src/main/resources/certs/` directory. If you need to generate them, you can use OpenSSL. The file `src/main/resources/certs/note.md` contains detailed instructions. A common way to generate a compatible PKCS#8 private key and a public key is:

    ```bash
    # Generate a 2048-bit RSA private key in PKCS#8 format directly into the certs directory
    openssl genpkey -algorithm RSA -out src/main/resources/certs/private.pem -pkeyopt rsa_keygen_bits:2048

    # Extract the public key from the private key directly into the certs directory
    openssl rsa -pubout -in src/main/resources/certs/private.pem -out src/main/resources/certs/public.pem
    ```
    The commands above will place the keys directly into the `src/main/resources/certs/` directory.

3.  **Build the project:**
    ```bash
    mvn clean install
    ```

4.  **Run the application:**
    You can run the application using the Spring Boot Maven plugin:
    ```bash
    mvn spring-boot:run
    ```
    Alternatively, you can run the packaged JAR file (replace `<version>` with the actual project version, e.g., `0.0.1-SNAPSHOT`):
    ```bash
    java -jar target/spring-security-in-action-demo-<version>.jar
    ```

## Configuration

-   **RSA Key Paths:** Configured in `src/main/resources/application.yml`:
    ```yaml
    rsa:
      private-key: classpath:certs/private.pem
      public-key: classpath:certs/public.pem
    ```
-   **Application Port:** Runs on port `8080` by default. This can be changed in `src/main/resources/application.properties` or via command-line arguments.
-   **Logging:** Spring Security logging is set to `DEBUG` in `application.yml` for detailed output during authentication and authorization processes.

## Key Features & Endpoints

### OAuth2 Authorization Server Functionality

-   **Issues JWTs:** The application can issue JWTs for authenticated users.
    -   **Endpoint:** `POST /token`
    -   **Authentication:** HTTP Basic Authentication.
    -   **Credentials:** Use username `john` and password `12345`.
    -   **Response:** A JWT signed with the server's RSA private key.

    Example using cURL:
    ```bash
    curl -X POST -u "john:12345" http://localhost:8080/token
    ```

### OAuth2 Resource Server Functionality

-   **Protects Endpoints:** Secures API endpoints, requiring a valid JWT (Bearer token) for access.
-   **Example Protected Endpoint:** `GET /hello`
    -   Requires a valid JWT in the `Authorization: Bearer <token>` header.
    -   Returns a greeting message including the authenticated user's name.

    Example using cURL (replace `<your_jwt>` with the token obtained from `/token`):
    ```bash
    curl -H "Authorization: Bearer <your_jwt>" http://localhost:8080/hello
    ```

### Security Details

-   **JWT Signing:** Uses RSA keys (RS256) for signing and validating JWTs. The server signs tokens with its private key, and validates them using its public key.
-   **In-Memory User Store:** For the `/token` endpoint's Basic Authentication, an in-memory user store is configured with one user:
    -   Username: `john`
    -   Password: `12345` (Note: Stored as plain text due to `NoOpPasswordEncoder`)
    -   Authorities: `read` (this authority is included in the `scope` claim of the JWT)
-   **Important Security Note:** This application uses `NoOpPasswordEncoder` for the demo user. **This is highly insecure and NOT suitable for production environments.** For any real application, a strong password encoder like `BCryptPasswordEncoder` must be used.

## Technologies Used

-   Spring Boot 3.4.6
-   Spring Security 6.x (OAuth2 Resource Server, JWT)
-   Java 17
-   Maven

## Authentication Flow

1.  **Request Token:** The client application sends a `POST` request to the `/token` endpoint. The request must include an `Authorization` header with HTTP Basic credentials (e.g., `john:12345`).
2.  **Validate Credentials & Issue JWT:** The server validates the Basic Auth credentials against its user store. If valid, it generates a JWT. The JWT includes claims like `sub` (subject, user's name) and `scope` (user's authorities), and is signed with the server's RSA private key. This JWT is returned to the client.
3.  **Access Protected Resource:** The client application includes the received JWT in the `Authorization` header as a Bearer token (e.g., `Authorization: Bearer <jwt_string>`) when making requests to protected API endpoints like `/hello`.
4.  **Validate JWT & Grant Access:** The server's Resource Server component intercepts the request. It validates the JWT's signature using its RSA public key and checks its expiration and other claims. If the token is valid, access is granted to the protected resource.

This flow allows the application to both manage user authentication (via Basic Auth for the token endpoint) and secure its resources using stateless JWTs.
