<%--
  Created by IntelliJ IDEA.
  User: XT
  Date: 2009-8-25
  Time: 17:17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--导入整个页面需要用到的标签--%>
<%@ include file="/WEB-INF/jsp/comm/comm_taglib.jsp" %>
<html>
<head><title>修改字典数据</title>
        <%--导入后台需要用到的CSS及JS--%>
    <%@ include file="/WEB-INF/jsp/comm/js_css_comm_back.jsp" %>
    <style type="text/css">
        <!--
        .errorRed {
            color: red;
        }

        .okBlue {
            color: chartreuse;
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
<tr>
    <td width="50%" align="left" valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <form action="/adminback/zd/DictData.htm?method=update" method="post">
                <tr>
                    <td height="30" colspan="2" align="center" valign="middle" bgcolor="#808080">修改字典信息</td>
                </tr>
                <tr>
                    <td width="150" height="30" align="right" bgcolor="#efefef">父类代码：</td>
                    <td height="30" align="left">
                        <label>${dd.parent.id}</label><span>[不可更改]</span>
                    </td>
                </tr>


                <tr>
                    <td width="150" height="30" align="right" bgcolor="#efefef">字典名称：</td>
                    <td height="30" align="left">
                        <input type="text" name="dictData.name" id="dname" value="${dd.name}"
                               onchange="是否唯一('#dname','name','#existName')"/>
                        <span id="dnameerror" style="display:none;">[必填,长度不得小于2位!]</span>
                        <span id="existName"></span>
                    </td>
                </tr>
                <tr>
                    <td width="150" height="30" align="right" bgcolor="#efefef">字典代码：</td>
                    <td height="30" align="left">
                        <span>${dd.code}</span>
                        <input type="hidden" name="code" id="dcode" value="${dd.code}" />
                        <span id="dcodeerror">[不可更改]</span>
                        <span id="existCode"></span>
                    </td>
                </tr>
                <tr>
                    <td width="150" height="30" align="right" bgcolor="#efefef">备注：</td>
                    <td height="30" align="left">
                        <textarea name="dictData.remark" cols="30" rows="5" id="textfield3">${dd.remark}</textarea></td>
                </tr>
                <tr>
                    <td width="150" height="30" align="right" bgcolor="#efefef">是否禁用</td>
                    <td height="30" align="left"><select name="dictData.state">
                        <c:if test="${dd.disabled == true}">
                            <option value="true" selected="true">是</option>
                            <option value="false">否</option>
                        </c:if>
                        <c:if test="${dd.disabled != true}">
                            <option value="true">是</option>
                            <option value="false" selected="true">否</option>
                        </c:if>
                    </select></td>
                </tr>
                <tr>
                    <td width="150" height="30" align="right" bgcolor="#efefef">操作：</td>
                    <td height="30" align="left">
                        <input type="hidden" name="id" value="${dd.id}">
                        <%--由js控制表单提交,监听此按钮--%>
                        <input type="submit" name="button" id="dsave" value="确认修改"/>
                        <input type="button" name="button" value="取消并返回" onclick="history.back()"/></td>
                </tr>
            </form>
        </table>
    </td>
</tr>
</body>
</html>
