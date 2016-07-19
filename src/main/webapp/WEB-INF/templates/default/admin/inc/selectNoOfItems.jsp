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

<form action="${currentUrl}" method="get" class="form-inline" role="form">
    <input type="hidden" name="sort" value="${fn:replace(pageable.sort,': ',',')}"/>
    <input type="hidden" name="page" value="0"/>
    <select class="form-control input-sm" name="size" onchange="this.form.submit();">

        <c:forEach items="${itemsPerPageList}" var="itemsPerPage">
                    <option value="${itemsPerPage}"
                            <c:if test="${itemsPerPage==pageable.pageSize}">selected</c:if> >
                            ${itemsPerPage}
                    </option>
                </c:forEach>

    </select>
</form>