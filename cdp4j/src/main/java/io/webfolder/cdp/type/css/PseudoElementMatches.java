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

import io.webfolder.cdp.type.dom.PseudoType;
import java.util.ArrayList;
import java.util.List;

/**
 * CSS rule collection for a single pseudo style
 */
public class PseudoElementMatches {
    private PseudoType pseudoType;

    private List<RuleMatch> matches = new ArrayList<>();

    /**
     * Pseudo element type.
     */
    public PseudoType getPseudoType() {
        return pseudoType;
    }

    /**
     * Pseudo element type.
     */
    public void setPseudoType(PseudoType pseudoType) {
        this.pseudoType = pseudoType;
    }

    /**
     * Matches of CSS rules applicable to the pseudo style.
     */
    public List<RuleMatch> getMatches() {
        return matches;
    }

    /**
     * Matches of CSS rules applicable to the pseudo style.
     */
    public void setMatches(List<RuleMatch> matches) {
        this.matches = matches;
    }
}
