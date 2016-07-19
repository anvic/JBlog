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
        <spring:message code="blogs.page.htmlTitle"/>
    </jsp:attribute>

    <jsp:attribute name="header">
        <c:set var="topMenu" value="blogs" scope="request"/>
        <%@include file="inc/topMenu.jsp" %>
    </jsp:attribute>

    <jsp:attribute name="sidebar">
        <%@ include file="inc/sidebarLastPosts.jsp" %>
    </jsp:attribute>

    <jsp:body>
        <%--page header--%>
        <spring:message code="blogs.page.header" var="headerTitle"/>
        <%@ include file="inc/headerPage.jsp" %>

        <%--main content--%>
        <div class="media-list blogs">
            <c:forEach items="${blogs.content}" var="blog">
                <div class="media blog" id="blog_${blog.id}">
                    <div class="media-body">
                        <h4 class="media-heading title">
                            <%@ include file="inc/blogTitle.jsp" %>
                            <%@ include file="inc/blogRelationControl.jsp" %>
                        </h4>
                        <%@ include file="inc/blogStatistic.jsp" %>
                        <%@ include file="inc/blogInfo.jsp" %>
                    </div>
                    <div class="media-right">
                        <%@ include file="inc/blogRating.jsp" %>
                    </div>
                </div>
            </c:forEach>
        </div>

        <%--pagination--%>
        <c:set var="totalPages" value="${blogs.totalPages}" scope="request"/>
        <%@include file="inc/pagination.jsp" %>
    </jsp:body>

</custom:generic_2col>

