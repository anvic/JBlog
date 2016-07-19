<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="custom" tagdir="/WEB-INF/tags" %>

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

<custom:generic_1col>

    <jsp:attribute name="title">
        <spring:message code="postEdit.page.header"/>
    </jsp:attribute>

    <jsp:attribute name="header">
        <%@include file="inc/topMenu.jsp" %>
    </jsp:attribute>

    <jsp:body>
        <%--page header--%>
        <spring:message code="postEdit.page.header" var="headerTitle"/>
        <%@include file="inc/headerPage.jsp" %>

        <%--main content--%>
        <%@include file="inc/postForm.jsp" %>
    </jsp:body>

</custom:generic_1col>