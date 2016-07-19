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

package org.jblogcms.core.blog.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;
import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.common.model.JItem;
import org.jblogcms.core.post.model.Post;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;


/**
 * Provides the base model interface for the Blog service.
 * Represents a row in the <i>blog</i> database table,
 * with each column mapped to a property of this class.
 *
 * @author Victor Andreenko
 */
@Entity
@Table(name = "blog")
@DynamicInsert
@DynamicUpdate
public class Blog
        extends JItem
        implements Serializable {

    private static final long serialVersionUID = 4463552193826964681L;

    /**
     * the name
     */
    @NotBlank
    @Column(name = "name", unique = true)
    private String name;

    /**
     * the url name
     */
    @NotBlank
    @Column(name = "url_name", unique = true)
    private String urlName;

    /**
     * the short description
     */
    @Column(name = "description")
    private String description;

    /**
     * the numbers of posts related to the blog
     */
    @NotNull
    @Column(name = "no_of_posts")
    private Integer noOfPosts;

    /**
     * the account created the blog
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    /**
     * the posts related to the blog
     */
    @ManyToMany(mappedBy = "blogs", fetch = FetchType.LAZY)
    private Set<Post> posts;

    /**
     * the subscriptions
     */
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<BlogRelation> blogRelations;


    public Blog() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNoOfPosts() {
        return noOfPosts;
    }

    public void setNoOfPosts(Integer postsNumber) {
        this.noOfPosts = postsNumber;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<BlogRelation> getBlogRelations() {
        return blogRelations;
    }

    public void setBlogRelations(Set<BlogRelation> blogRelations) {
        this.blogRelations = blogRelations;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "name='" + name + '\'' +
                ", urlName='" + urlName + '\'' +
                '}';
    }

    @Override
    @PrePersist
    public void prePersist() {
        super.prePersist();
        this.setNoOfPosts(0);
    }


}
