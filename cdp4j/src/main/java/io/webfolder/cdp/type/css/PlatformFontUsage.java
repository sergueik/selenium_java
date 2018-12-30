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

import io.webfolder.cdp.annotation.Experimental;

/**
 * Information about amount of glyphs that were rendered with given font
 */
@Experimental
public class PlatformFontUsage {
    private String familyName;

    private Boolean isCustomFont;

    private Double glyphCount;

    /**
     * Font's family name reported by platform.
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Font's family name reported by platform.
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * Indicates if the font was downloaded or resolved locally.
     */
    public Boolean isIsCustomFont() {
        return isCustomFont;
    }

    /**
     * Indicates if the font was downloaded or resolved locally.
     */
    public void setIsCustomFont(Boolean isCustomFont) {
        this.isCustomFont = isCustomFont;
    }

    /**
     * Amount of glyphs that were rendered with this font.
     */
    public Double getGlyphCount() {
        return glyphCount;
    }

    /**
     * Amount of glyphs that were rendered with this font.
     */
    public void setGlyphCount(Double glyphCount) {
        this.glyphCount = glyphCount;
    }
}
