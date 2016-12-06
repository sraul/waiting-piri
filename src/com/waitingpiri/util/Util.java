package com.waitingpiri.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Util {
	
	public static String encriptar(String cadena) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "** error encriptacion **";
		}
		md.reset();
		md.update(cadena.getBytes());
		byte[] b = md.digest();
		String out = toHexadecimal(b);
		return out;

	}

	private static String toHexadecimal(byte[] digest) {
		String hash = "";
		for (byte aux : digest) {
			int b = aux & 0xff;
			if (Integer.toHexString(b).length() == 1)
				hash += "0";
			hash += Integer.toHexString(b);
		}
		return hash;
	}
	
	public static String Desencriptar(String textoEncriptado) {

		String secretKey = "qualityinfosolutions"; // llave para desenciptar
													// datos
		String base64EncryptedString = "";

		try {
			byte[] message = Base64.decodeBase64(textoEncriptado.getBytes());
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes());
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			SecretKey key = new SecretKeySpec(keyBytes, "DESede");

			Cipher decipher = Cipher.getInstance("DESede");
			decipher.init(Cipher.DECRYPT_MODE, key);

			byte[] plainText = decipher.doFinal(message);

			base64EncryptedString = new String(plainText);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return base64EncryptedString;
	}
	
	public static void main(String[] args) {
		String test = "sergio";
		String encriptado = Util.encriptar(test);
		String desencriptado = Util.Desencriptar(encriptado);
		System.out.println(encriptado);
		System.out.println(desencriptado);
	}

}
