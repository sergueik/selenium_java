package com.mycompany.app;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.ScrolledComposite;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

// based on: 
// http://stackoverflow.com/questions/10145547/enabling-scroll-bars-in-a-java-swt-window
class ScrolledTextEx {

	protected Shell shell;
	private String commandId;
	private Display display;
	private String dataKey = "CurrentCommandId";
	private Text text;
	private String payload = "Nothing here\nyet...";
	private int width = 450;
	private int height = 380;

	ScrolledTextEx(Display parentDisplay, Shell parent) {
		if (parent != null) {
			// commandId = parent.getData(dataKey).toString();
			payload = (String) parent.getData();
		} else {
			payload = render();
		}
		display = (parentDisplay != null) ? parentDisplay : new Display();

		shell = new Shell(display);
		shell.setSize(20, 20);
		shell.open();

		createContents();
		shell.setSize(450, 300);
		shell.setText("Configuration");
		shell.addListener(SWT.Close, new Listener() {

			@Override
			public void handleEvent(Event event) {
				shell.dispose();
			}
		});

		try {
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void createContents() {
		shell.setText("Generated QA source");
		shell.setLayout(new GridLayout(2, false));

		Label lblDomain = new Label(shell, SWT.NONE);
		lblDomain.setLayoutData(GridDataFactory.fillDefaults().create());
		lblDomain.setText("Name of the script");

		text = new Text(shell, SWT.BORDER);
		text.setLayoutData(
				GridDataFactory.fillDefaults().grab(true, false).create());

		final StyledText styledText = new StyledText(shell,
				SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.WRAP);
		styledText.setLayoutData(
				GridDataFactory.fillDefaults().grab(true, true).span(2, 1).create());
		styledText.setText(payload);
		Button btnWhois = new Button(shell, SWT.NONE);
		btnWhois.setText("Save");
	}

	public static void main(String[] arg) {
		ScrolledTextEx o = new ScrolledTextEx(null, null);
		// o.render();
	}

	private static final Map<String, String> elementData = createSampleElement();

	private static Map<String, String> createSampleElement() {
		Map<String, String> elementData = new HashMap<String, String>();
		elementData.put("ElementId", "id");
		elementData.put("ElementCodeName", "gmail link");
		elementData.put("ElementXPath", "/html//img[1]");
		elementData.put("ElementCssSelector", "div#gbw > a.highlight");
		elementData.put("useCss", "true");
		elementData.put("useXPath", "false");
		elementData.put("useId", "false");
		elementData.put("useText", "false");
		return elementData;
	}

	// http://jtwig.org/documentation/reference/
	private String render() {
		JtwigTemplate template = JtwigTemplate
				.classpathTemplate("templates/example.twig");
		JtwigModel model = JtwigModel.newModel();
		for (String key : elementData.keySet()) {
			model.with(key, elementData.get(key));
		}
		String output = template.render(model);
		System.err.println("Rendered: " + output);
		return output;
	}
}
