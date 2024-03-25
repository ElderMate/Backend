package com.example.eldermate.dto;

import com.example.eldermate.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        return collection;
    }

    @Override
    public String getPassword() {

        return userEntity.getPassword();
    }

    public Long getId() {

        return userEntity.getId();
    }

    @Override
    public String getUsername() {

        return userEntity.getUsername();
    }

    public Integer getGender(){

        return userEntity.getGender();
    }

    public String getName() {

        return userEntity.getName();
    }

    public LocalDate getBirthdate() {

        return userEntity.getBirthdate();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }

    public UserEntity getUserEntity(){
        return userEntity;
    }

}
