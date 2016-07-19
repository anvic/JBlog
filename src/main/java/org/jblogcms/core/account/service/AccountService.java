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

package org.jblogcms.core.account.service;

import org.jblogcms.core.account.exception.DuplicateEmailException;
import org.jblogcms.core.account.exception.OldPasswordNotMatchedException;
import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.account.model.ChangePasswordDTO;
import org.jblogcms.core.account.model.NewAccountDTO;
import org.jblogcms.core.common.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Provides the service for accessing, adding, changing, deleting {@link Account}.
 *
 * @author Victor Andreenko
 */
public interface AccountService
        extends ItemService<Account> {

    /**
     * Deletes the account
     *
     * @param account the account
     */
    void delete(Account account);

    /**
     * Returns the number of the account with admin with ROLE_ADMIN
     *
     * @return the number of the account with admin with ROLE_ADMIN
     */
    int countAllAccountsWithRoleAdmin();
    /**
     * Returns a {@link Page} of all the accounts
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable         the {@code Pageable} object
     * @param currentAccountId the primary key of the current {@link Account}
     * @return a page of the favorite accounts
     */
    Page<Account> findAccounts(Pageable pageable, Long currentAccountId);

    /**
     * Returns a {@link Page} of all the favorite accounts
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param accountId        the primary key of the account
     * @param pageable         the {@code Pageable} object
     * @param currentAccountId the primary key of the current {@link Account}
     * @return a page of the favorite accounts
     */
    Page<Account> findFavoriteAccounts(Long accountId, Pageable pageable, Long currentAccountId);

    /**
     * Returns a {@link Page} of all the subscribers of the account
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param accountId        the primary key of the account
     * @param pageable         the {@code Pageable} object
     * @param currentAccountId the primary key of the current {@link Account}
     * @return a page of the subscribers of the account
     */
    Page<Account> findAccountSubscribers(long accountId, Pageable pageable, Long currentAccountId);

    /**
     * Returns a {@link Page} of all the subscribers of the blog
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param blogId           the primary key of the blog
     * @param pageable         the {@code Pageable} object
     * @param currentAccountId the primary key of the current {@link Account}
     * @return a page of the subscribers of the account
     */
    Page<Account> findBlogSubscribers(Long blogId, Pageable pageable, Long currentAccountId);

    /**
     * Returns the account with the primary key.
     *
     * @param accountId        the primary key of the account
     * @param currentAccountId the primary key of the current {@link Account}
     * @return the account
     */
    Account findAccountById(Long accountId, Long currentAccountId);

    /**
     * Returns the account with the primary key.
     *
     * @param accountId        the primary key of the account
     * @return the account
     */
    Account findAccountByIdForEdit(Long accountId);

    /**
     * Changes the account.
     *
     * @param account the account
     * @return the saved account
     */
    Account editAccount(Account account) throws DuplicateEmailException;

    /**
     * Changes the security role of the account.
     *
     * @param accountId the primary key of the Account
     * @param newRole   the new role
     * @return true if operation was successful
     */
    boolean changeAccountRole(Long accountId, String newRole);

    /**
     * Adds new account.
     *
     * @param userAccountData the dto account object
     * @return the persisted account
     * @throws DuplicateEmailException
     */
    Account addAccount(NewAccountDTO userAccountData) throws DuplicateEmailException;

    /**
     * Changes the account's passwords
     *
     * @param changePasswordDTO the dto object
     * @return true if operation was successful
     * @throws OldPasswordNotMatchedException
     */
    boolean changePassword(ChangePasswordDTO changePasswordDTO) throws OldPasswordNotMatchedException;
}
