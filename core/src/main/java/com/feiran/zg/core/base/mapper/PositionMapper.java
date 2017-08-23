package com.feiran.zg.core.base.mapper;

import com.feiran.zg.core.base.domain.Position;
import java.util.List;

public interface PositionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Position record);

    Position selectByPrimaryKey(String id);

    List<Position> selectAll();

    int updateByPrimaryKey(Position record);
}