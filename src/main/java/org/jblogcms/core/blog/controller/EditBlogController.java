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

import org.jblogcms.core.blog.exception.DuplicateBlogNameException;
import org.jblogcms.core.blog.exception.DuplicateBlogUrlNameException;
import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.blog.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

/**
 * @author Victor Andreenko
 */
@Controller
@SessionAttributes("blog")
public class EditBlogController {

    private static final Logger logger =
            LoggerFactory.getLogger(EditBlogController.class);

    private BlogService blogService;
    private MessageSource messageSource;

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Shows form for updating {@link Blog}
     *
     * @param model the {@code Model} object
     * @return logical String-based view name
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/blog/update/{blogId}", method = RequestMethod.GET)
    public String editBlogSetupForm(
            @PathVariable("blogId") long blogId,
            Model model) {

        Blog blog = blogService.findBlogByIdForEdit(blogId);
        model.addAttribute("blog", blog);

        return "admin/blogEdit";
    }

    /**
     * Submits form for adding new {@link Blog}
     *
     * @param blogForm the blog with updated fields
     * @param result   the {@code BindingResult} object
     * @param status   the {@code SessionStatus} object
     * @param model    the {@code Model} object
     * @return logical String-based view name
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/blog/update/{blogId}", method = RequestMethod.POST)
    public String editBlogSubmitForm(
            @PathVariable("blogId") String blogId,
            @Valid @ModelAttribute("blog") Blog blogForm,
            BindingResult result,
            SessionStatus status,
            Model model) {

        if (result.hasErrors()) {
            return "admin/blogEdit";
        } else {
            Blog blog = editBlog(blogForm, result);
            if (blog == null) {
                return "admin/blogEdit";
            }
            status.setComplete();

            return "redirect:/admin/blogs";
        }
    }

    /**
     * Returns saved blog, if exceptions occurs translates them into {@link org.springframework.validation.FieldError}s
     * and adds those errors to the {@code BindingResult} object
     *
     * @param blogForm the new blog
     * @param result   the {@code BindingResult} object
     * @return the saved blog
     */
    private Blog editBlog(Blog blogForm, BindingResult result) {
        Blog blog = null;
        try {
            blog = blogService.editBlog(blogForm);

        } catch (DuplicateBlogNameException e) {

            String errorCode = messageSource.getMessage(e.getLocalMessage(), null, null);
            FieldError error = new FieldError(
                    "blog",
                    "name",
                    blogForm.getName(),
                    false,
                    new String[]{errorCode},
                    new Object[]{},
                    errorCode
            );
            result.addError(error);

        } catch (DuplicateBlogUrlNameException e) {

            String errorCode = messageSource.getMessage(e.getLocalMessage(), null, null);
            FieldError error = new FieldError(
                    "blog",
                    "urlName",
                    blogForm.getUrlName(),
                    false,
                    new String[]{errorCode},
                    new Object[]{},
                    errorCode
            );
            result.addError(error);

        }
        return blog;
    }
}