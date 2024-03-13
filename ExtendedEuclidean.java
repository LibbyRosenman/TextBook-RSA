import java.math.BigInteger;

public class ExtendedEuclidean {

    /**
     * Computes the extended Euclidean algorithm to find the greatest common divisor
     * (GCD) of two integers and their coefficients x and y such that ax + by = gcd(a, b).
     *
     * @param a The first integer.
     * @param b The second integer.
     * @return An array containing three elements: the GCD of a and b, and the coefficients x and y.
     */
    public static BigInteger[] Euclid(BigInteger a, BigInteger b) {
        BigInteger ZERO = BigInteger.valueOf(0);
        BigInteger ONE = BigInteger.valueOf(1);
        if (b.equals(ZERO)) {
            return new BigInteger[]{a, ONE, ZERO};
        }
        BigInteger[] answer = new BigInteger[3];
        BigInteger[] QR = Div(a,b);
        BigInteger[] DXY = Euclid(b, QR[1]);
        answer[0] = DXY[0];
        answer[1] = DXY[2];
        answer[2] = DXY[1].subtract(QR[0].multiply(DXY[2]));
        // Ensure that d is positive
        answer[1] = answer[1].mod(b);
        if (answer[1].compareTo(ZERO) < 0) {
            answer[1] = answer[1].add(b);
        }
        return answer;
    }

    /**
     * Performs division with remainder.
     *
     * @param x The dividend.
     * @param y The divisor.
     * @return An array containing the quotient and remainder when x is divided by y.
     */
    public static BigInteger[] Div(BigInteger x, BigInteger y) {
        BigInteger ZERO = BigInteger.valueOf(0);
        BigInteger ONE = BigInteger.valueOf(1);
        BigInteger TWO = BigInteger.valueOf(2);
        if (x.equals(ZERO)) {
            return new BigInteger[]{ZERO,ZERO};
        }
        BigInteger[] QR = Div(x.divide(TWO), y);
        BigInteger q = TWO.multiply(QR[0]);
        BigInteger r = TWO.multiply(QR[1]);
        if (!x.mod(TWO).equals(ZERO)) {
            r = r.add(ONE);
        }
        if (r.compareTo(y) >= 0) {
            r = r.subtract(y);
            q = q.add(ONE);
        }
        return new BigInteger[]{q,r};
    }

    public static void main(String[] args) throws Exception {
        BigInteger[] euclidean = Euclid(BigInteger.valueOf(12345), BigInteger.valueOf(1358006));
        System.out.println("The inverse of 12345 mod 1358006 is: " + euclidean[1]);
    }
}
