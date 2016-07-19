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

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Provides the generic model interface.
 * Represents a row in the database table with each column mapped to a property of this class.
 *
 * @author Victor Andreenko
 */
@MappedSuperclass
public abstract class JItem implements Serializable {

    private static final long serialVersionUID = 2684156139535901049L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    /**
     * registration date
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    /**
     * number of subscribers
     */
    @NotNull
    @Column(name = "no_of_subscribers")
    private int noOfSubscribers;

    /**
     * number of people rated this item
     */
    @NotNull
    @Column(name = "no_of_rated")
    private int noOfRated;

    /**
     * number of people rated up this item
     */
    @NotNull
    @Column(name = "no_of_rated_up")
    private int noOfRatedUp;

    /**
     * number of people rated down this item
     */
    @NotNull
    @Column(name = "no_of_rated_down")
    private int noOfRatedDown;

    /**
     * rating of item
     */
    @NotNull
    @Column(name = "rating")
    private int rating;

    /**
     * version
     */
    @Version
    protected int version;

    @Transient
    private ItemRelation currentItemRelation;

    @Transient
    private ItemRate currentItemRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNoOfSubscribers() {
        return noOfSubscribers;
    }

    public void setNoOfSubscribers(int noOfSubscribers) {
        this.noOfSubscribers = noOfSubscribers;
    }

    public int getNoOfRated() {
        return noOfRated;
    }

    public void setNoOfRated(int noOfRated) {
        this.noOfRated = noOfRated;
    }

    public int getNoOfRatedUp() {
        return noOfRatedUp;
    }

    public void setNoOfRatedUp(int noOfRatedUp) {
        this.noOfRatedUp = noOfRatedUp;
    }

    public int getNoOfRatedDown() {
        return noOfRatedDown;
    }

    public void setNoOfRatedDown(int noOfRatedDown) {
        this.noOfRatedDown = noOfRatedDown;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public ItemRelation getCurrentItemRelation() {
        return currentItemRelation;
    }

    public void setCurrentItemRelation(ItemRelation currentItemRelation) {
        this.currentItemRelation = currentItemRelation;
    }

    public ItemRate getCurrentItemRate() {
        return currentItemRate;
    }

    public void setCurrentItemRate(ItemRate currentItemRate) {
        this.currentItemRate = currentItemRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JItem)) return false;
        JItem item = (JItem) o;
        return Objects.equals(getId(), item.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void prePersist() {
        if (getDate() == null) {
            setDate(new Date());
        }
        setNoOfSubscribers(0);
        setNoOfRated(0);
        setNoOfRatedUp(0);
        setNoOfRatedDown(0);
        setRating(0);
    }

    @Override
    public String toString() {
        return "JItem{" +
                "id=" + id +
                '}';
    }
}
