/*
 * Decompiled with CFR 0.150.
 */
package jetbrains.annotations;

import java.lang.annotation.*;

@Documented
@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
public @interface Nullable {

    @NonNls
    public String value() default "";

}

