package sistema.lojavirtual.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sistema.lojavirtual.service.ImplementacaoUserDetailsService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ImplementacaoUserDetailsService implementacaoUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Verifica se existe header Authorization e começa com Bearer
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // remove "Bearer "

            try {
                // Extrai username do token
                String username = jwtUtil.extractUsername(token);

                // Se não houver autenticação ainda no contexto e username não for null
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    var userDetails = implementacaoUserDetailsService.loadUserByUsername(username);

                    // Valida token
                    if (jwtUtil.isTokenValid(token)) {
                        // Cria autenticação e seta no contexto
                        var auth = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities() // roles do usuário
                        );
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    } else {
                        // Token inválido
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("Token inválido");
                        return;
                    }
                }

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Erro na autenticação: " + e.getMessage());
                return;
            }
        }

        // Continua a cadeia de filtros normalmente
        filterChain.doFilter(request, response);
    }

}
