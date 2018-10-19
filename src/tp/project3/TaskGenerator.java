package tp.project3;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class TaskGenerator {
public String minProcessingPeriod, maxProcessingPeriod, currentTime;

public TaskGenerator(String minProcessing, String maxProcessing) {
	//LocalTime lt = LocalTime.MIN.plusMinutes(3);
	this.minProcessingPeriod = minProcessing;
	//lt = LocalTime.MIN.plusMinutes(15);
	this.maxProcessingPeriod = maxProcessing;
	LocalTime lt = LocalTime.now();
	this.currentTime = lt.toString();
}

public Task generateTask() {
	Random r = new Random();
	LocalTime min = LocalTime.parse(minProcessingPeriod);
	LocalTime max = LocalTime.parse(maxProcessingPeriod);
	LocalTime lt = LocalTime.MIN.plusSeconds(r.nextInt(max.getSecond())+min.getSecond());
	String processingPeriod = lt.toString();
	lt = LocalTime.now().plusSeconds(r.nextInt(10)+1);
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);
	String arrivalTime = formatter.format(lt);
	Task t = new Task(arrivalTime, processingPeriod);
	return t;
}

}
