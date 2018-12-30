/**
 * cdp4j - Chrome DevTools Protocol for Java
 * Copyright © 2017 WebFolder OÜ (support@webfolder.io)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.webfolder.cdp.type.runtime;

import io.webfolder.cdp.annotation.Experimental;

@Experimental
public class CustomPreview {
    private String header;

    private Boolean hasBody;

    private String formatterObjectId;

    private String bindRemoteObjectFunctionId;

    private String configObjectId;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Boolean isHasBody() {
        return hasBody;
    }

    public void setHasBody(Boolean hasBody) {
        this.hasBody = hasBody;
    }

    public String getFormatterObjectId() {
        return formatterObjectId;
    }

    public void setFormatterObjectId(String formatterObjectId) {
        this.formatterObjectId = formatterObjectId;
    }

    public String getBindRemoteObjectFunctionId() {
        return bindRemoteObjectFunctionId;
    }

    public void setBindRemoteObjectFunctionId(String bindRemoteObjectFunctionId) {
        this.bindRemoteObjectFunctionId = bindRemoteObjectFunctionId;
    }

    public String getConfigObjectId() {
        return configObjectId;
    }

    public void setConfigObjectId(String configObjectId) {
        this.configObjectId = configObjectId;
    }
}
