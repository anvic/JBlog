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

<button class="item-relation ${(account.currentItemRelation != null) ? 'btn btn-primary btn-xs' : 'btn btn-default btn-xs'}"
        <sec:authorize access="isAnonymous()"> disabled </sec:authorize>
        data-item="${account.id}"
        data-type="account"
        data-state="${(account.currentItemRelation != null) ? 1 : 0}"
        data-subscribers="${account.noOfSubscribers}" href="#">
    <c:choose>
        <c:when test="${(account.currentItemRelation != null)}">
            <span class="glyphicon glyphicon-star"></span>
            <spring:message code='accountRelation.subscribed.label'/>
        </c:when>
        <c:otherwise>
            <span class="glyphicon glyphicon-star-empty"></span>
            <spring:message code='accountRelation.unsubscribed.label'/>
        </c:otherwise>
    </c:choose>
</button>