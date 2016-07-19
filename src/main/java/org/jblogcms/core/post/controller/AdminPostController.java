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

package org.jblogcms.core.post.controller;

import org.jblogcms.core.post.model.Post;
import org.jblogcms.core.post.service.PostService;
import org.jblogcms.core.security.service.SecurityService;
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

/**
 * @author Victor Andreenko
 */
@Controller
public class AdminPostController {

    private static final Logger logger =
            LoggerFactory.getLogger(AdminPostController.class);

    private PostService postService;
    private SecurityService securityService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * Shows all the {@link Post}s for admin page
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param model    the {@code Model} object
     * @param pageable the {@code Pageable} object
     * @return logical String-based view name
     */
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @RequestMapping(value = "/admin/posts", method = RequestMethod.GET)
    public String showPosts(@PageableDefault(sort = {"id"}, value = 10, page = 0) Pageable pageable,
                            Model model) {

        Page<Post> posts = postService.findPosts(pageable, null);

        model.addAttribute("posts", posts);
        model.addAttribute("pageable", pageable);
        model.addAttribute("currentUrl", "/admin/posts");
        model.addAttribute("itemsPerPageList", new int[]{3, 10, 50});
        model.addAttribute("pageableSortList",
                new String[]{"id,ASC", "id,DESC", "title,ASC", "title,DESC"});

        return "admin/posts";
    }

    /**
     * Shows all the {@link Post}s for admin page
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param model    the {@code Model} object
     * @param pageable the {@code Pageable} object
     * @return logical String-based view name
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/admin/myposts", method = RequestMethod.GET)
    public String showCurrentAccountPosts(@PageableDefault(sort = {"id"}, value = 10, page = 0) Pageable pageable,
                                          Model model) {

        Long currentAccountId = securityService.getCurrentAccountId();

        Page<Post> posts = postService.findAccountPosts(currentAccountId, pageable, null);

        model.addAttribute("posts", posts);
        model.addAttribute("pageable", pageable);
        model.addAttribute("currentUrl", "/admin/myposts");
        model.addAttribute("itemsPerPageList", new int[]{3, 10, 50});
        model.addAttribute("pageableSortList",
                new String[]{"id,ASC", "id,DESC", "title,ASC", "title,DESC"});

        return "admin/myposts";
    }
}
