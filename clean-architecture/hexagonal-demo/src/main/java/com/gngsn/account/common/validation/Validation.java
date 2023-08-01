package com.gngsn.account.common.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.util.Set;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;

/**
 * - validate 메서드 제공
 * - 유스케이스 구현체 주위에 사실상 오류 방지 레이어(anti corruption layer)를 생성.
 *   - 해당 레이어는 계층형 아키텍처의 계층이 아니라, 잘못된 입력을 호출자에게 돌려주는 유스케이스 보호막
 */
public class Validation<T> {

    // Your IDE may complain that the ValidatorFactory needs to be closed, but if we do that here,
    // we break the contract of ValidatorFactory#close.
    private final static Validator validator =
            buildDefaultValidatorFactory().getValidator();

    /**
     * 필드에 저장된 Bean Validation Annotation 검증, 유효성 검증 규칙을 위반한 경우 예외를 던짐.
     */
    public static <T> void validate(T subject) {
        Set<ConstraintViolation<T>> violations = validator.validate(subject);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
