<%--
  Created by IntelliJ IDEA.
  User: 阳葵
  Date: 2009-8-24
  Time: 16:10:20
  页面功能描述：前台共用的JS及CSS导入,通常用于头部的head区域导入 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<link rel="stylesheet" type="text/css" href="/css/ht1.0.css"/>--%>
<script type="text/javascript" src="/js/comm/all-comm.js"></script>
<script type="text/javascript" src="/js/comm/jquery1.3.2_other.js"></script>
<script type="text/javascript">
    var dpURL = "";
    if ($.browser.msie && $.browser.version == "7.0") {
        document.write("<link rel='stylesheet' type='text/css' href='" + dpURL + "/css/mianIE71.0.css'/>");
        document.write("<link rel='stylesheet' type='text/css' href='" + dpURL + "/css/dianpingIE71.0.css'/>");
    } else {
        document.write("<link rel='stylesheet' type='text/css' href='" + dpURL + "/css/mian1.0.css'/>");
        document.write("<link rel='stylesheet' type='text/css' href='" + dpURL + "/css/dianping1.0.css'/>");
        document.domain = document.domain;
    }
</script>
<%--此文件一定要设置document.domain = document.domain 否则登录窗口登录成功后无法返回，及图片上传文件无法正常使用--%>
