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

import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.common.model.JItem;
import org.jblogcms.core.common.service.ItemRateService;
import org.jblogcms.core.post.model.Post;
import org.jblogcms.core.post.model.PostRate;


/**
 * Provides the service for accessing, adding, changing, deleting {@link PostRate}.
 *
 * @author Victor Andreenko
 */
public interface PostRateService
        extends ItemRateService<Post, PostRate> {

    /**
     * Creates {@link PostRate} using the method parameters and saves the entity
     *
     * @param itemId    the primary key of the {@link Post} which extends {@link JItem}
     * @param accountId the primary key of the {@link Account}
     * @return the saved comment rate
     *
     */
    @Override
    PostRate saveItemRate(long itemId, byte valueNumber, long accountId);

}

