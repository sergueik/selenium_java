package example;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.swt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

// based on: http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/DemonstratesaCanvas.htm
// https://github.com/sgothel/jogl-demos/blob/master/maven/jp4da/jp4da-desktop/src/main/java/com/io7m/examples/jp4da/DesktopExample.java

public class CanvasEx {

	private FPSAnimator animator;
	@SuppressWarnings("unused")
	private Shell shell;
	private GLCanvas glCanvas;

	public void run() {

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Animator Example");
		createContents(shell);
		shell.open();
		while (animator.isAnimating() && glCanvas.isVisible()
				&& !shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		animator.stop();
		display.dispose();
	}

	private void createContents(Shell shell) {

		shell.setLayout(new FillLayout());
		animator = new FPSAnimator(60);
		animator.setUpdateFPSFrames(60, System.err);

		@SuppressWarnings("unused")
		Canvas canvas = new Canvas(shell, SWT.NONE);
		final GLProfile pro = GLProfile.get(GLProfile.GL2GL3);
		final GLCapabilities cap = new GLCapabilities(pro);
		glCanvas = new GLCanvas(shell, 0, cap, (GLCapabilitiesChooser) null);
		glCanvas.addGLEventListener(new GLEventListener() {
			@Override
			public void display(final GLAutoDrawable drawable) {
				final GL2ES2 gl = drawable.getGL().getGL2ES2();
				gl.glClearColor(1.0f, 0f, 1.0f, 1f);
				gl.glClear(GL.GL_COLOR_BUFFER_BIT);
			}

			@Override
			public void dispose(final GLAutoDrawable drawable) {
				System.err.println("dispose");
			}

			@Override
			public void init(final GLAutoDrawable drawable) {
				System.err.println("init");
			}

			@Override
			public void reshape(final GLAutoDrawable drawable, final int x,
					final int y, final int w, final int h) {
			}
		});
		animator.add(glCanvas);
		glCanvas.setVisible(true);
		animator.start();
	}

	public static void main(String[] args) {
		new CanvasEx().run();
	}
}
