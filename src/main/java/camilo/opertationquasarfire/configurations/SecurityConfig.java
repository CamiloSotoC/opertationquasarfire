package camilo.opertationquasarfire.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import camilo.opertationquasarfire.configurations.jwt.JwtAuthenticationFilter;
import camilo.opertationquasarfire.configurations.jwt.JwtAuthorizationFilter;
import camilo.opertationquasarfire.configurations.jwt.JwtUtils;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private UserDetailsService userDetailsService;
    private JwtUtils jwtUtils;
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    SecurityConfig(UserDetailsService userDetailsService, JwtUtils jwtUtils, JwtAuthorizationFilter jwtAuthorizationFilter){
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(this.jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");
        return httpSecurity
                .csrf(csrf -> {
                    csrf.disable();
                    /*csrf.ignoringRequestMatchers("/topsecret/");
                    csrf.ignoringRequestMatchers("/topsecret_split/*");
                    csrf.ignoringRequestMatchers("/user");
                    csrf.ignoringRequestMatchers("/login");
                    */
                })
                .authorizeHttpRequests(authorize -> {
                    //authorize.requestMatchers("/helloSecured").permitAll();
                    authorize.anyRequest().authenticated();
                })
                /*.formLogin(login -> {
                    login.successHandler(this.succesHanlder());
                    login.permitAll();
                })*/
                .sessionManagement(management -> {
                    management
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);// ALWAYS - IF_REQUIRED - NEVER -
                                                                                    // STATELESS
                    //management.invalidSessionUrl("/login");
                    //management.sessionFixation().migrateSession();//migrateSession/newSession/none
                    //management.maximumSessions(1).sessionRegistry(sessionRegistry());
                })
                //.httpBasic(basic -> { })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
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

    /*@Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withUsername("user")
                        .password("password")
                        .roles()
                        .build());
        return manager;
    }*/

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("password"));
        //$2a$10$Xcv70sQDNnErs9hBI/8sP.tndwqKzQwTKvxALYkZYU92qiXFHAFse
    }

}
