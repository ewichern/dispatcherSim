package odu.edu.cs.ewichern.dispatcher;

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
	private byte priority;
	
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
		Process proc = new Process();
		assertFalse(proc == null);
	}
	
	// TODO write tests for 4 constructors
	
	// TODO write tests for getters/setters (once written)


}
