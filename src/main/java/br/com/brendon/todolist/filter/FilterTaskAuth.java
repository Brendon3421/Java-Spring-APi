package br.com.brendon.todolist.filter;

import java.io.IOException;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.brendon.todolist.users.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var serveletPath = request.getServletPath();

        if (serveletPath.startsWith("/task/")) {
            // pegar auth ( user e password)
            var authorization = request.getHeader("Authorization");

            var authEncode = authorization.substring("Basic".length()).trim();
            byte[] decodedBytes = Base64.getDecoder().decode(authEncode);
            String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);

            String[] credentials = decodedString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            // validar user
            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401);
            } else {
                // validar a senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
