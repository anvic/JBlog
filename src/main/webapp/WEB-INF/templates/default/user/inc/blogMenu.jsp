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

<div class="blog-menu">
    <ul class="nav nav-pills nav-justified">
        <%--Blog Posts--%>
        <li role="presentation" ${blogMenu=='posts' ? 'class="active"' : ''}>
            <c:url value="/blog/${blog.id}/posts" var="postsUrl"/>
            <a href="${postsUrl}">
                <spring:message code="blog.menu.posts"/></a></li>

        <%--Blog Subscribers--%>
        <li role="presentation" ${blogMenu=='subsribers' ? 'class="active"' : ''}>
            <c:url value="/blog/${blog.id}/subscribers" var="subscribersUrl"/>
            <a href="${subscribersUrl}">
                <spring:message code="blog.menu.subscribers"/></a></li>

    </ul>
</div>