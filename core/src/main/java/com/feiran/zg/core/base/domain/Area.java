package com.feiran.zg.core.base.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Area extends BaseDomain{

    private String areaName;

    private Integer parentId;

    private Boolean level;

}