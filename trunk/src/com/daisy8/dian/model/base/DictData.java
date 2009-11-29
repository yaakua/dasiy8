package com.daisy8.dian.model.base;

import com.chii.util.model.DirEntity;

public class DictData extends DirEntity {
    /**
     * 管理员角色字典代码
     */
    public static final String ADMIN_ROLE = "10";
    /**
     * 站内信模板分类代码
     */
    public static final String Template_ROLE = "20";

    private String remark;

    private String code;

    public DictData() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String val) {
        this.code = val;
    }

    /**
     * <p style="margin-top: 0">
     * &#22791;&#27880;&#20449;&#24687;
     * </p>
     */
    public String getRemark() {
        return remark;
    }

    public void setRemark(String val) {
        this.remark = val;
    }

}