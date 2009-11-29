/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daisy8.dian.dao.impl;

import com.chii.util.query.EntityQuery;
import com.chii.util.query.Page;
import com.chii.util.query.QueryUtils;
import com.chii.util.web.page.SimplePage;
import com.daisy8.dian.dao.IUtilDao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author 钱剑林
 */
public class UtilDao extends HibernateDaoSupport implements IUtilDao {

    public Object get(String hql, Object... params) {
        List list = getHibernateTemplate().find(hql, params);
        if (list.isEmpty()) {
            return null;
        }
        return getHibernateTemplate().find(hql, params).get(0);
    }

    public List find(final String hql, final Object... params) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                if (params != null) {
                    for (int i = 0; i < params.length; i++) {
                        if(params[i] instanceof  Date){
                            query.setDate(i,(Date) params[i]);
                        }else{
                            query.setParameter(i, params[i]);
                        }
                    }
                }
                return query.list();
            }
        });
//        return getHibernateTemplate().find(hql, params);
    }

    public void saveOrUpdate(Object o) {
        getHibernateTemplate().saveOrUpdate(o);
    }

    public HibernateTemplate getHt() {
        return getHibernateTemplate();
    }

    public void find(EntityQuery query, SimplePage page) {
        if (page.getCurrentPage() == null) {
            page.reSetTotal(count(query));
        }
        if (page.getTotal() > 0) {
            query.setPage(new Page(page.getCurrentPage(), page.getPageSize()));
            page.setList(find(query));
        }
    }

    public Integer count(EntityQuery query) {
        return QueryUtils.count(query, getSession());
    }

    public List find(EntityQuery query) {
        return QueryUtils.find(query, getSession());
    }

    public void save(Object o) {
        getHibernateTemplate().save(o);
    }

    public void update(Object o) {
        getHibernateTemplate().update(o);
    }

    public void saveOrUpdateAll(Collection list) {
        getHt().saveOrUpdateAll(list);
    }

    public List findMax(final String hql, final int num, final Object... param) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                for (int i = 0; i < param.length; i++) {
                    query.setParameter(i, param[i]);
                }
                query.setMaxResults(num);
                return query.list();
            }
        });
    }

    public void deleteAll(Collection delList) {
        getHibernateTemplate().deleteAll(delList);
    }

    public void findPage(final String hql, final SimplePage page, final Object... param) {
        if (page.getTotal() > 0) {
            getHibernateTemplate().executeFind(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    Query query = session.createQuery(hql);
                    for (int i = 0; i < param.length; i++) {
                        query.setParameter(i, param[i]);
                    }
                    if (page.getCurrentPage() == null || page.getCurrentPage() < 1) {
                        page.setCurrentPage(1);
                    }
                    if (page.getPageSize() == null || page.getPageSize() < 1) {
                        page.setPageSize(20);
                    }
                    query.setFirstResult(
                            (page.getCurrentPage() - 1) * page.getPageSize());
                    query.setMaxResults(page.getPageSize());
                    page.setList(query.list());
                    return null;
                }
            });
        }
    }

    public void delete(Object o) {
        getHibernateTemplate().delete(o);
    }

    public void excute(final String hql, final Object... param) {
        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                if (param != null) {
                    int i = 0;
                    for (Object o : param) {
                        query.setParameter(i++, o);
                    }
                }
                query.executeUpdate();
                return null;
            }
        });
    }

    public boolean exist(String hql, Object... param) {
        List list = find(hql, param);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }
}