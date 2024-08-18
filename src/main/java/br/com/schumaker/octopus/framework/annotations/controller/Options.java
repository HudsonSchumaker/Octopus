package br.com.schumaker.octopus.framework.annotations.controller;

import br.com.schumaker.octopus.framework.web.http.Http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Options {
    String value() default "/";
    String type() default "application/json";
    int httpCode() default Http.HTTP_200;
}
