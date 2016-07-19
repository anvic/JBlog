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

package org.jblogcms.core.common.controller;

import org.jblogcms.core.account.service.AccountRelationService;
import org.jblogcms.core.blog.service.BlogRelationService;
import org.jblogcms.core.comment.service.CommentRelationService;
import org.jblogcms.core.common.model.ItemRelationJson;
import org.jblogcms.core.post.service.PostRelationService;
import org.jblogcms.core.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author Victor Andreenko
 */
@RestController
public class ItemRelationController {

    @Value("#{messageSource.getMessage('accountRelation.subscribed.label', null, null)}")
    private String accountSubscribedLabel;
    @Value("#{messageSource.getMessage('accountRelation.subscribed.info', null, null)}")
    private String accountSubscribedInfo;
    @Value("#{messageSource.getMessage('accountRelation.unsubscribed.label', null, null)}")
    private String accountUnsubscribedLabel;
    @Value("#{messageSource.getMessage('accountRelation.unsubscribed.info', null, null)}")
    private String accountUnsubscribedInfo;

    @Value("#{messageSource.getMessage('blogRelation.subscribed.label', null, null)}")
    private String blogSubscribedLabel;
    @Value("#{messageSource.getMessage('blogRelation.subscribed.info', null, null)}")
    private String blogSubscribedInfo;
    @Value("#{messageSource.getMessage('blogRelation.unsubscribed.label', null, null)}")
    private String blogUnsubscribedLabel;
    @Value("#{messageSource.getMessage('blogRelation.unsubscribed.info', null, null)}")
    private String blogUnsubscribedInfo;

    @Value("#{messageSource.getMessage('postRelation.subscribed.label', null, null)}")
    private String postSubscribedLabel;
    @Value("#{messageSource.getMessage('postRelation.subscribed.info', null, null)}")
    private String postSubscribedInfo;
    @Value("#{messageSource.getMessage('postRelation.unsubscribed.label', null, null)}")
    private String postUnsubscribedLabel;
    @Value("#{messageSource.getMessage('postRelation.unsubscribed.info', null, null)}")
    private String postUnsubscribedInfo;

    @Value("#{messageSource.getMessage('commentRelation.subscribed.label', null, null)}")
    private String commentSubscribedLabel;
    @Value("#{messageSource.getMessage('commentRelation.subscribed.info', null, null)}")
    private String commentSubscribedInfo;
    @Value("#{messageSource.getMessage('commentRelation.unsubscribed.label', null, null)}")
    private String commentUnsubscribedLabel;
    @Value("#{messageSource.getMessage('commentRelation.unsubscribed.info', null, null)}")
    private String commentUnsubscribedInfo;

    @Value("#{messageSource.getMessage('security.authenticationIsRequired', null, null)}")
    private String authenticationIsRequired;

    private AccountRelationService accountRelationService;
    private BlogRelationService blogRelationService;
    private PostRelationService postRelationService;
    private CommentRelationService commentRelationService;

    private MessageSource messageSource;
    private SecurityService securityService;

    @Autowired
    public void setAccountRelationService(AccountRelationService accountRelationService) {
        this.accountRelationService = accountRelationService;
    }

    @Autowired
    public void setBlogRelationService(BlogRelationService blogRelationService) {
        this.blogRelationService = blogRelationService;
    }

    @Autowired
    public void setPostRelationService(PostRelationService postRelationService) {
        this.postRelationService = postRelationService;
    }

    @Autowired
    public void setCommentRelationService(CommentRelationService commentRelationService) {
        this.commentRelationService = commentRelationService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * Adds the item relation.
     *
     * @param item  the primary key of the item
     * @param type  the type(account, blog, post, comment) of the item
     * @param state the current state of the item relation
     * @return the object for the request
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/itemRelation/add.json", method = RequestMethod.POST)
    @ResponseBody
    public ItemRelationJson addItemRelation(@RequestParam(value = "item", required = true) long item,
                                            @RequestParam(value = "type", required = true) String type,
                                            @RequestParam(value = "state", required = true) String state,
                                            @RequestParam(value = "subscribers", required = true) int subscribers) {

        ItemRelationJson itemRelationJson = new ItemRelationJson();
        itemRelationJson.setNewState(state);
        Long currentAccountId = securityService.getCurrentAccountId();

        String subscribedLabel = null;
        String subscribedInfo = null;
        boolean result = false;

        if (currentAccountId != null) {

            if (Objects.equals(type, "account")) {
                subscribedLabel = accountSubscribedLabel;
                subscribedInfo = accountSubscribedInfo;
                result = accountRelationService.saveItemRelation(item, currentAccountId) != null;

            } else if (Objects.equals(type, "blog")) {
                subscribedLabel = blogSubscribedLabel;
                subscribedInfo = blogSubscribedInfo;
                result = blogRelationService.saveItemRelation(item, currentAccountId) != null;

            } else if (Objects.equals(type, "post")) {
                subscribedLabel = postSubscribedLabel;
                subscribedInfo = postSubscribedInfo;
                result = postRelationService.saveItemRelation(item, currentAccountId) != null;

            } else if (Objects.equals(type, "comment")) {
                subscribedLabel = commentSubscribedLabel;
                subscribedInfo = commentSubscribedInfo;
                result = commentRelationService.saveItemRelation(item, currentAccountId) != null;
            }
            if (result) {
                itemRelationJson.setNewState("1");
                itemRelationJson.setLabel(subscribedLabel);
                itemRelationJson.setInfo(subscribedInfo);
                itemRelationJson.setNewSubscribers(subscribers + 1);
            }
        } else {
            itemRelationJson.setInfo(authenticationIsRequired);
        }
        return itemRelationJson;
    }

    /**
     * Deletes the item relation.
     *
     * @param item  the primary key of the item
     * @param type  the type(account, blog, post, comment) of the item
     * @param state the current state of the item relation
     * @return the object for the request
     */
    @RequestMapping(value = "/itemRelation/delete.json", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public ItemRelationJson deleteItemRelation(@RequestParam(value = "item", required = true) long item,
                                               @RequestParam(value = "type", required = true) String type,
                                               @RequestParam(value = "state", required = true) String state,
                                               @RequestParam(value = "subscribers", required = true) int subscribers) {

        ItemRelationJson itemRelationJson = new ItemRelationJson();
        itemRelationJson.setNewState(state);

        Long currentAccountId = securityService.getCurrentAccountId();
        String unsubscribedLabel = null;
        String unsubscribedInfo = null;

        boolean result = false;
        if (currentAccountId != null) {

            if (Objects.equals(type, "account")) {
                unsubscribedLabel = accountUnsubscribedLabel;
                unsubscribedInfo = accountUnsubscribedInfo;
                result = accountRelationService.deleteItemRelation(item, currentAccountId);

            } else if (Objects.equals(type, "blog")) {
                unsubscribedLabel = blogUnsubscribedLabel;
                unsubscribedInfo = blogUnsubscribedInfo;
                result = blogRelationService.deleteItemRelation(item, currentAccountId);

            } else if (Objects.equals(type, "post")) {
                unsubscribedLabel = postUnsubscribedLabel;
                unsubscribedInfo = postUnsubscribedInfo;
                result = postRelationService.deleteItemRelation(item, currentAccountId);

            } else if (Objects.equals(type, "comment")) {
                unsubscribedLabel = commentUnsubscribedLabel;
                unsubscribedInfo = commentUnsubscribedInfo;
                result = commentRelationService.deleteItemRelation(item, currentAccountId);

            }
            if (result) {
                itemRelationJson.setNewState("0");
                itemRelationJson.setLabel(unsubscribedLabel);
                itemRelationJson.setInfo(unsubscribedInfo);
                itemRelationJson.setNewSubscribers(subscribers - 1);
            }
        } else {
            itemRelationJson.setInfo(authenticationIsRequired);
        }
        return itemRelationJson;
    }

}