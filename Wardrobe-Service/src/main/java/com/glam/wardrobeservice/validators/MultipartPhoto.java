package com.glam.wardrobeservice.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MultipartPhotoValidator.class)
public @interface MultipartPhoto {
	String message() default "No garment's photo file was provided";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
