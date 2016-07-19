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

import org.jblogcms.core.blog.service.BlogService;
import org.jblogcms.core.post.model.Post;
import org.jblogcms.core.post.service.PostService;
import org.jblogcms.core.security.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

/**
 * @author Victor Andreenko
 */
@Controller
@SessionAttributes("post")
public class AddPostController {

    private static final Logger logger =
                LoggerFactory.getLogger(AddPostController.class);

    private BlogService blogService;
    private MessageSource messageSource;
    private PostService postService;
    private SecurityService securityService;

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * Shows form for adding new post
     *
     * @param model the {@code Model} object
     * @return logical String-based view name
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/post/add", method = RequestMethod.GET)
    public String addPostSetupForm(Model model) {

        Post post = new Post();
        post.prePersist();

        model.addAttribute("post", post);
        model.addAttribute("allBlogs", blogService.findBlogList(null));

        return "admin/postAdd";
    }

    /**
     * Submits form for adding new post
     *
     * @param postForm the new post
     * @param result   the {@code BindingResult} object
     * @param status   the {@code SessionStatus} object
     * @param model    the {@code Model} object
     * @return logical String-based view name
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/post/add", method = RequestMethod.POST)
    public String addPostSubmitForm(
            @Valid @ModelAttribute("post") Post postForm,
            BindingResult result,
            SessionStatus status,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("allBlogs", blogService.findBlogList(null));

            return "admin/postAdd";
        } else {
            Long currentAccountId = securityService.getCurrentAccountId();
            Post post = addPost(postForm, currentAccountId, result);
            if (post == null) {
                model.addAttribute("allBlogs", blogService.findBlogList(null));
                return "admin/postAdd";
            }
            status.setComplete();

            return "redirect:/";
        }
    }

    /**
     * Returns saved post, if exceptions occurs translates them into {@link org.springframework.validation.FieldError}s
     * and adds those errors to the {@code BindingResult}
     *
     * @param postForm         the new post
     * @param currentAccountId the primary key of the account, post creator
     * @param result           the {@code BindingResult} object
     * @return the saved post
     */
    private Post addPost(Post postForm, Long currentAccountId, BindingResult result) {
        return postService.addPost(postForm, currentAccountId);
    }
}
