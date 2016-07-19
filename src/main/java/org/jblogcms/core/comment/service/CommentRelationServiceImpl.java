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

import org.jblogcms.core.comment.model.Comment;
import org.jblogcms.core.comment.model.CommentRelation;
import org.jblogcms.core.comment.repository.CommentRepository;
import org.jblogcms.core.common.service.ItemRelationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Victor Andreenko
 */
@Service
public class CommentRelationServiceImpl
        extends ItemRelationServiceImpl<Comment, CommentRelation>
        implements CommentRelationService {

    private CommentRepository commentRepository;

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentRelationServiceImpl() {
        super(Comment.class, CommentRelation.class);
    }

    @Override
    @Transactional
    public CommentRelation saveItemRelation(long itemId, long accountId) {
        CommentRelation itemRelation = super.saveItemRelation(itemId, accountId);
        if (itemRelation != null) {
            Comment item = commentRepository.findOne(itemId);
            item.setNoOfSubscribers(item.getNoOfSubscribers() + 1);
            commentRepository.save(item);
        }
        return itemRelation;
    }

    @Override
    @Transactional
    public boolean deleteItemRelation(long itemId, long accountId) {
        boolean result = super.deleteItemRelation(itemId, accountId);
        if (result) {
            Comment item = commentRepository.findOne(itemId);
            item.setNoOfSubscribers(item.getNoOfSubscribers() - 1);
            commentRepository.save(item);
        }
        return result;
    }
}
