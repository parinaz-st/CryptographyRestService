package com.hsm.sample;

import iaik.pkcs.pkcs11.*;
import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.objects.AESSecretKey;
import iaik.pkcs.pkcs11.parameters.InitializationVectorParameters;
import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class MainHsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainHsmApplication.class);
        getCryptoServerSession();
    }

    public static void getCryptoServerSession(){
        try{
            // User's PIN for given slot. Can get this as an user input also.
            String userPin = "Bb123456@";
            // Get an instance of PKCS#11 module.
            Module pkcs11Module = Module.getInstance("C:\\Utimaco\\CryptoServer\\Software\\PKCS11_R2\\lib\\cs_pkcs11_R2.dll");
            // Initialize module.
            pkcs11Module.initialize(null);
            // Get list of slots with tokens.
            Slot[] slotsWithTokens = pkcs11Module.getSlotList(Module.SlotRequirement.TOKEN_PRESENT);
            // Assume that you've configured slot 0001, get the token of slotsWithTokens[0]
            Token token = slotsWithTokens[1].getToken();
            Session session = token.openSession(Token.SessionType.SERIAL_SESSION,
                    Token.SessionReadWriteBehavior.RW_SESSION, null, null);
            // Login to the session.
            session.login(Session.UserType.USER, userPin.toCharArray());
            // Perform the required cryptographic operation here.
            // Create a template of the key that we are going to generate.
            AESSecretKey secretKeyTemplate = new AESSecretKey();
            // Set the token value true. This saves the key in the token.
            secretKeyTemplate.getToken().setBooleanValue(Boolean.TRUE);
            // Makes key to be used for encrypting operations.
            secretKeyTemplate.getEncrypt().setBooleanValue(Boolean.TRUE);
            // Makes key to be used for decrypting operations.
            secretKeyTemplate.getDecrypt().setBooleanValue(Boolean.TRUE);
            // Make key private. When a key is private only authorized user can access the key.
                    secretKeyTemplate.getPrivate().setBooleanValue(Boolean.TRUE);
            // Makes the key sensitive.
            secretKeyTemplate.getSensitive().setBooleanValue(Boolean.TRUE);
            // Makes the key not extractable. So key can't be retrieved outside the HSM.
            secretKeyTemplate.getExtractable().setBooleanValue(Boolean.FALSE);
            // Set a label to the key. Label can be used to retrieve the key.
            secretKeyTemplate.getLabel().setCharArrayValue("SampleAESKey".toCharArray());
            // Set the length of the key.
            secretKeyTemplate.getValueLen().setLongValue(32L);
            // Key template configuration is complete.
            // Selects the key generation mechanism.
            Mechanism keyGenMechanism = Mechanism.get(PKCS11Constants.CKM_AES_KEY_GEN);
            // Generates the key using the initiated session.
            AESSecretKey secretKey = (AESSecretKey) session.generateKey(keyGenMechanism, secretKeyTemplate);



            // Sample text to encrypt.
            String stringToEncrypt = "Sample string to encrypt.";
            // Selects encryption mechanism. I've selected AES, CBC(Code Block Chaning) with Padding.
            Mechanism encryptionMechanism = Mechanism.get(PKCS11Constants.CKM_AES_CBC_PAD);
            // For some mechanisms there are parameters required to configure before performing the operation.
            InitializationVectorParameters encryptionIV = new InitializationVectorParameters(new byte[16]);
            // Set the parameters of the mechanism.
            encryptionMechanism.setParameters(encryptionIV);
            // Instantiate encryption. Mechanism and secret key is required.
            session.encryptInit(encryptionMechanism, secretKey);
            // Convert text to a byte array.
            byte[] dataToBeEncrypted = stringToEncrypt.getBytes(StandardCharsets.UTF_8);
            // Returns a byte array of encrypted data.
            byte[] encryptedData = session.encrypt(dataToBeEncrypted);
            // Print the encrypted text.
            System.out.println("Encrypted text : " + new String(encryptedData, StandardCharsets. UTF_8));

            // Close the session.
            session.closeSession();
            int i = 0;

        }
         catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception ex){
            ex.printStackTrace();

        }

    }
//    private static AESSecretKey symetricKeyGeneration(){
//
//    }
}
