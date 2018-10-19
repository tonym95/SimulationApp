package tp.project3;


import java.awt.EventQueue;
import java.time.LocalTime;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class Server implements Callable<String>, Comparable {

private AtomicInteger waitingPeriod;
private long id;
private BlockingQueue<Task> tasks = new ArrayBlockingQueue<Task>(6);
private String serverInfo;
private static JTextArea console = new JTextArea();

	public Server(Task t) {
		this.setWaitingPeriod(new AtomicInteger());
		//this.getId().set(Thread.currentThread().getId());
		this.addTask(t);
	}
	
	public void addTask(Task t) {
		this.getWaitingPeriod().incrementAndGet();
		this.tasks.add(t);
	}
	
	public Task processTask() {
		try {
			return this.tasks.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public AtomicInteger getWaitingPeriod() {
		return waitingPeriod;
	}

	public void setWaitingPeriod(AtomicInteger waitingPeriod) {
		this.waitingPeriod = waitingPeriod;
	}

	public BlockingQueue<Task> getTasks() {
		return tasks;
	}

	public void setTasks(BlockingQueue<Task> tasks) {
		this.tasks = tasks;
	}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public static String getConsole() {
		return console.getText();
	}

	@Override
	public String call() throws Exception {
		//while(!tasks.isEmpty()) {
			Task t = processTask();
			setId(Thread.currentThread().getId());
			consoleText("Server #" + getId() + "\n" + "Arrival time: " +
						t.getArrivalTime() + "\n" + 
						t.getProcessingPeriod() + "\n" + "\n");
			try {
				LocalTime lt = LocalTime.parse(t.getProcessingPeriod());
				int sec = lt.toSecondOfDay();
				//serverInfo.set(toString());
				Thread.sleep(sec*1000);
				waitingPeriod.decrementAndGet();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//}
		return getServerInfo();
		//return this.waitingPeriod;
	}

	public static void consoleText(final String consoleUpdate){
		 EventQueue.invokeLater(new Runnable() {
		    public void run() {
		      console.append(consoleUpdate);
		    }
		 });
	}
	
	@Override
	public String toString() {
		return getServerInfo();
	}

	@Override
	public int compareTo(Object o) {
		Server s = (Server) o;
		int compare = this.getWaitingPeriod().intValue() - s.getWaitingPeriod().intValue();
		return compare;
	}

	public String getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(String serverInfo) {
		this.serverInfo = serverInfo;
	}

}
