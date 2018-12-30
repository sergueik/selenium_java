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
package io.webfolder.cdp.type.css;

import java.util.ArrayList;
import java.util.List;

/**
 * Inherited CSS rule collection from ancestor node
 */
public class InheritedStyleEntry {
    private CSSStyle inlineStyle;

    private List<RuleMatch> matchedCSSRules = new ArrayList<>();

    /**
     * The ancestor node's inline style, if any, in the style inheritance chain.
     */
    public CSSStyle getInlineStyle() {
        return inlineStyle;
    }

    /**
     * The ancestor node's inline style, if any, in the style inheritance chain.
     */
    public void setInlineStyle(CSSStyle inlineStyle) {
        this.inlineStyle = inlineStyle;
    }

    /**
     * Matches of CSS rules matching the ancestor node in the style inheritance chain.
     */
    public List<RuleMatch> getMatchedCSSRules() {
        return matchedCSSRules;
    }

    /**
     * Matches of CSS rules matching the ancestor node in the style inheritance chain.
     */
    public void setMatchedCSSRules(List<RuleMatch> matchedCSSRules) {
        this.matchedCSSRules = matchedCSSRules;
    }
}
