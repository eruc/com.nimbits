/*
 * Copyright (c) 2013 Nimbits Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expressed or implied.  See the License for the specific language governing permissions and limitations under the License.
 */

package com.nimbits.client.model.email.impl;


import com.nimbits.client.model.common.impl.CommonIdentifierImpl;
import com.nimbits.client.model.email.EmailAddress;

import java.io.Serializable;

/**
 * Created by bsautner
 * User: benjamin
 * Date: 8/1/11
 * Time: 9:56 AM
 */
@SuppressWarnings("unused")
public class EmailAddressImpl extends CommonIdentifierImpl implements Serializable, EmailAddress {
    private static final long serialVersionUID =1L;

    protected EmailAddressImpl() {
        super();
    }

    public EmailAddressImpl(final String value) {
        super(value.toLowerCase());

    }
}
