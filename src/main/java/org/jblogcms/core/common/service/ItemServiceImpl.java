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
import org.jblogcms.core.common.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @param <I>
 * @author Victor Andreenko
 */
public abstract class ItemServiceImpl<I extends JItem>
        implements ItemService<I> {

    private static final Logger logger =
            LoggerFactory.getLogger(ItemServiceImpl.class);

    private final Class<I> itemClass;
    private ItemRepository<I> itemRepository;

    public ItemServiceImpl(Class<I> itemClass) {
        this.itemClass = itemClass;
    }

    @Autowired
    public void setItemRepository(ItemRepository<I> itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void delete(Long id) {
        itemRepository.delete(id);
    }

    @Override
    public I save(I item) {
        return itemRepository.save(item);
    }

}
