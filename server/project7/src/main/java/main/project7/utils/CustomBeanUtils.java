package main.project7.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Collection;

@Component
public class CustomBeanUtils<T> {
    public T copyNonNullProperties(T source, T destination) {
        if (source == null || destination == null || source.getClass() != destination.getClass()) {
            return null;
        }

        final BeanWrapper src = new BeanWrapperImpl(source);
        final BeanWrapper dest = new BeanWrapperImpl(destination);

        for (final Field field : source.getClass().getDeclaredFields()) {
            PropertyDescriptor propertyDescriptor = src.getPropertyDescriptor(field.getName());
            String propertyName = propertyDescriptor.getPropertyType().getSimpleName();

            // Collection 타입의 필드는 복사하지 않음
            if (Collection.class.isAssignableFrom(propertyDescriptor.getPropertyType())) {
                continue;
            }

            Object sourceProperty = src.getPropertyValue(field.getName());
            if (sourceProperty != null) {
                dest.setPropertyValue(field.getName(), sourceProperty);
            }
        }

        return destination;
    }
}