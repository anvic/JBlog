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

package org.jblogcms.core.comment.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;
import org.jblogcms.core.account.model.Account;
import org.jblogcms.core.common.model.JItem;
import org.jblogcms.core.post.model.Post;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Provides the base model interface for the Comment service.
 * Represents a row in the <i>comment</i> database table,
 * with each column mapped to a property of this class.
 *
 * @author Victor Andreenko
 */
@Entity
@Table(name = "comment")
@DynamicInsert
@DynamicUpdate
public class Comment
        extends JItem
        implements Serializable {

    private static final long serialVersionUID = -8981383179402046102L;

    /**
     * the text
     */
    @NotBlank
    @Column(name = "text")
    private String text;

    /**
     * the post
     *
     * @see org.jblogcms.core.post.model.Post
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    /**
     * the author of the comment
     *
     * @see org.jblogcms.core.account.model.Account
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;


    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<CommentRelation> commentRelations;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<CommentRate> commentRates;

    public Comment() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account author) {
        this.account = author;
    }

    public Set<CommentRelation> getCommentRelations() {
        return commentRelations;
    }

    public void setCommentRelations(Set<CommentRelation> commentRelations) {
        this.commentRelations = commentRelations;
    }

    public Set<CommentRate> getCommentRates() {
        return commentRates;
    }

    public void setCommentRates(Set<CommentRate> commentRates) {
        this.commentRates = commentRates;
    }

    @Override
    @PrePersist
    public void prePersist() {
        super.prePersist();
    }

}
