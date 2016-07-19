/*
 * Copyright 2016 Victor Andreenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jblogcms.core.comment.service;

import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.comment.model.Comment;
import org.jblogcms.core.common.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Provides the service for accessing, adding, changing, deleting {@link Comment}
 */
public interface CommentService
        extends ItemService<Comment> {

    void delete(Comment comment);

    /**
     * Returns all the comments of the post.
     *
     * @param commentId           the primary key of the comment
     * @return all the comments of the post
     */
    Comment findCommentByIdForEdit(Long commentId);

    /**
     * Returns all the comments of the post.
     *
     * @param postId           the primary key of the post
     * @param currentAccountId the primary key of the current {@link Account}
     * @return all the comments of the post
     */
    List<Comment> findPostComments(Long postId, Long currentAccountId);

    /**
     * Returns a {@link Page} of all the favorite comments
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param accountId        the primary key of the account
     * @param pageable         the {@code Pageable} object
     * @param currentAccountId the primary key of the current {@link Account}
     * @return a page of the favorite comments
     */
    Page<Comment> findFavoriteComments(Long accountId, Pageable pageable, Long currentAccountId);

    /**
     * Returns a {@link Page} of all the comments meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param accountId        the primary key of the account
     * @param pageable         the {@code Pageable} object
     * @param currentAccountId the primary key of the current {@link Account}
     * @return a page of the favorite comments
     */
    Page<Comment> findAccountComments(Long accountId, Pageable pageable, Long currentAccountId);

    /**
     * Creates the comment entity with the parameters.
     *
     * @param comment          the comment
     * @param currentAccountId the primary key of the account which is creator of the comment
     * @return the saved comment
     */
    Comment addComment(Comment comment, Long currentAccountId);

    /**
     * Changes the comment entity with the parameters.
     *
     * @param comment the comment
     * @return the saved comment
     */
    Comment editComment(Comment comment);
}
