import java.util.Random;
import java.math.BigInteger;

public class MillerRabin {


    /*
     * This method returns a random prime BigInteger with n bits
     * accuracy parameter 1000.
     * @param n - number of bits
     * @return a prime BigInteger
     */
    public static BigInteger SamplePrime(int n) {
        Random rand = new Random();
        BigInteger p;
        boolean prime = true;
        do {
            prime = true;
            p = BigInteger.probablePrime(n, rand);
            for (int i = 0; i<50; i++) {
                prime = isPrime(p) && prime;
            }
        } while (!prime);
        return p;
    }

    /*
     * this method determines if a BigInteger is prime or composite
     */
    public static boolean isPrime(BigInteger N) {
        BigInteger ONE = BigInteger.valueOf(1);
        BigInteger Nminus1 = N.subtract(ONE);
        // find r,u s.t N-1 = u*2^r
        BigInteger[] ur = findUR(Nminus1);
        BigInteger u = ur[0];
        int r = ur[1].intValue();
        // sample random a
        Random rand = new Random();
        BigInteger a = new BigInteger(N.bitLength(), rand).mod(N);
        // compute a^(u*2^r)
        // check if the result is -1\1
        BigInteger exponent;
        BigInteger current;
        BigInteger result;
        // condition 1 : a^u != -1 && a^u != 1
        boolean cond1 = !a.modPow(u,N).equals(ONE) || !a.modPow(u,N).equals(Nminus1);
        boolean cond2 = true;
        for (int i = 0; i <= r; i++) {
            exponent = u.multiply(BigInteger.valueOf(2).pow(i));
            current = a.modPow(exponent, N);
            result = current.mod(N);
            // if a^(u*2^r) != 1modN >> return composite
            if (!result.equals(ONE) && i==r) {
                return false;
            }
            // condition 2 : a^(u*2^i) != -1 for every i
            cond2 = cond2 && !result.equals(Nminus1);
        }
        if (cond1 && cond2) {
            return false;
        } else { 
            return true;
        }
    }

    /*
     * this method returns u,r s.t N-1 = u*2^r
     */
    public static BigInteger[] findUR(BigInteger N) {
        int counter = 0;
        BigInteger TWO = BigInteger.valueOf(2);
        BigInteger ZERO = BigInteger.valueOf(0);
        while (N.mod(BigInteger.valueOf(2)).equals(ZERO)) {
            N = N.divide(TWO);
            counter++;
        }
        return new BigInteger[]{N, BigInteger.valueOf(counter)};
    }

    /*
     * This method returns a random odd BigInteger with n bits
     */
    public static BigInteger SampleNumber(int n) {
        int bitLength = n;
        Random random = new Random();
        // Start with MSB set to 1
        StringBuilder binaryString = new StringBuilder("1");
        // Generate random bits for all bits except MSB and LSB
        for (int i = 1; i < bitLength - 1; i++) {
            // Append a random bit (0 or 1)
            binaryString.append(random.nextInt(2));
        }
        // Set LSB to 1
        binaryString.append("1");
        // Convert binary string to BigInteger
        BigInteger bigInteger = new BigInteger(binaryString.toString(), 2);
        return bigInteger;
    }


    /*
     * This method returns a random odd BigInteger with n bits
     */
    public static BigInteger SampleNumberUnderN(int n, BigInteger N) {
        int bitLength = n;
        Random random = new Random();
        // Start with MSB set to 1
        StringBuilder binaryString = new StringBuilder("0");
        // Generate random bits for all bits except MSB and LSB
        for (int i = 1; i < bitLength - 1; i++) {
            // Append a random bit (0 or 1)
            binaryString.append(random.nextInt(2));
        }
        // Set LSB to 1
        binaryString.append("1");
        // Convert binary string to BigInteger
        BigInteger bigInteger = new BigInteger(binaryString.toString(), 2);
        return N.subtract(bigInteger);
    }
    
    /*
     * check the method
     */
    public static void main(String[] args) {
        int bitLength = 512;
        int accuracy = 40;
        int currAttempts = 0;
        int sumAttempts = 0;
        BigInteger bigInt;
        // Calculate the expected number of attempts using the prime number theorem: n*ln2
        double expectedAttempts = bitLength * Math.log(2);
        for (int i = 0; i < 10; i++) {
            currAttempts = 0;
            do {
                // sample a bigInteger with n bits, MSB=1 && LSB=1
                bigInt = SampleNumber(bitLength);
                currAttempts++;
                boolean isPrime = true;
                int k = 0;
                // run 40 times isPrime(bigInt) to validate the primality
                while (k <= accuracy) {
                    isPrime = isPrime && isPrime(bigInt);
                    if (!isPrime) {
                        break;
                    }
                    k++;
                }
            } while (!isPrime(bigInt));
            sumAttempts += currAttempts;
            System.out.println("for sample number: " + i + " number of attempts: "+ currAttempts);
        }
        double average = sumAttempts/10.0;
        System.out.println("the average of all attempts is: " + average);
        System.out.println("the expected number of attempts from the prime number theorem is: " + expectedAttempts);
        System.out.println("the difference between the average of attempts and the expected number of attempts is: "
                                                                             + Math.abs(average - expectedAttempts));
    }
}