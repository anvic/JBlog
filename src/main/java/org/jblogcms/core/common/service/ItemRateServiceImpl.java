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
import org.jblogcms.core.common.repository.ItemRateRepository;
import org.jblogcms.core.common.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @param <I>
 * @param <IRA>
 * @author Victor Andreenko
 */
public class ItemRateServiceImpl<I extends JItem, IRA extends ItemRate>
        implements ItemRateService<I, IRA> {

    private static final Logger logger =
            LoggerFactory.getLogger(ItemRateServiceImpl.class);

    protected static final int NUMBER_OF_RATED_INCREMENT = 1;
    protected static final int NUMBER_OF_RATED_DECREMENT = -1;

    protected static final int RATED_UP_VALUE = 1;
    protected static final int RATED_DOWN_VALUE = -1;

    private final Class<IRA> itemRateClass;
    private final Class<I> itemClass;

    private ItemRateRepository<I, IRA> itemRateRepository;
    private ItemRepository<I> itemRepository;
    private ItemRepository<Account> accountRepository;

    public ItemRateServiceImpl(Class<I> itemClass, Class<IRA> itemRateClass) {
        this.itemClass = itemClass;
        this.itemRateClass = itemRateClass;
    }

    @Autowired
    public void setItemRateRepository(ItemRateRepository<I, IRA> itemRateRepository) {
        this.itemRateRepository = itemRateRepository;
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
    public IRA saveItemRate(long itemId, byte value, long accountId) {
        IRA itemRate = itemRateRepository.getItemRate(itemId, accountId);
        if (itemRate == null) {
            Account account = accountRepository.getOne(accountId);
            I item = itemRepository.getOne(itemId);
            try {
                itemRate = itemRateClass.newInstance();
                itemRate.setAccount(account);
                itemRate.setItem(item);
                itemRate.setRate(value);
                itemRateRepository.save(itemRate);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return itemRate;
    }
}
