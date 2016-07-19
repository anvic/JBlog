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

package org.jblogcms.core.post.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;
import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.blog.model.Blog;
import org.jblogcms.core.comment.model.Comment;
import org.jblogcms.core.common.model.JItem;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Provides the base model interface for the Post service.
 * Represents a row in the <i>post</i> database table,
 * with each column mapped to a property of this class.
 *
 * @author Victor Andreenko
 */
@Entity
@Table(name = "post")
@DynamicInsert
@DynamicUpdate
public class Post
        extends JItem
        implements Serializable {

    private static final long serialVersionUID = 3142887589377332404L;

    /**
     * the title
     */
    @NotBlank
    @Column(name = "title")
    private String title;

    /**
     * the url link
     */
    @Column(name = "link_to_source")
    private String linkToSource;

    /**
     * the short text
     */
    @Lob
    @NotBlank
    @Column(name = "short_text", columnDefinition = "TEXT")
    private String shortText;

    /**
     * the full text
     */
    @Lob
    @NotBlank
    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    /**
     * the url link to the image
     */
    @Column(name = "picture")
    private String picture;

    /**
     * the author
     *
     * @see org.jblogcms.core.account.model.Account
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    /**
     * the date of the last updating
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;

    /**
     * the number the post was showed
     */
    @NotNull
    @Column(name = "no_of_views")
    private int noOfViews;

    /**
     * the number of post's comment
     */
    @NotNull
    @Column(name = "no_of_comments")
    private int noOfComments;

    /**
     * the tags
     */
    @Column(name = "tags")
    private String tags;

    @OrderBy("id asc")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "join_blog_post",
            joinColumns = {@JoinColumn(name = "post_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "blog_id", referencedColumnName = "id")})
    private Set<Blog> blogs;

    /**
     * the comments related to the post
     */
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Comment> comments;

    /**
     * the subscribers of the post
     */
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<PostRelation> postRelations;

    /**
     * the subscribers of the post
     */
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<PostRate> postRates;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkToSource() {
        return linkToSource;
    }

    public void setLinkToSource(String linkToSource) {
        this.linkToSource = linkToSource;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account author) {
        this.account = author;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public int getNoOfViews() {
        return noOfViews;
    }

    public void setNoOfViews(int noOfViews) {
        this.noOfViews = noOfViews;
    }

    public int getNoOfComments() {
        return noOfComments;
    }

    public void setNoOfComments(int noOfComments) {
        this.noOfComments = noOfComments;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Set<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(Set<Blog> blogs) {
        this.blogs = blogs;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<PostRelation> getPostRelations() {
        return postRelations;
    }

    public void setPostRelations(Set<PostRelation> postRelations) {
        this.postRelations = postRelations;
    }

    public Set<PostRate> getPostRates() {
        return postRates;
    }

    public void setPostRates(Set<PostRate> postRates) {
        this.postRates = postRates;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                '}';
    }

    @Override
    @PrePersist
    public void prePersist() {
        super.prePersist();
        setNoOfComments(0);
        setNoOfViews(0);
        setRating(0);
    }
}
