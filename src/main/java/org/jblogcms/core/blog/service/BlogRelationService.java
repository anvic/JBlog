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

import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.blog.model.BlogRelation;
import org.jblogcms.core.common.model.JItem;
import org.jblogcms.core.common.service.ItemRelationService;

import java.util.List;

/**
 * Provides the service for accessing, adding, changing, deleting {@link BlogRelation}.
 *
 * @author Victor Andreenko
 */
public interface BlogRelationService
        extends ItemRelationService<Blog, BlogRelation> {
    /**
     * Creates {@link BlogRelation} using the method parameters and saves the entity
     *
     * @param itemId    the primary key of the {@link Blog} which extends the {@link JItem}
     * @param accountId the primary key of the {@link Account}
     * @return the saved blog relation
     */
    @Override
    BlogRelation saveItemRelation(long itemId, long accountId);

    /**
     * Deletes {@link BlogRelation} using the method parameters
     *
     * @param itemId    the primary key of the {@link Blog} which extends the {@link JItem}
     * @param accountId the primary key of the {@link Account}
     */
    @Override
    boolean deleteItemRelation(long itemId, long accountId);

    List<BlogRelation> getBlogRelations(Long accountId);

}
