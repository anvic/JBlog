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
import org.jblogcms.core.post.model.Post;
import org.jblogcms.core.post.model.PostRate;
import org.jblogcms.core.post.model.PostRelation;
import org.jblogcms.core.post.repository.PostRateRepository;
import org.jblogcms.core.post.repository.PostRelationRepository;
import org.jblogcms.core.post.repository.PostRepository;
import org.jblogcms.core.post.service.PostToolServiceImpl;
import org.jblogcms.core.common.model.ItemRate;
import org.jblogcms.core.common.model.ItemRelation;
import org.jblogcms.core.common.repository.ItemRateRepository;
import org.jblogcms.core.common.repository.ItemRelationRepository;
import org.jblogcms.core.common.repository.ItemRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * @author Victor Andreenko
 */
@RunWith(MockitoJUnitRunner.class)
public class PostServiceToolImplTest {

    private static final Long ACCOUNT_ID = 101L;
    private static final Long ACCOUNT_ID_NULL = null;

    private static final Long POST_1_ID = 1L;
    private static final Long POST_2_ID = 2L;
    private static final Long POST_3_ID = 3L;

    private static final int PAGEABLE_PAGE = 0;
    private static final int PAGEABLE_SIZE = 5;
    private static final String PAGEABLE_SORT = "id";

    private Account account = new Account();

    private Post post1 = new Post();
    private Post post2 = new Post();
    private Post post3 = new Post();
    
    private List<Post> postList = Arrays.asList(post1, post2, post3);;
    private List<Post> postEmptyList = new ArrayList<>();

    private Pageable pageable;
    private Page<Post> postPage;
    private Page<Post> postEmptyPage;

    private PostRelation postRelation1 = new PostRelation();
    private PostRelation postRelation3 = new PostRelation();
    
    private List<PostRelation> postRelationList = Arrays.asList(postRelation1, postRelation3);
    private List<PostRelation> postRelationEmptyList = new ArrayList<>();

    private PostRate postRate1 = new PostRate();
    private PostRate postRate3 = new PostRate();
    private List<PostRate> postRateList = Arrays.asList(postRate1, postRate3);
    private List<PostRate> postRateListEmpty = new ArrayList<>();

    @Mock
    private PostRepository itemRepositoryMock;
    @Mock
    private PostRelationRepository itemRelationRepositoryMock;
    @Mock
    private PostRateRepository itemRateRepositoryMock;

    @InjectMocks
    PostToolServiceImpl itemServiceTool = new PostToolServiceImpl();

    @Before
    public void setUp() throws Exception {
        post1.setId(POST_1_ID);
        post2.setId(POST_2_ID);
        post3.setId(POST_3_ID);

        Sort sort = new Sort(PAGEABLE_SORT);
        pageable = new PageRequest(PAGEABLE_PAGE, PAGEABLE_SIZE, sort);
        postPage = new PageImpl(postList, pageable, 3);

        postEmptyPage = new PageImpl(postEmptyList, pageable, 0);

        account.setId(ACCOUNT_ID);

        postRelation1.setAccount(account);
        postRelation1.setItem(post1);

        postRelation3.setAccount(account);
        postRelation3.setItem(post3);

        postRate1.setAccount(account);
        postRate1.setItem(post1);

        postRate3.setAccount(account);
        postRate3.setItem(post3);


    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testAddItemRelationsToItemPage_NotEmptyItems_AccountNotNull() throws Exception {
        when(itemRelationRepositoryMock.getItemRelations(postList, ACCOUNT_ID)).thenReturn(postRelationList);

        itemServiceTool.addItemRelationsToItemPage(postPage, ACCOUNT_ID);

        verify(itemRelationRepositoryMock, times(1)).getItemRelations(postList, ACCOUNT_ID);

        Assert.assertEquals(POST_1_ID, postPage.getContent().get(0).getId());
        Assert.assertNotNull(postPage.getContent().get(0).getCurrentItemRelation());

        Assert.assertEquals(POST_2_ID, postPage.getContent().get(1).getId());
        Assert.assertNull(postPage.getContent().get(1).getCurrentItemRelation());

        Assert.assertEquals(POST_3_ID, postPage.getContent().get(2).getId());
        Assert.assertNotNull(postPage.getContent().get(2).getCurrentItemRelation());
    }

    @Test
    public void testAddItemRelationsToItemPage_NotEmptyItems_AccountNull() throws Exception {
        itemServiceTool.addItemRelationsToItemPage(postPage, ACCOUNT_ID_NULL);

        verifyZeroInteractions(itemRelationRepositoryMock);

        Assert.assertEquals(POST_1_ID, postPage.getContent().get(0).getId());
        Assert.assertNull(postPage.getContent().get(0).getCurrentItemRelation());

        Assert.assertEquals(POST_2_ID, postPage.getContent().get(1).getId());
        Assert.assertNull(postPage.getContent().get(1).getCurrentItemRelation());

        Assert.assertEquals(POST_3_ID, postPage.getContent().get(2).getId());
        Assert.assertNull(postPage.getContent().get(2).getCurrentItemRelation());
    }

    @Test
    public void testAddItemRelationsToItemPage_NullItemsParam_AccountNotNull() throws Exception {
        itemServiceTool.addItemRelationsToItemPage(null, ACCOUNT_ID);

        verifyZeroInteractions(itemRelationRepositoryMock);
    }

    @Test
    public void testAddItemRelationsToItemPage_EmptyItems_AccountNotNull() throws Exception {
        itemServiceTool.addItemRelationsToItemPage(postEmptyPage, ACCOUNT_ID);

        verifyZeroInteractions(itemRelationRepositoryMock);

        Assert.assertEquals(0L, postEmptyPage.getTotalElements());
    }

    @Test
    public void testAddItemRelationsToItemPage_EmptyItems_AccountNull() throws Exception {
        itemServiceTool.addItemRelationsToItemPage(postEmptyPage, ACCOUNT_ID_NULL);

        verifyZeroInteractions(itemRelationRepositoryMock);

        Assert.assertEquals(0L, postEmptyPage.getTotalElements());
    }
    
    @Test
    public void testAddItemRelationsToItemList_NotEmptyItems_AccountNotNull() throws Exception {
        when(itemRelationRepositoryMock.getItemRelations(postList, ACCOUNT_ID)).thenReturn(postRelationList);

        itemServiceTool.addItemRelationsToItemList(postList, ACCOUNT_ID);

        verify(itemRelationRepositoryMock, times(1)).getItemRelations(postList, ACCOUNT_ID);

        Assert.assertEquals(POST_1_ID, postList.get(0).getId());
        Assert.assertNotNull(postList.get(0).getCurrentItemRelation());

        Assert.assertEquals(POST_2_ID, postList.get(1).getId());
        Assert.assertNull(postList.get(1).getCurrentItemRelation());

        Assert.assertEquals(POST_3_ID, postList.get(2).getId());
        Assert.assertNotNull(postList.get(2).getCurrentItemRelation());
    }

    @Test
    public void testAddItemRelationsToItemList_NotEmptyItems_AccountNull() throws Exception {
        itemServiceTool.addItemRelationsToItemList(postList, ACCOUNT_ID_NULL);

        verifyZeroInteractions(itemRelationRepositoryMock);

        Assert.assertEquals(POST_1_ID, postList.get(0).getId());
        Assert.assertNull(postList.get(0).getCurrentItemRelation());

        Assert.assertEquals(POST_2_ID, postList.get(1).getId());
        Assert.assertNull(postList.get(1).getCurrentItemRelation());

        Assert.assertEquals(POST_3_ID, postList.get(2).getId());
        Assert.assertNull(postList.get(2).getCurrentItemRelation());
    }

    @Test
    public void testAddItemRelationsToItemList_NullItemsParam_AccountNotNull() throws Exception {
        itemServiceTool.addItemRelationsToItemList(null, ACCOUNT_ID);

        verifyZeroInteractions(itemRelationRepositoryMock);
    }

    @Test
    public void testAddItemRelationsToItemList_EmptyItems_AccountNotNull() throws Exception {
        itemServiceTool.addItemRelationsToItemList(postEmptyList, ACCOUNT_ID);

        verifyZeroInteractions(itemRelationRepositoryMock);

        Assert.assertEquals(0L, postEmptyList.size());
    }

    @Test
    public void testAddItemRelationsToItemList_EmptyItems_AccountNull() throws Exception {
        itemServiceTool.addItemRelationsToItemList(postEmptyList, ACCOUNT_ID_NULL);

        verifyZeroInteractions(itemRelationRepositoryMock);

        Assert.assertEquals(0L, postEmptyList.size());
    }


    @Test
    public void testAddItemRelationToItem_PostNotNull_AccountNotNull() throws Exception {
        when(itemRelationRepositoryMock.getItemRelation(POST_1_ID, ACCOUNT_ID)).thenReturn(postRelation1);

        itemServiceTool.addItemRelationToItem(post1, ACCOUNT_ID);

        verify(itemRelationRepositoryMock, times(1)).getItemRelation(POST_1_ID, ACCOUNT_ID);

        Assert.assertEquals(POST_1_ID, post1.getId());
        Assert.assertNotNull(post1.getCurrentItemRelation());
    }

    @Test
    public void testAddItemRelationToItem_NullItemParam_AccountNotNull() throws Exception {
        itemServiceTool.addItemRelationToItem(null, ACCOUNT_ID);

        verifyZeroInteractions(itemRelationRepositoryMock);
    }

    @Test
    public void testAddItemRelationToItem_PostNotNull_AccountNull() throws Exception {

        itemServiceTool.addItemRelationToItem(post1, ACCOUNT_ID_NULL);

        verifyZeroInteractions(itemRelationRepositoryMock);

        Assert.assertEquals(POST_1_ID, post1.getId());
        Assert.assertNull(post1.getCurrentItemRelation());
    }

    @Test
    public void testAddItemRatesToItemPage_NotEmptyItems_AccountNotNull() throws Exception {
        when(itemRateRepositoryMock.getItemRates(postList, ACCOUNT_ID)).thenReturn(postRateList);

        itemServiceTool.addItemRatesToItemPage(postPage, ACCOUNT_ID);

        verify(itemRateRepositoryMock, times(1)).getItemRates(postList, ACCOUNT_ID);

        Assert.assertEquals(POST_1_ID, postPage.getContent().get(0).getId());
        Assert.assertNotNull(postPage.getContent().get(0).getCurrentItemRate());

        Assert.assertEquals(POST_2_ID, postPage.getContent().get(1).getId());
        Assert.assertNull(postPage.getContent().get(1).getCurrentItemRate());

        Assert.assertEquals(POST_3_ID, postPage.getContent().get(2).getId());
        Assert.assertNotNull(postPage.getContent().get(2).getCurrentItemRate());
    }

    @Test
    public void testAddItemRatesToItemPage_NotEmptyItems_AccountNull() throws Exception {
        itemServiceTool.addItemRatesToItemPage(postPage, ACCOUNT_ID_NULL);

        verifyZeroInteractions(itemRateRepositoryMock);

        Assert.assertEquals(POST_1_ID, postPage.getContent().get(0).getId());
        Assert.assertNull(postPage.getContent().get(0).getCurrentItemRate());

        Assert.assertEquals(POST_2_ID, postPage.getContent().get(1).getId());
        Assert.assertNull(postPage.getContent().get(1).getCurrentItemRate());

        Assert.assertEquals(POST_3_ID, postPage.getContent().get(2).getId());
        Assert.assertNull(postPage.getContent().get(2).getCurrentItemRate());
    }

    @Test
    public void testAddItemRatesToItemPage_NullItemsParam_AccountNotNull() throws Exception {
        itemServiceTool.addItemRatesToItemPage(null, ACCOUNT_ID);

        verifyZeroInteractions(itemRateRepositoryMock);
    }

    @Test
    public void testAddItemRatesToItemPage_EmptyItems_AccountNotNull() throws Exception {
        itemServiceTool.addItemRatesToItemPage(postEmptyPage, ACCOUNT_ID);

        verifyZeroInteractions(itemRateRepositoryMock);

        Assert.assertEquals(0L, postEmptyPage.getTotalElements());
    }

    @Test
    public void testAddItemRatesToItemPage_EmptyItems_AccountNull() throws Exception {
        itemServiceTool.addItemRatesToItemPage(postEmptyPage, ACCOUNT_ID_NULL);

        verifyZeroInteractions(itemRateRepositoryMock);

        Assert.assertEquals(0L, postEmptyPage.getTotalElements());
    }

    @Test
    public void testAddItemRatesToItemList_NotEmptyItems_AccountNotNull() throws Exception {
        when(itemRateRepositoryMock.getItemRates(postList, ACCOUNT_ID)).thenReturn(postRateList);

        itemServiceTool.addItemRatesToItemList(postList, ACCOUNT_ID);

        verify(itemRateRepositoryMock, times(1)).getItemRates(postList, ACCOUNT_ID);

        Assert.assertEquals(POST_1_ID, postList.get(0).getId());
        Assert.assertNotNull(postList.get(0).getCurrentItemRate());

        Assert.assertEquals(POST_2_ID, postList.get(1).getId());
        Assert.assertNull(postList.get(1).getCurrentItemRate());

        Assert.assertEquals(POST_3_ID, postList.get(2).getId());
        Assert.assertNotNull(postList.get(2).getCurrentItemRate());
    }

    @Test
    public void testAddItemRatesToItemList_NotEmptyItems_AccountNull() throws Exception {
        itemServiceTool.addItemRatesToItemList(postList, ACCOUNT_ID_NULL);

        verifyZeroInteractions(itemRateRepositoryMock);

        Assert.assertEquals(POST_1_ID, postList.get(0).getId());
        Assert.assertNull(postList.get(0).getCurrentItemRate());

        Assert.assertEquals(POST_2_ID, postList.get(1).getId());
        Assert.assertNull(postList.get(1).getCurrentItemRate());

        Assert.assertEquals(POST_3_ID, postList.get(2).getId());
        Assert.assertNull(postList.get(2).getCurrentItemRate());
    }

    @Test
    public void testAddItemRatesToItemList_NullItemsParam_AccountNotNull() throws Exception {
        itemServiceTool.addItemRatesToItemList(null, ACCOUNT_ID);

        verifyNoMoreInteractions(itemRateRepositoryMock);
    }

    @Test
    public void testAddItemRatesToItemList_EmptyItems_AccountNotNull() throws Exception {
        itemServiceTool.addItemRatesToItemList(postEmptyList, ACCOUNT_ID);

        verifyNoMoreInteractions(itemRateRepositoryMock);

        Assert.assertEquals(0L, postEmptyList.size());
    }

    @Test
    public void testAddItemRatesToItemList_EmptyItems_AccountNull() throws Exception {
        itemServiceTool.addItemRatesToItemList(postEmptyList, ACCOUNT_ID_NULL);

        verifyNoMoreInteractions(itemRateRepositoryMock);

        Assert.assertEquals(0L, postEmptyList.size());
    }

    @Test
    public void testAddItemRateToItem_PostNotNull_AccountNotNull() throws Exception {
        when(itemRateRepositoryMock.getItemRate(POST_1_ID, ACCOUNT_ID)).thenReturn(postRate1);

        itemServiceTool.addItemRateToItem(post1, ACCOUNT_ID);

        verify(itemRateRepositoryMock, times(1)).getItemRate(POST_1_ID, ACCOUNT_ID);
        verifyNoMoreInteractions(itemRateRepositoryMock);

        Assert.assertEquals(POST_1_ID, post1.getId());
        Assert.assertNotNull(post1.getCurrentItemRate());
    }

    @Test
    public void testAddItemRateToItem_NullItemParam_AccountNotNull() throws Exception {
        itemServiceTool.addItemRateToItem(null, ACCOUNT_ID);

        verifyNoMoreInteractions(itemRateRepositoryMock);
    }

    @Test
    public void testAddItemRateToItem_PostNotNull_AccountNull() throws Exception {
        itemServiceTool.addItemRateToItem(post1, ACCOUNT_ID_NULL);

        verifyNoMoreInteractions(itemRateRepositoryMock);

        Assert.assertEquals(POST_1_ID, post1.getId());
        Assert.assertNull(post1.getCurrentItemRate());
    }

}