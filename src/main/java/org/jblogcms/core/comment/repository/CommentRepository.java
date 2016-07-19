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

package org.jblogcms.core.comment.repository;

import org.jblogcms.core.comment.model.Comment;
import org.jblogcms.core.common.repository.ItemRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Provides the persistence service for accessing, adding, changing, deleting {@link Comment}.
 *
 * @author Victor Andreenko
 */
@Repository
public interface CommentRepository extends ItemRepository<Comment> {

    @Override
    @CacheEvict(value = "comments", allEntries = true)
    <S extends Comment> S save(S entity);

    @Override
    @CacheEvict(value = "comments", allEntries = true)
    void delete(Long aLong);

    @Override
    @CacheEvict(value = "comments", allEntries = true)
    void delete(Comment entity);

    /**
     * Returns the comment with the primary key, or {@code null} if the comment with the primary key wasn't found
     *
     * @param commentId the primary key of the comment
     * @return the comment or {@code null} if the comment with the primary key wasn't found
     */
    @Query(value = "select comment " +
            "from Comment comment " +
            "join fetch comment.account account " +
            "join fetch comment.post post " +
            "where comment.id = :commentId ")
    Comment findCommentById(@Param("commentId") long commentId);

    /**
     * Returns the comment with the primary key, or {@code null} if the comment with the primary key wasn't found
     *
     * @param commentId the primary key of the comment
     * @return the comment or {@code null} if the comment with the primary key wasn't found
     */
    @Query(value = "select comment " +
            "from Comment comment " +
            "join fetch comment.account account " +
            "join fetch comment.post post " +
            "where comment.id = :commentId ")
    Comment findCommentByIdForEdit(@Param("commentId") long commentId);

    /**
     * Returns all the  comments of the {@link org.jblogcms.core.post.model.Post}.
     *
     * @param postId the primary key of the post
     * @return all the posts comments
     */
//    @Cacheable(value = "comments")
    @Query(value = "select comment " +
            "from Comment comment " +
            "inner join comment.post post " +
            "join fetch comment.account account " +
            "where post.id = :postId " +
            "order by comment.id",
            countQuery = "select count (comment) " +
                    "from Comment comment " +
                    "inner join comment.post post " +
                    "where post.id = :postId")
    List<Comment> findCommentsByPostId(@Param("postId") long postId);

    /**
     * Returns a {@link Page} of all the comments of the {@link org.jblogcms.core.account.model.Account}
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param accountId the primary key of the account
     * @param pageable  {@code Pageable} object
     * @return a page of all the comments
     */
    @Cacheable(value = "comments")
    @Query(value = "select comment " +
            "from Comment comment " +
            "join fetch comment.post post " +
            "join fetch comment.account account " +
            "where account.id = :accountId",
            countQuery = "select count (comment) " +
                    "from Comment comment " +
                    "inner join comment.account account " +
                    "where account.id = :accountId")
    Page<Comment> findCommentsByAccountId(@Param("accountId") long accountId, Pageable pageable);

    /**
     * Returns a {@link Page} of all the favorite comments of the {@link org.jblogcms.core.account.model.Account}
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param accountId the primary key of the account
     * @param pageable  {@code Pageable} object
     * @return a page of all the comments
     */
    @Cacheable(value = "comments")
    @Query(value = "select comment " +
            "from Comment comment " +
            "join fetch comment.post post " +
            "join fetch comment.account account " +
            "join comment.commentRelations commentRelations " +
            "where commentRelations.account.id = :accountId ",
            countQuery = "select count (comment) " +
                    "from Comment comment " +
                    "join comment.commentRelations commentRelations " +
                    "where commentRelations.account.id = :accountId ")
    Page<Comment> findFavoriteComments(@Param("accountId") long accountId, Pageable pageable);

}
