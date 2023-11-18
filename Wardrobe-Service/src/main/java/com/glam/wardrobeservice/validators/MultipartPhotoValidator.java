package com.glam.wardrobeservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MultipartPhotoValidator implements ConstraintValidator<MultipartPhoto, MultipartFile> {

	@Override
	public boolean isValid(MultipartFile photo, ConstraintValidatorContext constraintValidatorContext) {

		// convert multipart into byte[]
		byte [] byteArr = new byte[0];
		try {
			byteArr = photo.getBytes();

			if(byteArr == null || byteArr.length == 0){
				return false;
			}

		} catch (IOException e) {
			return false;
		}
		InputStream inputStream = new ByteArrayInputStream(byteArr);
		if(inputStream == null) {
			return false;
		}

		return true;
	}
}
