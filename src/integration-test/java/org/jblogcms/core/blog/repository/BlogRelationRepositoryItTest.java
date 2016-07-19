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
import org.jblogcms.core.blog.model.BlogRelation;
import org.jblogcms.core.config.PersistenceContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class BlogRelationRepositoryItTest {

    private static final Long BLOG_1_ID = 1L;
    private static final Long BLOG_2_ID = 2L;
    private static final Long ACCOUNT_ID = 1L;

    @Autowired
    BlogRelationRepository blogRelationRepository;

    @Autowired
    BlogRepository blogRepository;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    @Transactional
    public void testGetBlogRelations_resultOne() throws Exception {
        List<BlogRelation> blogRelations = blogRelationRepository.getBlogRelations(1l);

        Assert.assertEquals(2, blogRelations.size());

        Assert.assertEquals(BLOG_1_ID, blogRelations.get(0).getId());
        Assert.assertEquals(BLOG_2_ID, blogRelations.get(1).getId());
    }

    @Test
    @Transactional
    public void testGetBlogRelations_resultTwo() throws Exception {
        Blog blog1 = blogRepository.findOne(BLOG_1_ID);
        Blog blog2 = blogRepository.findOne(BLOG_2_ID);
        List<Blog> blogs = Arrays.asList(blog1, blog2);

        List<BlogRelation> blogRelations =
                blogRelationRepository.getItemRelations(blogs, ACCOUNT_ID);

        Assert.assertEquals(2, blogRelations.size());

        Assert.assertEquals(ACCOUNT_ID, blogRelations.get(0).getAccount().getId());
        Assert.assertEquals(BLOG_1_ID, blogRelations.get(0).getItem().getId());

        Assert.assertEquals(ACCOUNT_ID, blogRelations.get(1).getAccount().getId());
        Assert.assertEquals(BLOG_2_ID, blogRelations.get(1).getItem().getId());
    }

    @Test
    @Transactional
    public void testGetBlogRelation_found() throws Exception {
        BlogRelation blogRelation =
                blogRelationRepository.getItemRelation(BLOG_1_ID, ACCOUNT_ID);

        Assert.assertNotNull(blogRelation);

        Assert.assertEquals(ACCOUNT_ID, blogRelation.getAccount().getId());
        Assert.assertEquals(BLOG_1_ID, blogRelation.getItem().getId());
    }
}