package odu.edu.cs.ewichern.dispatcher;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;

public class ProcessQueue {

	private static final int INITIAL_QUEUE_SIZE = 20;
	private byte priority; // matches priority of processes placed in queue
	private Queue<Process> processQueue = new ArrayDeque<Process>(INITIAL_QUEUE_SIZE);
	
	// No default constructor
//	public ProcessQueue() {
//	}
	
	public ProcessQueue( byte priorityInput ) {
		priority = priorityInput;
	}
	
	public byte getPriority() {
		return priority;
	}
	
	public boolean addProcess( Process newProcess ) {
		boolean success = processQueue.add(newProcess);
		return success;
	}
	
	public Process getNextProcess() {
		Process next;
		try {
			next = processQueue.remove();
		} catch (NoSuchElementException e) {
			throw e;
		}
		return next;
	}
	
}
