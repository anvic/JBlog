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
import org.jblogcms.core.common.model.ItemRelation;
import org.jblogcms.core.common.model.JItem;
import org.jblogcms.core.common.repository.ItemRelationRepository;
import org.jblogcms.core.common.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @param <I>
 * @param <IREL>
 * @author Victor Andreenko
 */
public class ItemRelationServiceImpl<I extends JItem, IREL extends ItemRelation>
        implements ItemRelationService<I, IREL> {

    private static final Logger logger =
            LoggerFactory.getLogger(ItemRelationServiceImpl.class);

    private final Class<IREL> itemRelationClass;
    private final Class<I> itemClass;

    private ItemRelationRepository<I, IREL> itemRelationRepository;
    private ItemRepository<I> itemRepository;
    private ItemRepository<Account> accountRepository;


    public ItemRelationServiceImpl(Class<I> itemClass, Class<IREL> itemRelationClass) {
        this.itemClass = itemClass;
        this.itemRelationClass = itemRelationClass;
    }

    @Autowired
    public void setItemRelationRepository(ItemRelationRepository<I, IREL> itemRelationRepository) {
        this.itemRelationRepository = itemRelationRepository;
    }

    @Autowired
    public void setItemRepository(ItemRepository<I> itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Autowired
    public void setAccountRepository(ItemRepository<Account> accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public IREL saveItemRelation(long itemId, long accountId) {
        IREL itemRelation = itemRelationRepository.getItemRelation(itemId, accountId);

        if (itemRelation == null) {
            Account account = accountRepository.getOne(accountId);
            I item = itemRepository.getOne(itemId);

            try {
                itemRelation = itemRelationClass.newInstance();
                itemRelation.setAccount(account);
                itemRelation.setItem(item);
                itemRelationRepository.save(itemRelation);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return itemRelation;
    }

    @Override
    @Transactional
    public boolean deleteItemRelation(long itemId, long accountId) {
        return itemRelationRepository.deleteItemRelation(itemId, accountId) == 1;
    }
}


