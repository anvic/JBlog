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

package org.jblogcms.core.blog.service;

import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.account.repository.AccountRepository;
import org.jblogcms.core.blog.exception.DuplicateBlogNameException;
import org.jblogcms.core.blog.exception.DuplicateBlogUrlNameException;
import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.blog.repository.BlogRepository;
import org.jblogcms.core.common.service.ItemServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Victor Andreenko
 */
@Service
public class BlogServiceImpl
        extends ItemServiceImpl<Blog>
        implements BlogService {

    private static final Logger logger =
            LoggerFactory.getLogger(BlogServiceImpl.class);


    private BlogRepository blogRepository;
    private BlogToolService blogToolService;
    private AccountRepository accountRepository;

    public BlogServiceImpl() {
        super(Blog.class);
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setBlogRepository(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Autowired
    public void setBlogToolService(BlogToolService blogToolService) {
        this.blogToolService = blogToolService;
    }

    @Override
    @Transactional
    public void delete(Long blogId) {
        blogRepository.delete(blogId);
    }

    @Override
    @Transactional
    public void delete(Blog blog) {
       blogRepository.delete(blog);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Blog> findBlogs(Pageable pageable, Long currentAccountId) {
        Page<Blog> blogs = blogRepository.findBlogs(pageable);
        blogs = blogToolService.addItemRelationsToItemPage(blogs, currentAccountId);

        return blogs;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Blog> findFavoriteBlogs(Long accountId, Pageable pageable, Long currentAccountId) {
        Page<Blog> blogs = blogRepository.findFavoriteBlogs(accountId, pageable);
        blogs = blogToolService.addItemRelationsToItemPage(blogs, currentAccountId);

        return blogs;
    }

    @Override
    @Transactional(readOnly = true)
    public Blog findBlogById(Long blogId, Long currentAccountId) {
        Blog blog = blogRepository.findBlogById(blogId);
        blog = blogToolService.addItemRelationToItem(blog, currentAccountId);

        return blog;
    }

    @Override
    @Transactional(readOnly = true)
    public Blog findBlogByIdForEdit(Long blogId) {
        return blogRepository.findBlogByIdForEdit(blogId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Blog> findBlogList(Long currentAccountId) {
        List<Blog> blogs = blogRepository.findAll();
        blogs = blogToolService.addItemRelationsToItemList(blogs, currentAccountId);

        return blogs;
    }

    @Override
    @Transactional
    public Blog addBlog(Blog blog, Long currentAccountId)
            throws DuplicateBlogNameException, DuplicateBlogUrlNameException {

        if (!isBlogNameUnique(blog)) {
            throw new DuplicateBlogNameException();
        }
        if (!isBlogUrlNameUnique(blog)) {
            throw new DuplicateBlogUrlNameException();
        }
        Account account = accountRepository.getOne(currentAccountId);
        blog.setAccount(account);

        return blogRepository.save(blog);
    }

    @Override
    @Transactional
    public Blog editBlog(Blog blog) throws DuplicateBlogNameException, DuplicateBlogUrlNameException {

        if (!isBlogNameUnique(blog) &&
                !blogRepository.findOne(blog.getId()).getName().equals(blog.getName())) {
            throw new DuplicateBlogNameException();
        }
        if (!isBlogUrlNameUnique(blog) &&
                !blogRepository.findOne(blog.getId()).getUrlName().equals(blog.getUrlName())) {
            throw new DuplicateBlogUrlNameException();
        }

        return blogRepository.save(blog);
    }

    private boolean isBlogNameUnique(Blog blog) {
        return (blogRepository.countBlogsByName(blog.getName())) == 0;
    }

    private boolean isBlogUrlNameUnique(Blog blog) {
        return (blogRepository.countBlogsByUrlName(blog.getUrlName())) == 0;
    }


}
