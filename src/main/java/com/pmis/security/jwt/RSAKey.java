package com.pmis.security.jwt;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAKey {
	public static PrivateKey getPrivateKey(String key) {
		try {
			// Base64 decode the result
			byte[] pkcs8EncodedBytes = Base64.getDecoder().decode(key);
			// extract the private key
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PrivateKey privKey = kf.generatePrivate(keySpec);
			return privKey;
		} catch (Exception e) {
			return null;
		}
	}

	public static PublicKey getPublicKey(String key) {
		try {
			byte[] pkcs8EncodedBytes = Base64.getDecoder().decode(key);
			X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(pkcs8EncodedBytes);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PublicKey pubKey = kf.generatePublic(keySpecX509);
			return pubKey;
		} catch (Exception e) {
			return null;
		}
	}
}
