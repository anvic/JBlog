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
import org.jblogcms.core.blog.repository.BlogRepository;
import org.jblogcms.core.blog.service.BlogRelationService;
import org.jblogcms.core.post.model.Post;
import org.jblogcms.core.post.repository.PostRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.*;

import java.lang.reflect.Array;
import java.util.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Victor Andreenko
 */
@RunWith(MockitoJUnitRunner.class)
public class PostServiceImplTest {

    private static final Long ACCOUNT_ID = 1L;
    private static final Long CURRENT_ACCOUNT_ID = 2L;

    private static final Long BLOG_ID = 1L;

    private static final Long EXISTING_POST_ID = 111L;
    private static final String EXISTING_POST_TITLE = "existing post text";

    private static final Long NEW_POST_ID = 112L;
    private static final String NEW_POST_TITLE = "new post text";

    private static final Long POST_1_ID = 1L;
    private static final String POST_1_TITLE = "Post Text 1";

    private static final Long POST_2_ID = 2L;
    private static final String POST_2_TITLE = "Post Text 2";

    private static final Long POST_3_ID = 3L;
    private static final String POST_3_TITLE = "Post Text 3";

    private static final int POSTS_SIZE = 3;

    private static final int PAGEABLE_PAGE = 0;
    private static final int PAGEABLE_SIZE = 5;
    private static final String PAGEABLE_SORT = "id";


    private Post post1 = new Post();
    private Post post2 = new Post();
    private Post post3 = new Post();
    private Post existingPost = new Post();

    private List<Post> postList = Arrays.asList(post1, post2, post3);
    private Set<Blog> blogs = new HashSet<>();
    private Pageable pageable;
    private Page<Post> posts;

    @Mock
    private BlogRelationService blogRelationServiceMock;
    @Mock
    private BlogRepository blogRepositoryMock;
    @Mock
    private PostRepository postRepositoryMock;
    @Mock
    private PostToolService postToolServiceMock;
    @Mock
    private AccountRepository accountRepositoryMock;

    @InjectMocks
    PostServiceImpl postService = new PostServiceImpl();

    @Before
    public void setUp() throws Exception {
        post1.setId(POST_1_ID);
        post1.setTitle(POST_1_TITLE);

        post2.setId(POST_2_ID);
        post2.setTitle(POST_2_TITLE);

        post3.setId(POST_3_ID);
        post3.setTitle(POST_3_TITLE);

        existingPost.setId(EXISTING_POST_ID);
        existingPost.setTitle(EXISTING_POST_TITLE);

        Sort sort = new Sort(PAGEABLE_SORT);
        pageable = new PageRequest(PAGEABLE_PAGE, PAGEABLE_SIZE, sort);
        posts = new PageImpl(postList, pageable, POSTS_SIZE);
    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void findLastPosts() throws Exception {

    }

    @Test
    public void findBlogPosts() throws Exception {
        List<Long> postIds = Arrays.asList(POST_1_ID, POST_2_ID, POST_3_ID);

        when(postRepositoryMock.findBlogPosts(BLOG_ID, pageable)).thenReturn(posts);
        when(postRepositoryMock.findPostsByPostIds(postIds)).thenReturn(postList);
        when(postToolServiceMock.addItemRelationsToItemPage(posts, CURRENT_ACCOUNT_ID)).thenReturn(posts);
        when(postToolServiceMock.addItemRatesToItemPage(posts, CURRENT_ACCOUNT_ID)).thenReturn(posts);

        Page<Post> testPosts = postService.findBlogPosts(BLOG_ID, pageable, CURRENT_ACCOUNT_ID);

        verify(postRepositoryMock, times(1)).findBlogPosts(BLOG_ID, pageable);
        verify(postRepositoryMock, times(1)).findPostsByPostIds(postIds);
        verify(postToolServiceMock, times(1)).addItemRelationsToItemPage(posts, CURRENT_ACCOUNT_ID);
        verify(postToolServiceMock, times(1)).addItemRatesToItemPage(posts, CURRENT_ACCOUNT_ID);

        Assert.assertEquals(POSTS_SIZE, testPosts.getTotalElements());

        Assert.assertEquals(POST_1_ID, testPosts.getContent().get(0).getId());
        Assert.assertEquals(POST_1_TITLE, testPosts.getContent().get(0).getTitle());

        Assert.assertEquals(POST_2_ID, testPosts.getContent().get(1).getId());
        Assert.assertEquals(POST_2_TITLE, testPosts.getContent().get(1).getTitle());

        Assert.assertEquals(POST_3_ID, testPosts.getContent().get(2).getId());
        Assert.assertEquals(POST_3_TITLE, testPosts.getContent().get(2).getTitle());
    }

    @Test
    public void findPosts() throws Exception {
        when(postRepositoryMock.findPosts(pageable)).thenReturn(posts);
        when(postToolServiceMock.addItemRelationsToItemPage(posts, CURRENT_ACCOUNT_ID)).thenReturn(posts);
        when(postToolServiceMock.addItemRatesToItemPage(posts, CURRENT_ACCOUNT_ID)).thenReturn(posts);

        Page<Post> testPosts = postService.findPosts(pageable, CURRENT_ACCOUNT_ID);

        verify(postRepositoryMock, times(1)).findPosts(pageable);
        verify(postToolServiceMock, times(1)).addItemRelationsToItemPage(posts, CURRENT_ACCOUNT_ID);
        verify(postToolServiceMock, times(1)).addItemRatesToItemPage(posts, CURRENT_ACCOUNT_ID);

        Assert.assertEquals(POSTS_SIZE, testPosts.getTotalElements());

        Assert.assertEquals(POST_1_ID, testPosts.getContent().get(0).getId());
        Assert.assertEquals(POST_1_TITLE, testPosts.getContent().get(0).getTitle());

        Assert.assertEquals(POST_2_ID, testPosts.getContent().get(1).getId());
        Assert.assertEquals(POST_2_TITLE, testPosts.getContent().get(1).getTitle());

        Assert.assertEquals(POST_3_ID, testPosts.getContent().get(2).getId());
        Assert.assertEquals(POST_3_TITLE, testPosts.getContent().get(2).getTitle());
    }

    @Test
    public void findFavoritePosts() throws Exception {
        when(postRepositoryMock.findFavoritePosts(ACCOUNT_ID, pageable)).thenReturn(posts);
        when(postToolServiceMock.addItemRelationsToItemPage(posts, CURRENT_ACCOUNT_ID)).thenReturn(posts);
        when(postToolServiceMock.addItemRatesToItemPage(posts, CURRENT_ACCOUNT_ID)).thenReturn(posts);

        Page<Post> testPosts = postService.findFavoritePosts(ACCOUNT_ID, pageable, CURRENT_ACCOUNT_ID);

        verify(postRepositoryMock, times(1)).findFavoritePosts(ACCOUNT_ID, pageable);
        verify(postToolServiceMock, times(1)).addItemRelationsToItemPage(posts, CURRENT_ACCOUNT_ID);
        verify(postToolServiceMock, times(1)).addItemRatesToItemPage(posts, CURRENT_ACCOUNT_ID);

        Assert.assertEquals(POSTS_SIZE, testPosts.getTotalElements());

        Assert.assertEquals(POST_1_ID, testPosts.getContent().get(0).getId());
        Assert.assertEquals(POST_1_TITLE, testPosts.getContent().get(0).getTitle());

        Assert.assertEquals(POST_2_ID, testPosts.getContent().get(1).getId());
        Assert.assertEquals(POST_2_TITLE, testPosts.getContent().get(1).getTitle());

        Assert.assertEquals(POST_3_ID, testPosts.getContent().get(2).getId());
        Assert.assertEquals(POST_3_TITLE, testPosts.getContent().get(2).getTitle());
    }

    @Test
    public void findAccountPosts() throws Exception {
        when(postRepositoryMock.findPostsByAccountId(ACCOUNT_ID, pageable)).thenReturn(posts);
        when(postToolServiceMock.addItemRelationsToItemPage(posts, CURRENT_ACCOUNT_ID)).thenReturn(posts);
        when(postToolServiceMock.addItemRatesToItemPage(posts, CURRENT_ACCOUNT_ID)).thenReturn(posts);

        Page<Post> testPosts = postService.findAccountPosts(ACCOUNT_ID, pageable, CURRENT_ACCOUNT_ID);

        verify(postRepositoryMock, times(1)).findPostsByAccountId(ACCOUNT_ID, pageable);
        verify(postToolServiceMock, times(1)).addItemRelationsToItemPage(posts, CURRENT_ACCOUNT_ID);
        verify(postToolServiceMock, times(1)).addItemRatesToItemPage(posts, CURRENT_ACCOUNT_ID);

        Assert.assertEquals(POSTS_SIZE, testPosts.getTotalElements());

        Assert.assertEquals(POST_1_ID, testPosts.getContent().get(0).getId());
        Assert.assertEquals(POST_1_TITLE, testPosts.getContent().get(0).getTitle());

        Assert.assertEquals(POST_2_ID, testPosts.getContent().get(1).getId());
        Assert.assertEquals(POST_2_TITLE, testPosts.getContent().get(1).getTitle());

        Assert.assertEquals(POST_3_ID, testPosts.getContent().get(2).getId());
        Assert.assertEquals(POST_3_TITLE, testPosts.getContent().get(2).getTitle());
    }

    @Test
    public void findFeedPosts() throws Exception {

    }

    @Test
    public void findOnePost() throws Exception {
        when(postRepositoryMock.findPostById(EXISTING_POST_ID)).thenReturn(existingPost);
        when(postToolServiceMock.addItemRelationToItem(existingPost, CURRENT_ACCOUNT_ID)).thenReturn(existingPost);
        when(postToolServiceMock.addItemRateToItem(existingPost, CURRENT_ACCOUNT_ID)).thenReturn(existingPost);

        Post testPost = postService.findPostById(EXISTING_POST_ID, CURRENT_ACCOUNT_ID);

        verify(postRepositoryMock, times(1)).findPostById(EXISTING_POST_ID);
        verify(postToolServiceMock, times(1)).addItemRelationToItem(existingPost, CURRENT_ACCOUNT_ID);
        verify(postToolServiceMock, times(1)).addItemRateToItem(existingPost, CURRENT_ACCOUNT_ID);

        Assert.assertNotNull(testPost);

        Assert.assertEquals(EXISTING_POST_ID, testPost.getId());
        Assert.assertEquals(EXISTING_POST_TITLE, testPost.getTitle());
    }

    @Test
    public void addPost() throws Exception {
        Account currentAccount = new Account();
        currentAccount.setId(CURRENT_ACCOUNT_ID);
        currentAccount.setNoOfPosts(0);

        Post newPost = new Post();
        newPost.setTitle(NEW_POST_TITLE);
        newPost.setAccount(currentAccount);
        newPost.setBlogs(blogs);

        when(accountRepositoryMock.findOne(CURRENT_ACCOUNT_ID)).thenReturn(currentAccount);
        when(accountRepositoryMock.save(currentAccount)).thenReturn(currentAccount);
        when(postRepositoryMock.save(newPost)).thenReturn(newPost);

        Post savedNewPost = postService.addPost(newPost, CURRENT_ACCOUNT_ID);

        verify(accountRepositoryMock, times(1)).findOne(CURRENT_ACCOUNT_ID);
        verify(postRepositoryMock, times(1)).save(newPost);
        verify(accountRepositoryMock, times(1)).save(currentAccount);

        Assert.assertNotNull(savedNewPost);
        Assert.assertEquals(NEW_POST_TITLE, savedNewPost.getTitle());
        Assert.assertEquals(CURRENT_ACCOUNT_ID, savedNewPost.getAccount().getId());
    }

}