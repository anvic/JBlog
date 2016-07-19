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

package org.jblogcms.core.blog.service;

import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.blog.exception.DuplicateBlogNameException;
import org.jblogcms.core.blog.exception.DuplicateBlogUrlNameException;
import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.common.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Provides the service for accessing, adding, changing, deleting {@link Blog}
 */
public interface BlogService
        extends ItemService<Blog> {

    void delete(Blog blog);

    /**
     * Returns a {@code Page} of all the blogs meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable         the {@code Pageable} object
     * @param currentAccountId the primary key of the current {@link Account}
     * @return a page of the favorite blogs
     */
    Page<Blog> findBlogs(Pageable pageable, Long currentAccountId);

    /**
     * Returns a {@code Page} of all the favorite blogs meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable         the {@code Pageable} object
     * @param currentAccountId the primary key of the current {@link Account}
     * @return a page of the favorite blogs
     */
    Page<Blog> findFavoriteBlogs(Long accountId, Pageable pageable, Long currentAccountId);

    /**
     * Returns the blog with the primary key
     *
     * @param blogId           the primary key of the blog
     * @param currentAccountId the primary key of the account
     * @return the blog with the primary key
     */
    Blog findBlogById(Long blogId, Long currentAccountId);

    /**
     * Returns the blog with the primary key
     *
     * @param blogId the primary key of the blog
     * @return the blog with the primary key
     */
    Blog findBlogByIdForEdit(Long blogId);

    /**
     * Returns all the blogs.
     *
     * @param currentAccountId the primary key of the current {@link Account}
     * @return all the blogs
     */
    List<Blog> findBlogList(Long currentAccountId);

    /**
     * Creates the blog entity with the parameters.
     *
     * @param blog             the blog
     * @param currentAccountId the primary key of the account which is creator of the blog
     * @return the saved blog
     * @throws DuplicateBlogNameException    if the blog with the name already existed
     * @throws DuplicateBlogUrlNameException if the blog with the url name already existed
     */
    Blog addBlog(Blog blog, Long currentAccountId) throws DuplicateBlogNameException, DuplicateBlogUrlNameException;

    /**
     * Changes the blog
     *
     * @param blog the blog with changed properties
     * @return the saved blog
     * @throws DuplicateBlogNameException    if the blog with the name already existed
     * @throws DuplicateBlogUrlNameException if the blog with the url name already existed
     */
    Blog editBlog(Blog blog) throws DuplicateBlogNameException, DuplicateBlogUrlNameException;

}
