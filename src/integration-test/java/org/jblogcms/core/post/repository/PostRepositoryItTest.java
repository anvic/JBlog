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


import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.config.PersistenceContext;
import org.jblogcms.core.post.model.Post;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @author Victor Andreenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceContext.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:blog.xml")
@Transactional
public class PostRepositoryItTest {

    private static final Long ACCOUNT_1_ID = 1L;
    private static final String ACCOUNT_1_NAME = "name1";
    private static final Long ACCOUNT_2_ID = 2L;
    private static final String ACCOUNT_2_NAME = "name2";

    private static final Long BLOG_1_ID = 1L;
    private static final String BLOG_1_NAME = "blog1";
    private static final String BLOG_1_URL_NAME = "blogUrl1";

    private static final Long BLOG_2_ID = 2L;
    private static final String BLOG_2_NAME = "blog2";
    private static final String BLOG_2_URL_NAME = "blogUrl2";

    private static final Long BLOG_3_ID = 3L;
    private static final String BLOG_3_NAME = "blog3";
    private static final String BLOG_3_URL_NAME = "blogUrl3";

    private static final Long NOT_EXISTING_POST_ID = 1545L;
    private static final String NOT_EXISTING_POST_TITLE = "not existing post title";
    private static final Long BLOG_WITH_NO_POSTS = 234234L;
    private static final Long ACCOUNT_WITH_NO_POSTS = 678434L;
    private static final Long ACCOUNT_WITH_NO_FAVORITE_POSTS = 678234L;

    private static final Long POST_1_ID = 1L;
    private static final String POST_1_TITLE = "Post Title 1";
    private static final Long POST_1_ACCOUNT_1_ID = ACCOUNT_1_ID;
    private static final String POST_1_ACCOUNT_1_NAME = ACCOUNT_1_NAME;
    private static final int POST_1_BLOGS_SIZE = 3;
    private static final int POST_1_NO_OF_COMMENTS = 0;
    private static final int POST_1_NO_OF_COMMENTS_INCREMENT = 1;

    private static final Long POST_2_ID = 2L;
    private static final String POST_2_TITLE = "Post Title 2";
    private static final Long POST_2_ACCOUNT_2_ID = ACCOUNT_2_ID;
    private static final String POST_2_ACCOUNT_2_NAME = ACCOUNT_2_NAME;
    private static final int POST_2_BLOGS_SIZE = 2;

    private static final Long POST_3_ID = 3L;
    private static final String POST_3_TITLE = "Post Title 3";
    private static final Long POST_3_ACCOUNT_1_ID = ACCOUNT_1_ID;
    private static final String POST_3_ACCOUNT_1_NAME = ACCOUNT_1_NAME;
    private static final int POST_3_BLOGS_SIZE = 1;

    private static final int PAGEABLE_PAGE = 0;
    private static final int PAGEABLE_SIZE = 5;
    private static final String PAGEABLE_SORT = "id";

    private Pageable pageable;

    private final Blog blog1 = new Blog();
    private final Blog blog2 = new Blog();
    private final Blog blog3 = new Blog();

    private final Set<Blog> post1Blogs = new HashSet<>(Arrays.asList(blog1, blog2, blog3));
    private final Set<Blog> post2Blogs = new HashSet<>(Arrays.asList(blog2, blog3));
    private final Set<Blog> post3Blogs = new HashSet<>(Arrays.asList(blog1));

    @Autowired
    private PostRepository postRepository;

    @Before
    public void setUp() throws Exception {
        Sort sort = new Sort(PAGEABLE_SORT);
        pageable = new PageRequest(PAGEABLE_PAGE, PAGEABLE_SIZE, sort);

        blog1.setId(BLOG_1_ID);
        blog1.setName(BLOG_1_NAME);
        blog1.setUrlName(BLOG_1_URL_NAME);

        blog2.setId(BLOG_2_ID);
        blog2.setName(BLOG_2_NAME);
        blog2.setUrlName(BLOG_2_URL_NAME);

        blog3.setId(BLOG_3_ID);
        blog3.setName(BLOG_3_NAME);
        blog3.setUrlName(BLOG_3_URL_NAME);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    @Transactional
    public void testFindPostById_returnNull() throws Exception {
        Post notExistingPost = postRepository.findPostById(NOT_EXISTING_POST_ID);

        Assert.assertEquals(null, notExistingPost);
    }

    @Test
    @Transactional
    public void testFindPostById_returnPost() throws Exception {
        Post existingPost = postRepository.findPostById(POST_1_ID);

        Assert.assertEquals(POST_1_ID, existingPost.getId());
        Assert.assertEquals(POST_1_TITLE, existingPost.getTitle());
        Assert.assertEquals(POST_1_ACCOUNT_1_ID, existingPost.getAccount().getId());
        Assert.assertEquals(POST_1_ACCOUNT_1_NAME, existingPost.getAccount().getName());

        Assert.assertEquals(POST_1_BLOGS_SIZE, existingPost.getBlogs().size());
        Assert.assertTrue(existingPost.getBlogs().containsAll(post1Blogs));
    }

    @Test
    @Transactional
    public void testFindBlogPosts_returnNoPosts() throws Exception {
        Page<Post> posts = postRepository.findBlogPosts(BLOG_WITH_NO_POSTS, pageable);

        Assert.assertEquals(0, posts.getTotalElements());

    }


    @Test
    @Transactional
    public void testFindBlogPosts_returnTwoPost() throws Exception {
        Page<Post> posts = postRepository.findBlogPosts(BLOG_2_ID, pageable);
        Assert.assertEquals(2, posts.getTotalElements());

        Assert.assertEquals(POST_1_ID, posts.getContent().get(0).getId());
        Assert.assertEquals(POST_1_TITLE, posts.getContent().get(0).getTitle());
        Assert.assertEquals(POST_1_ACCOUNT_1_ID, posts.getContent().get(0).getAccount().getId());
        Assert.assertEquals(POST_1_ACCOUNT_1_NAME, posts.getContent().get(0).getAccount().getName());

        Assert.assertEquals(POST_2_ID, posts.getContent().get(1).getId());
        Assert.assertEquals(POST_2_TITLE, posts.getContent().get(1).getTitle());
        Assert.assertEquals(POST_2_ACCOUNT_2_ID, posts.getContent().get(1).getAccount().getId());
        Assert.assertEquals(POST_2_ACCOUNT_2_NAME, posts.getContent().get(1).getAccount().getName());

    }

    @Test
    @Transactional
    public void testFindPosts_returnAllPosts() throws Exception {
        Page<Post> posts = postRepository.findPosts(pageable);

        Assert.assertEquals(3, posts.getTotalElements());

        Assert.assertEquals(POST_1_ID, posts.getContent().get(0).getId());
        Assert.assertEquals(POST_1_TITLE, posts.getContent().get(0).getTitle());
        Assert.assertEquals(POST_1_ACCOUNT_1_ID, posts.getContent().get(0).getAccount().getId());
        Assert.assertEquals(POST_1_ACCOUNT_1_NAME, posts.getContent().get(0).getAccount().getName());
        Assert.assertEquals(POST_1_BLOGS_SIZE, posts.getContent().get(0).getBlogs().size());
        Assert.assertTrue(posts.getContent().get(0).getBlogs().containsAll(post1Blogs));

        Assert.assertEquals(POST_2_ID, posts.getContent().get(1).getId());
        Assert.assertEquals(POST_2_TITLE, posts.getContent().get(1).getTitle());
        Assert.assertEquals(POST_2_ACCOUNT_2_ID, posts.getContent().get(1).getAccount().getId());
        Assert.assertEquals(POST_2_ACCOUNT_2_NAME, posts.getContent().get(1).getAccount().getName());
        Assert.assertEquals(POST_2_BLOGS_SIZE, posts.getContent().get(1).getBlogs().size());
        Assert.assertTrue(posts.getContent().get(1).getBlogs().containsAll(post2Blogs));

        Assert.assertEquals(POST_3_ID, posts.getContent().get(2).getId());
        Assert.assertEquals(POST_3_TITLE, posts.getContent().get(2).getTitle());
        Assert.assertEquals(POST_3_ACCOUNT_1_ID, posts.getContent().get(2).getAccount().getId());
        Assert.assertEquals(POST_3_ACCOUNT_1_NAME, posts.getContent().get(2).getAccount().getName());
        Assert.assertEquals(POST_3_BLOGS_SIZE, posts.getContent().get(2).getBlogs().size());
        Assert.assertTrue(posts.getContent().get(2).getBlogs().containsAll(post3Blogs));
    }

    @Test
    @Transactional
    public void testFindFavoritePosts_returnNoPost() throws Exception {
        Page<Post> posts = postRepository.findFavoritePosts(ACCOUNT_WITH_NO_FAVORITE_POSTS, pageable);

        Assert.assertEquals(0, posts.getTotalElements());
    }

    @Test
    @Transactional
    public void testFindFavoritePosts_returnOnePost() throws Exception {
        Page<Post> posts = postRepository.findFavoritePosts(ACCOUNT_2_ID, pageable);

        Assert.assertEquals(1, posts.getTotalElements());

        Assert.assertEquals(POST_2_ID, posts.getContent().get(0).getId());
        Assert.assertEquals(POST_2_TITLE, posts.getContent().get(0).getTitle());
        Assert.assertEquals(POST_2_ACCOUNT_2_ID, posts.getContent().get(0).getAccount().getId());
        Assert.assertEquals(POST_2_ACCOUNT_2_NAME, posts.getContent().get(0).getAccount().getName());
        Assert.assertEquals(POST_2_BLOGS_SIZE, posts.getContent().get(0).getBlogs().size());
        Assert.assertTrue(posts.getContent().get(0).getBlogs().containsAll(post2Blogs));

    }

    @Test
    @Transactional
    public void testFindFavoritePosts_returnTwoPosts() throws Exception {
        Page<Post> posts = postRepository.findFavoritePosts(ACCOUNT_1_ID, pageable);

        Assert.assertEquals(2, posts.getTotalElements());

        Assert.assertEquals(POST_1_ID, posts.getContent().get(0).getId());
        Assert.assertEquals(POST_1_TITLE, posts.getContent().get(0).getTitle());
        Assert.assertEquals(POST_1_ACCOUNT_1_ID, posts.getContent().get(0).getAccount().getId());
        Assert.assertEquals(POST_1_ACCOUNT_1_NAME, posts.getContent().get(0).getAccount().getName());
        Assert.assertEquals(POST_1_BLOGS_SIZE, posts.getContent().get(0).getBlogs().size());
        Assert.assertTrue(posts.getContent().get(0).getBlogs().containsAll(post1Blogs));

        Assert.assertEquals(POST_2_ID, posts.getContent().get(1).getId());
        Assert.assertEquals(POST_2_TITLE, posts.getContent().get(1).getTitle());
        Assert.assertEquals(POST_2_ACCOUNT_2_ID, posts.getContent().get(1).getAccount().getId());
        Assert.assertEquals(POST_2_ACCOUNT_2_NAME, posts.getContent().get(1).getAccount().getName());
        Assert.assertEquals(POST_2_BLOGS_SIZE, posts.getContent().get(1).getBlogs().size());
        Assert.assertTrue(posts.getContent().get(0).getBlogs().containsAll(post2Blogs));
    }

    @Test
    @Transactional
    public void testFindPostsByAccountId_returnNoPosts() throws Exception {
        Page<Post> posts = postRepository.findPostsByAccountId(ACCOUNT_WITH_NO_POSTS, pageable);

        Assert.assertEquals(0, posts.getTotalElements());
    }

    @Test
    @Transactional
    public void testFindPostsByAccountId_returnOnePost() throws Exception {
        Page<Post> posts = postRepository.findPostsByAccountId(ACCOUNT_2_ID, pageable);

        Assert.assertEquals(1, posts.getTotalElements());

        Assert.assertEquals(POST_2_ID, posts.getContent().get(0).getId());
        Assert.assertEquals(POST_2_TITLE, posts.getContent().get(0).getTitle());
        Assert.assertEquals(POST_2_ACCOUNT_2_ID, posts.getContent().get(0).getAccount().getId());
        Assert.assertEquals(POST_2_ACCOUNT_2_NAME, posts.getContent().get(0).getAccount().getName());
        Assert.assertEquals(POST_2_BLOGS_SIZE, posts.getContent().get(0).getBlogs().size());
        Assert.assertTrue(posts.getContent().get(0).getBlogs().containsAll(post2Blogs));
    }

    @Test
    @Transactional
    public void testFindPostsByAccountId_returnTwoPosts() throws Exception {
        Page<Post> posts = postRepository.findPostsByAccountId(ACCOUNT_1_ID, pageable);

        Assert.assertEquals(2, posts.getTotalElements());

        Assert.assertEquals(POST_1_ID, posts.getContent().get(0).getId());
        Assert.assertEquals(POST_1_TITLE, posts.getContent().get(0).getTitle());
        Assert.assertEquals(POST_1_ACCOUNT_1_ID, posts.getContent().get(0).getAccount().getId());
        Assert.assertEquals(POST_1_ACCOUNT_1_NAME, posts.getContent().get(0).getAccount().getName());
        Assert.assertEquals(POST_1_BLOGS_SIZE, posts.getContent().get(0).getBlogs().size());
        Assert.assertTrue(posts.getContent().get(0).getBlogs().containsAll(post1Blogs));

        Assert.assertEquals(POST_3_ID, posts.getContent().get(1).getId());
        Assert.assertEquals(POST_3_TITLE, posts.getContent().get(1).getTitle());
        Assert.assertEquals(POST_3_ACCOUNT_1_ID, posts.getContent().get(1).getAccount().getId());
        Assert.assertEquals(POST_3_ACCOUNT_1_NAME, posts.getContent().get(1).getAccount().getName());
        Assert.assertEquals(POST_3_BLOGS_SIZE, posts.getContent().get(1).getBlogs().size());
        Assert.assertTrue(posts.getContent().get(0).getBlogs().containsAll(post3Blogs));
    }


    @Test
    @Transactional
    public void testFindPostsByBlogIds_OneElementBlogIdArray() throws Exception {
        List<Long> blogIds = Arrays.asList(BLOG_3_ID);
        Page<Post> posts = postRepository.findPostsByBlogIds(blogIds, pageable);

        Assert.assertEquals(2, posts.getTotalElements());

        Assert.assertEquals(POST_1_ID, posts.getContent().get(0).getId());
        Assert.assertEquals(POST_1_TITLE, posts.getContent().get(0).getTitle());
        Assert.assertEquals(POST_1_ACCOUNT_1_ID, posts.getContent().get(0).getAccount().getId());
        Assert.assertEquals(POST_1_ACCOUNT_1_NAME, posts.getContent().get(0).getAccount().getName());

        Assert.assertEquals(POST_2_ID, posts.getContent().get(1).getId());
        Assert.assertEquals(POST_2_TITLE, posts.getContent().get(1).getTitle());
        Assert.assertEquals(POST_2_ACCOUNT_2_ID, posts.getContent().get(1).getAccount().getId());
        Assert.assertEquals(POST_2_ACCOUNT_2_NAME, posts.getContent().get(1).getAccount().getName());
    }

    @Test
    @Transactional
    public void testFindPostsByBlogIds_AFewElementsBlogIdArray() throws Exception {
        List<Long> blogIds = Arrays.asList(BLOG_1_ID, BLOG_2_ID);
        Page<Post> posts = postRepository.findPostsByBlogIds(blogIds, pageable);

        Assert.assertEquals(3, posts.getTotalElements());

        Assert.assertEquals(POST_1_ID, posts.getContent().get(0).getId());
        Assert.assertEquals(POST_1_TITLE, posts.getContent().get(0).getTitle());
        Assert.assertEquals(POST_1_ACCOUNT_1_ID, posts.getContent().get(0).getAccount().getId());
        Assert.assertEquals(POST_1_ACCOUNT_1_NAME, posts.getContent().get(0).getAccount().getName());

        Assert.assertEquals(POST_2_ID, posts.getContent().get(1).getId());
        Assert.assertEquals(POST_2_TITLE, posts.getContent().get(1).getTitle());
        Assert.assertEquals(POST_2_ACCOUNT_2_ID, posts.getContent().get(1).getAccount().getId());
        Assert.assertEquals(POST_2_ACCOUNT_2_NAME, posts.getContent().get(1).getAccount().getName());

        Assert.assertEquals(POST_3_ID, posts.getContent().get(2).getId());
        Assert.assertEquals(POST_3_TITLE, posts.getContent().get(2).getTitle());
        Assert.assertEquals(POST_3_ACCOUNT_1_ID, posts.getContent().get(2).getAccount().getId());
        Assert.assertEquals(POST_3_ACCOUNT_1_NAME, posts.getContent().get(2).getAccount().getName());
    }

    @Test
    @Transactional
    public void testFindPostsByPostIds_returnOnePost() throws Exception {
        List<Long> postIds = Arrays.asList(POST_1_ID);
        List<Post> posts = postRepository.findPostsByPostIds(postIds);

        Assert.assertEquals(1, posts.size());

        Assert.assertEquals(POST_1_ID, posts.get(0).getId());
        Assert.assertEquals(POST_1_TITLE, posts.get(0).getTitle());
        Assert.assertEquals(POST_1_ACCOUNT_1_ID, posts.get(0).getAccount().getId());
        Assert.assertEquals(POST_1_ACCOUNT_1_NAME, posts.get(0).getAccount().getName());
        Assert.assertEquals(POST_1_BLOGS_SIZE, posts.get(0).getBlogs().size());
        Assert.assertTrue(posts.get(0).getBlogs().containsAll(post1Blogs));
    }

    @Test
    @Transactional
    public void testFindPostsByPostIds_returnTwoPosts() throws Exception {
        List<Long> postIds = Arrays.asList(POST_1_ID, POST_2_ID);
        List<Post> posts = postRepository.findPostsByPostIds(postIds);

        Assert.assertEquals(2, posts.size());

        Assert.assertEquals(POST_1_ID, posts.get(0).getId());
        Assert.assertEquals(POST_1_TITLE, posts.get(0).getTitle());
        Assert.assertEquals(POST_1_ACCOUNT_1_ID, posts.get(0).getAccount().getId());
        Assert.assertEquals(POST_1_ACCOUNT_1_NAME, posts.get(0).getAccount().getName());
        Assert.assertEquals(POST_1_BLOGS_SIZE, posts.get(0).getBlogs().size());
        Assert.assertTrue(posts.get(0).getBlogs().containsAll(post1Blogs));

        Assert.assertEquals(POST_2_ID, posts.get(1).getId());
        Assert.assertEquals(POST_2_TITLE, posts.get(1).getTitle());
        Assert.assertEquals(POST_2_ACCOUNT_2_ID, posts.get(1).getAccount().getId());
        Assert.assertEquals(POST_2_ACCOUNT_2_NAME, posts.get(1).getAccount().getName());
        Assert.assertEquals(POST_2_BLOGS_SIZE, posts.get(1).getBlogs().size());
        Assert.assertTrue(posts.get(1).getBlogs().containsAll(post2Blogs));
    }

}