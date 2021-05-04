package pl.michalzadrozny.familyrecipes.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.michalzadrozny.familyrecipes.model.dto.LoginDTO;
import pl.michalzadrozny.familyrecipes.model.entity.AppUser;
import pl.michalzadrozny.familyrecipes.model.mapper.UserMapper;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static pl.michalzadrozny.familyrecipes.security.SecurityConstants.EXPIRATION_TIME;
import static pl.michalzadrozny.familyrecipes.security.SecurityConstants.SECRET;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            log.info("1");
            LoginDTO loginDTO = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginDTO.class);
            log.info("2");
            AppUser credentials = UserMapper.signUpDtoToAppUserMapper().map(loginDTO, AppUser.class);
            log.info("3");

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            log.info("error");
            log.warn(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        AppUser user = (AppUser) authResult.getPrincipal();

        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));

        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("token", token);
        jsonMap.put("username", user.getUsername());
        jsonMap.put("userId", user.getId().toString());

        response.getWriter().write(new JSONObject(jsonMap).toString());
        response.setContentType("application/json");
        response.getWriter().flush();
    }
}