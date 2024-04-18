//package org.example.jwt_authentication_and_authorization_with_tests.utils;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.example.jwt_authentication_and_authorization_with_tests.entity.User;
//import org.example.jwt_authentication_and_authorization_with_tests.entity.UserRole;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.ArrayList;
//
//@Getter
//@Setter
//public class CustomUserDetails extends User implements UserDetails {
//
//    private String username;
//    private String password;
//
//    public CustomUserDetails(User byUsername) {
//        this.username = byUsername.getUsername();
//        this.password = byUsername.getPassword();
//
//        List<GrantedAuthority> auths = new ArrayList<>();
//
//        for (UserRole role : byUsername.getRoles()) {
//            auths.add(new SimpleGrantedAuthority(role.getRole().getName().toUpperCase()));
//        }
//        this.authorities = auths;
//    }
//
//    Collection<? extends GrantedAuthority> authorities;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}