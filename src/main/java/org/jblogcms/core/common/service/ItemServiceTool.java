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

import org.jblogcms.core.common.model.ItemRate;
import org.jblogcms.core.common.model.ItemRelation;
import org.jblogcms.core.common.model.JItem;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @param <I>
 * @param <IREL>
 * @param <IRA>
 * @author Victor Andreenko
 */
public interface ItemServiceTool<I extends JItem,
        IREL extends ItemRelation,
        IRA extends ItemRate> {

    List<I> addItemRelationsToItemList(List<I> items, Long accountId);

    Page<I> addItemRelationsToItemPage(Page<I> items, Long accountId);

    I addItemRelationToItem(I item, Long accountId);

    List<I> addItemRatesToItemList(List<I> items, Long accountId);

    Page<I> addItemRatesToItemPage(Page<I> items, Long accountId);

    I addItemRateToItem(I item, Long accountId);
}
