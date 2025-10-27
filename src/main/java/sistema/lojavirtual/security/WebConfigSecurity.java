package sistema.lojavirtual.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfigSecurity {
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            // Desabilita CSRF para APIs stateless
            .csrf(csrf -> csrf.disable())

            // Configura quais URLs são públicas e quais exigem autenticação
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/pessoas/**").permitAll()// login público
                .requestMatchers(HttpMethod.POST, "/categorias/**").permitAll()
                .requestMatchers("/marcas/**").permitAll()
                .requestMatchers("/contas/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/produtos/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/acessos/**").authenticated()// precisa de token
                .anyRequest().authenticated()                             // todo o resto exige autenticação
            )

            // Política de sessão stateless (sem sessão)
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Adiciona o filtro JWT antes do filtro padrão de autenticação do Spring
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

            .build();
    }

	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
	

}
