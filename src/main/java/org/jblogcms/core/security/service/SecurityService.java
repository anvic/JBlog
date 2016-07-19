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
import org.jblogcms.core.security.model.AccountDetails;

/**
 * Provides service for site security.
 *
 * @author Victor Andreenko
 */
public interface SecurityService {

    /**
     * Returns the current {@link Account}
     *
     * @return the current {@link Account}
     */
    AccountDetails getCurrentAccount();

    /**
     * Returns the primary key of the current {@link Account}
     *
     * @return the primary key of the current {@link Account}
     */
     Long getCurrentAccountId();

    /**
     * Signs In the account.
     *
     * @param account the account
     */
    void signIn(Account account);
}
