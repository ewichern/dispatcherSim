package odu.edu.cs.ewichern.dispatcher;

/**
 * A simulated process.
 * 
 * Contains identifying and state information, including:
 * <ul>
 * <li>PID
 * <li>Name
 * <li>Priority
 * <li>Process state (running / ready / blocked)
 * <ul>
 * 
 * state is an enum with possible values RUNNING, READY, BLOCKED priority is a
 * int value, where lower values == higher priority PID will be assigned
 * automatically by a static class variable counter
 * 
 * @author Erik Wichern
 *
 */

public class Process {

	private int priority = 2;
	private int PID;
	private State state = State.READY;
	private String name = "";

	private static int processCount = 0;

	/**
	 * Process default constructor
	 * 
	 * processCount is incremented. PID is assigned the current processCount.
	 * priority is 2 by default (expected range is 0-2) state default is READY
	 * name default is the PID
	 */
	public Process() {
		processCount += 1;

		PID = processCount;
		name = "process_" + PID;
	}

	/**
	 * Process constructor with custom name
	 * 
	 * processCount is incremented. PID is assigned the current processCount.
	 * priority is 2 by default (expected range is 0-2) state default is READY
	 * name assigned according to input
	 * 
	 * @param nameInput
	 *            String to name the process
	 */
	public Process(String nameInput) {
		processCount += 1;

		PID = processCount;
		name = nameInput;
	}

	/**
	 * Process constructor with custom priority
	 * 
	 * processCount is incremented. PID is assigned the current processCount.
	 * state default is READY name default is the PID priority is assigned
	 * according to input (expected range is 0-2)
	 * 
	 * @param priorityInput
	 *            int providing a priority for the process
	 */
	public Process(int priorityInput) {
		processCount += 1;

		PID = processCount;
		name = "process_" + PID;
		priority = (int)priorityInput;
	}

	/**
	 * Process constructor with custom priority
	 * 
	 * processCount is incremented. PID is assigned the current processCount.
	 * state default is READY name assigned according to input priority is
	 * assigned according to input (expected range is 0-2)
	 * 
	 * @param nameInput
	 *            String to name the process
	 * @param priorityInput
	 *            int providing a priority for the process
	 */
	public Process(int priorityInput, String nameInput) {
		processCount += 1;

		PID = processCount;
		name = nameInput;
		priority = (int)priorityInput;
	}

	// TODO write getters and setters
	public int getPriority() {
		return priority;
	}

	// should not be able to change priority after instantiation
	// public void setPriority( int priorityInput ) {
	// priority = priorityInput;
	// }

	public int getPID() {
		return PID;
	}

	// should not be able to change PID
	// public void setPID( int PIDInput ) {
	// PID = PIDInput;
	// }

	public State getState() {
		return state;
	}

	public void setState(State stateInput) {
		state = stateInput;
	}

	public String getName() {
		return name;
	}

	public void setName(String nameInput) {
		name = nameInput;
	}
	
	public String[] stringArray() {
		String[] output = new String[4];
		output[0] = "PID = " + ((Integer)PID).toString();
		output[1] = "State = " + state.toString();
		output[2] = "Priority = " + ((Integer)priority).toString();
		output[3] = "Name = " + name;
		return output;
	}
	
	public String shortString() {
		return PID + " - " + name;
	}

	@Override
	public String toString() {
		return "Process [PID=" + PID + ", state=" + state + ", priority=" + priority + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + PID;
		result = prime * result + priority;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Process other = (Process) obj;
		if (PID != other.PID)
			return false;
		if (priority != other.priority)
			return false;
		return true;
	}

}
