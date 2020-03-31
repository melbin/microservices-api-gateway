package sv.edu.uesocc.utils;


import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Encrypt {

	public static String encrypt(String textToEncrypt, String key) {
		String encryptedString = null;
		try {
			PBEStringEncryptor desencryptor = new StandardPBEStringEncryptor();
			desencryptor.setPassword(key);
			encryptedString = desencryptor.encrypt(textToEncrypt);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("ERROR en metodo 'encrypt()': "+e.toString());
		}
		return encryptedString;
	}
	
	public static String decrypt(String textEncrypted, String key) {
		String decryptedString = null;
		try {
			PBEStringEncryptor desencryptor = new StandardPBEStringEncryptor();
			desencryptor.setPassword(key);
			decryptedString = desencryptor.decrypt(textEncrypted);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("ERROR en metodo 'decrypt()': "+e.toString());
		}
		return decryptedString;
	}
	
}
