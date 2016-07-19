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
import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.blog.model.BlogRate;
import org.jblogcms.core.common.model.JItem;
import org.jblogcms.core.common.model.ItemRate;
import org.jblogcms.core.common.repository.ItemRateRepository;
import org.jblogcms.core.common.repository.ItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;


/**
 * @author Victor Andreenko
 */
@RunWith(MockitoJUnitRunner.class)
public class ItemRateServiceImplTest<I extends JItem,
        IREL extends ItemRate> {

    private static final Long ITEM_ID = 1L;
    private static final Long ITEM_RELATION_ID = 2L;
    private static final byte ITEM_RELATION_VALUE = 1;
    private static final Long ACCOUNT_ID = 2L;

    private Class<BlogRate> itemRateClass= BlogRate.class;
    private Class<Blog> itemClass = Blog.class;

    private Blog item;
    private Account account;
    private BlogRate itemRate;
    private BlogRate newItemRate;

    @Mock
    ItemRateRepository<Blog, BlogRate> itemRateRepositoryMock;
    @Mock
    ItemRepository<Blog> itemRepositoryMock;
    @Mock
    ItemRepository<Account> accountRepositoryMock;

    private ItemRateServiceImpl<Blog, BlogRate> itemRateService = null;


    @Before
    public void setUp() throws Exception {
        itemRateService =
                new ItemRateServiceImpl<>(Blog.class, BlogRate.class);

        itemRateService.setAccountRepository(accountRepositoryMock);
        itemRateService.setItemRateRepository(itemRateRepositoryMock);
        itemRateService.setItemRepository(itemRepositoryMock);

        item = itemClass.newInstance();
        item.setId(ITEM_ID);
        item.prePersist();

        account = new Account();
        account.setId(ACCOUNT_ID);
        account.prePersist();

        itemRate = itemRateClass.newInstance();
        itemRate.setId(ITEM_RELATION_ID);
        itemRate.setAccount(account);
        itemRate.setItem(item);
    }

    @Test
    public void testAddItemRate_ItemRateExistsAlready() throws Exception {
        when(itemRateRepositoryMock.getItemRate(ITEM_ID, ACCOUNT_ID)).thenReturn(itemRate);
        when(itemRepositoryMock.findOne(ITEM_ID)).thenReturn(item);
        when(accountRepositoryMock.findOne(ACCOUNT_ID)).thenReturn(account);

        newItemRate = itemRateService.saveItemRate(ITEM_ID, ITEM_RELATION_VALUE, ACCOUNT_ID);

        verify(itemRateRepositoryMock, times(1)).getItemRate(ITEM_ID, ACCOUNT_ID);
        verifyZeroInteractions(itemRepositoryMock);
        verifyZeroInteractions(accountRepositoryMock);
        verify(itemRateRepositoryMock, times(0)).save(itemRate);

        Assert.assertEquals(ITEM_ID, newItemRate.getItem().getId());
        Assert.assertEquals(ACCOUNT_ID, newItemRate.getAccount().getId());
    }

    @Test
    public void testAddItemRate_ItemRateNotExist_BlogExist_AccountExist() throws Exception {
        when(itemRateRepositoryMock.getItemRate(ITEM_ID, ACCOUNT_ID)).thenReturn(null);
        when(itemRepositoryMock.getOne(ITEM_ID)).thenReturn(item);
        when(accountRepositoryMock.getOne(ACCOUNT_ID)).thenReturn(account);

        newItemRate = itemRateService.saveItemRate(ITEM_ID, ITEM_RELATION_VALUE, ACCOUNT_ID);

        verify(itemRateRepositoryMock, times(1)).getItemRate(ITEM_ID, ACCOUNT_ID);
        verify(itemRepositoryMock, times(1)).getOne(ITEM_ID);
        verify(accountRepositoryMock, times(1)).getOne(ACCOUNT_ID);
        verify(itemRateRepositoryMock, times(1)).save(itemRate);

        Assert.assertEquals(ITEM_ID, newItemRate.getItem().getId());
        Assert.assertEquals(ACCOUNT_ID, newItemRate.getAccount().getId());
    }

}