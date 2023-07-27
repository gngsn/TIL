package com.gngsn.shared;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

/**
 * - validateSelf 메서드를 제공하며, 생성자 코드의 마지막 문장에서 해당 메서드 호출
 * - 유스케이스 구현체 주위에 사실상 오류 방지 레이어(anti corruption layer)를 생성.
 *   - 해당 레이어는 계층형 아키텍처의 계층이 아니라, 잘못된 입력을 호출자에게 돌려주는 유스케이스 보호막
 */
public abstract class SelfValidating<T> {
    private Validator validator;

    public SelfValidating() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * 필드에 저장된 Bean Validation Annotation 검증, 유효성 검증 규칙을 위반한 경우 예외를 던짐.
     */
    protected void validateSelf() {
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
