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

<c:set var="sort" value="${pageable.sort}"/>
<c:set var="pageableSort" value="${fn:replace(sort,':', ',')}"/>
<c:set var="pageablePageSize" value="${pageable.pageSize}"/>

<table>
    <tr>
        <td>Number of blogs: ${blogs.totalElements} &nbsp;</td>
        <td>Items on page: &nbsp;</td>
        <td>
            <%@include file="selectNoOfItems.jsp" %>
        </td>
        <td>&nbsp;&nbsp; Sort by: &nbsp; </td>
        <td>
            <%@include file="selectSortOfItems.jsp" %>
        </td>

        <td>
            <c:url value="/admin/blog/add" var="addBlogURL"/>
            <a href="${addBlogURL}" class="btn btn-primary" role="button">
                <span class="glyphicon glyphicon-plus"></span> Add a new blog</a>
        </td>
    </tr>
</table>

<hr>


<c:if test="${not empty blogs.content}">

    <table class="table table-striped table-bordered table-hover table-condensed table-responsive">
        <thead>
        <tr>
            <th> ID</th>
            <th> Name</th>
            <th> UrlName</th>
            <th> Actions</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${blogs.content}" var="blog">
            <tr>
                <td> ${blog.id}</td>
                <td> ${blog.name}</td>
                <td> ${blog.urlName}</td>
                <td>
                    <c:url value="/admin/blog/update/${blog.id}/" var="updateBlogURL"/>
                    <a href="${updateBlogURL}" class="btn btn-primary btn-xs" role="button">
                        <span class="glyphicon glyphicon-edit"></span> Edit</a>

                    <c:url value="/admin/blog/delete/${blog.id}/" var="deleteBlogURL"/>
                    <a href="${deleteBlogURL}" class="btn btn-primary btn-xs" role="button">
                        <span class="glyphicon glyphicon-remove"></span> Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</c:if>

<c:if test="${empty blogs.content}">
    There are no blogs
</c:if>