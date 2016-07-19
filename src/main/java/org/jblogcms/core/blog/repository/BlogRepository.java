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

package org.jblogcms.core.blog.repository;

import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.common.repository.ItemRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Provides the persistence service for accessing, adding, changing, deleting {@link Blog}.
 *
 * @author Victor Andreenko
 */
@Repository
public interface BlogRepository
        extends ItemRepository<Blog> {

    @Override
    @CacheEvict(value = "blogs", allEntries = true)
    <S extends Blog> S save(S entity);

    @Override
    @CacheEvict(value = "blogs", allEntries = true)
    void delete(Long aLong);

    @Override
    @CacheEvict(value = "blogs", allEntries = true)
    void delete(Blog entity);

    /**
     * Returns the blog with the primary key, or {@code null} if the blog with the name wasn't found
     *
     * @param blogId the primary key
     * @return the blog or {@code null} if the blog with the name wasn't found
     */
    @Query(value = "select blog " +
            "from Blog blog " +
            "where blog.id = :blogId ")
    @Cacheable(value = "blogs")
    Blog findBlogById(@Param("blogId") long blogId);

    /**
     * Returns the blog with the primary key, or {@code null} if the blog with the name wasn't found
     *
     * @param blogId the primary key
     * @return the blog or {@code null} if the blog with the name wasn't found
     */
    @Query(value = "select blog " +
            "from Blog blog " +
            "where blog.id = :blogId ")
    Blog findBlogByIdForEdit(@Param("blogId") long blogId);

    /**
     * Returns the number of blogs with the name
     *
     * @param name the name of the blog
     * @return the blog or {@code null} if the blog with the name wasn't found
     */
    @Query(value = "select count(blog) " +
            "from Blog blog " +
            "where blog.name = :name ")
    int countBlogsByName(@Param("name") String name);

    /**
     * Returns the number of blogs with the url name
     *
     * @param urlName the url name
     * @return the blog or {@code null} if the blog with the url name wasn't found
     */
    @Query(value = "select count(blog) " +
            "from Blog blog " +
            "where blog.urlName = :urlName ")
    int countBlogsByUrlName(@Param("urlName") String urlName);

    /**
     * Returns a {@link Page} of all the blogs
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable {@code Pageable} object
     * @return a page of all the blogs
     */
    @Cacheable(value = "blogs")
    @Query(value = "select blog " +
            "from Blog blog ",
            countQuery = "select count(blog) " +
                    "from Blog blog ")
    Page<Blog> findBlogs(Pageable pageable);


    /**
     * Returns a {@link Page} of all the favorite blogs of the {@link org.jblogcms.core.account.model.Account}
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable {@code Pageable} object
     * @return a page of all the favorite blogs
     */
    @Cacheable(value = "blogs")
    @Query(value = "select blog " +
            "from Blog blog " +
            "join blog.blogRelations blogRelations " +
            "where blogRelations.account.id = :accountId " +
            "order by blogRelations.id",
            countQuery = "select count(blog) " +
                    "from Blog blog " +
                    "join blog.blogRelations blogRelations " +
                    "where blogRelations.account.id = :accountId " +
                    "order by blogRelations.id")
    Page<Blog> findFavoriteBlogs(@Param("accountId") long accountId, Pageable pageable);

    /**
     * Updates the number of posts of the blogs, returns number of affected rows
     *
     * @param blogIds      the array of blog primary keys
     * @param noOfPostsInc the increment value
     */
    @CacheEvict(value = "blogs", allEntries = true)
    @Modifying
    @Query("update Blog blog " +
            "set blog.noOfPosts = blog.noOfPosts + :noOfPostsInc " +
            "where blog.id in :blogIds")
    void updateNoOfPosts(@Param("blogIds") List<Long> blogIds,
                         @Param("noOfPostsInc") int noOfPostsInc);


}
