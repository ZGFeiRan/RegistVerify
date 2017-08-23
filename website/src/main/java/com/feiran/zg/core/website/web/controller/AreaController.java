package com.feiran.zg.core.website.web.controller;

import com.alibaba.fastjson.JSON;
import com.feiran.zg.core.base.domain.Area;
import com.feiran.zg.core.base.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by FeiRan_ZG on 2017/8/22.
 */
public class AreaController {
    @Autowired
    private IAreaService areaService;

    @RequestMapping("selectAreaByParent")
    @ResponseBody
    public List<Area> selectAreaByParent(Long parentId){
        List<Area> area = areaService.selectByParentKey(parentId);
//        String jsonString = JSON.toJSONString(area);
        return area;
    }
}
