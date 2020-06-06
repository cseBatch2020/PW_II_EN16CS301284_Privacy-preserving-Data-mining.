/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed_treeclient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author ABC
 */
public class Paillier {
     BigInteger p, q, lambda;
    /**
     * n = p*q, where p and q are two large primes.
     */
     BigInteger n;
    /**
     * nsquare = n*n
     */
    BigInteger nsquare;
    /**
     * a random integer in Z*_{n^2} where gcd (L(g^lambda mod n^2), n) = 1.
     */
    BigInteger g;
    /**
     * number of bits of modulus
     */
    int bitLength;
    
    
    StringBuffer bufferKey;

    /**
     * Constructs an instance of the Paillier cryptosystem.
     *
     * @param bitLengthVal number of bits of modulus
     * @param certainty The probability that the new BigInteger represents a
     * prime number will exceed (1 - 2^(-certainty)). The execution time of this
     * constructor is proportional to the value of this parameter.
     */
    public Paillier(int bitLengthVal, int certainty) {
        KeyGeneration(bitLengthVal, certainty);
    }

    /**
     * Constructs an instance of the Paillier cryptosystem with 1024 or 512 bits
     * of modulus and at least 1-2^(-64) certainty of primes generation.
     */
    public Paillier() {
    }

    /**
     * Sets up the public key and private key.
     *
     * @param bitLengthVal number of bits of modulus.
     * @param certainty The probability that the new BigInteger represents a
     * prime number will exceed (1 - 2^(-certainty)). The execution time of this
     * constructor is proportional to the value of this parameter.
     */
    public void KeyGeneration(int bitLengthVal, int certainty) {
        
        
        bitLength = bitLengthVal;
        /*Constructs two randomly generated positive BigIntegers that are probably prime, with the specified bitLength and certainty.*/
        p = new BigInteger(bitLength / 2, certainty, new Random());
        q = new BigInteger(bitLength / 2, certainty, new Random());

        n = p.multiply(q);
        nsquare = n.multiply(n);

        g = new BigInteger("2");
        lambda = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)).divide(p.subtract(BigInteger.ONE).gcd(q.subtract(BigInteger.ONE)));
        bufferKey=new StringBuffer();
        bufferKey.append(lambda);
        bufferKey.append("lamb");
        bufferKey.append(nsquare);
        bufferKey.append("lamb");
        bufferKey.append(n);
        /* check whether g is good.*/
        if (g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).gcd(n).intValue() != 1) {
            System.out.println("g is not good. Choose g again.");
            System.exit(1);
        }
        
    }

    /**
     * Encrypts plaintext m. ciphertext c = g^m * r^n mod n^2. This function
     * explicitly requires random input r to help with encryption.
     *
     * @param m plaintext as a BigInteger
     * @param r random plaintext to help with encryption
     * @return ciphertext as a BigInteger
     */
    public BigInteger Encryption(BigInteger m, BigInteger r) {
        return g.modPow(m, nsquare).multiply(r.modPow(n, nsquare)).mod(nsquare);
    }

    /**
     * Encrypts plaintext m. ciphertext c = g^m * r^n mod n^2. This function
     * automatically generates random input r (to help with encryption).
     *
     * @param m plaintext as a BigInteger
     * @return ciphertext as a BigInteger
     */
    public BigInteger Encryption(String plaintext) {
        BigInteger m = new BigInteger(plaintext.getBytes());
        BigInteger r = new BigInteger(bitLength, new Random());
        return g.modPow(m, nsquare).multiply(r.modPow(n, nsquare)).mod(nsquare);
        
    }

    /**
     * Decrypts ciphertext c. plaintext m = L(c^lambda mod n^2) * u mod n, where
     * u = (L(g^lambda mod n^2))^(-1) mod n.
     *
     * @param c ciphertext as a BigInteger
     * @return plaintext as a BigInteger
     * @throws java.lang.Exception
     */
    public BigInteger Decryption(BigInteger c){
        g = new BigInteger("2");
        BigInteger u = g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).modInverse(n);
        return c.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).multiply(u).mod(n);
    }

    /**
     *
     * @param text
     * @param chunkSize
     * @param maxLength
     * @return
     */
   /* public String[] Split(String text, int chunkSize, int maxLength) {
        char[] data = text.toCharArray();
        int len = Math.min(data.length, maxLength);
        String[] result = new String[(len + chunkSize - 1) / chunkSize];
        int linha = 0;
        for (int i = 0; i < len; i += chunkSize) {
            result[linha] = new String(data, i, Math.min(chunkSize, len - i));
            linha++;
        }
        return result;

    }*/
    
    /**
     * 
     * @param plaintext
     * @return 
     */
   
/**
 * 
 * @param file
 * @return 
 */
    public String decryptedfile(String file) {
        String[] brk = null;
        String[] lamb = null;
        String[] sepComma = null;
        String[] sepTheRate = null;
        BigInteger em1 = null;
        em1 = new BigInteger(file);
        String k = new String(Decryption(em1).toByteArray());          
        return k;
    }
    public static void main(String ar[])
    {
       /*Paillier p=new Paillier();
       p.KeyGeneration(512, 64);
       BigInteger encrypteddata = p.Encryption("Hello");
       String enc=encrypteddata.toString();
       //String enc=p.encription("Hello");
       System.out.println(enc);
       String dec=p.decryptedfile(enc);
       System.out.println(dec);*/
    }
}
