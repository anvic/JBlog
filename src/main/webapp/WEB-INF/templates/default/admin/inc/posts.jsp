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

<table>
    <tr>
        <td>Number of posts: ${posts.totalElements} &nbsp;</td>
        <td>Items on page: &nbsp;</td>
        <td>
            <%@include file="selectNoOfItems.jsp" %>
        </td>
        <td>&nbsp;&nbsp; Sort by: &nbsp; </td>
        <td>
            <%@include file="selectSortOfItems.jsp" %>
        </td>
    </tr>
</table>
<hr>


<c:if test="${not empty posts.content}">
    <table class="table table-striped table-bordered table-hover table-condensed table-responsive">
        <thead>
        <tr>
            <th> ID</th>
            <th> Author</th>
            <th> Title</th>
            <th> Publishing Date</th>
            <th> Actions</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach items="${posts.content}" var="post">
            <tr>
                <td> ${post.id}</td>
                <td><span class="glyphicon glyphicon-user"></span> ${post.account.firstName} ${post.account.lastName}
                </td>
                <td> ${post.title}</td>
                <td><fmt:formatDate value="${post.date}" pattern="dd-MM-yyyy"/></td>
                <td>
                    <c:url value="/admin/post/update/${post.id}/" var="updatePostURL"/>
                    <a href="${updatePostURL}" class="btn btn-primary btn-xs" role="button">
                        <span class="glyphicon glyphicon-edit"></span> Edit</a>

                    <c:url value="/admin/post/delete/${post.id}/" var="deletePostURL"/>
                    <a href="${deletePostURL}" class="btn btn-primary btn-xs" role="button">
                        <span class="glyphicon glyphicon-remove"></span> Delete</a>
                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>

</c:if>

<c:if test="${empty posts.content}">
    There are no posts
</c:if>

