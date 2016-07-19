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

package org.jblogcms.core.account.controller;

import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.account.service.AccountService;
import org.jblogcms.core.blog.service.BlogService;
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
 * @author Victor Andreenko
 */
@Controller
public class AccountController extends AbstractController {

    private static final Logger logger =
            LoggerFactory.getLogger(AccountController.class);

    private AccountService accountService;
    private BlogService blogService;
    private SecurityService securityService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * Shows the subscribers of the account
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param accountId the primary key of the account
     * @param model     the {@code Model} object
     * @param pageable  the {@code Pageable} object
     * @return logical String-based view name
     */
    @RequestMapping(value = "/account/{accountId}/subscribers", method = RequestMethod.GET)
    public String showAccountSubscribers(@PathVariable("accountId") long accountId,
                                         Model model, Pageable pageable) {

        Long currentAccountId = securityService.getCurrentAccountId();
        Page<Account> accounts = accountService.findAccountSubscribers(accountId, pageable, currentAccountId);

        model.addAttribute("account", accountService.findAccountById(accountId, currentAccountId));
        model.addAttribute("accounts", accounts);
        model.addAttribute("pageable", pageable);
        model.addAttribute("currentUrl", "/account/" + accountId + "/subscribers");

        return "user/accountSubscribers";
    }

    /**
     * Shows the subscribers of the blog
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param blogId   the primary key of the account
     * @param model    the {@code Model} object
     * @param pageable the {@code Pageable} object
     * @return logical String-based view name
     */
    @RequestMapping(value = "/blog/{blogId}/subscribers", method = RequestMethod.GET)
    public String showBlogSubscribers(@PathVariable("blogId") long blogId,
                                      Model model, Pageable pageable) {

        Long currentAccountId = securityService.getCurrentAccountId();
        Page<Account> accounts = accountService.findBlogSubscribers(blogId, pageable, currentAccountId);

        model.addAttribute("blog", blogService.findBlogById(blogId, currentAccountId));
        model.addAttribute("accounts", accounts);
        model.addAttribute("pageable", pageable);
        model.addAttribute("currentUrl", "/blog/" + blogId + "/subscribers");

        return "user/blogSubscribers";
    }

    /**
     * Shows all the accounts meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param model    the {@code Model} object
     * @param pageable the {@code Pageable} object
     * @return logical String-based view name
     */
    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public String showAccounts(@PageableDefault(sort = {"name"}, value = 10, page = 0) Pageable pageable,
                               Model model) {

        Long currentAccountId = securityService.getCurrentAccountId();
        Page<Account> accounts = accountService.findAccounts(pageable, currentAccountId);

        model.addAttribute("accounts", accounts);
        model.addAttribute("pageable", pageable);
        model.addAttribute("currentUrl", "/accounts");

        return "user/accounts";
    }

}
