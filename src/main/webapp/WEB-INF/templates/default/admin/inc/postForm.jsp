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

<form:form method="post" modelAttribute="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <%-- Title --%>
    <c:set var="titleError">
        <form:errors path="title"/>
    </c:set>

    <div class="form-group ${not empty titleError ? 'has-error' : ''}">
        <label for="title" class="control-label">
            <spring:message code="postForm.title.label"/>
        </label>
        <spring:message code="postForm.title.placeholder" var="postFormTitlePlaceholder"/>
        <form:input path="title" class="form-control" id="title" type="text"
                    placeholder="${postFormTitlePlaceholder}"/>
                            <span class="help-block">
                                <form:errors path="title"/>
                            </span>
    </div>

    <%--Blogs--%>
    <c:set var="blogsError">
        <form:errors path="blogs"/>
    </c:set>
    <div class="form-group">
        <label for="blogs" class="control-label">
            <spring:message code="postForm.blogs.label"/>
        </label>
        <form:checkboxes path="blogs" items="${allBlogs}" itemValue="id"/>
        <span class="help-block">
            <form:errors path="blogs"/>
        </span>
    </div>
    <br>

    <%-- Tags --%>
    <c:set var="tagsError">
        <form:errors path="tags"/>
    </c:set>

    <div class="form-group ${not empty tagsError ? 'has-error' : ''}">
        <label for="tags" class="control-label">
            <spring:message code="postForm.tags.label"/>
        </label>
        <spring:message code="postForm.tags.placeholder" var="postFormTitlePlaceholder"/>
        <form:input path="tags" class="form-control" id="tags" type="text"
                    placeholder="${postFormTitlePlaceholder}"/>
                                <span class="help-block">
                                    <form:errors path="tags"/>
                                </span>
    </div>

    <%-- ShortText --%>
    <c:set var="shortTextError">
        <form:errors path="shortText"/>
    </c:set>

    <div class="form-group ${not empty shortTextError ? 'has-error' : ''}">
        <label for="shortText" class="control-label">
            <spring:message code="postForm.shortText.label"/>
        </label>
        <spring:message code="postForm.shortText.placeholder" var="postFormTitlePlaceholder"/>
        <form:textarea path="shortText" class="form-control" id="text" rows="20"
                       placeholder="${postFormTitlePlaceholder}"/>
                                <span class="help-block">
                                    <form:errors path="shortText"/>
                                </span>
    </div>

    <%-- Title --%>
    <c:set var="textError">
        <form:errors path="text"/>
    </c:set>

    <div class="form-group ${not empty textError ? 'has-error' : ''}">
        <label for="text" class="control-label">
            <spring:message code="postForm.text.label"/>
        </label>
        <spring:message code="postForm.text.placeholder" var="postFormTitlePlaceholder"/>
        <form:textarea path="text" class="form-control" id="text" rows="20"
                       placeholder="${postFormTitlePlaceholder}"/>
                                    <span class="help-block">
                                        <form:errors path="text"/>
                                    </span>
    </div>


    <%-- Hidden field: id --%>
    <form:input type="hidden" path="id"/>

    <%-- Buttons: Submit and Reset --%>
    <button class="btn btn-primary" type="submit">
        <spring:message code="postForm.button.submit" text="Save"/>
    </button>
    <button class="btn btn-primary" type="reset">
        <spring:message code="postForm.button.reset" text="Reset"/>
    </button>

</form:form>

