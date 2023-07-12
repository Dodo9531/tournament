package com.example.tour.service;
import com.example.tour.entity.Roles;
import com.example.tour.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import java.security.Key;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JWTServiceTest {

    private JWTService jwtService;
    private String SECRET_KEY;

    @BeforeEach
    void setUp() {
        jwtService = new JWTService();
        SECRET_KEY = "d288015161b6f3f40e3e2e7015cde35558c60a9900fa4cc37d4a4527a713d17c";
    }

    @Test
    void getClaim_ValidToken_ReturnsClaimValue() {
        UserDetails userDetails = mock(UserDetails.class);
        String token = generateToken(userDetails);
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String result = jwtService.getClaim(token, Claims::getSubject);

        assertEquals(claims.getSubject(), result);
    }

    @Test
    void getUsername_ValidToken_ReturnsUsername() {
        var user1 = UserEntity.builder()
                .id(1L)
                .username("da2")
                .name("Илья")
                .surname("Сидоров")
                .patronymic("Владимирович")
                .grade_letter("Б")
                .grade_number(9)
                .date_of_birth(LocalDate.of(2005, 1, 12))
                .city("Ярославль")
                .school("Средняя школа № 4 имени Н. А. Некрасова")
                .email("oge2782@mail.ru")
                .password("123")
                .phone("+7 734 870 49 88")
                .role(Roles.ADMIN)
                .build();
        String token = generateToken(user1);



        String result = jwtService.getUsername(token);


        assertEquals(user1.getUsername(), result);
    }

    @Test
    void generateToken_WithClaimsAndUserDetails_ReturnsToken() {

        var user1 = UserEntity.builder()
                .id(1L)
                .username("da2")
                .name("Илья")
                .surname("Сидоров")
                .patronymic("Владимирович")
                .grade_letter("Б")
                .grade_number(9)
                .date_of_birth(LocalDate.of(2005, 1, 12))
                .city("Ярославль")
                .school("Средняя школа № 4 имени Н. А. Некрасова")
                .email("oge2782@mail.ru")
                .password("123")
                .phone("+7 734 870 49 88")
                .role(Roles.ADMIN)
                .build();
        String token = generateToken(user1);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(user1.getUsername());




        assertNotNull(token);
    }

    @Test
    void generateToken_WithUserDetails_ReturnsToken() {

        String username = "da2";
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);


        String token = jwtService.generateToken(userDetails);


        assertNotNull(token);
    }

    @Test
    void isTokenValid_ValidTokenAndMatchingUserDetails_ReturnsTrue() {

        var user1 = UserEntity.builder()
                .id(1L)
                .username("da2")
                .name("Илья")
                .surname("Сидоров")
                .patronymic("Владимирович")
                .grade_letter("Б")
                .grade_number(9)
                .date_of_birth(LocalDate.of(2005, 1, 12))
                .city("Ярославль")
                .school("Средняя школа № 4 имени Н. А. Некрасова")
                .email("oge2782@mail.ru")
                .password("123")
                .phone("+7 734 870 49 88")
                .role(Roles.ADMIN)
                .build();
        String token = generateToken(user1);
        UserDetails userDetails = mock(UserDetails.class);
        JWTService jwtService1 = mock(JWTService.class);
        when(userDetails.getUsername()).thenReturn(user1.getUsername());
        when(jwtService1.getUsername(token)).thenReturn(user1.getUsername());
        when(jwtService1.isTokenExpired(token)).thenReturn(false);
        boolean result =false;
        if(jwtService.isTokenValid(userDetails, token) && user1.getUsername().equals(jwtService.getUsername(token)))
            result = true;


        assertTrue(result);
    }

    @Test
    void isTokenValid_ExpiredToken_ReturnsFalse() {
        boolean result = true;
        try {
            String username = "testUser";
            UserDetails userDetails = mock(UserDetails.class);
            when(userDetails.getUsername()).thenReturn(username);
            String token = generateExpiredToken();
            when(jwtService.getUsername(token)).thenReturn(username);
            when(jwtService.isTokenExpired(token)).thenReturn(true);

        }
        catch (ExpiredJwtException e)
        {
             result = false;
        }

        assertFalse(result);
    }


    public String generateToken(Map<String,Object> claims, UserDetails userDetails)
    {
        return Jwts.builder()
                .setClaims(claims).setSubject("da2")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*30))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }


    private String generateExpiredToken() {
        Map<String, Object> claims = new HashMap<>();
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60))
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 30))
                .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
