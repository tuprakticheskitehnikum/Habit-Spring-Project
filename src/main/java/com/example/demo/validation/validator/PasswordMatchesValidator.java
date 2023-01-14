package com.example.demo.validation.validator;

import com.example.demo.payload.request.SignUpRequest;
import com.example.demo.validation.constraint.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        SignUpRequest user = (SignUpRequest) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
