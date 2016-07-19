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
    <input type="hidden" name="size" value="${pageable.pageSize}"/>
    <input type="hidden" name="page" value="0"/>
    <select class="form-control input-sm" name="sort" onchange="this.form.submit();">
        <c:forEach items="${pageableSortList}" var="pageableSortItem">
            <option value="${pageableSortItem}"
                    <c:if test="${pageableSortItem==fn:replace(pageable.sort,': ',',')}">selected</c:if> >

                <c:if test="${pageableSortItem!=fn:replace(pageableSortItem,',ASC',' Up')}">
                    ${fn:replace(pageableSortItem,',ASC',' &uarr;')}</c:if>
                <c:if test="${pageableSortItem!=fn:replace(pageableSortItem,',DESC',' DOWN')}">
                    ${fn:replace(pageableSortItem,',DESC',' &darr;')}</c:if>
            </option>
        </c:forEach>
    </select>
</form>