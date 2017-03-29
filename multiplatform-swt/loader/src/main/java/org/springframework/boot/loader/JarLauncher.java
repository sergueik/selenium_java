/*
 * Copyright 2012-2013 the original author or authors.
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

package org.springframework.boot.loader;

import java.util.List;

import org.springframework.boot.loader.archive.Archive;
import org.springframework.boot.loader.util.AsciiBytes;

import com.janprach.multiplatformswt.loader.MultiPlatformSwtHelper;

/**
 * This is copied from original {@link JarLauncher} spring-boot-loader project.
 * The only change here is to call
 * {@code MultiPlatformSwtHelper.isCorrectPlatformSwtJarOrTrue()}.
 * 
 * BEWARE: Keep the class name and package name the same! Also update this class
 * once you update spring-boot-loader project!
 * 
 * How this works? The spring-boot-maven-plugin will copy spring-boot-loader
 * classes first and user classes, jars and resources after that. We abuse that
 * fact and create class with the same fully qualified name with modified
 * behavior here.
 * 
 * Note: For that reason we can't inherit or reuse the original class. We would
 * need two different versions of the same class on the classpath. A cleaner way
 * would be to tell spring-boot-maven-plugin to use different main class (the
 * real 'Main-Class' class as written in MANIFEST.MF). Even better would be to
 * be able to register some sort of listener adding our functionality to
 * spring-boot-loader. However neither of the two options are (currently)
 * supported.
 */
public class JarLauncher extends ExecutableArchiveLauncher {

	private static final AsciiBytes LIB = new AsciiBytes("lib/");

	@Override
	protected boolean isNestedArchive(Archive.Entry entry) {
		return !entry.isDirectory() && entry.getName().startsWith(LIB)
				&& MultiPlatformSwtHelper
						.isCorrectPlatformSwtJarOrTrue(entry.getName().toString());
	}

	@Override
	protected void postProcessClassPathArchives(List<Archive> archives)
			throws Exception {
		archives.add(0, getArchive());
	}

	public static void main(String[] args) {
		new JarLauncher().launch(args);
	}
}