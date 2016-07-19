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
import org.jblogcms.core.account.repository.AccountRepository;
import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.blog.model.BlogRelation;
import org.jblogcms.core.blog.repository.BlogRepository;
import org.jblogcms.core.blog.service.BlogRelationService;
import org.jblogcms.core.common.service.ItemServiceImpl;
import org.jblogcms.core.post.model.Post;
import org.jblogcms.core.post.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Victor Andreenko
 */
@Service
public class PostServiceImpl
        extends ItemServiceImpl<Post>
        implements PostService {

    private static final Logger logger =
            LoggerFactory.getLogger(PostServiceImpl.class);

    private static final byte NUMBER_OF_POSTS_INCREMENT = 1;
    private static final byte NUMBER_OF_POSTS_DECREMENT = -1;

    private BlogRelationService blogRelationService;
    private BlogRepository blogRepository;
    private PostRepository postRepository;
    private PostToolService postToolService;
    private AccountRepository accountRepository;

    public PostServiceImpl() {
        super(Post.class);
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setBlogRelationService(BlogRelationService blogRelationService) {
        this.blogRelationService = blogRelationService;
    }

    @Autowired
    public void setBlogRepository(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setPostToolService(PostToolService postToolService) {
        this.postToolService = postToolService;
    }

    @Override
    @Transactional
    @PreAuthorize("#post.account.id == principal.id")
    public void delete(Post post) {
        Account account = accountRepository.findOne(post.getAccount().getId());
        account.setNoOfPosts(account.getNoOfPosts() + NUMBER_OF_POSTS_DECREMENT);
        accountRepository.save(account);

        if (post.getBlogs() != null && post.getBlogs().size() != 0) {
            blogRepository.updateNoOfPosts(getBlogsIds(post.getBlogs()), NUMBER_OF_POSTS_DECREMENT);
        }

        postRepository.delete(post);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> findLastPosts(Pageable pageable, Long currentAccountId) {
        Page<Post> posts = postRepository.findPosts(pageable);
        posts = postToolService.addItemRatesToItemPage(posts, currentAccountId);
        posts = postToolService.addItemRelationsToItemPage(posts, currentAccountId);

        return posts;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> findBlogPosts(Long blogId, Pageable pageable, Long currentAccountId) {
        Page<Post> posts = postRepository.findBlogPosts(blogId, pageable);

        if (!posts.getContent().isEmpty()) {
            List<Long> postIds = new ArrayList<>(posts.getContent().size());
            for (Post post : posts) {
                postIds.add(post.getId());
            }
            List<Post> postList = postRepository.findPostsByPostIds(postIds);
            posts = new PageImpl<Post>(postList, pageable, postList.size());

            posts = postToolService.addItemRatesToItemPage(posts, currentAccountId);
            posts = postToolService.addItemRelationsToItemPage(posts, currentAccountId);
        }
        return posts;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> findPosts(Pageable pageable, Long currentAccountId) {
        Page<Post> posts = postRepository.findPosts(pageable);
        posts = postToolService.addItemRatesToItemPage(posts, currentAccountId);
        posts = postToolService.addItemRelationsToItemPage(posts, currentAccountId);

        return posts;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> findFavoritePosts(Long accountId, Pageable pageable, Long currentAccountId) {
        Page<Post> posts = postRepository.findFavoritePosts(accountId, pageable);
        posts = postToolService.addItemRatesToItemPage(posts, currentAccountId);
        posts = postToolService.addItemRelationsToItemPage(posts, currentAccountId);

        return posts;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> findAccountPosts(long accountId, Pageable pageable, Long currentAccountId) {
        Page<Post> posts = postRepository.findPostsByAccountId(accountId, pageable);
        posts = postToolService.addItemRatesToItemPage(posts, currentAccountId);
        posts = postToolService.addItemRelationsToItemPage(posts, currentAccountId);

        return posts;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> findFeedPosts(Pageable pageable, Long currentAccountId) {

        List<BlogRelation> blogRelations =
                blogRelationService.getBlogRelations(currentAccountId);

        Page<Post> posts = new PageImpl<Post>(new ArrayList<Post>());

        if (!blogRelations.isEmpty()) {
            List<Long> blogIds = new ArrayList<>(blogRelations.size());
            for (BlogRelation blogRelation : blogRelations) {
                blogIds.add(blogRelation.getItem().getId());
            }
            posts = postRepository.findPostsByBlogIds(blogIds, pageable);

            posts = postToolService.addItemRatesToItemPage(posts, currentAccountId);
            posts = postToolService.addItemRelationsToItemPage(posts, currentAccountId);

        }
        return posts;
    }

    @Override
    @Transactional(readOnly = true)
    public Post findPostById(Long postId, Long currentAccountId) {
        Post post = postRepository.findPostById(postId);
        post = postToolService.addItemRelationToItem(post, currentAccountId);
        post = postToolService.addItemRateToItem(post, currentAccountId);

        return post;
    }

    @Override
    @Transactional(readOnly = true)
    @PostAuthorize("returnObject.account.id == principal.id")
    public Post findPostByIdForEdit(Long id) {
        return postRepository.findPostByIdForEdit(id);
    }

    @Override
    @Transactional
    public Post addPost(Post post, Long currentAccountId) {
        Account account = accountRepository.findOne(currentAccountId);
        post.setAccount(account);

        Post addedPost = postRepository.save(post);

        if (addedPost != null) {
            account.setNoOfPosts(account.getNoOfPosts() + NUMBER_OF_POSTS_INCREMENT);
            accountRepository.save(account);

            if (post.getBlogs() != null && post.getBlogs().size() != 0) {
                blogRepository.updateNoOfPosts(getBlogsIds(post.getBlogs()), NUMBER_OF_POSTS_INCREMENT);
            }
        }
        return addedPost;
    }

    @Override
    @Transactional
    @PreAuthorize("#post.account.id == principal.id")
    public Post editPost(Post post) {
        Post oldPost = postRepository.findPostById(post.getId());

        List<Long> oldPostBlogIds = getBlogsIds(oldPost.getBlogs());
        List<Long> oldPostBlogIdsCheckSimilar = getBlogsIds(oldPost.getBlogs());
        List<Long> blogIds = getBlogsIds(post.getBlogs());

        oldPostBlogIdsCheckSimilar.retainAll(blogIds);
        oldPostBlogIds.removeAll(oldPostBlogIdsCheckSimilar);
        blogIds.removeAll(oldPostBlogIdsCheckSimilar);

        Account account = accountRepository.getOne(post.getAccount().getId());
        post.setAccount(account);
        Post editedPost = postRepository.save(post);

        if (editedPost != null) {
            if (!oldPostBlogIds.isEmpty()) {
                blogRepository.updateNoOfPosts(oldPostBlogIds, NUMBER_OF_POSTS_DECREMENT);
            }
            blogRepository.flush();
            if (!blogIds.isEmpty()) {
                blogRepository.updateNoOfPosts(blogIds, NUMBER_OF_POSTS_INCREMENT);
            }
        }
        return editedPost;
    }

    private List<Long> getBlogsIds(Collection<Blog> blogs) {
        List<Long> blogIds = new ArrayList<>();
        for (Blog blog : blogs) {
            blogIds.add(blog.getId());
        }

        return blogIds;
    }

}
