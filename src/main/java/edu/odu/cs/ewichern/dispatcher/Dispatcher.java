package edu.odu.cs.ewichern.dispatcher;

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

	public ArrayList<String> getBlockList() {
		ArrayList<String> output = new ArrayList<String>();
		for (Process p : blockedList) {
			output.add(p.shortString());
		}
		return output;
	}

	public Object[] terminatedArray() {
		return terminatedList.toArray();
	}

	public ArrayList<String> getTerminated() {
		ArrayList<String> output = new ArrayList<String>();
		for (Process p : terminatedList) {
			output.add(p.shortString());
		}
		return output;
	}

	public Process getActiveProcess() {
		return activeProcess;
	}

	public Process getByPID(int PID) {
		return processList.get(PID);
	}

	public void createProcess() {
		Process temp;

		temp = priorityQueues.get(0).createProcess();
		processList.put(temp.getPID(), temp);
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

	public boolean queueProcess(Process process) {
		int processPriority = process.getPriority();
		boolean success = false;

		if (process.getState() == State.BLOCKED) {
			blockedList.remove(process);
		}

		if (activeProcess == process) {
			activeProcess = null;
		}

		process.setState(State.READY);

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
		int processPriority = process.getPriority();
		boolean success = false;

		if(process.getState() == State.BLOCKED) {
			blockedList.remove(process);
		}
		
		if (activeProcess == process) {
			activeProcess = null;
			success = true;
		} else {
			success = priorityQueues.get(processPriority).removeProcess(process);
		}

		process.setState(State.TERMINATED);
		terminatedList.add(process);
		
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
			if (activeProcess == null) {
				activeProcess = queue.getNextProcess();
			} else {
				activeProcess.setState(State.RUNNING);
			}
		}
	}

	@Override
	public String toString() {
		return "Dispatcher [maxPriority=" + maxPriority + ", priorityQueues=" + priorityQueues + ", blockedList="
				+ blockedList + ", terminatedList=" + terminatedList + ", runningProcess=" + activeProcess + "]";
	}

}
