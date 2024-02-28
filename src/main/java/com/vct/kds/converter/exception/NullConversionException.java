package com.vct.kds.converter.exception;

public class NullConversionException extends RuntimeException {
    public <T> NullConversionException(T source, Class<?> target) {
        super(String.format("%s converion to %s resulted in null", source.getClass().getSimpleName(), target.getSimpleName()));
    }

    public NullConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
