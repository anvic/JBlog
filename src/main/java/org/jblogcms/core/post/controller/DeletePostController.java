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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Victor Andreenko
 */
@Controller
public class DeletePostController {

    private static final Logger logger =
            LoggerFactory.getLogger(DeletePostController.class);

    private PostService postService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    /**
     * Deletes the {@link Post} with the primary key.
     *
     * @param postId the primary key of the post
     * @return logical String-based view name
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/admin/post/delete/{postId}", method = RequestMethod.GET)
    public String deletePost(
            @PathVariable("postId") long postId) {

        Post post = postService.findPostById(postId, null);
        if (post != null) {
            postService.delete(post);
        }
        return "redirect:/";
    }


}