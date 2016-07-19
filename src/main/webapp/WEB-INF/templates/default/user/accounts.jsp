<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  ~ Copyright 2016 Victor Andreenko
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~     http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<custom:generic_2col>

    <jsp:attribute name="title">
        <spring:message code="accounts.page.htmlTitle"/>
    </jsp:attribute>

    <jsp:attribute name="header">
        <c:set var="topMenu" value="accounts" scope="request"/>
        <%@include file="inc/topMenu.jsp" %>
    </jsp:attribute>

    <jsp:attribute name="sidebar">
    </jsp:attribute>

    <jsp:body>
        <%--page header--%>
        <spring:message code="accounts.page.header" var="headerTitle"/>
        <%@ include file="inc/headerPage.jsp" %>

        <%--main content--%>
        <div class="media-list accounts">
            <c:forEach items="${accounts.content}" var="account">
                <div class="media account" id="account_${account.id}">

                    <%--<div class="media-left">--%>
                        <%--<a href="#">--%>
                            <%--<img class="media-object" src="..." alt="...">--%>
                        <%--</a>--%>
                    <%--</div>--%>
                    <div class="media-body">
                        <h4 class="media-heading title">
                            <%@ include file="inc/accountTitle.jsp" %>
                            <%@ include file="inc/accountRelationControl.jsp" %>
                        </h4>
                        <%@ include file="inc/accountStatistic.jsp" %>
                        <%@ include file="inc/accountInfo.jsp" %>
                    </div>

                    <div class="media-right">
                        <%@ include file="inc/accountRating.jsp" %>
                    </div>
                </div>
            </c:forEach>
        </div>

        <%--pagination--%>
        <c:set var="totalPages" value="${accounts.totalPages}" scope="request"/>
        <%@include file="inc/pagination.jsp" %>
    </jsp:body>

</custom:generic_2col>