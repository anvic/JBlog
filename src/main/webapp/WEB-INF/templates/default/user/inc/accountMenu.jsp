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

<div class="account-menu">

    <ul class="nav nav-pills nav-justified">

        <%--Account Posts--%>
        <li role="presentation" ${accountMenu=='posts' ? 'class="active"' : ''}>
            <c:url value="/account/${account.id}/posts" var="postsUrl"/>
            <a href="${postsUrl}">
                <spring:message code="account.menu.posts"/>
                <%--${account.noOfPosts}--%>
            </a></li>

        <%--Account Comments--%>
        <li role="presentation" ${accountMenu=='comments' ? 'class="active"' : ''}>
            <c:url value="/account/${account.id}/comments" var="commentsUrl"/>
            <a href="${commentsUrl}">
                <spring:message code="account.menu.comments"/>
            </a></li>

        <%--Account Favorite Posts--%>
        <li role="presentation" ${accountMenu=='favPosts' ? 'class="active"' : ''}>
            <c:url value="/account/${account.id}/favorite/posts" var="favPostsUrl"/>
            <a href="${favPostsUrl}">
                <spring:message code="account.menu.favoritePosts"/>
            </a></li>

        <%--Account Subscribers--%>
        <li role="presentation" ${accountMenu=='subscribers' ? 'class="active"' : ''}>
            <c:url value="/account/${account.id}/subscribers" var="subscribersUrl"/>
            <a href="${subscribersUrl}">
                <spring:message code="account.menu.subscribers"/>
                <%--${account.noOfSubscribers}--%>
            </a></li>

    </ul>
</div>