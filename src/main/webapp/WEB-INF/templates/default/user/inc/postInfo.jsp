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

<div class="info">

    <ul class="list-inline">
        <li>
            <div class="btn-group btn-group-xs">
                <button class="btn btn-link rate-item ${(post.currentItemRate.rate != null) ? 'disabled' : ''}"
                        <sec:authorize access="isAnonymous()"> disabled </sec:authorize>
                        data-item="${post.id}"
                        data-type="post"
                        data-value="-1"
                        data-state="${(post.currentItemRate.rate != null) ? 1 : 0}"

                        data-rated="${post.noOfRated}"
                        data-ratedup="${post.noOfRatedUp}"
                        data-rateddown="${post.noOfRatedDown}"
                        data-rating="${post.rating}"
                        href="#">
                    <span class="glyphicon glyphicon-minus"></span></button>

                <button class="btn btn-link rate-number ${(post.currentItemRate.rate != null) ? 'disabled' : ''}"
                        <sec:authorize access="isAnonymous()"> disabled </sec:authorize>
                        type="button">
                    ${post.rating} </button>

                <button class="btn btn-link rate-item ${(post.currentItemRate.rate != null) ? 'disabled' : ''}"
                        <sec:authorize access="isAnonymous()"> disabled </sec:authorize>
                        data-item="${post.id}"
                        data-type="post"
                        data-value="1"
                        data-state="${(post.currentItemRate.rate != null) ? 1 : 0}"

                        data-rated="${post.noOfRated}"
                        data-ratedup="${post.noOfRatedUp}"
                        data-rateddown="${post.noOfRatedDown}"
                        data-rating="${post.rating}"
                        href="#">
                    <span class="glyphicon glyphicon-plus"></span></button>
            </div>
        </li>

        <li>
            <button class="item-relation ${(post.currentItemRelation != null) ? 'btn btn-link' : 'btn btn-link'}"
                    data-item="${post.id}"
                    data-type="post"
                    data-state="${(post.currentItemRelation != null) ? 1 : 0}"
                    data-subscribers="${post.noOfSubscribers}"
                    <sec:authorize access="isAnonymous()"> disabled </sec:authorize>
                    href="#">
                <c:choose>
                    <c:when test="${(post.currentItemRelation != null)}">
                        <span class="glyphicon glyphicon-star"></span>
                    </c:when>
                    <c:otherwise>
                        <span class="glyphicon glyphicon-star-empty"></span>
                    </c:otherwise>
                </c:choose>
                ${post.noOfSubscribers}
            </button>

        </li>
        <li>
            <c:url value='/account/${post.account.id}/posts' var="showUserURL"/>
            <a href="${showUserURL}">${post.account.firstName} ${post.account.lastName}</a>
        </li>
        <li class="post-info-li">
            <span class="glyphicon glyphicon-comment"></span> ${post.noOfComments}

        </li>
    </ul>
</div>