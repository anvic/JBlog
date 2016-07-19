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


<table>
    <tr>
        <td>Number of accounts: ${accounts.totalElements} &nbsp;</td>
        <td>Items on page: &nbsp;</td>
        <td>
            <%@include file="selectNoOfItems.jsp" %>
        </td>
        <td>&nbsp;&nbsp; Sort by: &nbsp; </td>
        <td>
            <%@include file="selectSortOfItems.jsp" %>
        </td>
    </tr>
</table>
<hr>

<c:if test="${not empty accounts.content}">
    <table class="table table-striped table-bordered table-hover table-condensed table-responsive">
        <thead>
        <tr>
            <th> ID</th>
            <th> FullName</th>
            <th> Role</th>
            <th> EMail</th>
            <th> Registration date</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${accounts.content}" var="account">
            <tr>
                <td> ${account.id}</td>
                <td> ${account.firstName} ${account.lastName}</td>
                <td>
                    <form class="form-inline" role="form" action='' method="get">
                        <select data-account="${account.id}" id="role${account.id}"
                                class="account-role form-control input-sm" name="accountRole">
                            <c:forEach items="${accountRoles}" var="accountRole">
                                <option value="${accountRole}"
                                        <c:if test="${account.accountRole==accountRole}">selected</c:if>>${accountRole}</option>
                            </c:forEach>
                        </select>
                    </form>
                </td>
                <td>${account.email}</td>
                <td><fmt:formatDate value="${account.registrationDate}" pattern="dd-MM-yyyy"/></td>


            </tr>
        </c:forEach>

        </tbody>
    </table>

</c:if>

<c:if test="${empty accounts.content}">
    There are no accounts
</c:if>


