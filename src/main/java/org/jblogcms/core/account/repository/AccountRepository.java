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

package org.jblogcms.core.account.repository;

import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.common.repository.ItemRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Provides the persistence service for accessing, adding, changing, deleting {@link Account}.
 *
 * @author Victor Andreenko
 */
@Repository
public interface AccountRepository
        extends ItemRepository<Account> {

    @Override
    @CacheEvict(value = "accounts", allEntries = true)
    <S extends Account> S save(S entity);

    @Override
    @CacheEvict(value = "accounts", allEntries = true)
    void delete(Long aLong);

    @Override
    @CacheEvict(value = "accounts", allEntries = true)
    void delete(Account entity);

    /**
     * Returns the account with the primary key, or {@code null} if the account with the primary key wasn't found
     *
     * @param accountId the primary key of the account
     * @return the account or {@code null} if the account with the email wasn't found
     */
    @Cacheable(value = "accounts")
    @Query(value = "select account " +
            "from Account account " +
            "where account.id = :accountId ")
    Account findAccountById(@Param("accountId") long accountId);

    /**
     * Returns the account with the primary key, or {@code null} if the account with the primary key wasn't found
     *
     * @param accountId the primary key of the account
     * @return the account or {@code null} if the account with the email wasn't found
     */
    @Query(value = "select account " +
            "from Account account " +
            "where account.id = :accountId ")
    Account findAccountByIdForEdit(@Param("accountId") long accountId);

    /**
     * Returns the account with the email, or {@code null} if the account with the email wasn't found
     *
     * @param email the email
     * @return the account or {@code null} if the account with the email wasn't found
     */
    @Query(value = "select account " +
            "from Account account " +
            "where account.email = :email ")
    Account findAccountByEmail(@Param("email") String email);

    /**
     * Returns the number of accounts with the email
     *
     * @param email the email
     * @return the number of accounts with the email
     */
    @Query(value = "select count (account) " +
            "from Account account " +
            "where account.email = :email ")
    int countAccountsByEmail(@Param("email") String email);

    /**
     * Returns the number of all accounts with role 'role_admin'
     *
     * @return the number of all accounts with role 'role_admin'
     */
    @Query(value = "select count (account) " +
            "from Account account " +
            "where account.accountRole = 'ROLE_ADMIN'")
    int countAllAccountsWithRoleAdmin();

    /**
     * Returns a {@link Page} of all the  accounts
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable {@code Pageable} object
     * @return a page of the accounts
     */
    @Cacheable(value = "accounts")
    @Query(value = "select account " +
            "from Account account ",
            countQuery = "select count(account) " +
                    "from Account account")
    Page<Account> findAccounts(Pageable pageable);

    /**
     * Returns a {@link Page} of all the favorite accounts of the {@link Account}
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param accountId the primary key of the account
     * @param pageable  {@code Pageable} object
     * @return a page of the favorite accounts
     */
    @Cacheable(value = "accounts")
    @Query(value = "select account " +
            "from Account account " +
            "join account.targetAccountRelations rel " +
            "where rel.account.id = :accountId " +
            "order by rel.id",
            countQuery = "select count(account) " +
                    "from Account account " +
                    "join account.targetAccountRelations rel " +
                    "where rel.account.id = :accountId " +
                    "order by rel.id")
    Page<Account> findFavoriteAccounts(@Param("accountId") long accountId, Pageable pageable);

    /**
     * Returns a {@link Page} of all the subscribers of the {@link Account}
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param accountId the primary key of the account
     * @param pageable  {@code Pageable} object
     * @return a page of subscribers of the account
     */
    @Cacheable(value = "accounts")
    @Query(value = "select account " +
            "from Account account " +
            "join account.accountRelations rel " +
            "where rel.item.id = :accountId " +
            "order by rel.id",
            countQuery = "select count(account) " +
                    "from Account account " +
                    "join account.accountRelations rel " +
                    "where rel.item.id = :accountId " +
                    "order by rel.id")
    Page<Account> findAccountSubscribers(@Param("accountId") long accountId, Pageable pageable);

    /**
     * Returns a {@link Page} of all the subscribers of the blog
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param blogId   the primary key of the blog
     * @param pageable {@code Pageable} object
     * @return a page of subscribers of the blog
     */
    @Query(value = "select account " +
            "from Account account " +
            "join account.blogRelations rel " +
            "where rel.item.id = :blogId " +
            "order by rel.id",
            countQuery = "select count(account)  " +
                    "from Account account " +
                    "join account.blogRelations rel " +
                    "where rel.item.id = :blogId " +
                    "order by rel.id")
    Page<Account> findBlogSubscribers(@Param("blogId") long blogId, Pageable pageable);

    /**
     * Returns a {@link Page} of all the subscribers of the post
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param postId   the primary key of the post
     * @param pageable {@code Pageable} object
     * @return a page of subscribers of the blog
     */
    @Query(value = "select account " +
            "from Account account " +
            "join account.postRelations rel " +
            "where rel.item.id = :postId " +
            "order by rel.id",
            countQuery = "select count(account)  " +
                    "from Account account " +
                    "join account.postRelations rel " +
                    "where rel.item.id = :postId " +
                    "order by rel.id")
    Page<Account> findPostSubscribers(@Param("postId") long postId, Pageable pageable);

    /**
     * Returns a {@link Page} of all the subscribers of the comment
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param commentId the primary key of the comment
     * @param pageable  {@code Pageable} object
     * @return a page of subscribers of the blog
     */
    @Query(value = "select account " +
            "from Account account " +
            "join account.commentRelations rel " +
            "where rel.item.id = :commentId " +
            "order by rel.id",
            countQuery = "select count(account)  " +
                    "from Account account " +
                    "join account.commentRelations rel " +
                    "where rel.item.id = :commentId " +
                    "order by rel.id")
    Page<Account> findCommentSubscribers(@Param("commentId") long commentId, Pageable pageable);

}
