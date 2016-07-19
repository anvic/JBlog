<%@tag description="Overall Page template 2 columns" pageEncoding="UTF-8" %>
<%@attribute name="title" fragment="true" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="sidebar" fragment="true" %>

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

<!DOCTYPE html>
<html>
<head>

    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>
        <jsp:invoke fragment="title"/>
    </title>
    <%@ include file="/WEB-INF/templates/default/user/inc/cssLinks.jsp" %>

</head>

<body>


<jsp:invoke fragment="header"/>


<div class="container">
    <div class="row">
        <section id="main-content" class="col-sm-9">
            <jsp:doBody/>
        </section>

        <aside id="sidebar" class="col-sm-3">
            <jsp:invoke fragment="sidebar"/>
        </aside>
    </div>
    <!-- /.row -->

</div>
<!-- /.container -->


<footer class="footer">
    <div class="container">
        <p class="text-muted">Licensed under the Apache License, Version 2.0.</p>
    </div>
</footer>


<%@ include file="/WEB-INF/templates/default/user/inc/jsLinks.jsp" %>


</body>
</html>