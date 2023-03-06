package com.iktpreobuka.eDnevnik.security;

import java.lang.annotation.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;


@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {

}
