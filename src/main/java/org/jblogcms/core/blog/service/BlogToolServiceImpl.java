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

package org.jblogcms.core.blog.service;

import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.blog.model.BlogRate;
import org.jblogcms.core.blog.model.BlogRelation;
import org.jblogcms.core.common.service.ItemServiceToolImpl;
import org.springframework.stereotype.Service;


/**
 * @author Victor Andreenko
 */
@Service
public class BlogToolServiceImpl
        extends ItemServiceToolImpl<Blog, BlogRelation, BlogRate>
        implements BlogToolService {

    public BlogToolServiceImpl() {
    }
}
