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

package org.jblogcms.core.comment.service;

import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.account.repository.AccountRepository;
import org.jblogcms.core.comment.model.Comment;
import org.jblogcms.core.comment.repository.CommentRepository;
import org.jblogcms.core.post.model.Post;
import org.jblogcms.core.post.repository.PostRepository;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Victor Andreenko
 */
@RunWith(MockitoJUnitRunner.class)
public class CommentServiceImplTest {

    private static final Long ACCOUNT_ID = 1L;
    private static final Long CURRENT_ACCOUNT_ID = 2L;

    private static final Long POST_ID = 1L;

    private static final Long EXISTING_COMMENT_ID = 111L;
    private static final String EXISTING_COMMENT_TEXT = "existing comment text";

    private static final Long NEW_COMMENT_POST_ID = 112L;
    private static final String NEW_COMMENT_TEXT = "new comment text";

    private static final Long COMMENT_1_ID = 1L;
    private static final String COMMENT_1_TEXT = "Comment Text 1";

    private static final Long COMMENT_2_ID = 2L;
    private static final String COMMENT_2_TEXT = "Comment Text 2";

    private static final Long COMMENT_3_ID = 3L;
    private static final String COMMENT_3_TEXT = "Comment Text 3";

    private static final int COMMENTS_SIZE = 3;

    private static final int PAGEABLE_PAGE = 0;
    private static final int PAGEABLE_SIZE = 5;
    private static final String PAGEABLE_SORT = "id";

    private Comment comment1 = new Comment();
    private Comment comment2 = new Comment();
    private Comment comment3 = new Comment();
    private Comment existingComment = new Comment();

    private List<Comment> commentList = Arrays.asList(comment1, comment2, comment3);

    private Pageable pageable;
    private Page<Comment> comments;

    @Mock
    private CommentRepository commentRepositoryMock;
    @Mock
    private CommentToolService commentToolServiceMock;
    @Mock
    private AccountRepository accountRepositoryMock;
    @Mock
    private PostRepository postRepositoryMock;

    @InjectMocks
    CommentServiceImpl commentService = new CommentServiceImpl();

    @Before
    public void setUp() throws Exception {
        comment1.setId(COMMENT_1_ID);
        comment1.setText(COMMENT_1_TEXT);

        comment2.setId(COMMENT_2_ID);
        comment2.setText(COMMENT_2_TEXT);

        comment3.setId(COMMENT_3_ID);
        comment3.setText(COMMENT_3_TEXT);

        existingComment.setId(EXISTING_COMMENT_ID);
        existingComment.setText(EXISTING_COMMENT_TEXT);

        Sort sort = new Sort(PAGEABLE_SORT);
        pageable = new PageRequest(PAGEABLE_PAGE, PAGEABLE_SIZE, sort);
        comments = new PageImpl(commentList, pageable, COMMENTS_SIZE);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void findPostComments() throws Exception {
        when(commentRepositoryMock.findCommentsByPostId(POST_ID)).thenReturn(commentList);
        when(commentToolServiceMock.addItemRelationsToItemList(commentList, CURRENT_ACCOUNT_ID)).thenReturn(commentList);
        when(commentToolServiceMock.addItemRatesToItemList(commentList, CURRENT_ACCOUNT_ID)).thenReturn(commentList);

        List<Comment> testComments = commentService.findPostComments(POST_ID, CURRENT_ACCOUNT_ID);

        verify(commentRepositoryMock, times(1)).findCommentsByPostId(POST_ID);
        verify(commentToolServiceMock, times(1)).addItemRelationsToItemList(commentList, CURRENT_ACCOUNT_ID);
        verify(commentToolServiceMock, times(1)).addItemRatesToItemList(commentList, CURRENT_ACCOUNT_ID);

        Assert.assertEquals(COMMENTS_SIZE, testComments.size());

        Assert.assertEquals(COMMENT_1_ID, testComments.get(0).getId());
        Assert.assertEquals(COMMENT_1_TEXT, testComments.get(0).getText());
        Assert.assertEquals(COMMENT_2_ID, testComments.get(1).getId());
        Assert.assertEquals(COMMENT_2_TEXT, testComments.get(1).getText());
        Assert.assertEquals(COMMENT_3_ID, testComments.get(2).getId());
        Assert.assertEquals(COMMENT_3_TEXT, testComments.get(2).getText());
    }

    @Test
    public void findFavoriteComments() throws Exception {
        when(commentRepositoryMock.findFavoriteComments(ACCOUNT_ID, pageable)).thenReturn(comments);
        when(commentToolServiceMock.addItemRelationsToItemPage(comments, CURRENT_ACCOUNT_ID)).thenReturn(comments);
        when(commentToolServiceMock.addItemRatesToItemPage(comments, CURRENT_ACCOUNT_ID)).thenReturn(comments);

        Page<Comment> testComments = commentService.findFavoriteComments(ACCOUNT_ID, pageable, CURRENT_ACCOUNT_ID);

        verify(commentRepositoryMock, times(1)).findFavoriteComments(ACCOUNT_ID, pageable);
        verify(commentToolServiceMock, times(1)).addItemRelationsToItemPage(comments, CURRENT_ACCOUNT_ID);
        verify(commentToolServiceMock, times(1)).addItemRatesToItemPage(comments, CURRENT_ACCOUNT_ID);

        Assert.assertEquals(COMMENTS_SIZE, testComments.getTotalElements());

        Assert.assertEquals(COMMENT_1_ID, testComments.getContent().get(0).getId());
        Assert.assertEquals(COMMENT_1_TEXT, testComments.getContent().get(0).getText());
        Assert.assertEquals(COMMENT_2_ID, testComments.getContent().get(1).getId());
        Assert.assertEquals(COMMENT_2_TEXT, testComments.getContent().get(1).getText());
        Assert.assertEquals(COMMENT_3_ID, testComments.getContent().get(2).getId());
        Assert.assertEquals(COMMENT_3_TEXT, testComments.getContent().get(2).getText());
    }

    @Test
    public void findAccountComments() throws Exception {
        when(commentRepositoryMock.findCommentsByAccountId(ACCOUNT_ID, pageable)).thenReturn(comments);
        when(commentToolServiceMock.addItemRelationsToItemPage(comments, CURRENT_ACCOUNT_ID)).thenReturn(comments);
        when(commentToolServiceMock.addItemRatesToItemPage(comments, CURRENT_ACCOUNT_ID)).thenReturn(comments);

        Page<Comment> testComments = commentService.findAccountComments(ACCOUNT_ID, pageable, CURRENT_ACCOUNT_ID);

        verify(commentRepositoryMock, times(1)).findCommentsByAccountId(ACCOUNT_ID, pageable);
        verify(commentToolServiceMock, times(1)).addItemRelationsToItemPage(comments, CURRENT_ACCOUNT_ID);
        verify(commentToolServiceMock, times(1)).addItemRatesToItemPage(comments, CURRENT_ACCOUNT_ID);

        Assert.assertEquals(COMMENTS_SIZE, testComments.getTotalElements());

        Assert.assertEquals(COMMENT_1_ID, testComments.getContent().get(0).getId());
        Assert.assertEquals(COMMENT_1_TEXT, testComments.getContent().get(0).getText());
        Assert.assertEquals(COMMENT_2_ID, testComments.getContent().get(1).getId());
        Assert.assertEquals(COMMENT_2_TEXT, testComments.getContent().get(1).getText());
        Assert.assertEquals(COMMENT_3_ID, testComments.getContent().get(2).getId());
        Assert.assertEquals(COMMENT_3_TEXT, testComments.getContent().get(2).getText());
    }

    @Test
    public void addComment() throws Exception {
        Account currentAccount = new Account();
        currentAccount.setId(CURRENT_ACCOUNT_ID);
//        currentAccount.setNoOfComments(0);

        Post post = new Post();
        post.setId(NEW_COMMENT_POST_ID);
        post.setNoOfComments(0);

        Comment newComment = new Comment();
        newComment.setText(NEW_COMMENT_TEXT);
        newComment.setAccount(currentAccount);
        newComment.setPost(post);

        when(accountRepositoryMock.getOne(CURRENT_ACCOUNT_ID)).thenReturn(currentAccount);
        when(postRepositoryMock.findOne(NEW_COMMENT_POST_ID)).thenReturn(post);
        when(commentRepositoryMock.save(newComment)).thenReturn(newComment);
        when(postRepositoryMock.save(post)).thenReturn(post);

        Comment savedNewComment = commentService.addComment(newComment, CURRENT_ACCOUNT_ID);

        verify(accountRepositoryMock, times(1)).getOne(CURRENT_ACCOUNT_ID);
        verify(postRepositoryMock, times(1)).findOne(NEW_COMMENT_POST_ID);
        verify(commentRepositoryMock, times(1)).save(newComment);

        Assert.assertNotNull(savedNewComment);
        Assert.assertEquals(NEW_COMMENT_TEXT, savedNewComment.getText());
        Assert.assertEquals(CURRENT_ACCOUNT_ID, savedNewComment.getAccount().getId());
        Assert.assertEquals(NEW_COMMENT_POST_ID, savedNewComment.getPost().getId());
    }

    @Test
    public void editComment() throws Exception {
        Comment editedExistingComment = new Comment();
        editedExistingComment.setId(EXISTING_COMMENT_ID);
        editedExistingComment.setText(NEW_COMMENT_TEXT);

        when(commentRepositoryMock.save(editedExistingComment)).thenReturn(editedExistingComment);

        Comment savedEditedExistingComment = commentService.editComment(editedExistingComment);

        verify(commentRepositoryMock, times(1)).save(editedExistingComment);

        Assert.assertNotNull(savedEditedExistingComment);
        Assert.assertEquals(NEW_COMMENT_TEXT, savedEditedExistingComment.getText());
    }

}