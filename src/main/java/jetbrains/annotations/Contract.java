package jetbrains.annotations;

import java.lang.annotation.*;

@Documented
@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Contract {

    @NonNls
    public String value() default "";

    public boolean pure() default false;

    @NonNls
    public String mutates() default "";

}

