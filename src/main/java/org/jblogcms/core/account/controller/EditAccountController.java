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

import org.jblogcms.core.account.exception.DuplicateEmailException;
import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.account.service.AccountService;
import org.jblogcms.core.security.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

/**
 * @author Victor Andreenko
 */
@Controller
@SessionAttributes("account")
public class EditAccountController {

    private static final Logger logger =
                		LoggerFactory.getLogger(EditAccountController.class);

    private AccountService accountService;
    private SecurityService securityService;
    private MessageSource messageSource;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Shows form for updating {@link Account}
     *
     * @param model the {@code Model} object
     * @return logical String-based view name
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/admin/editProfile", method = RequestMethod.GET)
    public String editAccountSetupForm(
            Model model) {
        Long currentAccountId = securityService.getCurrentAccountId();

        Account account = accountService.findAccountByIdForEdit(currentAccountId);
        model.addAttribute("account", account);

        return "admin/accountEdit";
    }

    /**
     * Submits form for adding new {@link Account}
     *
     * @param accountForm the account with updated fields
     * @param result      the {@code BindingResult} object
     * @param status      the {@code SessionStatus} object
     * @param model       the {@code Model} object
     * @return logical String-based view name
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/admin/editProfile", method = RequestMethod.POST)
    public String editAccountSubmitForm(
            @Valid @ModelAttribute("account") Account accountForm,
            BindingResult result,
            SessionStatus status,
            Model model) {

        if (result.hasErrors()) {
            return "admin/accountEdit";
        } else {
            Account account = editAccount(accountForm, result);
            if (account == null) {
                return "admin/accountEdit";
            }
            status.setComplete();

            return "redirect:/";
        }
    }

    /**
     * Returns saved account, if exceptions occurs translates them into {@link org.springframework.validation.FieldError}s
     * and adds those errors to the {@code BindingResult} object
     *
     * @param accountForm the new account
     * @param result      the {@code BindingResult} object
     * @return the saved account
     */
    private Account editAccount(Account accountForm, BindingResult result) {
        Account account = null;

        try {
            account = accountService.editAccount(accountForm);
        } catch (DuplicateEmailException e) {
            String errorCode = messageSource.getMessage(e.getLocalMessage(), null, null);

            FieldError error = new FieldError(
                    "user",
                    "email",
                    accountForm.getEmail(),
                    false,
                    new String[]{errorCode},
                    new Object[]{},
                    errorCode
            );
            result.addError(error);
        }
        return account;
    }


}