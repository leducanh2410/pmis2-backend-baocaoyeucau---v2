package com.pmis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEParameterSpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class Util {
    public static Connection conn;

    private static Cipher m_EncryptCipher;
    private static Cipher m_DecryptCipher;
    private static char[] m_Password = "www.evnit.evn.com.vn/fmg_web/sercurity".toCharArray();
    private static byte[] m_Salt = new byte[] { (byte) 0xa3, (byte) 0x21, (byte) 0x24, (byte) 0x2c, (byte) 0xf2,
            (byte) 0xd2, (byte) 0x3e, (byte) 0x19 };

    private static Logger logger = LoggerFactory.getLogger(Util.class);

    public static Connection getConnection (
            String dbIp,
            String dbPort,
            String dbName,
            String dbUser,
            String dbPassword,
            String integratedSecurity,
            String encrypt,
            String sslProtocol ) throws SQLException
    {
        String connectionUrl = "jdbc:sqlserver://" + dbIp + ":" + dbPort + ";databaseName=" + dbName + ";";
        Properties properties = new Properties();
        properties.put("user", dbUser);
        properties.put("password", dbPassword);
         properties.put("integratedSecurity", integratedSecurity);
         properties.put("encrypt", encrypt);
         properties.put("sslProtocol", sslProtocol);
        conn = DriverManager.getConnection(connectionUrl, properties);
        logger.info("Opened connection: {}", dbIp);
        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn == null) {
                return;
            }
            if (!conn.isClosed()) {
                logger.info("Closed connection: {}", conn.getClientInfo().toString());
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized String encrypt(String str) throws SecurityException {
        try {
            // Khởi tạo
            initEncrypt(m_Password, m_Salt);
            byte[] utf8 = str.getBytes("UTF8");
            byte[] enc = m_EncryptCipher.doFinal(utf8);
//			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
//			return encoder.encode(enc);
            return Base64.getEncoder().encodeToString(enc);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SecurityException("Could not encrypt: " + e.getMessage());
        }

    }

    private static void initEncrypt(char[] pass, byte[] salt) throws SecurityException {
        try {
            PBEParameterSpec ps = new PBEParameterSpec(salt, 20);
            SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey k = kf.generateSecret(new javax.crypto.spec.PBEKeySpec(pass));
            m_EncryptCipher = Cipher.getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding");
            m_EncryptCipher.init(Cipher.ENCRYPT_MODE, k, ps);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SecurityException("Could not initialize CryptoLibrary: " + e.getMessage());
        }
    }

    public static String decrypt(String str) throws SecurityException {
        try {
            // Khởi tạo
            initDecrypt(m_Password, m_Salt);
//			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
//			byte[] dec = decoder.decodeBuffer(str);
            byte[] dec = Base64.getDecoder().decode(str);
            byte[] utf8 = m_DecryptCipher.doFinal(dec);
            return new String(utf8, "UTF8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new SecurityException("Could not decrypt: " + e.getMessage());
        }
    }

    private static void initDecrypt(char[] pass, byte[] salt) throws SecurityException {
        try {
            PBEParameterSpec ps = new PBEParameterSpec(salt, 20);
            SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey k = kf.generateSecret(new javax.crypto.spec.PBEKeySpec(pass));

            m_DecryptCipher = Cipher.getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding");
            m_DecryptCipher.init(Cipher.DECRYPT_MODE, k, ps);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SecurityException("Could not initialize CryptoLibrary: " + e.getMessage());
        }
    }
}
