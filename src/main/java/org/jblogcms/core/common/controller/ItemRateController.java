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

import org.jblogcms.core.comment.service.CommentRateService;
import org.jblogcms.core.common.model.ItemRateJson;
import org.jblogcms.core.post.service.PostRateService;
import org.jblogcms.core.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @author Victor Andreenko
 */
@Controller
public class ItemRateController {

    @Value("#{messageSource.getMessage('postRate.rated.label', null, null)}")
    private String postRatedLabel = "Rated";
    @Value("#{messageSource.getMessage('postRate.rated.info', null, null)}")
    private String postRatedInfo = "Rated";

    @Value("#{messageSource.getMessage('commentRate.rated.label', null, null)}")
    private String commentRatedLabel = "Rated";
    @Value("#{messageSource.getMessage('commentRate.rated.info', null, null)}")
    private String commentRatedInfo = "Rated";


    @Value("#{messageSource.getMessage('security.authenticationIsRequired', null, null)}")
    private String authenticationIsRequired;

    private MessageSource messageSource;
    private CommentRateService commentRateService;
    private PostRateService postRateService;

    private SecurityService securityService;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }



    @Autowired
    public void setCommentRateService(CommentRateService commentRateService) {
        this.commentRateService = commentRateService;
    }

    @Autowired
    public void setPostRateService(PostRateService postRateService) {
        this.postRateService = postRateService;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @RequestMapping(value = "/itemRate/add", method = RequestMethod.GET)
    public
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    ItemRateJson addPostRate(@RequestParam(value = "item", required = true) long item,
                             @RequestParam(value = "type", required = true) String type,
                             @RequestParam(value = "state", required = true) String state,
                             @RequestParam(value = "value", required = true) byte value,
                             @RequestParam(value = "rated", required = true) int rated,
                             @RequestParam(value = "ratedup", required = true) int ratedup,
                             @RequestParam(value = "rateddown", required = true) int rateddown,
                             @RequestParam(value = "rating", required = true) int rating) {

        ItemRateJson itemRateJson = new ItemRateJson();
        itemRateJson.setNewState(state);
        String ratedLabel = null;
        String ratedInfo = null;

        Long currentAccountId = securityService.getCurrentAccountId();
        boolean result = false;
        if (currentAccountId != null) {


            if (Objects.equals(type, "comment")) {
                ratedLabel = commentRatedLabel;
                ratedInfo = commentRatedInfo;
                result = commentRateService.saveItemRate(item, value, currentAccountId) != null;

            } else if (Objects.equals(type, "post")) {
                ratedLabel = postRatedLabel;
                ratedInfo = postRatedInfo;
                result = postRateService.saveItemRate(item, value, currentAccountId) != null;
            }
            if (result) {
                itemRateJson.setLabel(ratedLabel);
                itemRateJson.setNewRated(rated + 1);
                itemRateJson.setNewRatedUp(ratedup + ((value == 1) ? 1 : 0));
                itemRateJson.setNewRatedDown(rateddown + ((value == -1) ? 1 : 0));
                itemRateJson.setNewRating(rating + value);
                itemRateJson.setInfo(ratedInfo);
                itemRateJson.setNewState("1");
            }

        } else {
            itemRateJson.setInfo(authenticationIsRequired);
        }
        return itemRateJson;
    }
}