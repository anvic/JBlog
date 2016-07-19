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
import org.jblogcms.core.account.exception.OldPasswordNotMatchedException;
import org.jblogcms.core.account.model.ChangePasswordDTO;
import org.jblogcms.core.account.service.AccountService;
import org.jblogcms.core.security.model.AccountDetails;
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
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.Objects;

/**
 * @author Victor Andreenko
 */
@Controller
public class ChangePasswordController {

    private static final Logger logger =
            LoggerFactory.getLogger(ChangePasswordController.class);

    private AccountService accountService;
    private MessageSource messageSource;
    private SecurityService securityService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PreAuthorize("hasRole('ROLE_USER') and principal.socialSignInProvider==null")
    @RequestMapping(value = "/admin/changePassword", method = RequestMethod.GET)
    public String changePasswordSetupForm(WebRequest request, Model model) {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        model.addAttribute("changePasswordDTO", changePasswordDTO);

        return "user/changePassword";
    }

    @PreAuthorize("hasRole('ROLE_USER') and principal.socialSignInProvider==null")
    @RequestMapping(value = "/admin/changePassword", method = RequestMethod.POST)
    public String changePasswordSubmitForm(@Valid @ModelAttribute("changePasswordDTO") ChangePasswordDTO changePasswordDTO,
                                           BindingResult result,
                                           WebRequest request) throws DuplicateEmailException {

        if (result.hasErrors()) {
            return "user/changePassword";
        }
        changePasswordDTO.setAccountId(securityService.getCurrentAccountId());


        if (!changePassword(changePasswordDTO, result)) {
            return "user/changePassword";
        }

        return "redirect:/";
    }


    private boolean changePassword(ChangePasswordDTO changePasswordDTO, BindingResult result) {

        if (!Objects.equals(changePasswordDTO.getPassword(), changePasswordDTO.getPasswordVerification())) {

            String errorCode = messageSource.getMessage("fieldError.passwordsNotEqual", null, null);
            FieldError error = new FieldError(
                    "changePasswordDTO",
                    "passwordVerification",
                    errorCode
            );
            result.addError(error);
            return false;
        }

        try {
            accountService.changePassword(changePasswordDTO);
        } catch (OldPasswordNotMatchedException e) {
            String errorCode = messageSource.getMessage("exception.oldPasswordNotMatched", null, null);
            FieldError error = new FieldError(
                    "changePasswordDTO",
                    "oldPassword",
                    errorCode
            );
            result.addError(error);
            return false;
        }
        return true;
    }


}
