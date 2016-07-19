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

import org.junit.Assert;
import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.blog.model.BlogRelation;
import org.jblogcms.core.common.model.JItem;
import org.jblogcms.core.common.model.ItemRelation;
import org.jblogcms.core.common.repository.ItemRelationRepository;
import org.jblogcms.core.common.repository.ItemRepository;
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
public class ItemRelationServiceImplTest<I extends JItem,
        IREL extends ItemRelation> {

    private static final Long ITEM_ID = 1l;
    private static final Long ITEM_RELATION_ID = 2l;
    private static final Long ACCOUNT_ID = 2l;

    private Class<BlogRelation> itemRelationClass = BlogRelation.class;
    private Class<Blog> itemClass = Blog.class;

    private Blog item;
    private Account account;
    private BlogRelation itemRelation;
    private BlogRelation newItemRelation;

    @Mock
    ItemRelationRepository<Blog, BlogRelation> itemRelationRepositoryMock;
    @Mock
    ItemRepository<Blog> itemRepositoryMock;
    @Mock
    ItemRepository<Account> accountRepositoryMock;

    private ItemRelationServiceImpl<Blog, BlogRelation> itemRelationService;


    @Before
    public void setUp() throws Exception {
        itemRelationService =
                new ItemRelationServiceImpl<>(Blog.class, BlogRelation.class);

        itemRelationService.setAccountRepository(accountRepositoryMock);
        itemRelationService.setItemRelationRepository(itemRelationRepositoryMock);
        itemRelationService.setItemRepository(itemRepositoryMock);

        item = itemClass.newInstance();
        item.setId(ITEM_ID);
        item.prePersist();

        account = new Account();
        account.setId(ACCOUNT_ID);
        account.prePersist();

        itemRelation = itemRelationClass.newInstance();
        itemRelation.setId(ITEM_RELATION_ID);
        itemRelation.setAccount(account);
        itemRelation.setItem(item);
    }

    @Test
    public void testAddItemRelation_ItemRelationExistsAlready() throws Exception {
        when(itemRelationRepositoryMock.getItemRelation(ITEM_ID, ACCOUNT_ID)).thenReturn(itemRelation);
        when(itemRepositoryMock.getOne(ITEM_ID)).thenReturn(item);
        when(accountRepositoryMock.getOne(ACCOUNT_ID)).thenReturn(account);

        newItemRelation = itemRelationService.saveItemRelation(ITEM_ID, ACCOUNT_ID);

        verify(itemRelationRepositoryMock, times(1)).getItemRelation(ITEM_ID, ACCOUNT_ID);
        verifyZeroInteractions(itemRepositoryMock);
        verifyZeroInteractions(accountRepositoryMock);
        verify(itemRelationRepositoryMock, times(0)).save(itemRelation);

        Assert.assertEquals(ITEM_ID, newItemRelation.getItem().getId());
        Assert.assertEquals(ACCOUNT_ID, newItemRelation.getAccount().getId());
    }

    @Test
    public void testAddItemRelation_ItemRelationNotExist_BlogExist_AccountExist() throws Exception {
        when(itemRelationRepositoryMock.getItemRelation(ITEM_ID, ACCOUNT_ID)).thenReturn(null);
        when(itemRepositoryMock.getOne(ITEM_ID)).thenReturn(item);
        when(accountRepositoryMock.getOne(ACCOUNT_ID)).thenReturn(account);

        newItemRelation = itemRelationService.saveItemRelation(ITEM_ID, ACCOUNT_ID);

        verify(itemRelationRepositoryMock, times(1)).getItemRelation(ITEM_ID, ACCOUNT_ID);
        verify(itemRepositoryMock, times(1)).getOne(ITEM_ID);
        verify(accountRepositoryMock, times(1)).getOne(ACCOUNT_ID);
        verify(itemRelationRepositoryMock, times(1)).save(itemRelation);

        Assert.assertEquals(ITEM_ID, newItemRelation.getItem().getId());
                Assert.assertEquals(ACCOUNT_ID, newItemRelation.getAccount().getId());
    }


    @Test
    public void testDeleteItemRelation_ItemRelationExists() throws Exception {
        when(itemRelationRepositoryMock.deleteItemRelation(ITEM_ID, ACCOUNT_ID)).thenReturn(1);

        itemRelationService.deleteItemRelation(ITEM_ID, ACCOUNT_ID);

        verify(itemRelationRepositoryMock, times(1)).deleteItemRelation(ITEM_ID, ACCOUNT_ID);
    }

}