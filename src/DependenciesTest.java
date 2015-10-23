import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class DependenciesTest {

	@Test
	public void createObject() {
		assertNotNull("Making sure the object can be created.", new Dependencies());
	}
	
	@Test
	public void addAndRetrieve() { // Will all the methods with simple input?
		Dependencies test = new Dependencies();
		List<String> testDepend = Arrays.asList("B");
		test.add("A",testDepend);
		
		assertEquals("Given 'A' is the root and 'B' is its dependency.",
				testDepend, test.dependsFor("A"));
	}
	
	@Test
	public void multipleRootsWithDepends(){
		Dependencies test = new Dependencies();
		List<String> testDeps1 = Arrays.asList("B", "C"),
				testDeps2 = Arrays.asList("D", "E"),
				expected = Arrays.asList("B", "C", "D", "E");
		test.add("A", testDeps1);
		test.add("C", testDeps2);
		
		assertEquals("Given 'A' and 'C' as roots and 'C' as a dependency of 'A'.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void multipleRootsWithDuplicatedDependencies(){
		Dependencies test = new Dependencies();
		List<String> testDeps1 = Arrays.asList("B", "C"),
				testDeps2 = Arrays.asList("C", "D"),
				expected = Arrays.asList("B", "C", "D");
		test.add("A", testDeps1);
		test.add("B", testDeps2);
		
		assertEquals("Given 'A' and 'B' as roots and 'B' as a dependency of 'A' and both depends on 'C'.",
				expected, test.dependsFor("A"));
	}

}
