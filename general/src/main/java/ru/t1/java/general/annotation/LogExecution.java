<<<<<<<< HEAD:general/src/main/java/ru/t1/java/general/annotation/LogExecution.java
package ru.t1.java.general.annotation;
========
package ru.t1.java.demo.annotation;
>>>>>>>> start:src/main/java/ru/t1/java/demo/annotation/LogExecution.java

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecution {

}
