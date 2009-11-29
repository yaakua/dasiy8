<%--
  Created by IntelliJ IDEA.
  User: 阳葵
  Date: 2009-8-25
  Time: 17:34:47
  页面功能描述：分页模块
--%>
<%--导入整个页面需要用到的标签--%>
<%@ include file="/WEB-INF/jsp/comm/comm_taglib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    if (typeof(D) == "undefined") {
        alert("请导入all-comm.js文件");
    }
</script>
<%--分页--%>
<spring:bind path="page.currentPage">
    <input type="hidden" name="${status.expression}" id="page_currentPage" value="${status.value}"/>
</spring:bind>
<spring:bind path="page.pageNum">
    <input type="hidden" name="${status.expression}" value="${status.value}" id="pageNum_yk"/>
</spring:bind>
<spring:bind path="page.pageSize">
    <input type="hidden" name="${status.expression}" value="${status.value}" id="pageSize_yk"/>
</spring:bind>
<spring:bind path="page.total">
    <input type="hidden" name="${status.expression}" value="${status.value}" id="total_yk"/>
</spring:bind>
    共有记录${page.total}条 共${page.pageNum}页&nbsp;&nbsp;
    <c:choose>
        <c:when test="${page.pageNum <= 7 || page.currentPage <= 4}">
            <c:set var="begin" value="1"/>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${page.pageNum - page.currentPage < 3}">
                    <c:set var="begin" value="${page.pageNum - 6}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="begin" value="${page.currentPage - 3}"/>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${page.currentPage > 1}">
            <a class="aShowOne" onclick="D.page.formSubmit(this.form, '1');">|<</a>
            <a class="aShowOne" onclick="D.page.formSubmit(this.form, '${page.currentPage - 1}');"><<</a>
        </c:when>
        <c:otherwise> </c:otherwise>
    </c:choose>
    <c:forEach begin="${begin}" end="${page.pageNum - begin < 7 ? page.pageNum : begin + 6 }" varStatus="status">
        <c:choose>
            <c:when test="${status.index == page.currentPage}">
                <b><font color="black">[${status.index}]</font></b>
            </c:when>
            <c:otherwise>
                <a class="aShowOne" style="cursor:pointer"
                   onclick="D.page.formSubmit(this.form, '${status.index}');">[${status.index}]</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <c:choose>
        <c:when test="${page.currentPage < page.pageNum}">
            <a class="aShowOne" style="cursor:pointer"
               onclick="D.page.formSubmit(this.form, '${page.currentPage + 1}');">
                >>
            </a>
            <a class="aShowOne" style="cursor:pointer" onclick="D.page.formSubmit(this.form, '${page.pageNum}');">
                >|
            </a>
        </c:when>
        <c:otherwise> </c:otherwise>
    </c:choose>
    <c:if test="${page.pageNum > 1}">
        跳转到：<input size="1" style="font-size:12px;width:25px;padding:0" onkeyup="return D.page.enterPageNum(this.form, this.value,event);"/>
        <label style="width:20px;">&nbsp;</label>
    </c:if>
