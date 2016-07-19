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

package org.jblogcms.core.account.service;

import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.account.model.AccountRelation;
import org.jblogcms.core.account.repository.AccountRepository;
import org.jblogcms.core.common.service.ItemRelationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Provides the implementation service for accessing, adding, changing, deleting {@link AccountRelation}.
 *
 * @author Victor Andreenko
 */
@Service
public class AccountRelationServiceImpl
        extends ItemRelationServiceImpl<Account, AccountRelation>
        implements AccountRelationService {

    private AccountRepository accountRepository;

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountRelationServiceImpl() {
        super(Account.class, AccountRelation.class);
    }

    @Override
    @Transactional
    public AccountRelation saveItemRelation(long itemId, long accountId) {
        AccountRelation itemRelation = super.saveItemRelation(itemId, accountId);
        if (itemRelation != null) {
            Account item = accountRepository.findOne(itemId);
            item.setNoOfSubscribers(item.getNoOfSubscribers() + 1);
            accountRepository.save(item);
        }
        return itemRelation;
    }

    @Override
    @Transactional
    public boolean deleteItemRelation(long itemId, long accountId) {
        boolean result = super.deleteItemRelation(itemId, accountId);
        if (result) {
            Account item = accountRepository.findOne(itemId);
            item.setNoOfSubscribers(item.getNoOfSubscribers() - 1);
            accountRepository.save(item);
        }
        return result;
    }
}
