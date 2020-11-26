package jface.espressifproducts;

import java.util.Map;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;

import jface.espressifproducts.ui.MainWindow;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {
 
	@Override
	public Object start(IApplicationContext context) throws Exception {
		MainWindow start = new MainWindow();
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// nothing to do
	}
}
