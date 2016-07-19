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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author Victor Andreenko
 */
@Controller
public class DeleteAccountController {

    private static final Logger logger =
            LoggerFactory.getLogger(DeleteAccountController.class);

    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Deletes the account with the primary key.
     *
     * @param accountId the primary key of the account
     * @return logical String-based view name
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/account/delete/{accountId}", method = RequestMethod.GET)
    public String deleteUser(
            @PathVariable("accountId") long accountId) {

        Account account = accountService.findAccountById(accountId, null);
        if (account != null) {
            accountService.delete(accountId);
        }
        return "admin/accounts";
    }
}
