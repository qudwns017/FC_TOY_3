package org.group6.travel.testConfig;

import org.group6.travel.domain.token.provider.TokenProvider;
import org.group6.travel.domain.user.model.converter.UserConverter;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.group6.travel.domain.user.model.request.UserRegisterRequest;
import org.group6.travel.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Commit;

@Component
public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {

    @Autowired
    UserService userService;
    @Autowired
    TokenProvider tokenProvider;
    @Autowired
    UserConverter userConverter;
    @Autowired
    PasswordEncoder passwordEncoder;



    @Override
    @Commit
    public SecurityContext createSecurityContext(WithAccount annotation) {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(annotation.value(), "1234", "test");

        userService.register(userRegisterRequest);

        UserEntity userEntity = userService.getUserWithThrow(userRegisterRequest.getEmail(), userRegisterRequest.getPassword());

        Long userId = userEntity.getUserId();

        AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId,null, AuthorityUtils.NO_AUTHORITIES);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        return context;
    }
}
