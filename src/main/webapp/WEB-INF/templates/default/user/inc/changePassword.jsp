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

<sec:authorize access="isAuthenticated()">

    <form:form action="${pageContext.request.contextPath}/admin/changePassword" commandName="changePasswordDTO"
               method="POST"
               enctype="utf8" role="form">

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>


        <%-- Old Password --%>
        <c:set var="oldPasswordError">
            <form:errors path="oldPassword"/>
        </c:set>

        <div class="form-group ${not empty oldPasswordError ? 'has-error' : ''}">
            <label for="oldPassword" class="control-label">
                <spring:message code="changePassword.oldPassword.label"/>
            </label>
            <spring:message code="changePassword.oldPassword.placeholder" var="changePasswordPasswordPlaceholder"/>
            <form:input path="oldPassword" class="form-control" id="oldPassword" type="password"
                        placeholder="${changePasswordPasswordPlaceholder}"/>
                                    <span class="help-block">
                                        <form:errors path="oldPassword"/>
                                    </span>
        </div>

        <%-- Password --%>
        <c:set var="passwordError">
            <form:errors path="password"/>
        </c:set>

        <div class="form-group ${not empty passwordError ? 'has-error' : ''}">
            <label for="password" class="control-label">
                <spring:message code="changePassword.password.label"/>
            </label>
            <spring:message code="changePassword.password.placeholder" var="changePasswordPasswordPlaceholder"/>
            <form:input path="password" class="form-control" id="password" type="password"
                        placeholder="${changePasswordPasswordPlaceholder}"/>
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
                <spring:message code="changePassword.passwordVerification.label"/>
            </label>
            <spring:message code="changePassword.passwordVerification.placeholder"
                            var="changePasswordPasswordVerificationPlaceholder"/>
            <form:input path="passwordVerification" class="form-control" id="passwordVerification" type="password"
                        placeholder="${changePasswordPasswordVerificationPlaceholder}"/>
                            <span class="help-block">
                                <form:errors path="passwordVerification"/>
                            </span>
        </div>
        <%--</c:if>--%>

        <button class="btn btn-primary" type="submit">
            <spring:message code="changePassword.button.submit"/>
        </button>

        <button class="btn btn-primary" type="reset">
            <spring:message code="changePassword.button.reset"/>
        </button>

    </form:form>

</sec:authorize>

<sec:authorize access="isAnonymous()">
    <p><spring:message code="accountRegistration.page.signedInAlready"/></p>
</sec:authorize>