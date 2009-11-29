/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daisy8.dian.services.impl;

import com.chii.util.mana.UtilMana;
import com.daisy8.dian.services.IBaseConfigMana;
import com.daisy8.dian.model.base.DictData;

import java.util.List;

/**
 *
 * @author 钱剑林
 */
public class BaseConfigMana extends UtilMana implements IBaseConfigMana {

    public List<DictData> findDictData(Long parentID) {
        return null;
    }

    public List<DictData> findDictDataByCode(String code) {
        String hql = "FROM DictData a WHERE a.parent.code = ?";
        return dao.find(hql, code);
    }

    public List<DictData> findUseDictDataByCode(String code) {
        String hql = "FROM DictData a WHERE a.parent.code = ? and a.disabled = ?";
        return dao.find(hql, code, false);
    }

    public void saveOrUpdate(DictData dd) {
        if (dd.getId() == null) {
            dao.save(dd);
        } else {
            dao.update(dd);
        }
    }

}