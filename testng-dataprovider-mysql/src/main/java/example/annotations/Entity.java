package example.annotations;

import example.enums.Schema;

import java.lang.annotation.*;

/**
 * Author: Sergey Korol
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Entity {
	Class model();

	Schema schema();
}
