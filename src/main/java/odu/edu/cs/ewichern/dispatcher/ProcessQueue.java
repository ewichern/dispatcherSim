package odu.edu.cs.ewichern.dispatcher;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

public class ProcessQueue implements Iterable<Process>{

	private static final int INITIAL_QUEUE_SIZE = 20;
	private int priority; // matches priority of processes placed in queue
	private Queue<Process> processQueue = new ArrayDeque<Process>(INITIAL_QUEUE_SIZE);

	// No default constructor
	// public ProcessQueue() {
	// }

	public ProcessQueue(int priorityInput) {
		priority = (int)priorityInput;
	}

	public int getPriority() {
		return priority;
	}

	public boolean addProcess(Process process) {
		boolean success = processQueue.add(process);
		return success;
	}
	
	public boolean removeProcess(Process process) {
		boolean success = processQueue.remove(process);
		return success;
	}

	public Process getNextProcess() {
		Process next;
		next = processQueue.poll();
		return next;
	}

	public void clearQueue() {
		processQueue.clear();
	}
	
	public boolean isEmpty() {
		return processQueue.isEmpty();
	}
	
	public int size () {
		return processQueue.size();
	}
	
	public Iterator<Process> iterator() {
		return processQueue.iterator();
	}
	
	public Object[] toArray() {
		return processQueue.toArray();
	}

	@Override
	public String toString() {
		return "ProcessQueue [priority=" + priority + ", processQueue=" + processQueue + "]";
	}
	
}
