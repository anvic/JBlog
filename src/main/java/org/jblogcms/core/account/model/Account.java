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

package org.jblogcms.core.account.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.blog.model.BlogRelation;
import org.jblogcms.core.comment.model.Comment;
import org.jblogcms.core.comment.model.CommentRelation;
import org.jblogcms.core.common.model.JItem;
import org.jblogcms.core.post.model.Post;
import org.jblogcms.core.post.model.PostRelation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Provides the base model interface for the Account service.
 * Represents a row in the <i>account</i> database table,
 * with each column mapped to a property of this class.
 *
 * @author Victor Andreenko
 */
@Entity
@Table(name = "account")
@DynamicInsert
@DynamicUpdate
public class Account extends JItem implements Serializable {


    private static final long serialVersionUID = -8219109973526048961L;

    /**
     * the key of the activation
     */
    @Column(name = "activation_key")
    private String activationKey;

    /**
     * the name
     */
    @Column(name = "name")
    private String name;

    /**
     * the password
     */
    @Column(name = "password")
    private String password;

    /**
     * the registration email
     */
    @Email
    @NotBlank
    @Column(name = "email", unique = true)
    private String email;

    /**
     * the registration date
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date")
    private Date registrationDate;

    /**
     * whether account is active
     */
    @Column(name = "active")
    private Byte active;

    /**
     * the first name
     */
    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    /**
     * the last name
     */
    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    /**
     * the info
     */
    @Column(name = "info")
    private String info;

    /**
     * the birth date
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birth_date")
    private Date birthDate;

    /**
     * number of posts being published
     */
    @NotNull
    @Column(name = "no_of_posts")
    private int noOfPosts;


    /**
     * the blogs created by
     */
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Blog> blogs;

    /**
     * the posts published by
     */
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Post> posts;

    /**
     * the comments published by
     */
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Comment> comments;

    /**
     * the subscribers of the account
     */
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<AccountRelation> accountRelations;


    /**
     * the accounts subscribed to the account
     */
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<AccountRelation> targetAccountRelations;

    /**
     * the accounts subscribed to the blog
     */
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<BlogRelation> blogRelations;

    /**
     * the accounts subscribed to the post
     */
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<PostRelation> postRelations;

    /**
     * the accounts subscribed to the comment
     */
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<CommentRelation> commentRelations;
    /**
     * the security role
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20, nullable = false)
    private AccountRole accountRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "sign_in_provider", length = 20)
    private AccountSocialProvider signInProvider;


    public Account() {
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getNoOfPosts() {
        return noOfPosts;
    }

    public void setNoOfPosts(int noOfPosts) {
        this.noOfPosts = noOfPosts;
    }

    public Set<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(Set<Blog> blogs) {
        this.blogs = blogs;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<AccountRelation> getAccountRelations() {
        return accountRelations;
    }

    public void setAccountRelations(Set<AccountRelation> accountRelations) {
        this.accountRelations = accountRelations;
    }

    public Set<AccountRelation> getTargetAccountRelations() {
        return targetAccountRelations;
    }

    public void setTargetAccountRelations(Set<AccountRelation> targetAccountRelations) {
        this.targetAccountRelations = targetAccountRelations;
    }

    public Set<BlogRelation> getBlogRelations() {
        return blogRelations;
    }

    public void setBlogRelations(Set<BlogRelation> blogRelations) {
        this.blogRelations = blogRelations;
    }

    public Set<PostRelation> getPostRelations() {
        return postRelations;
    }

    public void setPostRelations(Set<PostRelation> postRelations) {
        this.postRelations = postRelations;
    }

    public Set<CommentRelation> getCommentRelations() {
        return commentRelations;
    }

    public void setCommentRelations(Set<CommentRelation> commentRelations) {
        this.commentRelations = commentRelations;
    }

    public AccountRole getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(AccountRole accountRole) {
        this.accountRole = accountRole;
    }

    public AccountSocialProvider getSignInProvider() {
        return signInProvider;
    }

    public void setSignInProvider(AccountSocialProvider signInProvider) {
        this.signInProvider = signInProvider;
    }

    @Override
    public String toString() {
        return "Account{" +
                "email='" + email + '\'' +
                '}';
    }

    @Override
    @PrePersist
    public void prePersist() {
        super.prePersist();
        this.setNoOfPosts(0);
        this.setRegistrationDate(new Date());
    }
}
