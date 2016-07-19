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

package org.jblogcms.core.account.exception;

/**
 * @author Victor Andreenko
 */
public class OldPasswordNotMatchedException extends Exception {

    private static final long serialVersionUID = 237646053730206137L;

    private Long accountId;
    private String wrongPassword;


    public OldPasswordNotMatchedException(Long accountId, String wrongPassword) {
        super("Change password exception: wrong old password accountId="
                + accountId + ", wrong password=" + wrongPassword);
        this.accountId = accountId;
        this.wrongPassword = wrongPassword;
    }

    public String getLocalMessage() {
        return "exception.oldPasswordNotMatched";
    }
}
