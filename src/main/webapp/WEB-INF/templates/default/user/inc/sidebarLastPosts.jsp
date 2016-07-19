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

<c:if test="${not empty lastPosts}">
    <div class="well sidebar-box">
        <div class="header">
            <spring:message code="sidebar.header.lastPosts"/>
        </div>

        <ul class="post-list">
            <c:forEach items="${lastPosts}" var="post">
                <c:url value='/post/${post.id}/' var="postURL"/>
                <li>
                    <a href="${postURL}">${post.title} </a>
                </li>
            </c:forEach>
        </ul>
    </div>
</c:if>


