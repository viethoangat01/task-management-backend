package com.personal.taskmanagement.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

/**
 * Support to mapping among these 3 objects - DTO(Data Transfer Object) - VO(Value Object like API
 * request) - Entity(DB domain object)
 */
public class MapperUtil {

  public static final ModelMapper mapper;

  static {
    mapper = new ModelMapper();
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

  }
}
