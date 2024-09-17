package com.csie.csieBooking.security;

import com.csie.csieBooking.domain.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    // Additional getter for name
    public String getName() {
        return member.getName();
    }

    @Override
    public String getUsername() {
        return member.getStudentId();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // Set roles if necessary
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement according to your logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement according to your logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement according to your logic
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement according to your logic
    }
}