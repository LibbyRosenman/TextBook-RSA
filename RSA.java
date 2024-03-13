import java.util.Random;
import java.math.BigInteger;

public class RSA {

    /**
     * This method creates an ecryption key
     * finds e,d s.t e*d = 1modphi(N)
     * @return an array of BigIntegers - N,e,d
     */
    public static BigInteger[] keyGen(int bitLength) {
        BigInteger ONE = BigInteger.valueOf(1);
        BigInteger[] answer = new BigInteger[3];
        // find 2 n-bit primes p,q
        BigInteger[] PQ = generatorPQ(bitLength);
        // compute N = pq
        BigInteger N = PQ[0].multiply(PQ[1]);
        answer[0] = N;
        // choose e>0 coprime to phi(N)=(p-1)(q-1)
        BigInteger phi_N = (PQ[0].subtract(ONE)).multiply(PQ[1].subtract(ONE));
        BigInteger e = generatorE(phi_N);
        answer[1] = e;
        // find d s.t e*d = 1modphi(N) using extended euclidean algorithm
        BigInteger d = ExtendedEuclidean.Euclid(e, N)[1];
        answer[2] = d;
        // pk = (N,e), sk = (N,d)
        return answer;
    }

    /**
     * This method overloads the first key generator
     * it gets e as input and finds only d
     * @return an array of BigIntegers - {N,e,d}
     */
    public static BigInteger[] keyGen(int bitLength, BigInteger e) {
        BigInteger ZERO = BigInteger.valueOf(0);
        BigInteger ONE = BigInteger.valueOf(1);
        BigInteger[] answer = new BigInteger[3];
        answer[1] = e;
        // Initialize prime numbers
        BigInteger p, q, phi_N=ZERO;
        // Find p and q such that phi_N = (p-1)(q-1) is coprime to e
        do {
            p = MillerRabin.SamplePrime(bitLength);
            q = MillerRabin.SamplePrime(bitLength);
            if (p.equals(q)) { continue;}
            phi_N = p.subtract(ONE).multiply(q.subtract(ONE));
        } while (!ExtendedEuclidean.Euclid(e,phi_N)[0].equals(ONE));
        // compute N = pq
        BigInteger N = p.multiply(q);
        answer[0] = N;
        // find d s.t e*d = 1modphi(N) using extended euclidean algorithm
        BigInteger d = ExtendedEuclidean.Euclid(e, phi_N)[1];
        answer[2] = d;
        // pk = (N,e), sk = (N,d)
        return answer;
    }

    /**
     * this method returns 2 n-bits prime numbers (p,q)
     * @param n - number of bits
     * @return an array of 2 BigIntegers
     */
    public static BigInteger[] generatorPQ(int n) {
        BigInteger p = MillerRabin.SamplePrime(n);
        BigInteger q = MillerRabin.SamplePrime(n);
        return new BigInteger[]{p,q};
    }

    /**
     * this method returns a number e which is co-prime to phi_N
     * @param phi_N - BigInteger
     * @return a BigInteger
     */
    public static BigInteger generatorE(BigInteger phi_N) {
        BigInteger ONE = BigInteger.valueOf(1);
        Random random = new Random();
        BigInteger e;
        do {
            e = new BigInteger(phi_N.bitLength(), random);
        } while (!ExtendedEuclidean.Euclid(phi_N, e)[0].equals(ONE));
        return e;
    }

    /**
     * This method encrypts the message into cypher text
     * Alternatively - this method decrypts the cypher text into the original message
     * @param message_cypherText
     * @return message or cypherText
     */
    public static BigInteger encryptDecrypt(BigInteger message_cypherText, BigInteger ed, BigInteger N) {
        return message_cypherText.modPow(ed, N);
    }


    /*
     * check the method
     */
    public static void main(String[] args) {
        int bitLength = 512;
        BigInteger e = BigInteger.valueOf(3);
        // creating key generator: {N,e,d}
        BigInteger[] keygen = keyGen(bitLength,e);
        System.out.println("N is: " + keygen[0]);
        System.out.println("e is: " + keygen[1]);
        System.out.println("d is: " + keygen[2]);
        // sampling a random message m < N
        BigInteger message = MillerRabin.SampleNumberUnderN(bitLength, keygen[0]);
        System.out.println("the message is: " + message);
        // encrypting the message
        BigInteger cypherText = encryptDecrypt(message, e, keygen[0]);
        System.out.println("the cypher text is: " + cypherText);
        // decrypting the message
        BigInteger decrypted = encryptDecrypt(cypherText, keygen[2], keygen[0]);
        System.out.println("the decrypted text is: " + decrypted);
        // assert equals
        System.out.println("Is the decrypted cypher text equals to the message? " + (decrypted.compareTo(message)==0));

    }

}
