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
import org.jblogcms.core.comment.model.CommentRate;
import org.jblogcms.core.comment.repository.CommentRepository;
import org.jblogcms.core.common.service.ItemRateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Victor Andreenko
 */
@Service
public class CommentRateServiceImpl
        extends ItemRateServiceImpl<Comment, CommentRate>
        implements CommentRateService {

    private CommentRepository commentRepository;

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentRateServiceImpl() {
        super(Comment.class, CommentRate.class);
    }

    @Override
    @Transactional
    public CommentRate saveItemRate(long itemId, byte value, long accountId) {
        CommentRate itemRate = super.saveItemRate(itemId, value, accountId);

        if (itemRate != null) {
            Comment item = commentRepository.findOne(itemId);
            int noOfRatedUpInc = (value == RATED_UP_VALUE) ? NUMBER_OF_RATED_INCREMENT : 0;
            int noOfRatedDownInc = (value == RATED_DOWN_VALUE) ? NUMBER_OF_RATED_INCREMENT : 0;
            item.setNoOfRated(item.getNoOfRated() + NUMBER_OF_RATED_INCREMENT);
            item.setNoOfRatedUp(item.getNoOfRatedUp() + noOfRatedUpInc);
            item.setNoOfRatedDown(item.getNoOfRatedDown() + noOfRatedDownInc);
            item.setRating(item.getRating() + value);
            commentRepository.save(item);
        }
        return itemRate;
    }
}
