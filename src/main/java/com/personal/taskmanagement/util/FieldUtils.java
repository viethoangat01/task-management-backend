package com.personal.taskmanagement.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FieldUtils {

  public static List<String> getFieldNames(Class<?> clazz) {
    return Arrays.stream(clazz.getDeclaredFields())
        .map(Field::getName)
        .collect(Collectors.toList());
  }
}
