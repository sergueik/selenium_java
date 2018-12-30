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
 * Match data for a CSS rule
 */
public class RuleMatch {
    private CSSRule rule;

    private List<Integer> matchingSelectors = new ArrayList<>();

    /**
     * CSS rule in the match.
     */
    public CSSRule getRule() {
        return rule;
    }

    /**
     * CSS rule in the match.
     */
    public void setRule(CSSRule rule) {
        this.rule = rule;
    }

    /**
     * Matching selector indices in the rule's selectorList selectors (0-based).
     */
    public List<Integer> getMatchingSelectors() {
        return matchingSelectors;
    }

    /**
     * Matching selector indices in the rule's selectorList selectors (0-based).
     */
    public void setMatchingSelectors(List<Integer> matchingSelectors) {
        this.matchingSelectors = matchingSelectors;
    }
}
