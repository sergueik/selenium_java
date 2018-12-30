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

import java.util.ArrayList;
import java.util.List;

/**
 * Profile
 */
public class Profile {
    private List<ProfileNode> nodes = new ArrayList<>();

    private Double startTime;

    private Double endTime;

    private List<Integer> samples = new ArrayList<>();

    private List<Integer> timeDeltas = new ArrayList<>();

    /**
     * The list of profile nodes. First item is the root node.
     */
    public List<ProfileNode> getNodes() {
        return nodes;
    }

    /**
     * The list of profile nodes. First item is the root node.
     */
    public void setNodes(List<ProfileNode> nodes) {
        this.nodes = nodes;
    }

    /**
     * Profiling start timestamp in microseconds.
     */
    public Double getStartTime() {
        return startTime;
    }

    /**
     * Profiling start timestamp in microseconds.
     */
    public void setStartTime(Double startTime) {
        this.startTime = startTime;
    }

    /**
     * Profiling end timestamp in microseconds.
     */
    public Double getEndTime() {
        return endTime;
    }

    /**
     * Profiling end timestamp in microseconds.
     */
    public void setEndTime(Double endTime) {
        this.endTime = endTime;
    }

    /**
     * Ids of samples top nodes.
     */
    public List<Integer> getSamples() {
        return samples;
    }

    /**
     * Ids of samples top nodes.
     */
    public void setSamples(List<Integer> samples) {
        this.samples = samples;
    }

    /**
     * Time intervals between adjacent samples in microseconds. The first delta is relative to the profile startTime.
     */
    public List<Integer> getTimeDeltas() {
        return timeDeltas;
    }

    /**
     * Time intervals between adjacent samples in microseconds. The first delta is relative to the profile startTime.
     */
    public void setTimeDeltas(List<Integer> timeDeltas) {
        this.timeDeltas = timeDeltas;
    }
}
