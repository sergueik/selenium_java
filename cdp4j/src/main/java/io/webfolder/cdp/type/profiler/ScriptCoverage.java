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
package io.webfolder.cdp.type.profiler;

import io.webfolder.cdp.annotation.Experimental;
import java.util.ArrayList;
import java.util.List;

/**
 * Coverage data for a JavaScript script
 */
@Experimental
public class ScriptCoverage {
    private String scriptId;

    private String url;

    private List<FunctionCoverage> functions = new ArrayList<>();

    /**
     * JavaScript script id.
     */
    public String getScriptId() {
        return scriptId;
    }

    /**
     * JavaScript script id.
     */
    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
    }

    /**
     * JavaScript script name or url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * JavaScript script name or url.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Functions contained in the script that has coverage data.
     */
    public List<FunctionCoverage> getFunctions() {
        return functions;
    }

    /**
     * Functions contained in the script that has coverage data.
     */
    public void setFunctions(List<FunctionCoverage> functions) {
        this.functions = functions;
    }
}
