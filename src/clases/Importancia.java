package clases;

import utilidades.anotaciones.nivelDeImportancia;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface Importancia {
    nivelDeImportancia nivel() default nivelDeImportancia.Baja;



}
