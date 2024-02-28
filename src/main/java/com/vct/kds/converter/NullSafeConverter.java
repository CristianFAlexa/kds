package com.vct.kds.converter;

import com.vct.kds.converter.exception.NullConversionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
@Slf4j
public class NullSafeConverter {

    private final ConversionService conversionService;

    public NullSafeConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /**
     * Converts the given source object to the specified target type.
     *
     * @param <S>        The type of the source object.
     * @param <T>        The type of the target object.
     * @param source     The source object to convert, which must be an instance of S.
     * @param targetType The target class to convert the source object to, which must be an instance of <T>.
     * @return The converted object, an instance of targetType.
     * @throws NullConversionException exception thrown if the conversion fails.
     */
    public <S, T> T convert(S source, Class<T> targetType) {
        log.debug("Converting an object of type {} to an object of type {}", source.getClass().getSimpleName(), targetType.getSimpleName());

        T target;
        try {
            target = conversionService.convert(source, targetType);
        } catch (IllegalArgumentException e) {
            throw new NullConversionException("Cannot convert because the target type provided is null.", e);
        } catch (ConversionException e) {
            throw new NullConversionException(format("Conversion from %s to %s failed.", source.getClass().getSimpleName(), targetType), e);
        } catch (RuntimeException e) {
            throw new NullConversionException("Unexpected error during conversion: " + e.getMessage(), e);
        }

        if (target == null) {
            throw new NullConversionException(source, targetType);
        }

        log.debug("Successfully converted the provided object of type {} to an object of type {}",
                source.getClass().getSimpleName(), targetType.getSimpleName());

        return target;
    }
}