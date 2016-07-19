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

<div class="media account-header-page" id="account_${account.id}">
    <%--<div class="media-left">--%>
        <%--<a href="#">--%>
            <%--<img class="media-object" src="..." alt="...">--%>
        <%--</a>--%>
    <%--</div>--%>

    <div class="media-body">
        <h4 class="media-heading title">
            <%@ include file="accountTitle.jsp" %>
            <%@ include file="accountRelationControl.jsp" %>
        </h4>
        <%@ include file="accountStatistic.jsp" %>
        <%@ include file="accountInfo.jsp" %>
    </div>

    <div class="media-right">
        <%@ include file="accountRating.jsp" %>
    </div>
</div>
