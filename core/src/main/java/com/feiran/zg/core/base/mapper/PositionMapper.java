package com.feiran.zg.core.base.mapper;

import com.feiran.zg.core.base.domain.Position;
import java.util.List;

public interface PositionMapper {

    Position selectByCountryside(String town);
}