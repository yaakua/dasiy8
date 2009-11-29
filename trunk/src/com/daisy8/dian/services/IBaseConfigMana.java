/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daisy8.dian.services;

import com.chii.util.mana.IUtilMana;
import com.daisy8.dian.model.base.DictData;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  基础数据设置接口
 * @author 钱剑林
 */
public interface IBaseConfigMana extends IUtilMana {

   

    /**
     * 查询数据字典
     * @param parentID 上级数据字典ID
     * @return
     */
    public List<DictData> findDictData(Long parentID);

    /**
     * 根据一级数据字典代码查询所有二级字典数据
     * @param code
     * @return
     */
    public List<DictData> findDictDataByCode(String code);
    /**
     * 根据一级数据字典代码查询所有二级字典数据
     * @param code
     * @return
     */
    public List<DictData> findUseDictDataByCode(String code);

    /**
     * 添加或删除一个数据字典
     * @param admin
     * @param dd
     */
    @Transactional
    public void saveOrUpdate( DictData dd);


}