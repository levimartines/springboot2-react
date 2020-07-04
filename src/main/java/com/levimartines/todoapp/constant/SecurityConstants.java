package com.levimartines.todoapp.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {

    public static final String SECRET_JWT = "SECRETJWT";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTH = "Authorization";

    public static final Long EXPIRATION_TIME = 1000L * 60L * 60L * 3L;
}
