package daste.spendaste.core.filter;

import daste.spendaste.core.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    public static final String SECRET = "2Wt6NL1Zl/kgPUTySOfWZ9ARb/YEg12UByzrDVDLAvigByu1RHxXo4JIulnwdfvlxhSNRUJ1iqID8FDmEvaFnA==";

    Claims claims;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            this.claims = parseClaims(token);
            setSecurityContext(token, request);
        }
        filterChain.doFilter(request, response);
    }

    private void setSecurityContext(String token, HttpServletRequest request) {
        String username = extractUsername();
        Long id = extractUserId();
        if (username == null || id == null) return;
        if (isTokenExpired()) return;
        if (existSecurityContext()) return;

        UserPrincipal userDetails = new UserPrincipal(id, username, "", extractAuthorities());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private boolean existSecurityContext() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String extractUsername() {
        return extractClaim(Claims::getSubject);
    }

    private Long extractUserId() {
        return claims.get("id", Long.class);
    }

    private Set<GrantedAuthority> extractAuthorities() {
        List<String> authorities = claims.get("authorities", List.class);
        return Optional.ofNullable(authorities).orElse(Collections.emptyList()).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    private Date extractExpiration() {
        return extractClaim(Claims::getExpiration);
    }

    private Claims parseClaims(String token) {
        return extractAllClaims(token);
    }

    private <T> T extractClaim(Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
//        Key key = getSignKey();
//        String base64Again = Encoders.BASE64.encode(key.getEncoded());
//        System.out.println(base64Again);
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired() {
        return extractExpiration().before(new Date());
    }

    private Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername();
        return (username.equals(userDetails.getUsername()) && !isTokenExpired());
    }


    public static void main(String[] args) {
        // 1. Generate a secure HS512 key (or load from config if you want fixed one)
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        // print the Base64 key (you can save this in application.properties)
//        String base64Key = Encoders.BASE64.encode(key.getEncoded());
//        System.out.println("Secret Key (Base64): " + base64Key);
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        Key key = Keys.hmacShaKeyFor(keyBytes);


        // 2. Set claims
        String subject = "dastemin";
        long exp = 27676872561L; // epoch seconds
        Date expiration = new Date(exp * 1000); // JJWT expects ms

        // 3. Build token
        String jwt = Jwts.builder()
                .setSubject(subject)
                .claim("tenant", 1)
                .claim("id", 1)
                .setExpiration(expiration)
                .signWith(key)
                .compact();

        System.out.println("JWT Token: " + jwt);
    }
}
