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

<form:form method="post" commandName="blog" role="form">

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <%--<form:errors path="*"/>--%>

    <%-- Blog name --%>
    <c:set var="nameError">
        <form:errors path="name"/>
    </c:set>

    <div class="form-group ${not empty nameError ? 'has-error' : ''}">
        <label for="name" class="control-label">
            <spring:message code="blogForm.name.label"/>
        </label>

        <spring:message code="blogForm.name.placeholder" var="blogFormNamePlaceholder"/>
        <form:input path="name" class="form-control" id="name" type="text"
                    placeholder="${blogFormNamePlaceholder}"/>

        <span class="help-block">
            <form:errors path="name"/>
        </span>
    </div>

    <%-- Blog url name --%>
    <c:set var="urlNameError">
        <form:errors path="urlName"/>
    </c:set>

    <div class="form-group ${not empty urlNameError ? 'has-error' : ''}">
        <label for="urlName" class="control-label">
            <spring:message code="blogForm.urlName.label"/>
        </label>

        <spring:message code="blogForm.urlName.label" var="blogFormUrlNamePlaceholder"/>
        <form:input path="urlName" id="urlName" class="form-control" type="text"
                    placeholder="${blogFormUrlNamePlaceholder}"/>

        <span class="help-block">
            <form:errors path="urlName"/>
        </span>
    </div>

    <%-- Short Description --%>
    <c:set var="descriptionError">
        <form:errors path="description"/>
    </c:set>

    <div class="form-group ${not empty descriptionError ? 'has-error' : ''}">
        <label for="description" class="control-label">
            <spring:message code="blogForm.description.label"/>
        </label>
        <spring:message code="blogForm.description.placeholder" var="blogFormDescriptionPlaceholder"/>
        <form:textarea path="description" id="description" class="form-control" rows="2"
                       placeholder="${blogFormDescriptionPlaceholder}"/>

                <span class="help-block">
                    <form:errors path="description"/>
                </span>
    </div>

    <form:input type="hidden" path="id"/>

    <button class="btn btn-success" type="submit">
        <spring:message code="blogForm.button.submit"/>
    </button>

    <button class="btn btn-primary" type="reset">
        <spring:message code="blogForm.button.reset"/>
    </button>

</form:form>

