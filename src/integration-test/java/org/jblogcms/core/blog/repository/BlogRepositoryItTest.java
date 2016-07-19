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

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.config.PersistenceContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BlogRepositoryItTest {

    private static final Long ACCOUNT_1_ID = 1L;
    private static final Long ACCOUNT_2_ID = 2L;

    private static final Long NOT_EXISTING_BLOG_ID = 15655L;
    private static final String NOT_EXISTING_BLOG_NAME = "not existing blog name";
    private static final String NOT_EXISTING_BLOG_URL_NAME = "not existing blog url name";
    private static final Long ACCOUNT_WITH_NO_FAVORITE_BLOG = 674434L;

    private static final Long BLOG_1_ID = 1L;
    private static final String BLOG_1_NAME = "blog1";
    private static final String BLOG_1_URL_NAME = "blogUrl1";

    private static final Long BLOG_2_ID = 2L;
    private static final String BLOG_2_NAME = "blog2";
    private static final String BLOG_2_URL_NAME = "blogUrl2";

    private static final Long BLOG_3_ID = 3L;
    private static final String BLOG_3_NAME = "blog3";
    private static final String BLOG_3_URL_NAME = "blogUrl3";

    private static final int PAGEABLE_PAGE = 0;
    private static final int PAGEABLE_SIZE = 5;
    private static final String PAGEABLE_SORT = "id";

    private Sort sort;
    private Pageable pageable;

    @Autowired
    private BlogRepository blogRepository;

    @Before
    public void setUp() throws Exception {
        sort = new Sort(PAGEABLE_SORT);
        pageable = new PageRequest(PAGEABLE_PAGE, PAGEABLE_SIZE, sort);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    @Transactional
    public void testFindBlogById_returnNull() throws Exception {
        Blog notExistingBlog = blogRepository.findBlogById(NOT_EXISTING_BLOG_ID);

        Assert.assertEquals(null, notExistingBlog);
    }

    @Test
    @Transactional
    public void testFindBlogById_returnBlog() throws Exception {
        Blog existingBlog = blogRepository.findBlogById(BLOG_1_ID);

        Assert.assertEquals(BLOG_1_ID, existingBlog.getId());
        Assert.assertEquals(BLOG_1_NAME, existingBlog.getName());
        Assert.assertEquals(BLOG_1_URL_NAME, existingBlog.getUrlName());
    }

    @Test
    @Transactional
    public void testCountBlogsByName_returnOne() throws Exception {
        final int countBlogsByName = blogRepository.countBlogsByName(BLOG_1_NAME);

        Assert.assertEquals(1, countBlogsByName);
    }

    @Test
    @Transactional
    public void testCountBlogsByName_returnZero() throws Exception {
        final int countBlogsByName = blogRepository.countBlogsByName(NOT_EXISTING_BLOG_NAME);

        Assert.assertEquals(0, countBlogsByName);
    }

    @Test
    @Transactional
    public void testCountBlogsByUrlName_returnOne() throws Exception {
        final int countBlogsByUrlName = blogRepository.countBlogsByUrlName(BLOG_1_URL_NAME);

        Assert.assertEquals(1, countBlogsByUrlName);
    }

    @Test
    @Transactional
    public void testCountBlogsByUrlName_returnZero() throws Exception {
        final int countBlogsByUrlName =
                blogRepository.countBlogsByUrlName(NOT_EXISTING_BLOG_URL_NAME);

        Assert.assertEquals(0, countBlogsByUrlName);
    }

    @Test
    @Transactional
    public void testFindBlogs_returnAllBlogs() throws Exception {
        Page<Blog> blogs = blogRepository.findBlogs(pageable);

        Assert.assertEquals(3, blogs.getNumberOfElements());

        Assert.assertEquals(BLOG_1_ID, blogs.getContent().get(0).getId());
        Assert.assertEquals(BLOG_1_NAME, blogs.getContent().get(0).getName());
        Assert.assertEquals(BLOG_1_URL_NAME, blogs.getContent().get(0).getUrlName());

        Assert.assertEquals(BLOG_2_ID, blogs.getContent().get(1).getId());
        Assert.assertEquals(BLOG_2_NAME, blogs.getContent().get(1).getName());
        Assert.assertEquals(BLOG_2_URL_NAME, blogs.getContent().get(1).getUrlName());

        Assert.assertEquals(BLOG_3_ID, blogs.getContent().get(2).getId());
        Assert.assertEquals(BLOG_3_NAME, blogs.getContent().get(2).getName());
        Assert.assertEquals(BLOG_3_URL_NAME, blogs.getContent().get(2).getUrlName());
    }

    @Test
    @Transactional
    public void testFindAllFavoriteBlogs_returnNoBlogs() throws Exception {
        Page<Blog> blogs = blogRepository.findFavoriteBlogs(ACCOUNT_WITH_NO_FAVORITE_BLOG, pageable);

        Assert.assertEquals(0, blogs.getNumberOfElements());
    }

    @Test
    @Transactional
    public void testFindAllFavoriteBlogs_returnOneBlog() throws Exception {
        Page<Blog> blogs = blogRepository.findFavoriteBlogs(ACCOUNT_2_ID, pageable);

        Assert.assertEquals(1, blogs.getNumberOfElements());

        Assert.assertEquals(BLOG_2_ID, blogs.getContent().get(0).getId());
        Assert.assertEquals(BLOG_2_NAME, blogs.getContent().get(0).getName());
        Assert.assertEquals(BLOG_2_URL_NAME, blogs.getContent().get(0).getUrlName());
    }

    @Test
    @Transactional
    public void testFindFavoriteBlogs_returnTwoBlogs() throws Exception {
        Page<Blog> blogs = blogRepository.findFavoriteBlogs(ACCOUNT_1_ID, pageable);

        Assert.assertEquals(2, blogs.getNumberOfElements());

        Assert.assertEquals(BLOG_1_ID, blogs.getContent().get(0).getId());
        Assert.assertEquals(BLOG_1_NAME, blogs.getContent().get(0).getName());
        Assert.assertEquals(BLOG_1_URL_NAME, blogs.getContent().get(0).getUrlName());

        Assert.assertEquals(BLOG_2_ID, blogs.getContent().get(1).getId());
        Assert.assertEquals(BLOG_2_NAME, blogs.getContent().get(1).getName());
        Assert.assertEquals(BLOG_2_URL_NAME, blogs.getContent().get(1).getUrlName());
    }


}