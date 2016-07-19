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
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">JBlogCMS</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li <c:if test="${topMenu=='blogs'}">class="active"</c:if>>
                    <c:url value="/blogs" var="blogsUrl"/>
                    <a href="${blogsUrl}">Blogs</a></li>

                <li <c:if test="${topMenu=='accounts'}">class="active"</c:if>>
                    <c:url value="/accounts" var="accountsUrl"/>
                    <a href="${accountsUrl}">Users</a></li>

                <li <c:if test="${topMenu=='feed'}">class="active"</c:if>>
                    <c:url value="/feed" var="feedUrl"/>
                    <a href="${feedUrl}">Feed</a></li>
            </ul>


            <ul class="nav navbar-nav navbar-right">
                <sec:authorize access="isAnonymous()">
                    <c:url value="/login" var="loginUrl"/>
                    <li><a href="${loginUrl}">LogIn</a></li>

                    <c:url value="/registration" var="registrationUrl"/>
                    <li><a href="${registrationUrl}">Registration</a></li>
                </sec:authorize>

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

                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <li role="separator" class="divider"></li>

                                <c:url value="/admin/posts" var="adminPagePostsUrl"/>
                                <li><a href="${adminPagePostsUrl}">Admin Page</a></li>
                            </sec:authorize>
                        </ul>
                    </li>
                </sec:authorize>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>