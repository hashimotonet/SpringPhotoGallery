/**
 * 
 */
package hashimotonet.authentication;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(login -> login
                .loginPage("/?param=signin")
                .loginProcessingUrl("/SignIn")
//                .usernameParameter("id")
//                .passwordParameter("password")
                .defaultSuccessUrl("/SignIn")
                .failureUrl("/error")
                .permitAll()
//        ).logout(logout -> logout
//        		.logoutUrl("/")
//                .logoutSuccessUrl("/")
        ).authorizeHttpRequests(authz -> authz
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/SignIn", "/Upload", "/ListImages").permitAll()
                .requestMatchers("/*/*.jpg").permitAll()
                //.antMatchers("/general").hasRole("GENERAL")
                //.antMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
        );
        http.csrf(csrf -> csrf.ignoringAntMatchers("/*"));
        return http.build();
    }
    
    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
//    @Bean
//    public AbstractPreAuthenticatedProcessingFilter preAuthenticatedProcessingFilter() throws Exception {
//
//    	PreAuthenticatedProcessingFilter preAuthenticatedProcessingFilter = new MyPreAuthenticatedProcessingFilter();
//        preAuthenticatedProcessingFilter.setAuthenticationManager(authenticationManager());
//
//        return preAuthenticatedProcessingFilter;
//    }
//
//    @Bean
//    public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
//
//        var preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
//        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(authenticationUserDetailsService());
//        preAuthenticatedAuthenticationProvider.setUserDetailsChecker(new AccountStatusUserDetailsChecker());
//
//        return preAuthenticatedAuthenticationProvider;
//    }

//    @Bean
//    public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> authenticationUserDetailsService() {
//
//        return new MyAuthenticationUserDetailService();
//    }

}
