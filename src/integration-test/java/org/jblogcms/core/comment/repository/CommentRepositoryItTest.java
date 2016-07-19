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
public class CommentRepositoryItTest {

    private static final Long ACCOUNT_1_ID = 1L;
    private static final String ACCOUNT_1_NAME = "name1";
    private static final Long ACCOUNT_2_ID = 2L;
    private static final String ACCOUNT_2_NAME = "name2";

    private static final Long POST_1_ID = 1L;
    private static final String POST_1_TITLE = "Post Title 1";

    private static final Long POST_2_ID = 2L;
    private static final String POST_2_TITLE = "Post Title 2";

    private static final Long POST_3_ID = 3L;
    private static final String POST_3_TITLE = "Post Title 3";

    private static final Long NOT_EXISTING_COMMENT_ID = 15655L;
    private static final String NOT_EXISTING_COMMENT_TEXT = "not existing comment text";
    private static final Long POST_WITH_NO_COMMENTS = 234234L;
    private static final Long ACCOUNT_WITH_NO_COMMENTS = 234234L;
    private static final Long ACCOUNT_WITH_NO_FAVORITE_COMMENTS = 234234L;

    private static final Long COMMENT_1_ID = 1L;
    private static final String COMMENT_1_TEXT = "Comment Text 1";
    private static final Long COMMENT_1_ACCOUNT_1_ID = ACCOUNT_1_ID;
    private static final String COMMENT_1_ACCOUNT_1_NAME = ACCOUNT_1_NAME;
    private static final Long COMMENT_1_POST_2_ID = POST_2_ID;
    private static final String COMMENT_1_POST_2_TITLE = POST_2_TITLE;

    private static final Long COMMENT_2_ID = 2L;
    private static final String COMMENT_2_TEXT = "Comment Text 2";
    private static final Long COMMENT_2_ACCOUNT_2_ID = ACCOUNT_2_ID;
    private static final String COMMENT_2_ACCOUNT_2_NAME = ACCOUNT_2_NAME;
    private static final Long COMMENT_2_POST_1_ID = POST_1_ID;
    private static final String COMMENT_2_POST_1_TITLE = POST_1_TITLE;

    private static final Long COMMENT_3_ID = 3L;
    private static final String COMMENT_3_TEXT = "Comment Text 3";
    private static final Long COMMENT_3_ACCOUNT_1_ID = ACCOUNT_1_ID;
    private static final String COMMENT_3_ACCOUNT_1_NAME = ACCOUNT_1_NAME;
    private static final Long COMMENT_3_POST_1_ID = POST_1_ID;
    private static final String COMMENT_3_POST_1_TITLE = POST_1_TITLE;

    private static final int PAGEABLE_PAGE = 0;
    private static final int PAGEABLE_SIZE = 5;
    private static final String PAGEABLE_SORT = "id";

    private Pageable pageable;


    @Autowired
    private CommentRepository commentRepository;

    @Before
    public void setUp() throws Exception {
        Sort sort = new Sort(PAGEABLE_SORT);
        pageable = new PageRequest(PAGEABLE_PAGE, PAGEABLE_SIZE, sort);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    @Transactional
    public void testFindCommentById_returnNull() throws Exception {
        Comment notExistingComment = commentRepository.findCommentById(NOT_EXISTING_COMMENT_ID);

        Assert.assertEquals(null, notExistingComment);
    }

    @Test
    @Transactional
    public void testFindCommentById_returnComment() throws Exception {
        Comment existingComment = commentRepository.findCommentById(COMMENT_1_ID);

        Assert.assertEquals(COMMENT_1_ID, existingComment.getId());
        Assert.assertEquals(COMMENT_1_TEXT, existingComment.getText());
        Assert.assertEquals(COMMENT_1_ACCOUNT_1_ID, existingComment.getAccount().getId());
        Assert.assertEquals(COMMENT_1_ACCOUNT_1_NAME, existingComment.getAccount().getName());
        Assert.assertEquals(COMMENT_1_POST_2_ID, existingComment.getPost().getId());
        Assert.assertEquals(COMMENT_1_POST_2_TITLE, existingComment.getPost().getTitle());
    }

    @Test
    @Transactional
    public void testFindCommentsByPostId_returnNoComments() throws Exception {
        List<Comment> comments = commentRepository.findCommentsByPostId(POST_WITH_NO_COMMENTS);

        Assert.assertEquals(0, comments.size());
    }

    @Test
    @Transactional
    public void testFindCommentsByPostId_returnOneComment() throws Exception {
        List<Comment> comments = commentRepository.findCommentsByPostId(POST_2_ID);

        Assert.assertEquals(1, comments.size());

        Assert.assertEquals(COMMENT_1_ID, comments.get(0).getId());
        Assert.assertEquals(COMMENT_1_TEXT, comments.get(0).getText());
        Assert.assertEquals(COMMENT_1_ACCOUNT_1_ID, comments.get(0).getAccount().getId());
        Assert.assertEquals(COMMENT_1_ACCOUNT_1_NAME, comments.get(0).getAccount().getName());

    }

    @Test
    @Transactional
    public void testFindCommentsByPostId_returnTwoComments() throws Exception {
        List<Comment> comments = commentRepository.findCommentsByPostId(POST_1_ID);

        Assert.assertEquals(2, comments.size());

        Assert.assertEquals(COMMENT_2_ID, comments.get(0).getId());
        Assert.assertEquals(COMMENT_2_TEXT, comments.get(0).getText());
        Assert.assertEquals(COMMENT_2_ACCOUNT_2_ID, comments.get(0).getAccount().getId());
        Assert.assertEquals(COMMENT_2_ACCOUNT_2_NAME, comments.get(0).getAccount().getName());

        Assert.assertEquals(COMMENT_3_ID, comments.get(1).getId());
        Assert.assertEquals(COMMENT_3_TEXT, comments.get(1).getText());
        Assert.assertEquals(COMMENT_3_ACCOUNT_1_ID, comments.get(1).getAccount().getId());
        Assert.assertEquals(COMMENT_3_ACCOUNT_1_NAME, comments.get(1).getAccount().getName());
    }

    @Test
    @Transactional
    public void testFindCommentsByAccountId_returNoComments() throws Exception {
        Page<Comment> comments = commentRepository.findCommentsByAccountId(ACCOUNT_WITH_NO_COMMENTS, pageable);

        Assert.assertEquals(0, comments.getNumberOfElements());
    }

    @Test
    @Transactional
    public void testFindCommentsByAccountId_returnOneComment() throws Exception {
        Page<Comment> comments = commentRepository.findCommentsByAccountId(ACCOUNT_2_ID, pageable);

        Assert.assertEquals(1, comments.getNumberOfElements());

        Assert.assertEquals(COMMENT_2_ID, comments.getContent().get(0).getId());
        Assert.assertEquals(COMMENT_2_TEXT, comments.getContent().get(0).getText());
        Assert.assertEquals(COMMENT_2_ACCOUNT_2_ID, comments.getContent().get(0).getAccount().getId());
        Assert.assertEquals(COMMENT_2_ACCOUNT_2_NAME, comments.getContent().get(0).getAccount().getName());
        Assert.assertEquals(COMMENT_2_POST_1_ID, comments.getContent().get(0).getPost().getId());
        Assert.assertEquals(COMMENT_2_POST_1_TITLE, comments.getContent().get(0).getPost().getTitle());
    }

    @Test
    @Transactional
    public void testFindCommentsByAccountId_returnTwoComments() throws Exception {
        Page<Comment> comments = commentRepository.findCommentsByAccountId(ACCOUNT_1_ID, pageable);

        Assert.assertEquals(2, comments.getNumberOfElements());

        Assert.assertEquals(COMMENT_1_ID, comments.getContent().get(0).getId());
        Assert.assertEquals(COMMENT_1_TEXT, comments.getContent().get(0).getText());
        Assert.assertEquals(COMMENT_1_ACCOUNT_1_ID, comments.getContent().get(0).getAccount().getId());
        Assert.assertEquals(COMMENT_1_ACCOUNT_1_NAME, comments.getContent().get(0).getAccount().getName());
        Assert.assertEquals(COMMENT_1_POST_2_ID, comments.getContent().get(0).getPost().getId());
        Assert.assertEquals(COMMENT_1_POST_2_TITLE, comments.getContent().get(0).getPost().getTitle());

        Assert.assertEquals(COMMENT_3_ID, comments.getContent().get(1).getId());
        Assert.assertEquals(COMMENT_3_TEXT, comments.getContent().get(1).getText());
        Assert.assertEquals(COMMENT_3_ACCOUNT_1_ID, comments.getContent().get(1).getAccount().getId());
        Assert.assertEquals(COMMENT_3_ACCOUNT_1_NAME, comments.getContent().get(1).getAccount().getName());
        Assert.assertEquals(COMMENT_3_POST_1_ID, comments.getContent().get(1).getPost().getId());
        Assert.assertEquals(COMMENT_3_POST_1_TITLE, comments.getContent().get(1).getPost().getTitle());
    }

    @Test
    @Transactional
    public void testFindFavoriteComments_returnNoComments() throws Exception {
        Page<Comment> comments = commentRepository.findFavoriteComments(ACCOUNT_WITH_NO_FAVORITE_COMMENTS, pageable);

        Assert.assertEquals(0, comments.getNumberOfElements());
    }

    @Test
    @Transactional
    public void testFindFavoriteComments_returnOneComment() throws Exception {
        Page<Comment> comments = commentRepository.findFavoriteComments(ACCOUNT_2_ID, pageable);

        Assert.assertEquals(1, comments.getNumberOfElements());

        Assert.assertEquals(COMMENT_2_ID, comments.getContent().get(0).getId());
        Assert.assertEquals(COMMENT_2_TEXT, comments.getContent().get(0).getText());
        Assert.assertEquals(COMMENT_2_ACCOUNT_2_ID, comments.getContent().get(0).getAccount().getId());
        Assert.assertEquals(COMMENT_2_ACCOUNT_2_NAME, comments.getContent().get(0).getAccount().getName());
        Assert.assertEquals(COMMENT_2_POST_1_ID, comments.getContent().get(0).getPost().getId());
        Assert.assertEquals(COMMENT_2_POST_1_TITLE, comments.getContent().get(0).getPost().getTitle());
    }

    @Test
    @Transactional
    public void testFindFavoriteComments_returnTwoComments() throws Exception {
        Page<Comment> comments = commentRepository.findFavoriteComments(ACCOUNT_1_ID, pageable);

        Assert.assertEquals(2, comments.getNumberOfElements());

        Assert.assertEquals(COMMENT_1_ID, comments.getContent().get(0).getId());
        Assert.assertEquals(COMMENT_1_TEXT, comments.getContent().get(0).getText());
        Assert.assertEquals(COMMENT_1_ACCOUNT_1_ID, comments.getContent().get(0).getAccount().getId());
        Assert.assertEquals(COMMENT_1_ACCOUNT_1_NAME, comments.getContent().get(0).getAccount().getName());
        Assert.assertEquals(COMMENT_1_POST_2_ID, comments.getContent().get(0).getPost().getId());
        Assert.assertEquals(COMMENT_1_POST_2_TITLE, comments.getContent().get(0).getPost().getTitle());

        Assert.assertEquals(COMMENT_2_ID, comments.getContent().get(1).getId());
        Assert.assertEquals(COMMENT_2_TEXT, comments.getContent().get(1).getText());
        Assert.assertEquals(COMMENT_2_ACCOUNT_2_ID, comments.getContent().get(1).getAccount().getId());
        Assert.assertEquals(COMMENT_2_ACCOUNT_2_NAME, comments.getContent().get(1).getAccount().getName());
        Assert.assertEquals(COMMENT_2_POST_1_ID, comments.getContent().get(1).getPost().getId());
        Assert.assertEquals(COMMENT_2_POST_1_TITLE, comments.getContent().get(1).getPost().getTitle());
    }

}