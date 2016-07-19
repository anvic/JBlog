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
import org.jblogcms.core.common.repository.ItemRateRepository;
import org.jblogcms.core.common.repository.ItemRelationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.List;


/**
 * @param <I>
 * @param <IREL>
 * @param <IRA>
 * @author Victor Andreenko
 */
public abstract class ItemServiceToolImpl<I extends JItem,
        IREL extends ItemRelation,
        IRA extends ItemRate>
        implements ItemServiceTool<I, IREL, IRA> {

    private static final Logger logger =
            LoggerFactory.getLogger(ItemServiceToolImpl.class);

    private ItemRelationRepository<I, IREL> itemRelationRepository;
    private ItemRateRepository<I, IRA> itemRateRepository;

    public ItemServiceToolImpl() {
    }

    @Autowired
    public void setItemRelationRepository(ItemRelationRepository<I, IREL> itemRelationRepository) {
        this.itemRelationRepository = itemRelationRepository;
    }

    @Autowired
    public void setItemRateRepository(ItemRateRepository<I, IRA> itemRateRepository) {
        this.itemRateRepository = itemRateRepository;
    }


    @Override
    public List<I> addItemRelationsToItemList(List<I> items, Long accountId) {
        if (items != null && !items.isEmpty() && accountId != null) {
            List<IREL> itemRelations = itemRelationRepository.getItemRelations(items, accountId);

            if (!itemRelations.isEmpty()) {
                long[] sortedItemRelations = new long[itemRelations.size()];
                int i = 0;
                for (ItemRelation itemRelation : itemRelations) {
                    sortedItemRelations[i++] = itemRelation.getItem().getId();
                }
                Arrays.sort(sortedItemRelations);

                for (I item : items) {
                    int k = Arrays.binarySearch(sortedItemRelations, item.getId());
                    if (k >= 0) {
                        item.setCurrentItemRelation(itemRelations.get(k));
                    }
                }
            }
        }
        return items;
    }

    @Override
    public Page<I> addItemRelationsToItemPage(Page<I> items, Long accountId) {
        if (items != null && items.getTotalElements() != 0) {
            addItemRelationsToItemList(items.getContent(), accountId);
        }
        return items;
    }

    @Override
    public I addItemRelationToItem(I item, Long accountId) {
        if (item != null && accountId != null) {
            ItemRelation itemRelation =
                    itemRelationRepository.getItemRelation(item.getId(), accountId);
            if (itemRelation != null) {
                item.setCurrentItemRelation(itemRelation);
            }
        }
        return item;
    }

    @Override
    public List<I> addItemRatesToItemList(List<I> items, Long accountId) {
        if (items != null && !items.isEmpty() && accountId != null) {
            List<IRA> itemRates =
                    itemRateRepository.getItemRates(items, accountId);

            if (!itemRates.isEmpty()) {
                long[] sortedPostRates = new long[itemRates.size()];
                int i = 0;
                for (IRA itemRate : itemRates) {
                    sortedPostRates[i++] = itemRate.getItem().getId();
                }
                Arrays.sort(sortedPostRates);

                for (I item : items) {
                    int k = Arrays.binarySearch(sortedPostRates, item.getId());
                    if (k >= 0) {
                        item.setCurrentItemRate(itemRates.get(k));
                    }
                }
            }
        }
        return items;
    }

    @Override
    public Page<I> addItemRatesToItemPage(Page<I> items, Long accountId) {
        if (items != null && items.getTotalElements() != 0) {
            addItemRatesToItemList(items.getContent(), accountId);
        }
        return items;
    }

    @Override
    public I addItemRateToItem(I item, Long accountId) {
        if (item != null && accountId != null) {
            IRA itemRate =
                    itemRateRepository.getItemRate(item.getId(), accountId);
            if (itemRate != null) {
                item.setCurrentItemRate(itemRate);
            }
        }
        return item;
    }
}
