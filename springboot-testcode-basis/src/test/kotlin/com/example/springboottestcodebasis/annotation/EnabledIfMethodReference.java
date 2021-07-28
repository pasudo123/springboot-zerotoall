package com.example.springboottestcodebasis.annotation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ExtendWith(EnableIfMethodCondition.class)
public @interface EnabledIfMethodReference {

    @AliasFor("methodReferenceName")
    String methodReferenceName() default "";
}
