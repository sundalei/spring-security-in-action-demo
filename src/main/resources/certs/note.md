# Commands to Generate Asymmetric Keys for Signing JWTs

This note outlines the OpenSSL commands used to generate a pair of asymmetric keys (private and public) and convert the private key to PKCS#8 format. These keys are typically used for signing and verifying JSON Web Tokens (JWTs).

## 1. Generate RSA Private Key

This command generates a 2048-bit RSA private key and saves it to `keypair.pem`.

```bash
openssl genrsa -out keypair.pem 2048
```

## 2. Extract Public Key from RSA Private Key

This command extracts the public key from the `keypair.pem` file and saves it to `public.pem`. The `-pubout` flag is used to output the public key.

```bash
openssl rsa -in keypair.pem -pubout -out public.pem
```

## 3. Convert Private Key to PKCS#8 Format

This command converts the RSA private key (`keypair.pem`) to PKCS#8 format and saves it as `private.pem`.

- `-topk8`: Specifies that a PKCS#8 BSafe private key is to be output or input.
- `-inform PEM`: Specifies that the input format is PEM.
- `-outform PEM`: Specifies that the output format is PEM.
- `-nocrypt`: Ensures the output private key is not encrypted.

This format is often required for compatibility with various systems and libraries (e.g., Java).

```bash
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```

## 4. Delete the Original Key Pair File (Optional)

After successfully extracting the public key to `public.pem` and converting the private key to PKCS#8 format in `private.pem`, the original `keypair.pem` file (which contains the private key in PKCS#1 format) might no longer be needed. 

It's good practice to remove it to avoid confusion or accidental use of the older format. However, ensure you have successfully created `private.pem` and `public.pem` and backed them up if necessary before deleting `keypair.pem`.

```bash
rm keypair.pem
```
