package com.pmis.security.jwt;

import com.pmis.exception.InvalidTokenRequestException;
import com.pmis.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
	// private static final Logger logger =
	// Logger.getLogger(JwtTokenValidator.class);
	//private final String jwtSecret;

	// EVN
	private final String publicKeyEVN;
	// GENCO
	private final String publicKeyGEN1;
	private final String publicKeyGEN2;
	private final String publicKeyGEN3;
	// PC
	private final String publicKeyHN;
	private final String publicKeyNPC;
	private final String publicKeyCPC;
	private final String publicKeySPC;
	private final String publicKeyHCM;
	// NPT
	private final String publicKeyNPT;

	// System
	private final String publicKeySys;

	private final String tokenRequestHeader;

//	@Autowired
//	public JwtTokenValidator(@Value("${app.jwt.secret}") String jwtSecret) {
//		this.jwtSecret = jwtSecret;
//
//	}

	@Autowired
	public JwtUtils(@Value("${app.jwt.publickey.evn}") String publicKeyEVN,
							 @Value("${app.jwt.publickey.gen1}") String publicKeyGEN1,
							 @Value("${app.jwt.publickey.gen2}") String publicKeyGEN2,
							 @Value("${app.jwt.publickey.gen3}") String publicKeyGEN3,
							 @Value("${app.jwt.publickey.hn}") String publicKeyHN,
							 @Value("${app.jwt.publickey.npc}") String publicKeyNPC,
							 @Value("${app.jwt.publickey.cpc}") String publicKeyCPC,
							 @Value("${app.jwt.publickey.spc}") String publicKeySPC,
							 @Value("${app.jwt.publickey.hcm}") String publicKeyHCM,
							 @Value("${app.jwt.publickey.npt}") String publicKeyNPT,
							 @Value("${app.jwt.publickey.sys}") String publicKeySys,
							 @Value("${app.jwt.header.prefix}") String tokenRequestHeader) {
		this.publicKeyEVN = publicKeyEVN;
		this.publicKeyGEN1 = publicKeyGEN1;
		this.publicKeyGEN2 = publicKeyGEN2;
		this.publicKeyGEN3 = publicKeyGEN3;
		this.publicKeyHN = publicKeyHN;
		this.publicKeyNPC = publicKeyNPC;
		this.publicKeyCPC = publicKeyCPC;
		this.publicKeySPC = publicKeySPC;
		this.publicKeyHCM = publicKeyHCM;
		this.publicKeyNPT = publicKeyNPT;
		this.publicKeySys= publicKeySys;
		this.tokenRequestHeader = tokenRequestHeader;
	}

//	public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
//		String jwt = generateTokenFromUsername(userPrincipal.getUserId());
//		ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true)
//				.build();
//		return cookie;
//	}
//
//	public ResponseCookie getCleanJwtCookie() {
//		ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
//		return cookie;
//	}
//
//	public String getUserNameFromJwtToken(String token) {
//		return Jwts.parser().setSigningKey(RSAKey.getPublicKey(publicKey)).parseClaimsJws(token).getBody().getSubject();
//	}

	public String validateToken(String authToken, String org_code) {
		String userId = null;
		String strKey = publicKeyEVN; //Ngầm định key EVNID EVN
		try {
			if (org_code.equalsIgnoreCase("GEN1")) {
				strKey = publicKeyGEN1;
			} else if (org_code.equalsIgnoreCase("GEN2")) {
				strKey = publicKeyGEN2;
			} else if (org_code.equalsIgnoreCase("GEN3")) {
				strKey = publicKeyGEN3;
			} else if (org_code.equalsIgnoreCase("HNPC")) {
				strKey = publicKeyHN;
			} else if (org_code.equalsIgnoreCase("NPC")) {
				strKey = publicKeyNPC;
			} else if (org_code.equalsIgnoreCase("CPC")) {
				strKey = publicKeyCPC;
			} else if (org_code.equalsIgnoreCase("SPC")) {
				strKey = publicKeySPC;
			} else if (org_code.equalsIgnoreCase("HCM")) {
				strKey = publicKeyHCM;
			} else if (org_code.equalsIgnoreCase("NPT")) {
				strKey = publicKeyNPT;
			}
			else if (org_code.equalsIgnoreCase("SYS")) {
				strKey = publicKeySys;
			}

			Claims claims = Jwts.parser().setSigningKey(RSAKey.getPublicKey(strKey)).parseClaimsJws(authToken)
					.getBody();
			userId = claims.getSubject();
		} catch (SignatureException ex) {
			// logger.error("Invalid JWT signature");
			ex.printStackTrace();
			throw new InvalidTokenRequestException("JWT", authToken, "Incorrect signature");
			// throw new InvalidTokenRequestException("JWT", authToken + " @ " + org_code + " @ " + sdebug, "Incorrect signature");
		} catch (MalformedJwtException ex) {
			// logger.error("Invalid JWT token");
			throw new InvalidTokenRequestException("JWT", authToken, "Malformed jwt token");

		} catch (ExpiredJwtException ex) {
			// logger.error("Expired JWT token");
			throw new InvalidTokenRequestException("JWT", authToken, "Token expired. Refresh required");

		} catch (UnsupportedJwtException ex) {
			// logger.error("Unsupported JWT token");
			throw new InvalidTokenRequestException("JWT", authToken, "Unsupported JWT token");

		} catch (IllegalArgumentException ex) {
			// logger.error("JWT claims string is empty.");
			throw new InvalidTokenRequestException("JWT", authToken, "Illegal argument token");
		}
		return userId;
	}

//	public String generateTokenFromUsername(String username) {
//		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
//				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
//				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
//	}

//	public String generateTokenFromUsername(String username) {
//		try {
//			Instant expiryDate = Instant.now().plusMillis(180 * 60 * 1000);
//			Date from = new Date(System.currentTimeMillis() - 120 * 1000);
//			Map<String, Object> headers = new HashMap<String, Object>();
//			headers.put("type", "JWT");
//
//			Claims claims = Jwts.claims().setSubject(username);
//			claims.put("provide", "PMIS");
//			JwtBuilder builder = Jwts.builder().setClaims(claims).setHeaderParams(headers).setIssuedAt(from)
//					.setExpiration(Date.from(expiryDate))
//					.signWith(SignatureAlgorithm.RS256, RSAKey.getPrivateKey(jwtSecret));
//			return builder.compact();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
}
