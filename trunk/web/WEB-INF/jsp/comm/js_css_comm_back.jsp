<%--
  Created by IntelliJ IDEA.
  User: 阳葵
  Date: 2009-8-24
  Time: 16:10:20
  页面功能描述：后台共用的JS及CSS导入,通常用于头部的head区域导入
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="/css/ht1.0.css"/>
<script type="text/javascript" src="/js/comm/jquery1.3.2_other.js"></script>
<script type="text/javascript">
    $(function() {
         if ($.browser.msie && $.browser.version != "7.0") {
             document.domain=document.domain;
        }
        if(!$.browser.msie){
             document.domain=document.domain;
        }
    });
</script>
