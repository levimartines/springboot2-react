package com.levimartines.todoapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levimartines.todoapp.bean.LoginBean;
import com.levimartines.todoapp.constant.SecurityConstants;
import com.levimartines.todoapp.util.JWTUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
        HttpServletResponse res) throws AuthenticationException {

        try {
            LoginBean loginBean = new ObjectMapper()
                .readValue(req.getInputStream(), LoginBean.class);

            String debugLogin = loginBean.getLogin().toUpperCase();
            log.info("[{}] - INICIANDO AUTENTICACAO", debugLogin);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginBean.getLogin(), loginBean.getPassword(), new ArrayList<>());

            log.info("[{}] - AUTENTICACAO REALIZADA COM SUCESSO", debugLogin);
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
        HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {

        String login = auth.getName();
        String token = JWTUtils.generateToken(login);
        String bearerToken = SecurityConstants.TOKEN_PREFIX + token;
        res.getWriter().write(bearerToken);
        res.addHeader(SecurityConstants.HEADER_AUTH, bearerToken);
        res.addHeader("access-control-expose-headers", SecurityConstants.HEADER_AUTH);
    }

    private static class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request,
            HttpServletResponse response,
            org.springframework.security.core.AuthenticationException e)
            throws IOException {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\", "
                + "\"path\": \"/login\"}";
        }
    }
}
