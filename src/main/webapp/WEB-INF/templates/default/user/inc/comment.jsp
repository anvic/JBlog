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

<div id="comment_${comment.id}"
     class="media comment">

    <div class="media-body">
        <div class="media-heading title">
            <c:url value='/account/${comment.account.id}/posts' var="showCommentUserURL"/>
            <a href="${showCommentUserURL}">${comment.account.firstName} ${comment.account.lastName}</a>

            &nbsp;&nbsp;
            <fmt:formatDate value="${comment.date}" pattern="dd-MM-yyyy, HH:mm"/>
            &nbsp;|
            <button class="item-relation state${(comment.currentItemRelation != null) ? 1 : 0} ${(comment.currentItemRelation != null) ? 'btn btn-link' : 'btn btn-link'}"
                    data-item="${comment.id}"
                    data-type="comment"
                    data-state="${(comment.currentItemRelation != null) ? 1 : 0}"
                    data-subscribers="${comment.noOfSubscribers}"
                    <sec:authorize access="isAnonymous()"> disabled </sec:authorize>
                    href="#">
                <c:choose>
                    <c:when test="${(comment.currentItemRelation != null)}">
                        <span class="glyphicon glyphicon-star"></span>
                    </c:when>
                    <c:otherwise>
                        <span class="glyphicon glyphicon-star-empty"></span>
                    </c:otherwise>
                </c:choose>
                ${comment.noOfSubscribers}
            </button>



            <div class="pull-right">

                <div class="btn-group btn-group-xs">

                    <button class="btn btn-link rate-item ${(comment.currentItemRate.rate != null) ? 'disabled' : ''}"
                            <sec:authorize access="isAnonymous()"> disabled </sec:authorize>
                            data-item="${comment.id}"
                            data-type="comment"
                            data-value="-1"
                            data-state="${(comment.currentItemRate.rate != null) ? 1 : 0}"
                            data-rated="${comment.noOfRated}"
                            data-ratedup="${comment.noOfRatedUp}"
                            data-rateddown="${comment.noOfRatedDown}"
                            data-rating="${comment.rating}"

                            href="#">
                        <span class="glyphicon glyphicon-menu-down"></span></button>

                    <button class="btn btn-link rate-number ${(comment.currentItemRate.rate != null) ? 'disabled' : ''}"
                            <sec:authorize access="isAnonymous()"> disabled </sec:authorize>
                            type="button">
                        ${comment.rating} </button>

                    <button class="btn btn-link rate-item ${(comment.currentItemRate.rate != null) ? 'disabled' : ''}"
                            <sec:authorize access="isAnonymous()"> disabled </sec:authorize>
                            data-item="${comment.id}"
                            data-type="comment"
                            data-value="1"
                            data-state="${(comment.currentItemRate.rate != null) ? 1 : 0}"
                            data-rated="${comment.noOfRated}"
                            data-ratedup="${comment.noOfRatedUp}"
                            data-rateddown="${comment.noOfRatedDown}"
                            data-rating="${comment.rating}"
                            href="#">
                        <span class="glyphicon glyphicon-menu-up"></span></button>

                </div>

            </div>
        </div>

        <p>
            ${comment.text}
        </p>

    </div>
</div>
