package com.g2forge.alexandria.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.tools.Diagnostic;

@Retention(RetentionPolicy.RUNTIME)
public @interface MessageAnnotation {
	public Class<? extends IMessageAnnotationHandler<?>> handler() default StandardMessageAnnotationHandler.class;

	public Diagnostic.Kind kind() default Diagnostic.Kind.WARNING;
	
	public String value();
}
