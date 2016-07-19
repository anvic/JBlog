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

package org.jblogcms.core.common.repository;

import org.jblogcms.core.common.model.ItemRate;
import org.jblogcms.core.common.model.JItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @param <I>
 * @param <IRA>
 * @author Victor Andreenko
 */
@NoRepositoryBean
public interface ItemRateRepository<I extends JItem, IRA extends ItemRate>
        extends JpaRepository<IRA, Long> {

    /**
     * Returns all item rates matching {@link JItem}s,
     * and primary key of {@link org.jblogcms.core.account.model.Account}.
     *
     * @param items     the items
     * @param accountId the primary key of account
     * @return all item rates
     */
    @Query("Select ir from #{#entityName} ir " +
            "where ir.item in :items " +
            "and ir.account.id = :accountId")
    List<IRA> getItemRates(@Param("items") Iterable<I> items,
                           @Param("accountId") Long accountId);

    /**
     * Returns  item rate matching the primary key ot the {@link JItem}s,
     * and primary key of {@link org.jblogcms.core.account.model.Account}.
     *
     * @param itemId    the primary key of the item
     * @param accountId the primary key of the account
     * @return all item rates
     */
    @Query("Select ir from #{#entityName} ir " +
            "where ir.item.id = :itemId " +
            "and ir.account.id = :accountId")
    IRA getItemRate(@Param("itemId") Long itemId,
                    @Param("accountId") Long accountId);
}

