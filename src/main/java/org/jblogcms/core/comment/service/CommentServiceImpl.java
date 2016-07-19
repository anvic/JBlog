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
import org.jblogcms.core.common.service.ItemServiceImpl;
import org.jblogcms.core.post.model.Post;
import org.jblogcms.core.post.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author Victor Andreenko
 */
@Service
public class CommentServiceImpl
        extends ItemServiceImpl<Comment>
        implements CommentService {

    private static final byte NUMBER_OF_COMMENTS_INCREMENT = 1;
    private static final byte NUMBER_OF_COMMENTS_DECREMENT = -1;

    private static final Logger logger =
            LoggerFactory.getLogger(CommentServiceImpl.class);

    private CommentRepository commentRepository;
    private CommentToolService commentToolService;
    private AccountRepository accountRepository;
    private PostRepository postRepository;


    public CommentServiceImpl() {
        super(Comment.class);
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setCommentToolService(CommentToolService commentToolService) {
        this.commentToolService = commentToolService;
    }

    @Override
    @Transactional
    @PreAuthorize("#comment.account.id == principal.id")
    public void delete(Comment comment) {
        commentRepository.delete(comment);

        Post post = postRepository.findOne(comment.getPost().getId());
        post.setNoOfComments(post.getNoOfComments() + NUMBER_OF_COMMENTS_DECREMENT);
        postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    @PostAuthorize("returnObject.account.id == principal.id")
    public Comment findCommentByIdForEdit(Long commentId) {
        return commentRepository.findCommentByIdForEdit(commentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findPostComments(Long postId, Long currentAccountId) {
        List<Comment> comments = commentRepository.findCommentsByPostId(postId);
        comments = commentToolService.addItemRatesToItemList(comments, currentAccountId);
        comments = commentToolService.addItemRelationsToItemList(comments, currentAccountId);

        return comments;
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Comment> findFavoriteComments(Long accountId, Pageable pageable, Long currentAccountId) {
        Page<Comment> comments = commentRepository.findFavoriteComments(accountId, pageable);
        comments = commentToolService.addItemRatesToItemPage(comments, currentAccountId);
        comments = commentToolService.addItemRelationsToItemPage(comments, currentAccountId);

        return comments;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Comment> findAccountComments(Long accountId, Pageable pageable, Long currentAccountId) {
        Page<Comment> comments = commentRepository.findCommentsByAccountId(accountId, pageable);
        comments = commentToolService.addItemRatesToItemPage(comments, currentAccountId);
        comments = commentToolService.addItemRelationsToItemPage(comments, currentAccountId);

        return comments;
    }

    @Override
    @Transactional
    public Comment addComment(Comment comment, Long currentAccountId) {
        Post post = postRepository.findOne(comment.getPost().getId());
        Account account = accountRepository.getOne(currentAccountId);

        comment.setAccount(account);
        comment.setPost(post);

        Comment addedComment = commentRepository.save(comment);
        if (addedComment != null) {
            post.setNoOfComments(post.getNoOfComments() + NUMBER_OF_COMMENTS_INCREMENT);
            postRepository.save(post);
        }
        return addedComment;
    }

    @Override
    @Transactional
    @PreAuthorize("#comment.account.id == principal.id")
    public Comment editComment(Comment comment) {
        return commentRepository.save(comment);
    }


}
