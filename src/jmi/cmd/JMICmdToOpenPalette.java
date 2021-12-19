package jmi.cmd;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import jmi.JMIApp;
import jmi.JMIPaint;
import x.XApp;
import x.XLoggableCmd;


public class JMICmdToOpenPalette extends XLoggableCmd {
    //field

	//private constructor
    private JMICmdToOpenPalette(XApp app) {
        super(app);
    }
    
    public static boolean execute(XApp app) {
        JMICmdToOpenPalette cmd = 
            new JMICmdToOpenPalette(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;
		ArrayList<JMIPaint> customPaints = new ArrayList<JMIPaint>();
		
		JFileChooser fileChooser = new JFileChooser();
		String home = System.getProperty("user.home");
		fileChooser.setCurrentDirectory(new File(home + "/Desktop"));
		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.getName().endsWith(".xml") || f.isDirectory();
			}

			@Override
			public String getDescription() {
				return "XML Files";
			}
		};
		fileChooser.setFileFilter(filter);

		if (fileChooser.showOpenDialog(app.getFrame()) == 
			JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			try {
				FileReader filereader = new FileReader(file);
				try (BufferedReader bufReader = new BufferedReader(filereader))
				{
					String line;
					while ((line = bufReader.readLine()) != null) {
						if ("null".equals(line)) {
							customPaints.add(new JMIPaint(null));
						} else {
							customPaints.add(new JMIPaint(new Color(
								Integer.parseInt(line.substring(0, 2), 16),
								Integer.parseInt(line.substring(2, 4), 16),
								Integer.parseInt(line.substring(4, 6), 16),
								Integer.parseInt(line.substring(6, 8), 16)
							)));
						}
					}
				}
				app.getPaintMgr().setCustomPaints(customPaints);
			} catch (FileNotFoundException ex) {
				return false;
			} catch (IOException ex) {
				return false;
			}
		}
		return false;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName());
        return sb.toString();
    }
}
