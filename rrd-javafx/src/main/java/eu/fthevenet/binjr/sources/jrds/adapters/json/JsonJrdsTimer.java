/*
 *    Copyright 2018 Frederic Thevenet
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package eu.fthevenet.binjr.sources.jrds.adapters.json;

public class JsonJrdsTimer {
    public String Name;
    public long LastCollect;
    public long LastDuration;

    @Override
    public String toString() {
        return "JsonJrdsTimer{" +
                "Name='" + Name + '\'' +
                ", LastCollect=" + LastCollect +
                ", LastDuration=" + LastDuration +
                '}';
    }
}
