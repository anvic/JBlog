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

import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.account.service.AccountService;
import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.blog.service.BlogService;
import org.jblogcms.core.comment.model.Comment;
import org.jblogcms.core.comment.service.CommentService;
import org.jblogcms.core.common.controller.AbstractController;
import org.jblogcms.core.post.model.Post;
import org.jblogcms.core.post.service.PostService;
import org.jblogcms.core.security.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Victor Andreenko
 */
@Controller
public class PostController extends AbstractController {

    private static final Logger logger =
                LoggerFactory.getLogger(PostController.class);

    private AccountService accountService;
    private BlogService blogService;
    private CommentService commentService;
    private PostService postService;
    private SecurityService securityService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
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
     * Shows all the posts of the blog
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param blogId   the primary key of the {@link Blog}
     * @param pageable the {@code Pageable} object
     * @param model    the {@code Model} object
     * @return logical String-based view name
     */
    @RequestMapping(value = "/blog/{blogId}/posts", method = RequestMethod.GET)
    public String showBlogPosts(@PathVariable("blogId") long blogId,
                                Model model,
                                @PageableDefault(sort = {"id"}, value = 10, page = 0) Pageable pageable) {

        Long currentAccountId = securityService.getCurrentAccountId();


        Blog blog = blogService.findBlogById(blogId, currentAccountId);
        Page<Post> posts = postService.findBlogPosts(blog.getId(), pageable, currentAccountId);

        model.addAttribute("blog", blog);
        model.addAttribute("posts", posts);
        model.addAttribute("pageable", pageable);
        model.addAttribute("currentUrl", "/blog/" + blogId + "/posts");

        return "user/blogPosts";
    }



    /**
     * Shows all the favorite posts of the account
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param accountId the primary key of the {@link Account}
     * @param pageable  the {@code Pageable} object
     * @param model     the {@code Model} object
     * @return logical String-based view name
     */
    @RequestMapping(value = "/account/{accountId}/favorite/posts", method = RequestMethod.GET)
    public String showFavoritePosts(@PathVariable("accountId") long accountId,
                                    @PageableDefault(sort = {"id"}, value = 10, page = 0) Pageable pageable,
                                    Model model) {

        Long currentAccountId = securityService.getCurrentAccountId();
        Page<Post> posts = postService.findFavoritePosts(accountId, pageable, currentAccountId);

        model.addAttribute("account", accountService.findAccountById(accountId, currentAccountId));
        model.addAttribute("posts", posts);
        model.addAttribute("pageable", pageable);
        model.addAttribute("currentUrl", "/account/" + accountId + "/favorite/posts");

        return "user/accountFavPosts";
    }

    /**
     * Shows all the feed posts of the account
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable the {@code Pageable} object
     * @param model    the {@code Model} object
     * @return logical String-based view name
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/feed", method = RequestMethod.GET)
    public String showFeed(Model model,
                           @PageableDefault(sort = {"id"}, value = 10, page = 0) Pageable pageable) {

        Long currentAccountId = securityService.getCurrentAccountId();
        List<Blog> blogs = blogService.findBlogList(currentAccountId);
        Page<Post> posts = postService.findFeedPosts(pageable, currentAccountId);

        model.addAttribute("blogs", blogs);
        model.addAttribute("posts", posts);
        model.addAttribute("pageable", pageable);
        model.addAttribute("currentUrl", "/feed");

        return "user/feed";
    }

    /**
     * Shows all the posts of the account
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param accountId the primary key of the {@link Account}
     * @param pageable  the {@code Pageable} object
     * @param model     the {@code Model} object
     * @return logical String-based view name
     */
    @RequestMapping(value = "/account/{accountId}/posts", method = RequestMethod.GET)
    public String showAccountPosts(@PathVariable("accountId") long accountId,
                                   @PageableDefault(sort = {"id"}, value = 10, page = 0) Pageable pageable,
                                   Model model) {

        Long currentAccountId = securityService.getCurrentAccountId();
        Page<Post> posts = postService.findAccountPosts(accountId, pageable, currentAccountId);

        model.addAttribute("account", accountService.findAccountById(accountId, currentAccountId));
        model.addAttribute("posts", posts);
        model.addAttribute("pageable", pageable);
        model.addAttribute("currentUrl", "/account/" + accountId + "/posts");

        return "user/accountPosts";
    }

    /**
     * Show the post with comments
     *
     * @param postId the primary key of the post
     * @param model  the {@code Model} object
     * @return logical String-based view name
     */
    @RequestMapping(value = "/post/{postId}", method = RequestMethod.GET)
    public String showPost(@PathVariable("postId") long postId,
                           Model model) {

        Long currentAccountId = securityService.getCurrentAccountId();
        Post post = postService.findPostById(postId, currentAccountId);

        Pageable pageable1 = new PageRequest(0, 10);
//        model.addAttribute("similarPosts",
//                postSolrService.findPosts(post.getTitle(), pageable1, currentAccountId));

        model.addAttribute("account", accountService.findAccountById(post.getAccount().getId(), currentAccountId));
        model.addAttribute("post", post);
        model.addAttribute("comments", commentService.findPostComments(postId, currentAccountId));
        model.addAttribute("comment", new Comment());

        return "user/post";
    }

    @RequestMapping(value = "/post/{postId}", method = RequestMethod.POST)
    public String addComment(@PathVariable("postId") long postId,
                             @Valid @ModelAttribute("comment") Comment comment,
                             BindingResult result,
                             SessionStatus status,
                             Model model) {

        Long currentAccountId = securityService.getCurrentAccountId();
        commentService.addComment(comment, currentAccountId);

        return "redirect:/post/"+postId;
    }
}
