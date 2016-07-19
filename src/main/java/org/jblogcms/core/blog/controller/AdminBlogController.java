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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminBlogController {

    private static final Logger logger =
                		LoggerFactory.getLogger(AdminBlogController.class);

    private BlogService blogService;

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * Shows all the {@link Blog} for admin page
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param model    the {@code Model} object
     * @param pageable the {@code Pageable} object
     * @return logical String-based view name
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/blogs", method = RequestMethod.GET)
    public String showBlogs(@PageableDefault(sort = {"name"}, value = 10, page = 0) Pageable pageable, Model model) {

        Page<Blog> blogs = blogService.findBlogs(pageable, null);

        model.addAttribute("blogs", blogs);
        model.addAttribute("pageable", pageable);
        model.addAttribute("currentUrl", "/admin/blogs");
        model.addAttribute("itemsPerPageList", new int[]{3,10,50});
        model.addAttribute("pageableSortList", new String[]{"id,ASC", "id,DESC", "name,ASC",
                "name,DESC", "urlName,ASC", "urlName,DESC"});

        return "admin/blogs";
    }
}
