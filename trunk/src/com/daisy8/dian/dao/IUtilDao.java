/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daisy8.dian.dao;

import com.chii.util.query.EntityQuery;
import com.chii.util.web.page.SimplePage;
import java.util.Collection;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 钱剑林
 */
public interface IUtilDao {

    public void save(Object o);
    public void update(Object o);

    public HibernateTemplate getHt();

    /**
     * 查询单一结果
     * @param hql
     * @param params
     * @return
     */
    public Object get(String hql, Object... params);

    /**
     * 简单查询
     * @param hql
     * @param params 参数
     * @return
     */
    public List find(String hql, Object... params);

    /**
     * 分页查询
     * @param query
     * @param page
     */
    public void find(EntityQuery query, SimplePage page);

    public Integer count(EntityQuery query);
    public List find(EntityQuery query);

    public void saveOrUpdateAll(Collection list);

    public List findMax(String hql, int num, Object... param);

    public void deleteAll(Collection delList);

    public void saveOrUpdate(Object o);

    public void findPage(String hql, SimplePage page, Object... param);

    public void delete(Object o);

    public void excute(String hql, Object... param);

    public boolean exist(String hql, Object... param);


}