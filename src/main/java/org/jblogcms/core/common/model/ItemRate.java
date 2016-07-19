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

package org.jblogcms.core.common.model;

import org.jblogcms.core.account.model.Account;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Provides generic interface for rating in
 *
 * @param <T>
 * @author Victor Andreenko
 */
@MappedSuperclass
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"account_id", "item_id"})})
public class ItemRate<T extends JItem> implements Serializable {

    private static final long serialVersionUID = 1879173414500743926L;


    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    /**
     * the account rates item
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    /**
     * the item rated by account
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private T item;

    /**
     * rate
     */
    @Column(name = "rate")
    private Byte rate;

    /**
     * date of rating
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    /**
     * item
     */
    @Version
    private int version;

    public ItemRate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public Byte getRate() {
        return rate;
    }

    public void setRate(Byte rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void prePersist() {
        setDate(new Date());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemRate)) return false;
        ItemRate<?> that = (ItemRate<?>) o;
        return Objects.equals(getAccount(), that.getAccount()) &&
                Objects.equals(getItem(), that.getItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccount(), getItem());
    }

    @Override
    public String toString() {
        return "ItemRate{" +
                "item=" + item +
                ", account=" + account +
                '}';
    }
}
