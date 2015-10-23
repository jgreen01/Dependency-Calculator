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
	public void twoRootsWithDependencies(){
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
	public void twoRootsWithDuplicatedDependencies(){
		Dependencies test = new Dependencies();
		List<String> testDeps1 = Arrays.asList("B", "C"),
				testDeps2 = Arrays.asList("C", "D"),
				expected = Arrays.asList("B", "C", "D");
		test.add("A", testDeps1);
		test.add("B", testDeps2);
		
		assertEquals("Given 'A' and 'B' as roots and 'B' as a dependency of 'A' and both depends on 'C'.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void twoRootsWithUnorderedDependencies(){ // result should be ordered alphabetically
		Dependencies test = new Dependencies();
		List<String> testDeps1 = Arrays.asList("C", "Z"),
				testDeps2 = Arrays.asList("E", "D"),
				expected = Arrays.asList("C", "D", "E", "Z");
		test.add("A", testDeps1);
		test.add("C", testDeps2);
		
		assertEquals("Given 'A' and 'C' as roots and 'C' as a dependency of 'A' and both of unordered depends.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void multipleIndependentRoots(){
		Dependencies test = new Dependencies();
		List<String> testDeps1 = Arrays.asList("C", "Z"),
				testDeps2 = Arrays.asList("E", "D"),
				testDeps3 = Arrays.asList("H", "J", "X"),
				testDeps4 = Arrays.asList("q", "w", "e", "r", "t"),
				expected = Arrays.asList("C", "D", "E", "Z");
		test.add("A", testDeps1);
		test.add("C", testDeps2);
		test.add("G", testDeps3);
		test.add("O", testDeps4);
		
		assertEquals("Given 'A' and 'C' as roots and 'C' as a dependency of 'A' and both of unordered depends.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void multipleDependentRoots(){
		Dependencies test = new Dependencies();
		List<String> testDeps1 = Arrays.asList("C", "Z"),
				testDeps2 = Arrays.asList("E", "D"),
				testDeps3 = Arrays.asList("H", "J", "X"),
				testDeps4 = Arrays.asList("q", "w", "e", "r", "t"),
				expected = Arrays.asList("C", "D", "E", "e", "H", "J", "q", "r", "t", "w", "X", "Z");
		test.add("A", testDeps1);
		test.add("C", testDeps2);
		test.add("Z", testDeps3);
		test.add("J", testDeps4);
		
		assertEquals("Given 'A' and 'C' as roots and 'C' as a dependency of 'A' and both of unordered depends.",
				expected, test.dependsFor("A"));
	}

}
