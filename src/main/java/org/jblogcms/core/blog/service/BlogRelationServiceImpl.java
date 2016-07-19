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

import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.blog.model.BlogRelation;
import org.jblogcms.core.blog.repository.BlogRelationRepository;
import org.jblogcms.core.blog.repository.BlogRepository;
import org.jblogcms.core.common.service.ItemRelationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Victor Andreenko
 */
@Service
public class BlogRelationServiceImpl
        extends ItemRelationServiceImpl<Blog, BlogRelation>
        implements BlogRelationService {

    private BlogRepository blogRepository;

    @Autowired
    public void setBlogRepository(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }


    private BlogRelationRepository blogRelationRepository;

    public BlogRelationServiceImpl() {
        super(Blog.class, BlogRelation.class);
    }

    @Autowired
    public void setBlogRelationRepository(BlogRelationRepository blogRelationRepository) {
        this.blogRelationRepository = blogRelationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlogRelation> getBlogRelations(Long accountId) {
        return blogRelationRepository.getBlogRelations(accountId);
    }

    @Override
    @Transactional
    public BlogRelation saveItemRelation(long itemId, long accountId) {
        BlogRelation itemRelation = super.saveItemRelation(itemId, accountId);
        if (itemRelation != null) {
            Blog item = blogRepository.findOne(itemId);
            item.setNoOfSubscribers(item.getNoOfSubscribers() + 1);
            blogRepository.save(item);
        }
        return itemRelation;
    }

    @Override
    @Transactional
    public boolean deleteItemRelation(long itemId, long accountId) {
        boolean result = super.deleteItemRelation(itemId, accountId);
        if (result) {
            Blog item = blogRepository.findOne(itemId);
            item.setNoOfSubscribers(item.getNoOfSubscribers() - 1);
            blogRepository.save(item);
        }
        return result;
    }

}
