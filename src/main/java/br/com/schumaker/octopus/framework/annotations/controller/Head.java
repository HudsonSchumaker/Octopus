package br.com.schumaker.octopus.framework.annotations.controller;

import br.com.schumaker.octopus.framework.web.http.Http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.schumaker.octopus.framework.web.http.Http.APPLICATION_JSON;

// TODO: add documentation

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Head {
    String value() default "/";
    String type() default APPLICATION_JSON;
    int httpCode() default Http.HTTP_200;
}
