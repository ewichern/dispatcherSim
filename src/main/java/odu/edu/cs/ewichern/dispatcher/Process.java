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
 * byte value, where lower values == higher priority PID will be assigned
 * automatically by a static class variable counter
 * 
 * @author Erik Wichern
 *
 */

public class Process {

	private byte priority = 2;
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
		name = "" + PID;
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
	 *            byte providing a priority for the process
	 */
	public Process(byte priorityInput) {
		processCount += 1;

		PID = processCount;
		name = "" + PID;
		priority = priorityInput;
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
	 *            byte providing a priority for the process
	 */
	public Process(String nameInput, byte priorityInput) {
		processCount += 1;

		PID = processCount;
		name = nameInput;
		priority = priorityInput;
	}

	// TODO write getters and setters
	public byte getPriority() {
		return priority;
	}

	// should not be able to change priority after instantiation
	// public void setPriority( byte priorityInput ) {
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

	@Override
	public String toString() {
		return "Process [PID=" + PID + ", state=" + state + ", priority=" + priority + ", name=" + name + "]";
	}

	// for printing overload toString
	// public toString()
}
