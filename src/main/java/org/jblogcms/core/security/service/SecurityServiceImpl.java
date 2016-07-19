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

package org.jblogcms.core.security.service;

import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.account.model.AccountSocialProvider;
import org.jblogcms.core.account.service.AccountService;
import org.jblogcms.core.security.model.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


/**
 * @author Victor Andreenko
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public AccountDetails getCurrentAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            if (!auth.getName().equals("anonymousUser")) {
                return ((AccountDetails) auth.getPrincipal());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long getCurrentAccountId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            if (!auth.getName().equals("anonymousUser")) {
                return ((AccountDetails) auth.getPrincipal()).getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void signIn(Account account) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(account.getAccountRole().toString());
        authorities.add(authority);
        if (account.getPassword() == null) {
            account.setPassword("socialpass");
        }

        AccountDetails userDetails = new AccountDetails(account.getEmail(), account.getPassword(), authorities);
        userDetails.setSocialSignInProvider(account.getSignInProvider());
        userDetails.setId(account.getId());
        userDetails.setLastName(account.getLastName());
        userDetails.setFirstName(account.getFirstName());

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
