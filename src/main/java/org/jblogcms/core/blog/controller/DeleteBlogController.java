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

package org.jblogcms.core.blog.controller;

import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.blog.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DeleteBlogController {

    private static final Logger logger =
            LoggerFactory.getLogger(DeleteBlogController.class);

    private BlogService blogService;

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * Deletes the {@link Blog} with the primary key.
     *
     * @param blogId the primary key of the blog
     * @return logical String-based view name
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/blog/delete/{blogId}", method = RequestMethod.GET)
    public String deleteBlog(
            @PathVariable("blogId") long blogId) {

        Blog blog = blogService.findBlogById(blogId, null);
        if (blog != null) {
            blogService.delete(blog);
        }
        return "redirect:/admin/blogs";
    }


}