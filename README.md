# TextBook-RSA
RSA Encryption and Key Generation
Overview
The RSA (Rivest-Shamir-Adleman) algorithm is a widely used asymmetric cryptographic algorithm for secure communication and digital signatures. This project implements key generation, encryption, and decryption functionalities of the RSA algorithm using Java and BigIntegers.

Key Concepts
- Key Generation - RSA key generation involves finding large prime numbers and computing encryption and decryption keys.
The project uses the Miller-Rabin primality test to generate prime numbers efficiently.
- Encryption and Decryption - The RSA algorithm uses modular exponentiation to encrypt and decrypt messages.
The encryptDecrypt method demonstrates how to perform these operations using BigIntegers.
- Extended Euclidean Algorithm - The Extended Euclidean algorithm is crucial for finding modular inverses, which are used in RSA key generation and decryption.
The ExtendedEuclidean class implements this algorithm to compute modular inverses.

Main Classes and Methods
- RSA Class - keyGen(int bitLength) and keyGen(int bitLength, BigInteger e) generate RSA encryption keys (N, e, d) based on given parameters.
encryptDecrypt(BigInteger message_cypherText, BigInteger ed, BigInteger N) performs RSA encryption or decryption.
- MillerRabin Class - SamplePrime(int n) generates random prime numbers using the Miller-Rabin primality test.
isPrime(BigInteger N) checks if a BigInteger is prime using Miller-Rabin.
ExtendedEuclidean Class - Euclid(BigInteger a, BigInteger b) computes the extended Euclidean algorithm to find modular inverses.

Purpose of the Project:
- Security - The project showcases the fundamental principles of RSA encryption, which is vital for secure communication and data protection.
- Cryptographic Operations - It demonstrates key generation, encryption, and decryption processes essential for implementing RSA in real-world applications.
- Mathematical Concepts - Users can learn about primality testing, modular arithmetic, and modular inverses in the context of RSA cryptography.
- 
By understanding these concepts and implementing the provided classes and methods, developers can gain insights into RSA encryption, cryptographic algorithms, and secure communication protocols. The project serves as a practical introduction to cryptography and mathematical algorithms used in modern security systems.






