package camilo.opertationquasarfire.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorize -> {
                    //authorize.requestMatchers("/").permitAll();
                    authorize.anyRequest().authenticated();
                })
                /*.formLogin(login -> {
                    login.successHandler(this.succesHanlder());
                    login.permitAll();
                })*/
                .sessionManagement(management -> {
                    management
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);// ALWAYS - IF_REQUIRED - NEVER - STATELESS
                    management.invalidSessionUrl("/login");
                    //management.sessionFixation().migrateSession();// migrateSession - newSession - none
                    //management.maximumSessions(1).sessionRegistry(sessionRegistry());
                })
                .httpBasic(basic -> {})
                .build();

    }

    /*private AuthenticationSuccessHandler succesHanlder() {
        return ((request, response, authentication) -> response.sendRedirect(""));
    }*/

    @Bean()
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}
