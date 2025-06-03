<<<<<<<< HEAD:general/src/main/java/ru/t1/java/general/annotation/HandlingResult.java
package ru.t1.java.general.annotation;
========
package ru.t1.java.demo.annotation;
>>>>>>>> start:src/main/java/ru/t1/java/demo/annotation/HandlingResult.java

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HandlingResult {
}
