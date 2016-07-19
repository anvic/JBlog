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

package org.jblogcms.core.security.model;


import org.jblogcms.core.account.model.AccountRole;
import org.jblogcms.core.account.model.AccountSocialProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;

import java.util.Collection;


/**
 * @author Victor Andreenko
 */
public class AccountDetails extends SocialUser {

    private static final long serialVersionUID = -2132470707462931310L;

    private Long id;

    private String firstName;

    private String lastName;

    private AccountRole accountRole;

    private AccountSocialProvider socialSignInProvider;

    public AccountDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AccountRole getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(AccountRole accountRole) {
        this.accountRole = accountRole;
    }

    public AccountSocialProvider getSocialSignInProvider() {
        return socialSignInProvider;
    }

    public void setSocialSignInProvider(AccountSocialProvider socialSignInProvider) {
        this.socialSignInProvider = socialSignInProvider;
    }
}
