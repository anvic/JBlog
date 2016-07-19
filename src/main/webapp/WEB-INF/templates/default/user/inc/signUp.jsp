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

<sec:authorize access="isAnonymous()">

    <form:form action="${pageContext.request.contextPath}/registration" commandName="user" method="POST"
               enctype="utf8" role="form">

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <%--pageError--%>
        <c:set var="pageError">
            <form:errors path="*"/>
        </c:set>

        <c:if test="${not empty pageError}">
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <form:errors path="*"/>
            </div>
        </c:if>

        <%--signInProvider--%>
        <c:if test="${user.signInProvider != null}">
            <form:hidden path="signInProvider"/>
        </c:if>

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

        <%-- Email --%>
        <c:set var="emailError">
            <form:errors path="email"/>
        </c:set>

        <div class="form-group ${not empty emailError ? 'has-error' : ''}">
            <label for="email" class="control-label">
                <spring:message code="accountForm.email.label"/>
            </label>
            <spring:message code="accountForm.email.placeholder" var="accountFormEmailPlaceholder"/>
            <form:input path="email" class="form-control" id="email" type="text"
                        placeholder="${accountFormEmailPlaceholder}"/>
                            <span class="help-block">
                                <form:errors path="email"/>
                            </span>
        </div>

        <c:if test="${user.signInProvider == null}">

            <%-- Password --%>
            <c:set var="passwordError">
                <form:errors path="password"/>
            </c:set>

            <div class="form-group ${not empty passwordError ? 'has-error' : ''}">
                <label for="password" class="control-label">
                    <spring:message code="accountForm.password.label"/>
                </label>
                <spring:message code="accountForm.password.placeholder" var="accountFormPasswordPlaceholder"/>
                <form:input path="password" class="form-control" id="password" type="password"
                            placeholder="${accountFormPasswordPlaceholder}"/>
                            <span class="help-block">
                                <form:errors path="password"/>
                            </span>
            </div>

            <%-- PasswordVerification --%>
            <c:set var="passwordVerificationError">
                <form:errors path="passwordVerification"/>
            </c:set>

            <div class="form-group ${not empty passwordVerificationError ? 'has-error' : ''}">
                <label for="passwordVerification" class="control-label">
                    <spring:message code="accountForm.passwordVerification.label"/>
                </label>
                <spring:message code="accountForm.passwordVerification.placeholder"
                                var="accountFormPasswordVerificationPlaceholder"/>
                <form:input path="passwordVerification" class="form-control" id="passwordVerification" type="password"
                            placeholder="${accountFormPasswordVerificationPlaceholder}"/>
                            <span class="help-block">
                                <form:errors path="passwordVerification"/>
                            </span>
            </div>
        </c:if>

        <form:input type="hidden" path="role"/>

        <button class="btn btn-primary" type="submit">
            <spring:message code="accountForm.button.submit"/>
        </button>

        <button class="btn btn-primary" type="reset">
            <spring:message code="accountForm.button.reset"/>
        </button>

    </form:form>

</sec:authorize>

<sec:authorize access="isAuthenticated()">
    <p><spring:message code="accountRegistration.page.signedInAlready"/></p>
</sec:authorize>