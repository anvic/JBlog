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

import org.jblogcms.core.account.repository.AccountRepository;
import org.jblogcms.core.blog.exception.DuplicateBlogNameException;
import org.jblogcms.core.blog.exception.DuplicateBlogUrlNameException;
import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.blog.repository.BlogRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * @author Victor Andreenko
 */
@RunWith(MockitoJUnitRunner.class)
public class BlogServiceImplTest {

    private static final Long CURRENT_ACCOUNT_ID = 1L;
    private static final Long ACCOUNT_ID = 2L;

    private static final Long BLOG_1_ID = 1L;
    private static final String BLOG_1_NAME = "blog1";
    private static final String BLOG_1_URL_NAME = "blogUrl1";

    private static final Long BLOG_2_ID = 2L;
    private static final String BLOG_2_NAME = "blog1";
    private static final String BLOG_2_URL_NAME = "blogUrl1";

    private static final Long BLOG_3_ID = 3L;
    private static final String BLOG_3_NAME = "blog1";
    private static final String BLOG_3_URL_NAME = "blogUrl1";

    private static final int BLOGS_SIZE = 3;

    private static final Long EXISTING_BLOG_ID = 111L;
    private static final String EXISTING_BLOG_NAME = "Existing blog name";
    private static final String EXISTING_BLOG_URL_NAME = "Existing blog url name";

    private static final Long NOT_EXISTING_BLOG_ID = 114L;

    private static final String BLOG_DUPLICATE_NAME = "blog1";
    private static final String BLOG_DUPLICATE_URL_NAME = "blogUrl1";
    private static final String BLOG_NOT_DUPLICATE_NAME = "Blog Not Duplicate Name";
    private static final String BLOG_NOT_DUPLICATE_URL_NAME = "blogNotDuplicateUrlName";

    private static final int PAGEABLE_PAGE = 0;
    private static final int PAGEABLE_SIZE = 5;
    private static final String PAGEABLE_SORT = "id";


    private Blog blog1 = new Blog();
    private Blog blog2 = new Blog();
    private Blog blog3 = new Blog();
    private Blog existingBlog = new Blog();

    private List<Blog> blogList = Arrays.asList(blog1, blog2, blog3);

    private Pageable pageable;
    private Page<Blog> blogs;

    @Mock
    private BlogRepository blogRepositoryMock;
    @Mock
    private BlogToolService blogToolServiceMock;
    @Mock
    private AccountRepository accountRepositoryMock;

    @InjectMocks
    BlogServiceImpl blogService = new BlogServiceImpl();

    @Before
    public void setUp() throws Exception {
        blog1.setId(BLOG_1_ID);
        blog1.setName(BLOG_1_NAME);
        blog1.setUrlName(BLOG_1_URL_NAME);

        blog2.setId(BLOG_2_ID);
        blog2.setName(BLOG_2_NAME);
        blog2.setUrlName(BLOG_2_URL_NAME);

        blog3.setId(BLOG_3_ID);
        blog3.setName(BLOG_3_NAME);
        blog3.setUrlName(BLOG_3_URL_NAME);

        existingBlog.setId(EXISTING_BLOG_ID);
        existingBlog.setName(EXISTING_BLOG_NAME);
        existingBlog.setUrlName(EXISTING_BLOG_URL_NAME);

        Sort sort = new Sort(PAGEABLE_SORT);
        pageable = new PageRequest(PAGEABLE_PAGE, PAGEABLE_SIZE, sort);
        blogs = new PageImpl(blogList, pageable, BLOGS_SIZE);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFindBlogs() throws Exception {

        when(blogRepositoryMock.findBlogs(pageable)).thenReturn(blogs);
        when(blogToolServiceMock.addItemRelationsToItemPage(blogs, CURRENT_ACCOUNT_ID)).thenReturn(blogs);

        Page<Blog> testBlogs = blogService.findBlogs(pageable, CURRENT_ACCOUNT_ID);

        verify(blogRepositoryMock, times(1)).findBlogs(pageable);
        verify(blogToolServiceMock, times(1)).addItemRelationsToItemPage(blogs, CURRENT_ACCOUNT_ID);

        Assert.assertEquals(BLOGS_SIZE, testBlogs.getTotalElements());

        Assert.assertEquals(BLOG_1_ID, testBlogs.getContent().get(0).getId());
        Assert.assertEquals(BLOG_1_NAME, testBlogs.getContent().get(0).getName());
        Assert.assertEquals(BLOG_1_URL_NAME, testBlogs.getContent().get(0).getUrlName());

        Assert.assertEquals(BLOG_2_ID, testBlogs.getContent().get(1).getId());
        Assert.assertEquals(BLOG_2_NAME, testBlogs.getContent().get(1).getName());
        Assert.assertEquals(BLOG_2_URL_NAME, testBlogs.getContent().get(1).getUrlName());

        Assert.assertEquals(BLOG_3_ID, testBlogs.getContent().get(2).getId());
        Assert.assertEquals(BLOG_3_NAME, testBlogs.getContent().get(2).getName());
        Assert.assertEquals(BLOG_3_URL_NAME, testBlogs.getContent().get(2).getUrlName());
    }

    @Test
    public void testFindBlogList() throws Exception {

        when(blogRepositoryMock.findAll()).thenReturn(blogList);
        when(blogToolServiceMock.addItemRelationsToItemList(blogList, CURRENT_ACCOUNT_ID)).thenReturn(blogList);

        List<Blog> testBlogs = blogService.findBlogList(CURRENT_ACCOUNT_ID);

        verify(blogRepositoryMock, times(1)).findAll();
        verify(blogToolServiceMock, times(1)).addItemRelationsToItemList(blogList, CURRENT_ACCOUNT_ID);

        Assert.assertEquals(BLOGS_SIZE, testBlogs.size());

        Assert.assertEquals(BLOG_1_ID, testBlogs.get(0).getId());
        Assert.assertEquals(BLOG_1_NAME, testBlogs.get(0).getName());
        Assert.assertEquals(BLOG_1_URL_NAME, testBlogs.get(0).getUrlName());

        Assert.assertEquals(BLOG_2_ID, testBlogs.get(1).getId());
        Assert.assertEquals(BLOG_2_NAME, testBlogs.get(1).getName());
        Assert.assertEquals(BLOG_2_URL_NAME, testBlogs.get(1).getUrlName());

        Assert.assertEquals(BLOG_3_ID, testBlogs.get(2).getId());
        Assert.assertEquals(BLOG_3_NAME, testBlogs.get(2).getName());
        Assert.assertEquals(BLOG_3_URL_NAME, testBlogs.get(2).getUrlName());
    }

    @Test
    public void testFindFavoriteBlogs() throws Exception {

        when(blogRepositoryMock.findFavoriteBlogs(ACCOUNT_ID, pageable)).thenReturn(blogs);
        when(blogToolServiceMock.addItemRelationsToItemPage(blogs, CURRENT_ACCOUNT_ID)).thenReturn(blogs);

        Page<Blog> testBlogs = blogService.findFavoriteBlogs(ACCOUNT_ID, pageable, CURRENT_ACCOUNT_ID);

        verify(blogRepositoryMock, times(1)).findFavoriteBlogs(ACCOUNT_ID, pageable);
        verify(blogToolServiceMock, times(1)).addItemRelationsToItemPage(blogs, CURRENT_ACCOUNT_ID);

        Assert.assertEquals(BLOGS_SIZE, testBlogs.getTotalElements());

        Assert.assertEquals(BLOG_1_ID, testBlogs.getContent().get(0).getId());
        Assert.assertEquals(BLOG_1_NAME, testBlogs.getContent().get(0).getName());
        Assert.assertEquals(BLOG_1_URL_NAME, testBlogs.getContent().get(0).getUrlName());

        Assert.assertEquals(BLOG_2_ID, testBlogs.getContent().get(1).getId());
        Assert.assertEquals(BLOG_2_NAME, testBlogs.getContent().get(1).getName());
        Assert.assertEquals(BLOG_2_URL_NAME, testBlogs.getContent().get(1).getUrlName());

        Assert.assertEquals(BLOG_3_ID, testBlogs.getContent().get(2).getId());
        Assert.assertEquals(BLOG_3_NAME, testBlogs.getContent().get(2).getName());
        Assert.assertEquals(BLOG_3_URL_NAME, testBlogs.getContent().get(2).getUrlName());
    }

    @Test
    public void testFindOneBlog_returnBlog() throws Exception {
        when(blogRepositoryMock.findBlogById(EXISTING_BLOG_ID)).thenReturn(existingBlog);
        when(blogToolServiceMock.addItemRelationToItem(existingBlog, CURRENT_ACCOUNT_ID)).thenReturn(existingBlog);

        Blog testBlog = blogService.findBlogById(EXISTING_BLOG_ID, CURRENT_ACCOUNT_ID);

        verify(blogRepositoryMock, times(1)).findBlogById(EXISTING_BLOG_ID);
        verify(blogToolServiceMock, times(1)).addItemRelationToItem(existingBlog, CURRENT_ACCOUNT_ID);

        Assert.assertEquals(EXISTING_BLOG_ID, testBlog.getId());
        Assert.assertEquals(EXISTING_BLOG_NAME, testBlog.getName());
        Assert.assertEquals(EXISTING_BLOG_URL_NAME, testBlog.getUrlName());
    }

    @Test
    public void testFindOneBlog_returnNull() throws Exception {
        when(blogRepositoryMock.findBlogById(NOT_EXISTING_BLOG_ID)).thenReturn(null);
        when(blogToolServiceMock.addItemRelationToItem(null, CURRENT_ACCOUNT_ID)).thenReturn(null);

        Blog testBlog = blogService.findBlogById(NOT_EXISTING_BLOG_ID, CURRENT_ACCOUNT_ID);

        verify(blogRepositoryMock, times(1)).findBlogById(NOT_EXISTING_BLOG_ID);
        verify(blogToolServiceMock, times(1)).addItemRelationToItem(null, CURRENT_ACCOUNT_ID);

        Assert.assertNull(testBlog);
    }


    @Test(expected = DuplicateBlogNameException.class)
    public void testAddBlog_DuplicateBlogName() throws Exception {
        Blog newBlog = new Blog();
        newBlog.setName(BLOG_DUPLICATE_NAME);
        newBlog.setUrlName(BLOG_NOT_DUPLICATE_URL_NAME);

        when(blogRepositoryMock.countBlogsByName(BLOG_DUPLICATE_NAME)).thenReturn(1);
        when(blogRepositoryMock.countBlogsByUrlName(BLOG_DUPLICATE_URL_NAME)).thenReturn(0);
        when(blogRepositoryMock.save(newBlog)).thenReturn(newBlog);

        blogService.addBlog(newBlog, CURRENT_ACCOUNT_ID);

    }

    @Test(expected = DuplicateBlogNameException.class)
    public void testAddBlog_DuplicateBlogName_DuplicateBlogUrlName() throws Exception {
        Blog newBlog = new Blog();
        newBlog.setName(BLOG_DUPLICATE_NAME);
        newBlog.setUrlName(BLOG_DUPLICATE_URL_NAME);

        when(blogRepositoryMock.countBlogsByName(BLOG_DUPLICATE_NAME)).thenReturn(1);
        when(blogRepositoryMock.countBlogsByUrlName(BLOG_DUPLICATE_NAME)).thenReturn(1);
        when(blogRepositoryMock.save(newBlog)).thenReturn(newBlog);

        blogService.addBlog(newBlog, CURRENT_ACCOUNT_ID);
    }

    @Test(expected = DuplicateBlogUrlNameException.class)
    public void testAddBlog_DuplicateBlogUrlName() throws Exception {

        Blog newBlog = new Blog();
        newBlog.setName(BLOG_NOT_DUPLICATE_NAME);
        newBlog.setUrlName(BLOG_DUPLICATE_URL_NAME);

        when(blogRepositoryMock.countBlogsByName(BLOG_NOT_DUPLICATE_NAME)).thenReturn(0);
        when(blogRepositoryMock.countBlogsByUrlName(BLOG_DUPLICATE_URL_NAME)).thenReturn(1);
        when(blogRepositoryMock.save(newBlog)).thenReturn(newBlog);

        blogService.addBlog(newBlog, CURRENT_ACCOUNT_ID);
    }

    @Test
    public void testAddBlog_NoDuplicates() throws Exception {
        Blog newBlog = new Blog();
        newBlog.setName(BLOG_NOT_DUPLICATE_NAME);
        newBlog.setUrlName(BLOG_NOT_DUPLICATE_URL_NAME);

        when(blogRepositoryMock.countBlogsByName(BLOG_NOT_DUPLICATE_NAME)).thenReturn(0);
        when(blogRepositoryMock.countBlogsByUrlName(BLOG_NOT_DUPLICATE_URL_NAME)).thenReturn(0);
        when(blogRepositoryMock.save(newBlog)).thenReturn(newBlog);

        Blog savedNewBlog = blogService.addBlog(newBlog, CURRENT_ACCOUNT_ID);

        verify(blogRepositoryMock, times(1)).countBlogsByName(BLOG_NOT_DUPLICATE_NAME);
        verify(blogRepositoryMock, times(1)).countBlogsByUrlName(BLOG_NOT_DUPLICATE_URL_NAME);
        verify(blogRepositoryMock, times(1)).save(newBlog);

        Assert.assertNotNull(savedNewBlog);
        Assert.assertEquals(BLOG_NOT_DUPLICATE_NAME, savedNewBlog.getName());
        Assert.assertEquals(BLOG_NOT_DUPLICATE_URL_NAME, savedNewBlog.getUrlName());
    }

    @Test(expected = DuplicateBlogNameException.class)
    public void testEditBlog_DuplicateBlogName() throws Exception {
        Blog editedExistingBlog = new Blog();
        editedExistingBlog.setId(EXISTING_BLOG_ID);
        editedExistingBlog.setName(BLOG_DUPLICATE_NAME);
        editedExistingBlog.setUrlName(EXISTING_BLOG_URL_NAME);

        when(blogRepositoryMock.findOne(EXISTING_BLOG_ID)).thenReturn(existingBlog);
        when(blogRepositoryMock.countBlogsByName(BLOG_DUPLICATE_NAME)).thenReturn(1);
        when(blogRepositoryMock.countBlogsByUrlName(EXISTING_BLOG_URL_NAME)).thenReturn(1);
        when(blogRepositoryMock.save(editedExistingBlog)).thenReturn(editedExistingBlog);

        blogService.editBlog(editedExistingBlog);
    }

    @Test(expected = DuplicateBlogUrlNameException.class)
    public void testEditBlog_DuplicateBlogUrlName() throws Exception {
        Blog editedExistingBlog = new Blog();
        editedExistingBlog.setId(EXISTING_BLOG_ID);
        editedExistingBlog.setName(BLOG_NOT_DUPLICATE_NAME);
        editedExistingBlog.setUrlName(BLOG_DUPLICATE_URL_NAME);

        when(blogRepositoryMock.findOne(EXISTING_BLOG_ID)).thenReturn(existingBlog);
        when(blogRepositoryMock.countBlogsByName(BLOG_NOT_DUPLICATE_NAME)).thenReturn(0);
        when(blogRepositoryMock.countBlogsByUrlName(BLOG_DUPLICATE_URL_NAME)).thenReturn(1);
        when(blogRepositoryMock.save(editedExistingBlog)).thenReturn(editedExistingBlog);

        blogService.editBlog(editedExistingBlog);
    }


    @Test
    public void testEditBlog_NoDuplicates() throws Exception {
        Blog editedExistingBlog = new Blog();
        editedExistingBlog.setId(EXISTING_BLOG_ID);
        editedExistingBlog.setName(BLOG_NOT_DUPLICATE_NAME);
        editedExistingBlog.setUrlName(BLOG_NOT_DUPLICATE_URL_NAME);

        when(blogRepositoryMock.countBlogsByName(BLOG_NOT_DUPLICATE_NAME)).thenReturn(0);
        when(blogRepositoryMock.countBlogsByUrlName(BLOG_DUPLICATE_URL_NAME)).thenReturn(0);
        when(blogRepositoryMock.save(editedExistingBlog)).thenReturn(editedExistingBlog);

        Blog savedEditedExistingBlog = blogService.editBlog(editedExistingBlog);

        verify(blogRepositoryMock, times(1)).countBlogsByName(BLOG_NOT_DUPLICATE_NAME);
        verify(blogRepositoryMock, times(1)).countBlogsByUrlName(BLOG_NOT_DUPLICATE_URL_NAME);
        verify(blogRepositoryMock, times(1)).save(editedExistingBlog);

        Assert.assertNotNull(savedEditedExistingBlog);
        Assert.assertEquals(BLOG_NOT_DUPLICATE_NAME, savedEditedExistingBlog.getName());
        Assert.assertEquals(BLOG_NOT_DUPLICATE_URL_NAME, savedEditedExistingBlog.getUrlName());
    }

    @Test
    public void testEditBlog_theSameNameAndUrlName() throws Exception {
        Blog editedExistingBlog = new Blog();
        editedExistingBlog.setId(EXISTING_BLOG_ID);
        editedExistingBlog.setName(EXISTING_BLOG_NAME);
        editedExistingBlog.setUrlName(EXISTING_BLOG_URL_NAME);

        when(blogRepositoryMock.findOne(EXISTING_BLOG_ID)).thenReturn(existingBlog);

        when(blogRepositoryMock.countBlogsByName(EXISTING_BLOG_NAME)).thenReturn(1);
        when(blogRepositoryMock.countBlogsByUrlName(EXISTING_BLOG_URL_NAME)).thenReturn(1);
        when(blogRepositoryMock.save(editedExistingBlog)).thenReturn(editedExistingBlog);

        Blog savedEditedExistingBlog = blogService.editBlog(editedExistingBlog);

        verify(blogRepositoryMock, times(1)).countBlogsByName(EXISTING_BLOG_NAME);
        verify(blogRepositoryMock, times(1)).countBlogsByUrlName(EXISTING_BLOG_URL_NAME);
        verify(blogRepositoryMock, times(1)).save(editedExistingBlog);

        Assert.assertNotNull(savedEditedExistingBlog);
        Assert.assertEquals(EXISTING_BLOG_NAME, savedEditedExistingBlog.getName());
        Assert.assertEquals(EXISTING_BLOG_URL_NAME, savedEditedExistingBlog.getUrlName());

    }
}