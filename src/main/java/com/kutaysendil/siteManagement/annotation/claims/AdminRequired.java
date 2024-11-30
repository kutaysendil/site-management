package com.kutaysendil.siteManagement.annotation.claims;

import com.kutaysendil.siteManagement.annotation.RequiresClaim;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RequiresClaim("ADMIN_ACCESS")
public @interface AdminRequired {
}
