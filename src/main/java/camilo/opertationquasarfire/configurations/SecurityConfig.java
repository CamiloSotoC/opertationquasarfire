package camilo.opertationquasarfire.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorize -> {
                    // authorize.requestMatchers("/").permitAll();
                    authorize.anyRequest().authenticated();
                })
                /*
                 * .formLogin(login -> {
                 * login.successHandler(this.succesHanlder());
                 * login.permitAll();
                 * })
                 */
                .sessionManagement(management -> {
                    management
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);// ALWAYS - IF_REQUIRED - NEVER -
                                                                                    // STATELESS
                    management.invalidSessionUrl("/login");
                    // management.sessionFixation().migrateSession();// migrateSession - newSession
                    // - none
                    // management.maximumSessions(1).sessionRegistry(sessionRegistry());
                })
                .httpBasic(basic -> {
                })
                .csrf(csrf -> {
                    // csrf.disable();
                    csrf.ignoringRequestMatchers("/topsecret/");
                    csrf.ignoringRequestMatchers("/topsecret_split/*");
                    csrf.ignoringRequestMatchers("/user");
                })
                .build();

    }

    /*
     * private AuthenticationSuccessHandler succesHanlder() {
     * return ((request, response, authentication) -> response.sendRedirect(""));
     * }
     */

    /*
     * @Bean()
     * public SessionRegistry sessionRegistry() {
     * return new SessionRegistryImpl();
     * }
     */

    @Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withUsername("user")
                        .password("passwordd")
                        .roles()
                        .build());
        return manager;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

}
