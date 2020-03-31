//package sv.edu.uesocc.utils;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//import org.springframework.stereotype.Service;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//@Service
//public class JwtUtil {
//	
//	private String SECRET_KEY = "token";
//	
//	public String extractCaic(String token) {
//		return extractClaim(token, Claims::getSubject);
//	}
//	
//	public Date extractExpiration(String token) {
//		return extractClaim(token, Claims::getExpiration);
//	}
//	
//	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
//		final Claims claims = extractAllClaims(token);
//		return claimResolver.apply(claims);
//	}
//	
//	private Claims extractAllClaims(String token) {
//		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//	}
//	
//	private Boolean isTokenExpired(String token) {
//		return extractExpiration(token).before(new Date());
//	}
//	
//	public String generateToken(String caic) {
//		Map<String, Object> claims = new HashMap<>();
//		return createToken(claims, caic);
//	}
//	
//	private String createToken(Map<String, Object> claims, String subject) {
//		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis()+100 * 60 *60 * 1))
//				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
//	}
//	
//	public Boolean validateToken(String token, String caic) {
//		final String tokenCaic = extractCaic(token);
//		return (tokenCaic.equals(caic) && !isTokenExpired(token));
//	}
//
//}
