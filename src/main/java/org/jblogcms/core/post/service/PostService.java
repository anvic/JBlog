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

package org.jblogcms.core.post.service;

import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.common.service.ItemService;
import org.jblogcms.core.post.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostService
        extends ItemService<Post> {

    void delete(Post post);

    /**
     * Returns a {@link Page} of all the posts with date less than the date
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param date             the date
     * @param pageable         {@code Pageable} object
     * @param currentAccountId the primary key of the current {@link Account}
     * @return a page of all the posts
     */
    Page<Post> findLastPosts( Pageable pageable, Long currentAccountId);

    /**
     * Returns a {@link Page} of all the posts of the {@link Blog}
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param blogId           the primary key of the blog
     * @param pageable         {@code Pageable} object
     * @param currentAccountId the primary key of the current {@link Account}
     * @return a page of all the posts of the blog
     */
    Page<Post> findBlogPosts(Long blogId, Pageable pageable, Long currentAccountId);

    /**
     * Returns a {@link Page} of all the posts
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable         {@code Pageable} object
     * @param currentAccountId the primary key of the current {@link Account}
     * @return a page of all the posts
     */
    Page<Post> findPosts(Pageable pageable, Long currentAccountId);

    /**
     * Returns a {@link Page} of all the favorite posts of the account
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param accountId        the primary key of the {@link Account}
     * @param pageable         {@code Pageable} object
     * @param currentAccountId the primary key of the current {@link Account}
     * @return a page of all the posts
     */
    Page<Post> findFavoritePosts(Long accountId, Pageable pageable, Long currentAccountId);

    /**
     * Returns a {@link Page} of all the posts created by {@link Account}
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param accountId        the primary key of the {@link Account}
     * @param pageable         {@code Pageable} object
     * @param currentAccountId the primary key of the current {@link Account}
     * @return a page of all the posts
     */
    Page<Post> findAccountPosts(long accountId, Pageable pageable, Long currentAccountId);

    /**
     * Returns a {@link Page} of all the posts of the accounts feed
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable         {@code Pageable} object
     * @param currentAccountId the primary key of the current {@link Account}
     * @return a page of all the posts
     */

    Page<Post> findFeedPosts(Pageable pageable, Long currentAccountId);

    /**
     * Returns the post with the primary key.
     *
     * @param id               the primary key of the post
     * @param currentAccountId the primary key of the current {@link Account}
     * @return the post with the primary key
     */
    Post findPostById(Long id, Long currentAccountId);

    /**
     * Returns the post with the primary key.
     *
     * @param id               the primary key of the post
     * @return the post with the primary key
     */

    Post findPostByIdForEdit(Long id);

    /**
     * Creates the comment entity with the parameters.
     *
     * @param post             the post
     * @param currentAccountId the primary key of the {@link Account} which is creator of the comment
     * @return the saved post
     */
    Post addPost(Post post, Long currentAccountId);

    /**
     * Edits the comment entity with the parameters.
     *
     * @param post the post
     * @return the saved post
     */

    Post editPost(Post post);

}
