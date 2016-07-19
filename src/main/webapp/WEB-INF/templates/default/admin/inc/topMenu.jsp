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

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Admin Page</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li <c:if test="${topMenu=='blogs'}">class="active"</c:if>>
                        <c:url value="/admin/blogs" var="blogsUrl"/>
                        <a href="${blogsUrl}">Blogs</a></li>
                </sec:authorize>

                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li <c:if test="${topMenu=='accounts'}">class="active"</c:if>>
                        <c:url value="/admin/accounts" var="accountsUrl"/>
                        <a href="${accountsUrl}">Users</a></li>
                </sec:authorize>

                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')">
                    <li <c:if test="${topMenu=='posts'}">class="active"</c:if>>
                        <c:url value="/admin/posts" var="postsUrl"/>
                        <a href="${postsUrl}">Posts</a></li>
                </sec:authorize>

                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')">
                    <li <c:if test="${topMenu=='myposts'}">class="active"</c:if>>
                        <c:url value="/admin/myposts" var="myPostsUrl"/>
                        <a href="${myPostsUrl}">My Posts</a></li>
                </sec:authorize>
                <li>
                    <c:url value="/" var="userPageUrl"/>
                    <a href="${userPageUrl}">Return to User Page</a></li>
            </ul>


            <ul class="nav navbar-nav navbar-right">

                <sec:authorize access="!isAnonymous()">
                    <li class="dropdown">

                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false">
                            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                                ${currentUser.firstName} ${currentUser.lastName} <span class="caret"></span></a>

                        <ul class="dropdown-menu">
                            <c:url value="/post/add" var="addPostUrl"/>
                            <li><a href="${addPostUrl}">Add Post</a></li>

                            <c:url value="/admin/myposts" var="editMyPostsUrl"/>
                            <li><a href="${editMyPostsUrl}">Edit My Posts</a></li>

                            <li role="separator" class="divider"></li>

                            <c:url value="/admin/editProfile" var="editAccountUrl"/>
                            <li><a href="${editAccountUrl}">Edit profile</a></li>

                            <c:url value="/admin/changePassword" var="changePasswordUrl"/>
                            <li><a href="${changePasswordUrl}">Change Password</a></li>

                            <c:url value="/signout" var="signoutUrl"/>
                            <li><a href="${signoutUrl}">SignOut</a></li>
                        </ul>
                    </li>
                </sec:authorize>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>


