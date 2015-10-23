import static org.junit.Assert.*;

import org.junit.Test;

public class DependenciesTest {

	@Test
	public void createObject() {
		assertNotNull("Making sure the object can be created.", new Dependencies());
	}
	
	@Test
	public void addAndRetrieve() { // Will all the methods with simple input?
		Dependencies test = new Dependencies();
		String[] testDepend = new String[] {"B"};
		test.add("A",testDepend);
		
		assertEquals("Given 'A' is the root and 'B' is its dependency.",
				testDepend, test.dependsFor("A"));
	}
	
	@Test
	public void multipleRootsWithDepends(){
		Dependencies test = new Dependencies();
		String[] testDeps1 = new String[] {"B", "C"},
				testDeps2 = new String[] {"D", "E"},
				expected = new String[] {"B", "C", "D", "E"};
		test.add("A", testDeps1);
		test.add("C", testDeps2);
		
		assertEquals("Given 'A' and 'C' as roots and 'C' as a dependency of 'A'.",
				expected, test.dependsFor("A"));
	}
	
	/*@Test
	public void multipleRootsWithDuplicatedDependencies(){
		Dependencies test = new Dependencies();
		String[] testDeps1 = new String[] {"B", "C"},
				testDeps2 = new String[] {"C", "D"},
				expected = new String[] {"B", "C", "D"};
		test.add("A", testDeps1);
		test.add("B", testDeps2);
		
		assertEquals("Given 'A' and 'B' as roots and 'B' as a dependency of 'A' and both depends on 'C'.",
				expected, test.dependsFor("A"));
	}*/

}
