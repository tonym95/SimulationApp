package tp.project3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class ServerController {
	Scheduler s;
	ServerGUI gui;
	
	public ServerController() {
		gui = new ServerGUI();
		Scheduler s;
		gui.addSimListener(new SimListener());
	}
	
	public static void main(String args[]) {
		new ServerController();
	}
	
	class SimListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			s = new Scheduler(Integer.parseInt(gui.getTextField().getText()), Integer.parseInt(gui.getTextField_4().getText()), gui.getTextField_1().getText(),
					gui.getTextField_2().getText(), gui.getTextField_3().getText());
			gui.getTextArea().setText(Integer.toString(s.getPeakHour()));
			gui.getTextArea_1().setText(Double.toString(s.getAvgWaitTime()));
			Thread t = new Thread(s);
			t.start();
			
		}
		
	}
}
