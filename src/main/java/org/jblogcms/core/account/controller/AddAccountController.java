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
import org.jblogcms.core.account.model.AccountSocialProvider;
import org.jblogcms.core.account.model.NewAccountDTO;
import org.jblogcms.core.account.service.AccountService;
import org.jblogcms.core.security.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.Objects;


/**
 * @author Victor Andreenko
 */
@Controller
@SessionAttributes("account")
public class AddAccountController {

    private static final Logger logger = LoggerFactory.getLogger(AddAccountController.class);

    private AccountService accountService;
    private ProviderSignInUtils providerSignInUtils;
    private SecurityService securityService;
    private MessageSource messageSource;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setProviderSignInUtils(ProviderSignInUtils providerSignInUtils) {
        this.providerSignInUtils = providerSignInUtils;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationSetupForm(@RequestParam(value = "role", required = false) String role,
                                        WebRequest request, Model model) {

        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        NewAccountDTO newAccountDTO = new NewAccountDTO();

        if (connection != null) {
            UserProfile socialMediaProfile = connection.fetchUserProfile();
            newAccountDTO.setEmail(socialMediaProfile.getEmail());
            newAccountDTO.setFirstName(socialMediaProfile.getFirstName());
            newAccountDTO.setLastName(socialMediaProfile.getLastName());

            ConnectionKey providerKey = connection.getKey();
            newAccountDTO.setSignInProvider(
                    AccountSocialProvider.valueOf(providerKey.getProviderId().toUpperCase()));
        }
        newAccountDTO.setRole(role);
        model.addAttribute("user", newAccountDTO);

        return "user/signUp";
    }


    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registrationSubmitForm(@Valid @ModelAttribute("user") NewAccountDTO newAccountDTO,
                                         BindingResult result,
                                         WebRequest request) throws DuplicateEmailException {

        if (result.hasErrors()) {
            return "user/signUp";
        }

        Account account = createAccount(newAccountDTO, result);

        if (account == null) {
            return "user/signUp";
        }

        securityService.signIn(account);
        providerSignInUtils.doPostSignUp(account.getEmail(), request);

        return "redirect:/";
    }


    private Account createAccount(NewAccountDTO newAccountDTO, BindingResult result) {

        Account account = null;

        if (!Objects.equals(newAccountDTO.getPassword(), newAccountDTO.getPasswordVerification())) {

            String errorCode = messageSource.getMessage("fieldError.passwordsNotEqual", null, null);
            FieldError error = new FieldError(
                    "user",
                    "passwordVerification",
                    errorCode
            );
            result.addError(error);
            return null;
        }

        if (Objects.equals(newAccountDTO.getRole(), "admin") && accountService.countAllAccountsWithRoleAdmin() > 0) {

            String errorCode = messageSource.getMessage("fieldError.adminAccountExistsAlready", null, null);
            FieldError error = new FieldError(
                    "user",
                    "*",
                    errorCode
            );
            result.addError(error);
            return null;
        }

        try {
            account = accountService.addAccount(newAccountDTO);
        } catch (DuplicateEmailException e) {
            String errorCode = messageSource.getMessage(e.getLocalMessage(), null, null);

            FieldError error = new FieldError(
                    "user",
                    "email",
                    newAccountDTO.getEmail(),
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
