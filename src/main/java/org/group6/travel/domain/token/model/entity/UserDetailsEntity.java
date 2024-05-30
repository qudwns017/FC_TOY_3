package org.group6.travel.domain.token.model.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.group6.travel.domain.user.model.enums.StoreUserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsEntity implements UserDetails {

    private Long userId;

    private String email;

    private String password;

    private String userName;

    private StoreUserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();}

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public static UserDetailsEntity toDto(UserEntity userEntity){
        return UserDetailsEntity.builder()
                .userId(userEntity.getUserId())
                .email(userEntity.getEmail())
                .userName(userEntity.getUserName())
                .password(userEntity.getEncryptedPassword())
                .build();
    }
}
