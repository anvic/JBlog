

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

<form:form method="post" commandName="account" role="form">

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <%--<form:errors path="*"/>--%>

    <%-- First name --%>
    <c:set var="firstNameError">
        <form:errors path="firstName"/>
    </c:set>

    <div class="form-group ${not empty firstNameError ? 'has-error' : ''}">
        <label for="firstName" class="control-label">
            <spring:message code="accountForm.firstName.label"/>
        </label>
        <spring:message code="accountForm.firstName.placeholder" var="accountFormFirstNamePlaceholder"/>
        <form:input path="firstName" class="form-control" id="firstName" type="text"
                    placeholder="${accountFormFirstNamePlaceholder}"/>
                <span class="help-block">
                    <form:errors path="firstName"/>
                </span>
    </div>

    <%-- Last name --%>
    <c:set var="lastNameError">
        <form:errors path="lastName"/>
    </c:set>

    <div class="form-group ${not empty lastNameError ? 'has-error' : ''}">
        <label for="lastName" class="control-label">
            <spring:message code="accountForm.lastName.label"/>
        </label>
        <spring:message code="accountForm.lastName.placeholder" var="accountFormLastNamePlaceholder"/>
        <form:input path="lastName" class="form-control" id="lastName" type="text"
                    placeholder="${accountFormLastNamePlaceholder}"/>
                        <span class="help-block">
                            <form:errors path="lastName"/>
                        </span>
    </div>

    <%-- Info --%>
    <c:set var="infoError">
        <form:errors path="info"/>
    </c:set>

    <div class="form-group ${not empty infoError ? 'has-error' : ''}">
        <label for="info" class="control-label">
            <spring:message code="accountForm.info.label"/>
        </label>
        <spring:message code="accountForm.info.placeholder" var="accountFormLastNamePlaceholder"/>
        <form:input path="info" class="form-control" id="info" type="text"
                    placeholder="${accountFormLastNamePlaceholder}"/>
                            <span class="help-block">
                                <form:errors path="info"/>
                            </span>
    </div>

    <%-- Birth date --%>
    <%--<c:set var="birthDateError">--%>
        <%--<form:errors path="birthDate"/>--%>
    <%--</c:set>--%>

    <%--<div class="form-group ${not empty birthDateError ? 'has-error' : ''}">--%>
        <%--<label for="birthDate" class="control-label">--%>
            <%--<spring:message code="accountForm.birthDate.label"/>--%>
        <%--</label>--%>
        <%--<spring:message code="accountForm.birthDate.placeholder" var="accountFormLastNamePlaceholder"/>--%>
        <%--<form:input path="birthDate" class="form-control" id="birthDate" type="text"--%>
                    <%--placeholder="${accountFormLastNamePlaceholder}"/>--%>
                                <%--<span class="help-block">--%>
                                    <%--<form:errors path="birthDate"/>--%>
                                <%--</span>--%>
    <%--</div>--%>

    <form:input type="hidden" path="id"/>

    <button class="btn btn-primary" type="submit">
        <spring:message code="accountForm.button.submit"/>
    </button>

    <button class="btn btn-primary" type="reset">
        <spring:message code="accountForm.button.reset"/>
    </button>

</form:form>