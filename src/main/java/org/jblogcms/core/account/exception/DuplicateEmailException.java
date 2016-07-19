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
 * The exception is thrown when the email given during the registration
 * phase is already found from the database.
 * @author Victor Andreenko
 */
public class DuplicateEmailException extends Exception {

    private static final long serialVersionUID = 2526495157465022636L;

    private String email;

    public DuplicateEmailException() {
        super("Duplicate email Exception");
    }

    public DuplicateEmailException(String email) {
        super("Duplicate email (" + email + ")  Exception");
        this.email = email;
    }

    public String getLocalMessage() {
        return "exception.duplicateEmail";
    }
}
