package example;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
public class OwnWindowEx implements Runnable
{
  public static void main(
    final String args[])
  {
    final OwnWindowEx de = new OwnWindowEx(new Example());
    de.run();
  }

  private final GLWindow    window;
  private final FPSAnimator animator;

  private OwnWindowEx(
    final GLEventListener gle)
  {
    final GLProfile pro = GLProfile.get(GLProfile.GL2GL3);
    final GLCapabilities cap = new GLCapabilities(pro);

    this.window = GLWindow.create(cap);
    this.window.setSize(640, 480);
    this.window.setTitle("Test1");
    this.window.addGLEventListener(gle);
    this.window.setVisible(true);

    this.animator = new FPSAnimator(60);
    this.animator.setUpdateFPSFrames(60, System.err);
    this.animator.add(this.window);
    this.animator.start();
  }

  @Override public void run()
  {
    try {
      while (this.animator.isAnimating() && this.window.isVisible()) {
        Thread.sleep(100);
      }

      this.animator.stop();
      this.window.destroy();

      System.err.println("Exiting...");
    } catch (final InterruptedException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

private static class Example implements GLEventListener
{
		@Override
		public void display(final GLAutoDrawable drawable) {
			final GL2ES2 gl = drawable.getGL().getGL2ES2();

			gl.glClearColor(0f, 0f, 1.0f, 1f);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		}

		@Override
		public void dispose(
				@SuppressWarnings("unused") final GLAutoDrawable drawable) {
			// Nothing.
		}

		@Override
		public void init(
				@SuppressWarnings("unused") final GLAutoDrawable drawable) {
			// Nothing.
		}

		@Override
		public void reshape(
				@SuppressWarnings("unused") final GLAutoDrawable drawable,
				@SuppressWarnings("unused") final int x,
				@SuppressWarnings("unused") final int y,
				@SuppressWarnings("unused") final int w,
				@SuppressWarnings("unused") final int h) {
			// Nothing.
		}
}
}