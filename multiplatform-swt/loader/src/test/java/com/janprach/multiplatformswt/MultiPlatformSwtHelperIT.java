package com.janprach.multiplatformswt;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Test;

import com.janprach.multiplatformswt.loader.MultiPlatformSwtHelper;

public class MultiPlatformSwtHelperIT {
	private static final String SOME_SWT_CLASS_NAME = "org.eclipse.swt.SWT";

	@Test(expected = ClassNotFoundException.class)
	public void importSwtJarUninitializedSystemClassLoaderTest() throws ClassNotFoundException {
		final URLClassLoader systemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		systemClassLoader.loadClass(SOME_SWT_CLASS_NAME);
	}

	@Test(expected = ClassNotFoundException.class)
	public void importSwtJarUninitializedURLClassLoaderTest() throws ClassNotFoundException, MalformedURLException {
		final URL swtJarUrl = new URL("file:/non-existing-path");
		final URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[] { swtJarUrl });
		urlClassLoader.loadClass(SOME_SWT_CLASS_NAME);
	}

	@Test
	public void importSwtJarInitializedURLClassLoaderTest() throws ClassNotFoundException, MalformedURLException {
		final URL swtJarUrl = MultiPlatformSwtHelper.getSwtPlatformDependentJarFileUrl();
		final URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[] { swtJarUrl });
		final Class<?> someSwtClass = urlClassLoader.loadClass(SOME_SWT_CLASS_NAME);
		assertNotNull(someSwtClass);
	}
}
