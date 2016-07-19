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
import org.jblogcms.core.common.model.ItemRelation;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Provides the base model interface for the PostRelation service.
 * Represents a row in the <i>post_relation</i> database table,
 * with each column mapped to a property of this class.
 *
 * @author Victor Andreenko
 */
@Entity
@Table(name = "post_relation")
@DynamicInsert
@DynamicUpdate
public class PostRelation
        extends ItemRelation<Post>
        implements Serializable {

    private static final long serialVersionUID = 3066107959519421047L;

    @Override
    @PrePersist
    public void prePersist() {
        super.prePersist();
    }

}
