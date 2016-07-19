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

import org.jblogcms.core.common.service.ItemRelationServiceImpl;
import org.jblogcms.core.post.model.Post;
import org.jblogcms.core.post.model.PostRelation;
import org.jblogcms.core.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Victor Andreenko
 */
@Service
public class PostRelationServiceImpl
        extends ItemRelationServiceImpl<Post, PostRelation>
        implements PostRelationService {

    private PostRepository postRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostRelationServiceImpl() {
        super(Post.class, PostRelation.class);
    }

    @Override
    @Transactional
    public PostRelation saveItemRelation(long itemId, long accountId) {
        PostRelation itemRelation = super.saveItemRelation(itemId, accountId);
        if (itemRelation != null) {
            Post item = postRepository.findOne(itemId);
            item.setNoOfSubscribers(item.getNoOfSubscribers() + 1);
            postRepository.save(item);
        }
        return itemRelation;
    }

    @Override
    @Transactional
    public boolean deleteItemRelation(long itemId, long accountId) {
        boolean result = super.deleteItemRelation(itemId, accountId);
        if (result) {
            Post item = postRepository.findOne(itemId);
            item.setNoOfSubscribers(item.getNoOfSubscribers() - 1);
            postRepository.save(item);
        }
        return result;
    }
}
