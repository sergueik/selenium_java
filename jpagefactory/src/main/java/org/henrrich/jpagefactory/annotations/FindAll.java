package org.henrrich.jpagefactory.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Similar to selenium original <code>@FindAll</code> annotation. It contains one or more JPageFactory <code>@FindBy</code> annotations.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface FindAll {
  FindBy[] value();
}
