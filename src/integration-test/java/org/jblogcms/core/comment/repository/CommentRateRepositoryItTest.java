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

package org.jblogcms.core.comment.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.jblogcms.core.comment.model.Comment;
import org.jblogcms.core.comment.model.CommentRate;
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
public class CommentRateRepositoryItTest {

    private static final Long COMMENT_1_ID = 1L;
    private static final Long COMMENT_2_ID = 2L;
    private static final Long ACCOUNT_1_ID = 1L;

    @Autowired
    CommentRateRepository commentRateRepository;

    @Autowired
    CommentRepository commentRepository;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Transactional
    public void testGetCommentRates() throws Exception {
        Comment comment1 = commentRepository.findOne(COMMENT_1_ID);
        Comment comment2 = commentRepository.findOne(COMMENT_2_ID);

        List<Comment> comments = new ArrayList<Comment>(2);
        comments.add(comment1);
        comments.add(comment2);

        List<CommentRate> commentRates = commentRateRepository.getItemRates(comments, ACCOUNT_1_ID);

        Assert.assertEquals(2, commentRates.size());
        Assert.assertEquals(ACCOUNT_1_ID, commentRates.get(0).getAccount().getId());
        Assert.assertEquals(COMMENT_1_ID, commentRates.get(0).getItem().getId());

        Assert.assertEquals(ACCOUNT_1_ID, commentRates.get(1).getAccount().getId());
        Assert.assertEquals(COMMENT_2_ID, commentRates.get(1).getItem().getId());
    }

    @Test
    @Transactional
    public void testGetCommentRate_found() throws Exception {
        CommentRate commentRate = commentRateRepository.getItemRate(COMMENT_1_ID, ACCOUNT_1_ID);

        Assert.assertNotNull(commentRate);

        Assert.assertEquals(ACCOUNT_1_ID, commentRate.getAccount().getId());
        Assert.assertEquals(COMMENT_1_ID, commentRate.getItem().getId());
    }


}