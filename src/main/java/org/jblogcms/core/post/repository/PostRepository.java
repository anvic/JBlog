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

package org.jblogcms.core.post.repository;

import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.common.repository.ItemRepository;
import org.jblogcms.core.post.model.Post;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Provides the persistence service for accessing, adding, changing, deleting {@link Post}.
 *
 * @author Victor Andreenko
 */
@Repository
public interface PostRepository
        extends ItemRepository<Post> {

    @Override
    @CacheEvict(value = "posts", allEntries = true)
    <S extends Post> S save(S entity);

    @Override
    @CacheEvict(value = "posts", allEntries = true)
    void delete(Long aLong);

    @Override
    @CacheEvict(value = "posts", allEntries = true)
    void delete(Post entity);

    /**
     * Returns the post with the primary key, or {@code null} if the post with the primary key wasn't found
     *
     * @param postId the primary key of the post
     * @return the post or {@code null} if the post with the primary key wasn't found
     */
    @Cacheable(value = "posts")
    @Query("select post " +
            "from Post post " +
            "left join fetch post.blogs " +
            "left join fetch  post.account " +
            "where post.id = :postId")
    Post findPostById(@Param("postId") long postId);

    /**
     * Returns the post with the primary key, or {@code null} if the post with the primary key wasn't found
     *
     * @param postId the primary key of the post
     * @return the post or {@code null} if the post with the primary key wasn't found
     */
    @Query("select post " +
            "from Post post " +
            "left join fetch post.blogs " +
            "left join fetch  post.account " +
            "where post.id = :postId")
    Post findPostByIdForEdit(@Param("postId") long postId);

    /**
     * Returns a {@link Page} of all the posts
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable {@code Pageable} object
     * @return a page of all the posts of the blog
     */
    @Cacheable(value = "posts")
    @Query(value = "select post " +
            "from Post post " +
            "left join fetch post.blogs blogs " +
            "left join fetch post.account account",
            countQuery = "select count(post) " +
                    "from Post post ")
    Page<Post> findPosts(Pageable pageable);

    /**
     * Returns a {@link Page} of all the posts of the {@link Blog}
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param blogId   the primary key of the blog
     * @param pageable {@code Pageable} object
     * @return a page of all the posts of the blog
     */
    @Query("select post " +
            "from Post post " +
            "inner join post.blogs blogs " +
            "where blogs.id = :blogId")
    Page<Post> findBlogPosts(@Param("blogId") long blogId, Pageable pageable);

    /**
     * Returns all the posts with the array of the primary keys of the posts
     *
     * @param postIds the array of primary key of the posts     *
     * @return all the posts with the array of the primary keys of the posts
     */
    @Cacheable(value = "posts")
    @Query(value = "select distinct post " +
            "from Post post " +
            "left join fetch post.blogs " +
            "left join fetch post.account " +
            "where post.id in :postIds",
            countQuery = "select count (distinct post) " +
                    "from Post post " +
                    "where post.id in :postIds")
    List<Post> findPostsByPostIds(@Param("postIds") List<Long> postIds);

    /**
     * Returns a {@link Page} of all the favorite posts of the account
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param accountId the primary key of the {@link Account}
     * @param pageable  {@code Pageable} object
     * @return a page of all the posts
     */
    @Cacheable(value = "posts")
    @Query(value = "select distinct post " +
            "from Post post " +
            "join fetch post.blogs blogs " +
            "join fetch post.account account " +
            "join post.postRelations postRelations " +
            "where postRelations.account.id = :accountId " +
            "order by blogs.id",
            countQuery = "select count (distinct post) " +
                    "from Post post " +
                    "join post.postRelations postRelations " +
                    "where postRelations.account.id = :accountId")
    Page<Post> findFavoritePosts(@Param("accountId") long accountId, Pageable pageable);

    /**
     * Returns a {@link Page} of all the posts created by {@link Account}
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param accountId the primary key of the {@link Account}
     * @param pageable  {@code Pageable} object
     * @return a page of all the posts
     */
    @Cacheable(value = "posts")
    @Query(value = "select distinct post " +
            "from Post post " +
            "left join fetch post.blogs blogs " +
            "left join fetch post.account " +
            "where post.account.id = :accountId ",
            countQuery = "select count (post) " +
                    "from Post post " +
                    "left join post.account " +
                    "where post.account.id = :accountId ")
    Page<Post> findPostsByAccountId(@Param("accountId") long accountId, Pageable pageable);

    /**
     * Returns a {@link Page} of all the posts related to the {@link Blog}s
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param blogIds  the array of the primary keys of the {@link Blog}s
     * @param pageable {@code Pageable} object
     * @return a page of all the posts
     */
    @Cacheable(value = "posts")
    @Query(value = "select distinct post " +
            "from Post post " +
            "left join fetch post.blogs blogs " +
            "left join fetch post.account account " +
            "where blogs.id in :blogIds",
            countQuery = "select count(distinct post) " +
                    "from Post post " +
                    "left join post.blogs blogs " +
                    "where blogs.id in :blogIds")
    Page<Post> findPostsByBlogIds(@Param("blogIds") List<Long> blogIds, Pageable pageable);

}
