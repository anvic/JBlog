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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;


@Controller
@SessionAttributes("post")
public class EditPostController {

    private static final Logger logger =
                LoggerFactory.getLogger(EditPostController.class);

    private BlogService blogService;
    private PostService postService;

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    /**
     * Shows form for updating {@link Post}
     *
     * @param model the {@code Model} object
     * @return logical String-based view name
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/admin/post/update/{postId}", method = RequestMethod.GET)
    public String editPostSetupForm(
            @PathVariable("postId") long postId,
            Model model){

        Post post = postService.findPostByIdForEdit(postId);
        model.addAttribute("post", post);
        model.addAttribute("allBlogs", blogService.findBlogList(null));

        return "admin/postEdit";
    }


    /**
     * Submits form for adding new {@link Post}
     *
     * @param postForm the post with updated fields
     * @param result   the {@code BindingResult} object
     * @param status   the {@code SessionStatus} object
     * @param model    the {@code Model} object
     * @return logical String-based view name
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/admin/post/update/{postId}", method = RequestMethod.POST)
    public String editPostSubmitForm(
            @PathVariable("postId") long postId,
            @Valid @ModelAttribute("post") Post postForm,
            BindingResult result,
            SessionStatus status,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("allBlogs", blogService.findBlogList(null));

            return "admin/postEdit";
        } else {
            Post post = editPost(postForm, result);
            if (post == null) {
                model.addAttribute("allBlogs", blogService.findBlogList(null));
                return "admin/postEdit";
            }
            status.setComplete();

            return "redirect:/";
        }
    }

    /**
     * Returns saved post, if exceptions occurs translates them into {@link org.springframework.validation.FieldError}s
     * and adds those errors to the {@code BindingResult} object
     *
     * @param postForm the new post
     * @param result   the {@code BindingResult} object
     * @return the saved post
     */

    private Post editPost(Post postForm, BindingResult result){
        return postService.editPost(postForm);
    }
}
