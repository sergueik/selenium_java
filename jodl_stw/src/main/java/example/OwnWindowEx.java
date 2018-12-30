package example;

import com.jogamp.newt.opengl.GLWindow;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES2;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;

import com.jogamp.opengl.util.FPSAnimator;

// based on: https://github.com/sgothel/jogl-demos/blob/master/maven/jp4da/jp4da-desktop/src/main/java/com/io7m/examples/jp4da/DesktopExample.java
// and https://github.com/sgothel/jogl-demos/blob/master/maven/jp4da/jp4da-core/src/main/java/com/io7m/examples/jp4da/Example.java

public class OwnWindowEx implements Runnable {

	private final GLWindow topWindow;
	private final FPSAnimator animator;
	private static final String profile = GLProfile.GL2GL3;

	public static void main(final String args[]) {
		(new OwnWindowEx(new ExampleGLEventListener())).run();
	}

	private OwnWindowEx(final GLEventListener gle) {
		final GLCapabilities cap = new GLCapabilities(GLProfile.get(profile));

		topWindow = GLWindow.create(cap);
		topWindow.setSize(640, 480);
		topWindow.setTitle("Test1");
		topWindow.addGLEventListener(gle);
		topWindow.setVisible(true);

		animator = new FPSAnimator(60);
		animator.setUpdateFPSFrames(60, System.err);
		animator.add(topWindow);
		animator.start();
	}

	@Override
	public void run() {
		try {
			while (animator.isAnimating() && topWindow.isVisible()) {
				Thread.sleep(100);
			}

			animator.stop();
			topWindow.destroy();

			System.err.println("Exiting...");
		} catch (final InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static class ExampleGLEventListener implements GLEventListener {

		@Override
		public void display(final GLAutoDrawable drawable) {
			final GL2ES2 gl = drawable.getGL().getGL2ES2();

			gl.glClearColor(0f, 0f, 1.0f, 1f);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		}

		@Override
		public void dispose(final GLAutoDrawable drawable) {
		}

		@Override
		public void init(final GLAutoDrawable drawable) {
		}

		@Override
		public void reshape(final GLAutoDrawable drawable, final int x, final int y,
				final int w, final int h) {
		}
	}
}