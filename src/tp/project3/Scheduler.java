package tp.project3;

import java.awt.EventQueue;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultCaret;

public class Scheduler extends JFrame implements Runnable{

private String simTime;
private String finalSimTime;
private List<Task> tasks;
private int maxNoServers;
private PriorityQueue<Server> servers;
private JPanel printingServers;
private JScrollPane scroll;
private JTextArea console;

 public Scheduler(int maxNoServers, int clientNo, String finalSimTime, String minProcessing, String maxProcessing) {
	 TaskGenerator tg = new TaskGenerator(minProcessing, maxProcessing);
	 this.maxNoServers = maxNoServers;
	 this.finalSimTime = finalSimTime;
	 this.tasks = Collections.synchronizedList(new ArrayList<Task>());
	 
	 for(int i = 0; i < clientNo; i++) {
	 this.addTask(tg.generateTask());
	 }
	 
	 this.servers = new PriorityQueue<Server>(maxNoServers);
	 this.printingServers = new JPanel();
	 this.console = new JTextArea("Console:");

	 DefaultCaret caret = (DefaultCaret)console.getCaret();
	 caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
     this.scroll = new JScrollPane();
     this.scroll.setViewportView(this.console);
	 this.printingServers.setLayout(new BoxLayout(this.printingServers, BoxLayout.PAGE_AXIS));
	 this.printingServers.add(this.scroll);
	 this.add(printingServers);
	 
	 this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.LINE_AXIS));
		this.setSize(900, 460);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setFocusable(true);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
 }

public void addTask(Task t) {
		this.tasks.add(t);
}

@Override
public void run() {
	Future<String> f;
	ExecutorService es = Executors.newFixedThreadPool(maxNoServers);
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);
	setSimTime(formatter.format(LocalTime.now()));
	//System.out.println(getSimTime());
	while(!simTime.equals(finalSimTime)) {
		this.revalidate();
		setSimTime(formatter.format(LocalTime.now()));

				for(Task t : tasks) {
					if(t.getArrivalTime().equals(simTime)) {
				Server s = new Server(t);
				f = es.submit(s);
				servers.add(s);
					
		}
	}
	consoleText(Server.getConsole());
	try {
		//iteration++;
		Thread.sleep(1000);
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm", Locale.US);
		setSimTime(formatter2.format(LocalTime.now()));
		System.out.println(getSimTime());
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	//Thread.currentThread().getClass();
	}

public void consoleText(final String consoleUpdate){
	 EventQueue.invokeLater(new Runnable() {
	    public void run() {
	      console.setText("Console:\n" + consoleUpdate);
	    }
	 });
}

public int getPeakHour() {
	ArrayList<Integer> arrivalHours = new ArrayList<Integer>();
	int max = 0;
	int hour = 0;
	for(Task t : tasks)
	arrivalHours.add(LocalTime.parse(t.getArrivalTime()).getHour());
	for(int i = 0; i < arrivalHours.size(); i++) {
		if (max < Collections.frequency(arrivalHours, arrivalHours.get(i))) {
			max = Collections.frequency(arrivalHours, arrivalHours.get(i));
			hour = arrivalHours.get(i);
		}
	}
	return hour;
}

public double getAvgWaitTime() {
	double waitTime;
	int sum = 0;
	for(Task t : tasks)
		sum = sum + LocalTime.parse(t.getProcessingPeriod()).getSecond();
	waitTime = sum/tasks.size();
	return waitTime;
}

public String getSimTime() {
	return simTime;
}


public void setServers(PriorityQueue<Server> servers) {
	this.servers = servers;
}

public void setSimTime(String simTime) {
	this.simTime = simTime;
}

}
