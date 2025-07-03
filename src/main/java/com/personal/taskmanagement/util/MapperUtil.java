package com.personal.taskmanagement.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class MapperUtil {

  public static final ModelMapper mapper;

  static {
    mapper = new ModelMapper();
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  }
}
