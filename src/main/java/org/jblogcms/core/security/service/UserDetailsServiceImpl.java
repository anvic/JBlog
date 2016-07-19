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
import org.jblogcms.core.account.repository.AccountRepository;
import org.jblogcms.core.security.model.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


/**
 * @author Victor Andreenko
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    public UserDetailsServiceImpl() {
    }

    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
   	 * @see UserDetailsService#loadUserByUsername(String)
   	 */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Account account = accountRepository.findAccountByEmail(username);

        if (account == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(account.getAccountRole().toString());
        authorities.add(authority);

        AccountDetails userDetails =
                new AccountDetails(account.getEmail(), account.getPassword(), authorities);
        userDetails.setId(account.getId());
        userDetails.setFirstName(account.getFirstName());
        userDetails.setLastName(account.getLastName());
        userDetails.setSocialSignInProvider(account.getSignInProvider());
        return userDetails;
    }
}
