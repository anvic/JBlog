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

package org.jblogcms.core.blog.converter;

import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.blog.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

/**
 * @author Victor Andreenko
 */
@Service
public class StringToBlogConverter implements Converter<String, Blog> {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog convert(String s) {
        Long blogId = Long.parseLong(s);
        return blogRepository.findOne(blogId);
    }
}
