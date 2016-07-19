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

package org.jblogcms.core.comment.controller;

import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.account.service.AccountService;
import org.jblogcms.core.comment.model.Comment;
import org.jblogcms.core.comment.service.CommentService;
import org.jblogcms.core.common.controller.AbstractController;
import org.jblogcms.core.security.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Provides controller service to show comments.
 *
 * @author Victor Andreenko
 */
@Controller
public class CommentController extends AbstractController {

    private static final Logger logger =
            LoggerFactory.getLogger(CommentController.class);

    private AccountService accountService;
    private SecurityService securityService;
    private CommentService commentService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Shows all the favorite comments of the account
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param accountId the primary key of the {@link Account}
     * @param model     the {@code Model} object
     * @param pageable  the {@code Pageable} object
     * @return logical String-based view name
     */
    @RequestMapping(value = "/account/{accountId}/favorite/comments", method = RequestMethod.GET)
    public String showFavoriteComments(@PathVariable("accountId") long accountId,
                                       @PageableDefault(sort = {"id"}, value = 10, page = 0) Pageable pageable,
                                       Model model) {

        Long currentAccountId = securityService.getCurrentAccountId();
        Page<Comment> comments = commentService.findFavoriteComments(accountId, pageable, currentAccountId);

        model.addAttribute("account", accountService.findAccountById(accountId, currentAccountId));
        model.addAttribute("comments", comments);
        model.addAttribute("pageable", pageable);
        model.addAttribute("currentUrl", "/account/" + accountId + "/favorite/comments");

        return "user/accountFavComments";
    }

    /**
     * Shows all the comments created by the account
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param accountId the primary key of the {@link Account}
     * @param model     the {@code Model} object
     * @param pageable  the {@code Pageable} object
     * @return logical String-based view name
     */
    @RequestMapping(value = "/account/{accountId}/comments", method = RequestMethod.GET)
    public String showAccountsComments(@PathVariable("accountId") long accountId,
                                       @PageableDefault(sort = {"id"}, value = 10, page = 0) Pageable pageable,
                                       Model model) {

        Long currentAccountId = securityService.getCurrentAccountId();
        Page<Comment> comments = commentService.findAccountComments(accountId, pageable, currentAccountId);

        model.addAttribute("account", accountService.findAccountById(accountId, currentAccountId));
        model.addAttribute("comments", comments);
        model.addAttribute("pageable", pageable);
        model.addAttribute("currentUrl", "/account/" + accountId + "/comments");

        return "user/accountComments";
    }
}
