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
import org.jblogcms.core.account.model.AccountRole;
import org.jblogcms.core.account.model.AccountRoleJson;
import org.jblogcms.core.account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminAccountController {

    private static final Logger logger =
            LoggerFactory.getLogger(AdminAccountController.class);

    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Shows all the accounts for admin page meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param model    the {@code Model} object
     * @param pageable the {@code Pageable} object
     * @return logical String-based view name
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/accounts", method = RequestMethod.GET)
    public String showAccounts(@PageableDefault(sort = {"name"}, value = 10, page = 0) Pageable pageable,
                               Model model) {

        Page<Account> accounts = accountService.findAccounts(pageable, null);

        model.addAttribute("accountRoles", AccountRole.values());
        model.addAttribute("accounts", accounts);
        model.addAttribute("pageable", pageable);
        model.addAttribute("currentUrl", "/admin/accounts");
        model.addAttribute("itemsPerPageList", new int[]{3, 10, 50});
        model.addAttribute("pageableSortList",
                new String[]{"id,ASC", "id,DESC", "name,ASC", "name,DESC",
                        "firstName,ASC", "firstName,DESC", "lastName,ASC", "lastName,DESC",
                        "email,ASC", "email,DESC"});

        return "admin/accounts";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/ajax/changeRole", method = RequestMethod.GET)
    public
    @ResponseBody
    AccountRoleJson changeRole(@RequestParam(value = "accountId", required = true) Long accountId,
                               @RequestParam(value = "role", required = true) String role,
                               Model model) {

        AccountRoleJson accountRoleJson = new AccountRoleJson();
        accountRoleJson.setNewState("0");
        if (accountService.changeAccountRole(accountId, role)) {
            accountRoleJson.setNewState("1");
            accountRoleJson.setRole(role);
            accountRoleJson.setInfo("AccountId: " + accountId + "; new Role: " + role);
        }
        return accountRoleJson;
    }
}
