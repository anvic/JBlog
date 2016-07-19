
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

<sec:authorize access="!isAnonymous()">
<div id="add_comment" class="comment">
    <div class="reply">
        <div id="reply_comments_0" class="reply_comments">
                <form:form method="post" modelAttribute="comment" role="form">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <form:errors path="*" cssClass="error"/>
                    <form:input type="hidden" path="post.id" value="${post.id}"/>
                    <form:input type="hidden" path="id"/>
                    <div class="form-group">
                        <form:textarea path="text" rows="4" class="form-control" id="text"
                                       placeholder="Enter your comment here"/>
                        <button class="btn btn-sm btn-primary btn-block" type="submit">Add comment</button>
                    </div>
                </form:form>
        </div>
    </div>
</div>
</sec:authorize>

