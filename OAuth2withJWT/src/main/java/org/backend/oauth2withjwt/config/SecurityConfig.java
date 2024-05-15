package org.backend.oauth2withjwt.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests((authorize) -> authorize
//                        .anyRequest().permitAll()
//                )
////                .httpBasic(Customizer.withDefaults())
//                .httpBasic(AbstractHttpConfigurer::disable)
////                .formLogin(Customizer.withDefaults());
//                .formLogin(AbstractHttpConfigurer::disable);


        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/cashcards/**").hasRole("CARD-OWNER")
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(daoAuthenticationProvider);
    }

//    @Bean
//    UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
//        User.UserBuilder users = User.builder();
//        UserDetails sarah = users
//                .username("sarah1")
//                .password(passwordEncoder.encode("abc123"))
//                .roles("CARD-OWNER") // No roles for now
//                .build();
//
//        UserDetails hankOwnsNoCards = users
//                .username("hank-owns-no-cards")
//                .password(passwordEncoder.encode("qrs456"))
//                .roles("NON-OWNER") // new role
//                .build();
//
//        UserDetails kumar = users
//                .username("kumar2")
//                .password(passwordEncoder.encode("xyz789"))
//                .roles("CARD-OWNER")
//                .build();
//        return new InMemoryUserDetailsManager(sarah, hankOwnsNoCards, kumar);
//    }

}

