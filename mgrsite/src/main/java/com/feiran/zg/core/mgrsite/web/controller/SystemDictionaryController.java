package com.feiran.zg.core.mgrsite.web.controller;

import com.feiran.zg.core.base.domain.SystemDictionary;
import com.feiran.zg.core.base.query.SystemDictionaryQueryObject;
import com.feiran.zg.core.base.service.ISystemDictionaryService;
import com.feiran.zg.core.base.domain.SystemDictionaryItem;
import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zhangguangfei on 2017/1/18.
 */
@Controller
public class SystemDictionaryController {

    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    /***
     * 数据字典分类列表
     * @param qo
     * @return
     */
    @RequestMapping("/systemDictionary_list")
    public String systemDictionaryList(@ModelAttribute("qo") SystemDictionaryQueryObject qo, Model model){
        PageResult pageResult = systemDictionaryService.queryForDictionaryPageResult(qo);

        model.addAttribute("pageResult",pageResult);
        return "systemdic/systemDictionary_list";
    }

    /***
     * 添加或修改数据字典分类
     * @param systemDictionary
     * @return
     */
    @RequestMapping("/systemDictionary_update")
    @ResponseBody
    public JsonResult systemDictionaryUpdate(SystemDictionary systemDictionary){
        JsonResult jsonResult = new JsonResult();
        try {
            systemDictionaryService.saveOrUpdateSystemDictionary(systemDictionary);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setSuccess(false);
            jsonResult.setMsg("操作失败");
        }
        return jsonResult;
    }

    /**
     * 数据字典明细列表
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("systemDictionaryItem_list")
    public String systemDictionaryItemList(@ModelAttribute("qo") SystemDictionaryQueryObject qo, Model model){
        PageResult pageResult = systemDictionaryService.queryForDictionaryItemsPageResult(qo);
        model.addAttribute("pageResult",pageResult);

        List<SystemDictionary> systemDictionaryGroups = systemDictionaryService.listForDictionary();
        model.addAttribute("systemDictionaryGroups",systemDictionaryGroups );
        return "systemdic/systemDictionaryItem_list";
    }

    /**
     * 添加/修改 数据字典明细
     * @param systemDictionaryItem
     * @return
     */
    @RequestMapping("systemDictionaryItem_update")
    @ResponseBody
    public JsonResult systemDictionaryItemUpdate(SystemDictionaryItem systemDictionaryItem){
        JsonResult jsonResult = new JsonResult();

        try {
            this.systemDictionaryService.saveOrUpdateSystemDictionaryItem(systemDictionaryItem);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setSuccess(false);
            jsonResult.setMsg(e.getMessage() );
        }

        return jsonResult ;
    }
}
