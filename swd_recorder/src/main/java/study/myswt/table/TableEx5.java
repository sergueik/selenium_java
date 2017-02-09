package study.myswt.table;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
// import org.eclipse.equinox.common;
// import  org.eclipse.core.commands;

public class TableEx5 extends Dialog {
	private Table table;
	private TableCursor tableCursor;
	private Point selectionStartPoint;
	private volatile boolean shiftKeyPressed;
	private volatile boolean ctrlKeyPressed;
	private List<Point> currentSelection = new ArrayList<>();

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public TableEx5(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final TableViewer tableViewer = new TableViewer(composite,
				SWT.BORDER | SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		disableSWTTableCellSelection(tableViewer);

		attachMouseCellSelectionListener(tableViewer);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer,
				SWT.NONE);
		TableColumn tblclmnItem = tableViewerColumn.getColumn();
		tblclmnItem.setWidth(100);
		tblclmnItem.setText("Item0");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer,
				SWT.NONE);
		TableColumn tblclmnItem_1 = tableViewerColumn_1.getColumn();
		tblclmnItem_1.setWidth(100);
		tblclmnItem_1.setText("Item1");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer,
				SWT.NONE);
		TableColumn tblclmnItem_2 = tableViewerColumn_2.getColumn();
		tblclmnItem_2.setWidth(100);
		tblclmnItem_2.setText("Item2");

		tableCursor = new TableCursor(table, SWT.NONE);
		tableCursor.setBackground(new Color(null, 255, 204, 102));
		tableCursor.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.SHIFT) {
					selectionStartPoint = new Point(-1, -1);
					shiftKeyPressed = false;
					return;
				}

				if (e.keyCode == SWT.CTRL) {
					ctrlKeyPressed = false;
					return;
				}

				if (!((e.stateMask & SWT.SHIFT) != 0)) {
					clearSelection(currentSelection, tableViewer);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.keyCode == SWT.SHIFT) && shiftKeyPressed == false) {
					selectionStartPoint = tableCursor.getLocation();
					shiftKeyPressed = true;
				}

				if ((e.keyCode == SWT.CTRL) && ctrlKeyPressed == false) {
					selectionStartPoint = tableCursor.getLocation();
					ctrlKeyPressed = true;
				}

				if (ctrlKeyPressed && !shiftKeyPressed) {
					if (e.keyCode == SWT.ARROW_RIGHT) {
						tableCursor.setSelection(table.indexOf(tableCursor.getRow()),
								tableViewer.getTable().getColumnCount() - 1);
						return;
					}

					if (e.keyCode == SWT.ARROW_LEFT) {
						tableCursor.setSelection(table.indexOf(tableCursor.getRow()), 0);
						return;
					}

					if (e.keyCode == SWT.ARROW_UP) {
						tableCursor.setSelection(0, tableCursor.getColumn());
						return;
					}

					if (e.keyCode == SWT.ARROW_DOWN) {
						tableCursor.setSelection(tableViewer.getTable().getItemCount() - 1,
								tableCursor.getColumn());
						return;
					}
				}

				if (shiftKeyPressed && ctrlKeyPressed) {
					if (e.keyCode == SWT.ARROW_RIGHT) {
						tableCursor.setSelection(table.indexOf(tableCursor.getRow()),
								tableViewer.getTable().getColumnCount() - 1);
						selectCellsOnArrowKeys(tableViewer, e);
						return;
					}

					if (e.keyCode == SWT.ARROW_LEFT) {
						tableCursor.setSelection(table.indexOf(tableCursor.getRow()), 0);
						selectCellsOnArrowKeys(tableViewer, e);
						return;
					}

					if (e.keyCode == SWT.ARROW_UP) {
						tableCursor.setSelection(0, tableCursor.getColumn());
						selectCellsOnArrowKeys(tableViewer, e);
						return;
					}

					if (e.keyCode == SWT.ARROW_DOWN) {
						tableCursor.setSelection(tableViewer.getTable().getItemCount() - 1,
								tableCursor.getColumn());
						selectCellsOnArrowKeys(tableViewer, e);
						return;
					}

				}

				if ((e.keyCode == SWT.ARROW_UP) || (e.keyCode == SWT.ARROW_DOWN)
						|| (e.keyCode == SWT.ARROW_LEFT)
						|| (e.keyCode == SWT.ARROW_RIGHT)) {
					selectCellsOnArrowKeys(tableViewer, e);
				}

			}

			private void selectCellsOnArrowKeys(final TableViewer tableViewer,
					KeyEvent e) {
				if ((e.stateMask & SWT.SHIFT) != 0) {
					Point selectionEndPoint = tableCursor.getLocation();
					List<Point> cellsTobeSelected = getAllCellsBetweenTwoCells(
							selectionStartPoint, selectionEndPoint, tableViewer);
					if (cellsTobeSelected != null) {
						clearSelection(currentSelection, tableViewer);
						highlightCells(cellsTobeSelected, tableViewer);
						currentSelection.addAll(cellsTobeSelected);
					}
				}
			}
		});

		TableItem tableItem = new TableItem(table, SWT.NONE);
		tableItem.setText(0, "R0C0");
		tableItem.setText(1, "R0C1");
		tableItem.setText(2, "R0C2");

		TableItem tableItem1 = new TableItem(table, SWT.NONE);
		tableItem1.setText(0, "R1C0");
		tableItem1.setText(1, "R1C1");
		tableItem1.setText(2, "R1C2");

		TableItem tableItem2 = new TableItem(table, SWT.NONE);
		tableItem2.setText(0, "R2C0");
		tableItem2.setText(1, "R2C1");
		tableItem2.setText(2, "R2C2");

		TableItem tableItem3 = new TableItem(table, SWT.NONE);
		tableItem3.setText(0, "R3C0");
		tableItem3.setText(1, "R3C1");
		tableItem3.setText(2, "R3C2");

		TableItem tableItem4 = new TableItem(table, SWT.NONE);
		tableItem4.setText(0, "R4C0");
		tableItem4.setText(1, "R4C1");
		tableItem4.setText(2, "R4C2");

		return container;
	}

	private void attachMouseCellSelectionListener(final TableViewer tableViewer) {
		tableViewer.getTable().addMouseListener(new MouseAdapter() {
			private Point selectionEndPoint;

			public void mouseDown(MouseEvent e) {
				clearSelection(currentSelection, tableViewer);
				if (e.button == 1 && (e.stateMask & SWT.SHIFT) != 0) {
					selectionEndPoint = new Point(e.x, e.y);
					List<Point> cellsTobeSelected = getAllCellsBetweenTwoCells(
							selectionStartPoint, selectionEndPoint, tableViewer);
					if (cellsTobeSelected != null) {
						clearSelection(currentSelection, tableViewer);
						highlightCells(cellsTobeSelected, tableViewer);
						currentSelection.addAll(cellsTobeSelected);
					}
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {

			}
		});
	}

	private void disableSWTTableCellSelection(final TableViewer tableViewer) {
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			private boolean update;

			private ISelection lastSelection;

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection().isEmpty() && !update) {
					update = true;
					tableViewer.setSelection(lastSelection);
					update = false;
				} else if (!event.getSelection().isEmpty()) {
					lastSelection = event.getSelection();
				}

			}
		});
	}

	private void clearSelection(List<Point> currentSelection,
			TableViewer tableViewer) {
		for (Point cell : currentSelection) {
			tableViewer.getTable().getItem(cell.x).setBackground(cell.y,
					new Color(null, 255, 255, 255));
		}
		currentSelection.clear();
	}

	private void highlightCells(List<Point> cellsTobeHighlight,
			TableViewer tableViewer) {
		for (Point cell : cellsTobeHighlight) {
			tableViewer.getTable().getItem(cell.x).setBackground(cell.y,
					new Color(null, 255, 204, 102));
		}
	}

	private Point getCellId(Point mouseLocation, TableViewer tableViewer) {
		return null;

		// getCell(org.eclipse.swt.graphics.Point) is not public
		// in org.eclipse.jface.viewers.ColumnViewer;
		// prior to 3.4
		// JNI exception with jface 3.3.0 and above (3.5.1, 3.6.1.M20100825-0800)
		// java.lang.NoClassDefFoundError: org/eclipse/core/runtime/IStatus
		/*
		 * ViewerCell cell = tableViewer.getCell(mouseLocation); if (cell == null) {
		 * return null; } int columnIndex = cell.getColumnIndex(); int rowIndex =
		 * tableViewer.getTable().indexOf((TableItem) cell.getItem()); return new
		 * Point(rowIndex, columnIndex);
		 */
	}

	private List<Point> getAllCellsBetweenTwoCells(Point selectionStartPoint,
			Point selectionEndPoint, TableViewer tableViewer) {
		List<Point> currentSelection = new ArrayList<>();

		Point startCell = getCellId(selectionStartPoint, tableViewer);
		Point endCell = getCellId(selectionEndPoint, tableViewer);

		if (startCell == null || endCell == null) {
			return null;
		}

		int minX = Math.min(startCell.x, endCell.x);
		int minY = Math.min(startCell.y, endCell.y);

		int maxX = Math.max(startCell.x, endCell.x);
		int maxY = Math.max(startCell.y, endCell.y);

		int tmpMaxY;
		while (minX <= maxX) {
			tmpMaxY = maxY;
			while (minY <= tmpMaxY) {
				Point cell = new Point(maxX, tmpMaxY);
				currentSelection.add(cell);
				tmpMaxY--;
			}
			maxX--;
		}

		return currentSelection;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(766, 472);
	}

	public static void main(String[] args) {
		TableEx5 o = new TableEx5(new Shell());
		o.open();
	}
}
