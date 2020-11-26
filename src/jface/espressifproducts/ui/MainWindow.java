package jface.espressifproducts.ui;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import Products.AProduct;
import Products.DevKit;
import Products.Module;
import Products.ProductPojo;
import Products.ProductsHandler;
import Products.SOC;
import Products.TypeOfProducts;
import swing2swt.layout.BorderLayout;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.swing.Box.Filler;
import javax.swing.JFileChooser;

import org.eclipse.swt.custom.StyledText;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jface.text.TextViewer;

public class MainWindow extends ApplicationWindow {
	
	private String url = "https://www.espressif.com/en/products";
	private Table table;
	private Text textName;
	private TableItem item; 
	private ProductsHandler handler;
	
	List<AProduct> currentProducts; 
	
	
	/**
	 * Create the application window.
	 * @throws Exception 
	 */
	public MainWindow() throws Exception {
		super(null);
		handler = new ProductsHandler(url);	
		currentProducts = new ArrayList<AProduct>();
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(792, 540);
		shell.setText("MyAPP");
		shell.setLayout(new BorderLayout(0, 0));
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.CENTER);
		//selector of types 
		Combo cProductsType = new Combo(composite_1, SWT.NONE);
		cProductsType.setBounds(10, 0, 104, 38);
		cProductsType.setItems(new String[] {"SoC", "DevKit", "Module"});
		textName = new Text(composite_1, SWT.BORDER);
		textName.setBounds(10, 44, 104, 44);
		textName.setTextLimit(100);;
		//description textplace area
		TextViewer textViewerDescription = new TextViewer(composite_1, SWT.BORDER);
		StyledText styledTextDescription = textViewerDescription.getTextWidget();
		styledTextDescription.setBounds(10, 121, 281, 191);
		styledTextDescription.setTextLimit(300);
		Composite composite_5 = new Composite(shell, SWT.NONE);
		composite_5.setLayoutData(BorderLayout.WEST);
		// labels area
		Label lBProductsName = new Label(composite_5, SWT.NONE);
		lBProductsName.setLocation(0, 53);
		lBProductsName.setSize(114, 16);
		lBProductsName.setText("Products name:");
		
		Label lBDescription = new Label(composite_5, SWT.NONE);
		lBDescription.setBounds(0, 123, 91, 19);
		lBDescription.setText("Description:");
		
		Label lBproductsType = new Label(composite_5, SWT.NONE);
		lBproductsType.setLocation(0, 10);
		lBproductsType.setSize(96, 16);
		lBproductsType.setText("Products type:");
		//btns area
		Button btnSubmit = new Button(composite_5, SWT.CENTER);
		btnSubmit.setBounds(0, 253, 91, 32);
		btnSubmit.setText("Find");
		
		btnSubmit.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			currentProducts.addAll(handler.
					findByText(textName.getText(),cProductsType.getText(),styledTextDescription.getText()));
			fillTable();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		Button btnClear = new Button(composite_5, SWT.NONE);
		btnClear.setText("Clear All");
		btnClear.setBounds(0, 291, 91, 32);
		btnClear.addSelectionListener(btnDelete());
		
		Button btnAdd = new Button(composite_5, SWT.NONE);
		btnAdd.setBounds(0, 329, 91, 32);
		btnAdd.setText("Add");
		btnAdd.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
				AProduct temp = createAproduct(cProductsType.getText(), textName.getText(), styledTextDescription.getText());
				currentProducts.add(temp);
				fillTable();
				}catch(Exception e) {
					modalDialog(shell, display,"Products type could not empty!");
					e.printStackTrace();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button btnJson = new Button(composite_5, SWT.NONE);
		btnJson.setBounds(0, 185, 91, 26);
		btnJson.setText("To Json");
		btnJson.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					if(currentProducts.size() == 0) {
						modalDialog(shell, display, "Table is empty!");
					}else {
						handler.makeJson(currentProducts);
						modalDialog(shell,display,"Sucsessfully");}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});



		Composite composite_6 = new Composite(shell, SWT.NONE);
		composite_6.setLayoutData(BorderLayout.EAST);
		composite_6.setLayout(new GridLayout(1, false));
		
		//table area
		table = new Table(composite_6, SWT.VIRTUAL | SWT.BORDER);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));		
		table.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TableItem item =(TableItem)arg0.item;
				currentProducts.remove(currentProducts.removeIf(p->p.getName().equals(item.getText())));
				fillTable();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		TableColumn nameColum = new TableColumn(table, SWT.NONE);
		nameColum.setWidth(100);
		nameColum.setText("Name");
		
		TableColumn typeColum = new TableColumn(table, SWT.NONE);
		typeColum.setWidth(100);
		typeColum.setText("Type");
		
		TableColumn descriptionColumn = new TableColumn(table, SWT.NONE);
		descriptionColumn.setWidth(140);
		descriptionColumn.setText("Description");
		shell.open();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	

	/**
	 * fill table by type of products
	 * @param table
	 * @param types
	 * @param map
	 */
	private void fillTable() {
		table.removeAll();
		for(AProduct e: currentProducts) {
			item = new TableItem(table,SWT.NONE);
			item.setText(e.getItem());
		}
	}

	
	private SelectionListener btnDelete() {
		return new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				currentProducts.clear();
				fillTable();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	private void modalDialog(Shell shell, Display display,String message) {
		Shell alert = new Shell(shell,SWT.DIALOG_TRIM |SWT.APPLICATION_MODAL);
		alert.setLayout(new RowLayout());
		alert.setSize(200, 200);
		final Label not = new Label(alert, SWT.ERROR);
		not.setText(message);
		final Button ok = new Button(alert, SWT.APPLICATION_MODAL);
		ok.setText("Ok");
		Listener listener = new Listener() {
			 public void handleEvent(Event event) {
			        alert.close();
			      }
			    };
		ok.addListener(SWT.Selection, listener);
		alert.pack();
		alert.open();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private AProduct createAproduct (String type,String name,String description) {
		switch (type) {
		case "SoC":return new SOC(name,description);
		case "DevKit":return new DevKit(name,description);
		case "Module":return new Module(name, description);
		default:break;
		}
		return null;
	}
	
	public static void main(String [] args) throws Exception {
		new MainWindow();	
	}
}
