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
import org.jblogcms.core.common.controller.AbstractController;
import org.jblogcms.core.post.service.PostService;
import org.jblogcms.core.security.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Victor Andreenko
 */
@Controller
public class BlogController extends AbstractController {

    private static final Logger logger =
            LoggerFactory.getLogger(BlogController.class);

    private BlogService blogService;
    private PostService postService;
    private SecurityService securityService;

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        Long currentAccountId = securityService.getCurrentAccountId();
        if (currentAccountId != null) {
            return "redirect:/feed";
        }
        return "redirect:/blogs/";
    }

    /**
     * Shows all the blogs meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable the {@code Pageable} object
     * @param model    the {@code Model} object
     * @return logical String-based view name
     */
    @RequestMapping(value = "/blogs", method = RequestMethod.GET)
    public String showBlogs(@PageableDefault(sort = {"name"}, value = 10, page = 0) Pageable pageable,
                            Model model) {
        Long currentAccountId = securityService.getCurrentAccountId();
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageableLastPosts = new PageRequest(0, 10, sort);

        Page<Blog> blogs = blogService.findBlogs(pageable, currentAccountId);

        model.addAttribute("blogs", blogs);
        model.addAttribute("lastPosts", postService.findLastPosts(pageableLastPosts, currentAccountId).getContent());
        model.addAttribute("pageable", pageable);
        model.addAttribute("currentUrl", "/blogs");

        return "user/blogs";
    }

}
