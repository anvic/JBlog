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

package org.jblogcms.core.common.service;

import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.common.model.ItemRate;
import org.jblogcms.core.common.model.JItem;

/**
 * Provides the service for accessing, adding, changing, deleting entities that extends {@link ItemRate}.
 *
 * @author Victor Andreenko
 */
public interface ItemRateService<I extends JItem, IRA extends ItemRate> {

    /**
     * Creates the entity that extends {@link ItemRate} using the method parameters and saves the entity
     *
     * @param itemId    the primary key of the entity which extends {@link JItem}
     * @param accountId the primary key of the {@link Account}
     * @return the saved comment rate
     */
    IRA saveItemRate(long itemId, byte value, long accountId);
}
