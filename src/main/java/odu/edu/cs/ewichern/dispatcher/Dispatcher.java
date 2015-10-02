package odu.edu.cs.ewichern.dispatcher;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

public class Dispatcher {

	int maxPriority = 0;
	private static final int MAX_PROCESSES = 99;

	private ArrayList<ProcessQueue> priorityQueues = new ArrayList<ProcessQueue>();

	private SortedMap<Integer, Process> processList = new TreeMap<Integer, Process>();
	private ArrayList<Process> blockedList = new ArrayList<Process>();
	private ArrayList<Process> terminatedList = new ArrayList<Process>();

	private static Process activeProcess;

	public Dispatcher() {
		priorityQueues.add(new ProcessQueue(0));
	}

	public Dispatcher(int numPriorityLevels) {
		for (int i = 0; i < numPriorityLevels; i++) {
			priorityQueues.add(new ProcessQueue(i));
		}
		maxPriority = numPriorityLevels - 1;
	}

	public int getMaxPriority() {
		return maxPriority;
	}
	
	public Object[] queueArray() {
		return priorityQueues.toArray();
	}
	
	public Object[] blockedArray() {
		return blockedList.toArray();
	}

	public Object[] terminatedArray() {
		return terminatedList.toArray();
	}
	
	public Process getActiveProcess() {
		return activeProcess;
	}
	
	public Process getByPID(int PID) {
		return processList.get(PID);
	}
	
	public void createProcess(int priorityInput, String nameInput) {
		Process temp;
		
		if (priorityInput >= 0 && priorityInput <= maxPriority) {
			temp = priorityQueues.get(priorityInput).createProcess(nameInput);
			processList.put(temp.getPID(), temp);
		} else {
			throw new IndexOutOfBoundsException("Invalid priority level.");
		}
		
		runNextProcess();
	}
	
	public void createProcess(int priorityInput) {
		Process temp;
		
		if (priorityInput >= 0 && priorityInput <= maxPriority) {
			temp = priorityQueues.get(priorityInput).createProcess();
			processList.put(temp.getPID(), temp);
		} else {
			throw new IndexOutOfBoundsException("Invalid priority level.");
		}
		
		runNextProcess();
	}
	
	public boolean queueProcess(Process process) {
		process.setState(State.READY);
		int processPriority = process.getPriority();
		boolean success = false;

		if (activeProcess == process) {
			activeProcess = null;
		}
		
		if (processPriority >= 0 && processPriority <= maxPriority) {
			success = priorityQueues.get(processPriority).addProcess(process);
			// processList.add(process);
		} else {
			throw new IndexOutOfBoundsException("Invalid priority level.");
		}

		runNextProcess();
		return success;
	}

	public boolean killProcess(Process process) {
		process.setState(State.TERMINATED);
		int processPriority = process.getPriority();
		terminatedList.add(process);
		boolean success = false;

		if (activeProcess == process) {
			activeProcess = null;
			success = true;
		} else {
			success = priorityQueues.get(processPriority).removeProcess(process);
		}

		runNextProcess();
		return success;
	}

	public boolean blockProcess(Process process) {
		process.setState(State.BLOCKED);
		int processPriority = process.getPriority();
		blockedList.add(process);
		boolean success = false;

		if (activeProcess == process) {
			activeProcess = null;
			success = true;
		} else {
			success = priorityQueues.get(processPriority).removeProcess(process);
		}

		runNextProcess();
		return success;
	}

	private void runNextProcess() {
		for (ProcessQueue queue : priorityQueues) {
			if (activeProcess == null)
				activeProcess = queue.getNextProcess();
		}
	}

	@Override
	public String toString() {
		return "Dispatcher [maxPriority=" + maxPriority + ", priorityQueues=" + priorityQueues + ", blockedList="
				+ blockedList + ", terminatedList=" + terminatedList + ", runningProcess=" + activeProcess + "]";
	}
	
	

}
