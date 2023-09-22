package com.cryptography.cryptoOperation;

import com.cryptography.config.CertificateFieldsConfig;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.util.Arrays;

@Component
public class CryptographyOperation {

//    @Value("${encryption.key}")
//    private String encryptionKey;
    String encryptionKey ="sattarzadehparin";
    @Autowired
    CertificateFieldsConfig certificateFieldsConfig;

    private  final byte[] keyBytes = encryptionKey.getBytes();

    public  String getEncryptWithJavaGeneratedIV(String value) {
        try
        {
            SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            //we don't pass the iv here....
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

            AlgorithmParameters params = cipher.getParameters();
            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();

            //No Need To Generate IV By Yourself
            byte[] encryptedText = cipher.doFinal(value.getBytes("UTF-8"));

            String cipherText = concatIvToCipherText(iv, encryptedText);

            return cipherText;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
    public  String getEncryptWithUserGeneratedIV(String value) {
        try {

            SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            // IV Custom Genaration
            byte[] userGeneratedIV = getIVSecureRandom();
            IvParameterSpec iv = new IvParameterSpec(userGeneratedIV);
            //we should pass the iv here....
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encryptedText = cipher.doFinal(value.getBytes("UTF-8"));


            String CipherText =  concatIvToCipherText(userGeneratedIV, encryptedText);

            return CipherText;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public  String getDecrypt(String encrypted) {
        try {
            byte[] ciphertext = Base64.decodeBase64(encrypted);

            if (ciphertext.length < 16) {
                return null;
            }
            byte[] iv = Arrays.copyOfRange(ciphertext, 0, 16);
            byte[] cipherText = Arrays.copyOfRange(ciphertext, 16, ciphertext.length);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv));
            byte[] plaintext = cipher.doFinal(cipherText);
            return new String(plaintext, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public byte [] getIVSecureRandom()
    {
        SecureRandom secureRandom = new SecureRandom();
        byte[] ivBytes = new byte[16];
        secureRandom.nextBytes(ivBytes);
        return ivBytes;
    }

    public String concatIvToCipherText(byte [] iv, byte [] cipherText)
    {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(iv);
            outputStream.write(cipherText);
            return new String(Base64.encodeBase64(outputStream.toByteArray()));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public String signText(String text) {
        String signedData="";
        try
        {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(this.getClass().getClassLoader()
                            .getResourceAsStream(certificateFieldsConfig.getPath()),
                    certificateFieldsConfig.getPassword().toCharArray());
            PrivateKey privateKey =
                    (PrivateKey) keyStore.getKey(certificateFieldsConfig.getAlias(),
                            certificateFieldsConfig.getPassword().toCharArray());
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);

            byte[] messageBytes = text.getBytes();

            signature.update(messageBytes);
            byte[] digitalSignature = signature.sign();

            signedData = Base64.encodeBase64String(digitalSignature);
            return signedData;
        }
        catch (Exception ex)
        {
            return "Something Happened!";
        }
    }
    public boolean verifySignature(String signedData, String signatureValue)
    {
        try
        {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(this.getClass().getClassLoader()
                            .getResourceAsStream(certificateFieldsConfig.getPath()),
                    certificateFieldsConfig.getPassword().toCharArray());
            Certificate certificate = (Certificate) keyStore.getCertificate("receiverKeyPair");
            PublicKey publicKey = certificate.getPublicKey();

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            byte[] messageBytes = signedData.getBytes();
            signature.update(messageBytes);
            boolean isCorrect = signature.verify(signatureValue.getBytes());

            return isCorrect;
        }
        catch (Exception ex)
        {
            return false;
        }
    }
}
