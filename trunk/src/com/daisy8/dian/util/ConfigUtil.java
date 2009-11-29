package com.daisy8.dian.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: 阳葵
 * Date: 2009-8-25
 * Time: 14:29:53
 */
public class ConfigUtil {

    /**
     * 缓存时间5分钟
     */
    public final static int CACHE_5MIN = 5 * 60;
    /**
     * 后台每页显示街道列表的数量
     */
    public final static int STREET_BACK_MAX = 20;
    /**
     * 所有图片的数量
     */
    public final static int STREET_PIC_MAX = 100;
    /**
     * 前台每页显示街道评论的数量
     */
    public final static int STREET_DIANPING_MAX = 10;
    /**
     * 信件每页显示数量
     */
    public final static int LETTER_MAX = 10;
    /**
     * 每页显示的好友的数量
     */
    public final static int FRIEND_MAX = 24;
    /**
     * 分页显示数量，15个
     */
    public final static int PAGE_NUM_15 = 15;
    public final static int PAGE_NUM_10 = 10;
    public final static int PAGE_NUM_20 = 20;
    public final static int PAGE_NUM_30 = 30;
    public final static int PAGE_NUM_50 = 50;

    /**
     * 后台管理员操作的分页数量
     */
    public final static int PAGE_ADMINLOG_NUM = 30;

    public final static String CHECK_CODE = "code";
    public final static String ADMIN = "admin";
    /**
     * 用于保存在session当中标识用户字段
     */
    public final static String CUSTOMER = "customer";
    /**
     * Cookie中设定的登陆次数
     */
    public final static int LOGINTIMES_MAX = 5;
    /**
     * Cookie过期时间
     */
    public final static int LOGINTIMES_MAX_TIME = 3600;
    /**
     * 7天过期时间
     */
    public final static int MAX_7DAY = 604800;
    /**
     * 登录成功后在Cookie中保存用户ID,用户昵称,
     */
    public final static String COOKIE_USER_ID = "id";
    public final static String COOKIE_USER_NAME = "name";
    public final static String COOKIE_USER_LOGO = "logo";

    /**
     * 图片服务器根据此参数名判断是重命名还是删除
     */
    public final static String RENAME_STR = "renameURL";
    public final static String DELETENAME_STR = "deleteURL";


    /**
     * XML地址
     */
    public final static String PRODUCT_SHOW_XML_PATH = "/x/comm/";

    /**
     * �?��验证码是否正确忽略大小写
     *
     * @param request   request
     * @param checkCode 要检测的验证�?
     * @return 相等为true
     */
    public static boolean isTrueCheckCode(HttpServletRequest request, String checkCode) {
        try {
            Object o = request.getSession().getAttribute(ConfigUtil.CHECK_CODE);
            if (o != null) {
                String code = (String) o;
                return code.equalsIgnoreCase(checkCode);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isTrueCheckCode(HttpServletRequest request, String checkCode, String sessionKey) {
        try {
            Object o = request.getSession().getAttribute(sessionKey);
            if (o != null) {
                String code = (String) o;
                return code.equalsIgnoreCase(checkCode);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private ConfigUtil() {
    }
}