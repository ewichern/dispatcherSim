package odu.edu.cs.ewichern.dispatcher;

import edu.odu.cs.ewichern.dispatcher.Process;
import edu.odu.cs.ewichern.dispatcher.State;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit tests for Process Class
 * 
 * @author Erik Wichern
 */
public class ProcessTest extends TestCase {

	private String name;
	private int priority;
	private final int DEFAULT_PRIORITY = 2;
	
	protected void setUp () {
		name = "processName";
		priority = 1;
	}
	
	/**
	 * Create the test case
	 * 
	 * @param testName	name of the test case
	 */
	public ProcessTest( String testName ) {
		super( testName );
	}

	/**
	 * Run the suite of tests on this class
	 * 
	 * @return 	the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite( ProcessTest.class );
	}
	
	public void testConstructor() {
//		fail ("Not written yet.");
		Process process = new Process();
		assertFalse(process == null);
	}
		
	public void testNameConstructor() {
		Process process = new Process(name);
		assertFalse(process == null);
		assertEquals(name, process.getName());
		assertEquals(DEFAULT_PRIORITY, process.getPriority());
		assertEquals(State.READY, process.getState());
	}
	
	public void testPriorityConstructor() {
		Process process = new Process(priority);
		assertFalse(process == null);
		assertEquals(priority, process.getPriority());
		assertEquals(State.READY, process.getState());
	}
	
	public void testBothConstructor() {
		Process process = new Process(priority, name);
		assertFalse(process == null);
		assertEquals(name, process.getName());
		assertEquals(priority, process.getPriority());
		assertEquals(State.READY, process.getState());
	}
	
	// TODO write tests for getters/setters (once written)


}
