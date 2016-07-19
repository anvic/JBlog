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

    <c:if test="${param.error eq 'bad_credentials'}">
        <div class="alert alert-danger alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            <spring:message code="login.page.badCredentials"/>
        </div>
    </c:if>

    <c:url var="loginUrl" value="login/authenticate"/>
    <form action="${loginUrl}" method="POST" role="form">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <%--Email --%>
        <div class="form-group">
            <label for="username" class="control-label">
                <spring:message code="login.email.label"/>
            </label>
            <spring:message code="login.email.placeholder" var="loginEmailPlaceholder"/>
            <input name="username" class="form-control" id="username" type="text"
                   placeholder="${loginEmailPlaceholder}"/>
        </div>

        <div class="form-group">
            <label for="password" class="control-label">
                <spring:message code="login.password.label"/>
            </label>
            <spring:message code="login.password.placeholder" var="loginPasswordPlaceholder"/>
            <input name="password" class="form-control" id="password" type="password"
                   placeholder="${loginPasswordPlaceholder}"/>
        </div>

        <button class="btn btn-primary" type="submit">
            <spring:message code="login.button.submit"/>
        </button>

    </form>

    <br>
    <form action="<c:url value="/auth/facebook" />" method="POST">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="scope" value="email"/>
        <button type="submit" class="btn btn-primary">
            Facebook
        </button>
    </form>

    <br>
    <form action="<c:url value="/auth/twitter" />" method="POST">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="scope" value="email"/>
        <button type="submit" class="btn btn-primary">
            Twitter
        </button>
    </form>
</sec:authorize>


<sec:authorize access="isAuthenticated()">
    <p><spring:message code="login_form.user_is_signed_in"/></p>
</sec:authorize>
