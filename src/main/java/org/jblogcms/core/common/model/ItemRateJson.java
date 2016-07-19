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

/**
 * @author Victor Andreenko
 */
public class ItemRateJson {

    private String newState;
    private String label;
    private String info;

    private int newRated;
    private int newRatedUp;
    private int newRatedDown;

    private int newRating;

    public String getNewState() {
        return newState;
    }

    public void setNewState(String newState) {
        this.newState = newState;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getNewRated() {
        return newRated;
    }

    public void setNewRated(int newRated) {
        this.newRated = newRated;
    }

    public int getNewRatedUp() {
        return newRatedUp;
    }

    public void setNewRatedUp(int newRatedUp) {
        this.newRatedUp = newRatedUp;
    }

    public int getNewRatedDown() {
        return newRatedDown;
    }

    public void setNewRatedDown(int newRatedDown) {
        this.newRatedDown = newRatedDown;
    }

    public int getNewRating() {
        return newRating;
    }

    public void setNewRating(int newRating) {
        this.newRating = newRating;
    }
}