package ru.savostikov.advent.meta;

import ru.savostikov.advent.enumerations.Day;
import ru.savostikov.advent.enumerations.Year;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdventPuzzle {
    Year year() default Year.TWO_K_FIFTEEN;
    Day day() default Day.ONE;
}
