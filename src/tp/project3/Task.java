package tp.project3;


public class Task {
private String arrivalTime, finishTime, processingPeriod;

public Task(String arrivalTime, String finishTime, String processingPeriod) {
	this.arrivalTime = arrivalTime;
	this.finishTime = finishTime;
	this.processingPeriod = processingPeriod;
}

public Task(String arrivalTime, String processingPeriod) {
	this.arrivalTime = arrivalTime;
	this.processingPeriod = processingPeriod;
}

public String getArrivalTime() {
	return arrivalTime;
}

public void setArrivalTime(String arrivalTime) {
	this.arrivalTime = arrivalTime;
}

public String getFinishTime() {
	return finishTime;
}

public void setFinishTime(String finishTime) {
	this.finishTime = finishTime;
}

public String getProcessingPeriod() {
	return processingPeriod;
}

public void setProcessingPeriod(String processingPeriod) {
	this.processingPeriod = processingPeriod;
}

@Override
public String toString() {
	return "Task [arrivalTime=" + arrivalTime + ", processingPeriod=" + processingPeriod + "]";
}



}
