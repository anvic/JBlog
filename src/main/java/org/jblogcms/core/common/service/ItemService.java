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

package org.jblogcms.core.common.service;


import org.jblogcms.core.common.model.JItem;


public interface ItemService<I extends JItem> {
    /**
     * Deletes the entity which extends {@link JItem} with the primary key.
     *
     * @param id the primary key of the item, must not be {@literal null}
     * @throws IllegalArgumentException if the {@code id} is {@literal null}
     */
    void delete(Long id);

//    /**
//     * Returns the item with the primary key.
//     *
//     * @param id the primary key of the item, must not be {@literal null}
//     * @return the entity with the primary key or {@literal null} if none found
//     * @throws IllegalArgumentException if the {@code id} is {@literal null}
//     */
//    I findOne(Long id);

    I save(I item);

}
