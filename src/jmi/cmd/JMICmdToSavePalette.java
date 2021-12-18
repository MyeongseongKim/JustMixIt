package jmi.cmd;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import jmi.JMIApp;
import jmi.JMIPaint;
import x.XApp;
import x.XLoggableCmd;

public class JMICmdToSavePalette extends XLoggableCmd {
    //field
	
    //private constructor
    private JMICmdToSavePalette(XApp app) {
        super(app);
    }
    
    public static boolean execute(XApp app) {
        JMICmdToSavePalette cmd = 
            new JMICmdToSavePalette(app);
        return cmd.execute();
    }
    
    @Override
    protected boolean defineCmd() {
        JMIApp app = (JMIApp) this.mApp;
		ArrayList<JMIPaint> customPaints = app.getPaintMgr().getCustomPaints();
		String colors = "";
		for (JMIPaint c : customPaints) {
			if (c.getColor() == null) {
				colors += "null\n";
			} else {
				Integer r = c.getColor().getRed();
				Integer g = c.getColor().getGreen();
				Integer b = c.getColor().getBlue();
				Integer a = c.getColor().getAlpha();
				colors += String.format("%02x%02x%02x%02x\n", r, g, b, a);
			}
		}
		
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

		if (fileChooser.showSaveDialog(app.getFrame()) == 
			JFileChooser.APPROVE_OPTION) {
			try (FileWriter fw = new FileWriter(fileChooser.getSelectedFile() + 
					".xml")) {
				fw.write(colors);
				fw.close();
			} catch (IOException ex) {
				return false;
			}
			return true;
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
