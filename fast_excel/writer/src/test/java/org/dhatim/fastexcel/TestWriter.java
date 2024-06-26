/*
 * Copyright 2018 Dhatim.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dhatim.fastexcel;

import java.io.ByteArrayOutputStream;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TestWriter {

	@Test
	public void testEscaping() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Writer w = new Writer(baos);
		w.append("not escaped");
		w.appendEscaped(" but <this will be escaped \ud83d\ude01>");
		w.flush();
		String s = baos.toString("UTF-8");
		assertThat(s)
				.isEqualTo("not escaped but &lt;this will be escaped &#x1f601;&gt;");
	}

	@Test
	public void testEscapingInvalidCharacters() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Writer w = new Writer(baos);
		w.appendEscaped("some characters are ignored: \b or \u0001");
		w.flush();
		String s = baos.toString("UTF-8");
		assertThat(s).isEqualTo("some characters are ignored:  or ");
	}
}
