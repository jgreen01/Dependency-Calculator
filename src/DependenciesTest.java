import static org.junit.Assert.*;

import org.junit.Test;

public class DependenciesTest {

	@Test
	public void createObject() {
		assertNotNull("Making sure the object can be created.", new Dependencies());
	}
	
	@Test
	public void addAndRetrieve() { // Will all the methods with simple input
		Dependencies test = new Dependencies();
		String[] testDepend = new String[] {"B"};
		test.add("A",testDepend);
		
		assertEquals("Given 'A' is the root and 'B' is its dependency.",testDepend, test.dependsFor("A"));
	}

}
