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
package io.webfolder.cdp.session;

import static io.webfolder.cdp.type.constant.MouseButtonType.Left;
import static io.webfolder.cdp.type.constant.MouseEventType.MousePressed;
import static io.webfolder.cdp.type.constant.MouseEventType.MouseReleased;
import static java.lang.Math.floor;
import static java.lang.String.format;

import java.util.List;

import io.webfolder.cdp.command.DOM;
import io.webfolder.cdp.command.Input;
import io.webfolder.cdp.exception.ElementNotFoundException;
import io.webfolder.cdp.type.dom.BoxModel;

/**
 * Interface representing basic mouse operations.
 */
public interface Mouse {

    /**
     * Click on the specified element.
     * 
     * There are some preconditions for an element to be clicked.
     * The element must be visible and it must have a height and width greater then 0.
     * 
     * @param selector css or xpath selector
     * 
     * @return this
     */
    default Session click(final String selector) {
        return click(selector, Constant.EMPTY_ARGS);
    }

    /**
     * Click on the specified element.
     * 
     * There are some preconditions for an element to be clicked. The element
     * must be visible and it must have a height and width greater then 0.
     *
     * @param selector
     *            css or xpath selector
     * @param args
     *            format string
     * 
     * @return this
     */
    default Session click(final String selector, final Object... args) {
        getThis().logEntry("click", format(selector, args));
        DOM dom = getThis().getCommand().getDOM();
        Integer nodeId = getThis().getNodeId(format(selector, args));
        if (nodeId == null || Constant.EMPTY_NODE_ID.equals(nodeId)) {
            throw new ElementNotFoundException(format(selector, args));
        }
        BoxModel boxModel = dom.getBoxModel(nodeId, null, null);
        if (boxModel == null) {
            return getThis();
        }
        List<Double> content = boxModel.getContent();
        if (content == null           ||
                    content.isEmpty() ||
                    content.size() < 2) {
            return getThis();
        }
        double left = floor(content.get(0));
        double top  = floor(content.get(1));
        int clickCount = 1;
        Input input = getThis().getCommand().getInput();
        input.dispatchMouseEvent(MousePressed, left, top, null, null, Left, clickCount, null, null);
        input.dispatchMouseEvent(MouseReleased, left, top, null, null, Left, clickCount, null, null);
        return getThis();
    }

    Session getThis();
}
