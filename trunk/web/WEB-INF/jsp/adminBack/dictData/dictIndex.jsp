<%--
  Created by IntelliJ IDEA.
  User: XT
  Date: 2009-8-25
  Time: 11:26:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--导入整个页面需要用到的标签--%>
<%@ include file="/WEB-INF/jsp/comm/comm_taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>数据字典</title>
    <%--导入后台需要用到的CSS及JS--%>
    <%@ include file="/WEB-INF/jsp/comm/js_css_comm_back.jsp" %>
    <style type="text/css">
        <!--
        .errorRed {
            color: red;
        }

        body, td, th {
            font-size: 12px;
            color: #033;
        }

        body {
            background-color: #FFF;
        }

        a:link {
            color: #033;
        }

        a:visited {
            color: #033;
        }

        -->
    </style>
</head>

<body>
<table width="100%%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td height="30" colspan="2">
            <table width="100%%" border="0" >
                <tr>
                    <td width="50%" height="30" align="left" valign="middle"><p>
                        字典信息新增：1.对字典信息进行增加；2.每项必填；3.此操作由数据管理员进行操作；4.误操作后请及时联系管理员</p></td>
                    <td width="50%" align="left" valign="middle">字典信息列表：1.点击“修改”进行修改操作；</td>
                </tr>
                <tr>
                    <td>

                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td width="50%" align="left" valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <form action="/adminback/zd/DictData.htm?method=save" method="post" id="insert">
                    <tr>
                        <td height="30" colspan="2" align="center" valign="middle" bgcolor="#808080">添加字典信息</td>
                    </tr>
                    <tr>
                        <td width="150" height="30" align="right">字典数据类别：</td>
                        <td height="30" align="left">
                            <select name="dictData.parentCode" id="parentSelect">
                                <option value="0">顶级分类</option>
                                <c:forEach items="${list}" var="dd">
                                    <option value="${dd.id}">${dd.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td width="150" height="30" align="right" bgcolor="#efefef">字典名称*：</td>
                        <td height="30" align="left" bgcolor="#efefef">
                            <%-- <form:input path="name"/>--%>
                            <input type="text" name="dictData.name" id="dname"/>
                            <span id="dnameerror" style="display:none;">[必填,长度不得小于2位!]</span></td>
                    </tr>
                    <tr>
                        <td width="150" height="30" align="right">字典代码*：</td>
                        <td height="30" align="left">
                            <%--<form:input path="code"/>--%>
                            <input type="text" name="dictData.code" id="dcode"/>
                            <span id="dcodeerror" style="display:none;">[必填，长度不得小于2位!]示例:顶级代码:10 子类:1001</span></td>
                    </tr>
                    <tr>
                        <td width="150" height="30" align="right" bgcolor="#efefef">备注：</td>
                        <td height="30" align="left" bgcolor="#efefef">
                            <%-- <form:textarea path="remark"/>--%>
                            <textarea name="dictData.remark" cols="30" rows="5" id="textfield3"></textarea></td>
                    </tr>
                    <tr>
                        <td width="150" height="30" align="right">是否禁用</td>
                        <td height="30" align="left"><select name="dictData.state">
                            <option value="false">否</option>
                            <option value="true">是</option>
                        </select></td>
                    </tr>
                    <tr>
                        <td width="150" height="30" align="right" bgcolor="#efefef">操作：</td>
                        <td height="30" align="left" bgcolor="#efefef">
                            <input type="button" name="button" id="dsave" value="确认新增"/>
                            <label style="color:red">${msg}</label></td>
                    </tr>
                </form>
                <tr><td height="30" colspan="2" bgcolor="#808080"></td></tr>
            </table>
        </td>
        <td width="50%" valign="top">
            <table width="100%" border="0"  class="list">
                <tr>
                    <td height="30" colspan="7" align="center" valign="middle" style="font-size:16px;">数据字典列表搜索：
                        <select name="dictData.parentCode" id="search">
                            <option value="0">顶级分类</option>
                            <c:forEach items="${list}" var="dd">
                                <option value="${dd.id}"
                                        <c:if test="${parentCode == dd.id}">selected</c:if>>${dd.name}</option>
                            </c:forEach>
                        </select></td>
                </tr>
                <tr>
                    <td height="30" align="center" valign="middle" bgcolor="#808080">字典代码</td>
                    <td height="30" align="center" valign="middle" bgcolor="#808080">字典名字</td>
                    <td align="center" valign="middle" bgcolor="#808080">父类代码</td>
                    <td align="center" valign="middle" bgcolor="#808080">是否禁用</td>
                    <td align="center" valign="middle" bgcolor="#808080">&nbsp;</td>
                </tr>
                <%--如果parentCode为空,则说明当页面加载时进行顶级分类的显示--%>
                <c:if test="${empty parentCode}">
                    <c:forEach items="${list}" var="dd" varStatus="status">
                        <c:if test="${status.index %2 == 0}">
                            <tr>
                        </c:if>
                        <c:if test="${status.index %2 != 0}">
                            <tr bgcolor="#efefef">
                        </c:if>
                        <td height="30" align="center" valign="middle">${dd.code}</td>
                        <td height="30" align="center" valign="middle">${dd.name}</td>
                        <td align="center" valign="middle">${dd.parent.id}</td>
                        <td align="center" valign="middle">${dd.disabled}</td>
                        <td align="center" valign="middle"><a
                                href="/adminback/zd/DictData.htm?method=modify&code=${dd.id}">修改</a>
                        </td>
                        </tr>
                    </c:forEach>
                </c:if>

                <c:if test="${!empty parentCode}">
                    <c:forEach items="${nextList}" var="dd" varStatus="status">
                        <c:if test="${status.index %2 == 0}">
                            <tr class="list-row-even">
                        </c:if>
                        <c:if test="${status.index %2 != 0}">
                            <tr class="list-row-odd">
                        </c:if>
                        <td height="30" align="center" valign="middle">${dd.code}</td>
                        <td height="30" align="center" valign="middle">${dd.name}</td>
                        <td align="center" valign="middle">${dd.parent.id}</td>
                        <td align="center" valign="middle">${dd.disabled}</td>
                        <td align="center" valign="middle">
                            <a href="/adminback/zd/DictData.htm?method=modify&code=${dd.id}">修改</a>
                        </td>
                        </tr>
                    </c:forEach></c:if>
            </table>
        </td>
    </tr>
    <tr>
        <td height="30" colspan="2">&nbsp;</td>
    </tr>
</table>
</body>
</html>
