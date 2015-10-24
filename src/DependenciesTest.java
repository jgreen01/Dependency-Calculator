import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

public class DependenciesTest {

	@Test
	public void createObject() {
		assertNotNull("Making sure the object can be created.", new Dependencies());
	}
	
	@Test
	public void addAndRetrieve() { // Will all methods work with simple input?
		Dependencies test = new Dependencies();
		HashSet<String> testDepend = new HashSet<String>(Arrays.asList("B"));
		HashSet<String> expected = new HashSet<String>(testDepend);
		test.add("A",testDepend);
		
		assertEquals("Given 'A' is the root and 'B' is its dependency.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void twoRootsWithDuplicatedDependencies(){
		Dependencies test = new Dependencies();
		HashSet<String> testDeps1 = new HashSet<String>(Arrays.asList("B", "C")),
				testDeps2 = new HashSet<String>(Arrays.asList("C", "D"));
		HashSet<String> expected = new HashSet<String>(Arrays.asList("B", "C", "D"));
		test.add("A", testDeps1);
		test.add("B", testDeps2);
		
		assertEquals("Given 'A' and 'B' as roots and 'B' as a dependency of 'A' and both depends on 'C'.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void twoRootsWithDependencies(){
		Dependencies test = new Dependencies();
		HashSet<String> testDeps1 = new HashSet<String>(Arrays.asList("B", "C")),
				testDeps2 = new HashSet<String>(Arrays.asList("D", "E"));
		HashSet<String> expected = new HashSet<String>(Arrays.asList("B", "C", "D", "E"));
		test.add("A", testDeps1);
		test.add("C", testDeps2);
		
		assertEquals("Given 'A' and 'C' as roots and 'C' as a dependency of 'A'.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void multipleIndependentRoots(){
		Dependencies test = new Dependencies();
		HashSet<String> testDepsA = new HashSet<String>(Arrays.asList("C", "Z")),
				testDepsC = new HashSet<String>(Arrays.asList("E", "D")),
				testDepsG = new HashSet<String>(Arrays.asList("H", "J", "X")),
				testDepsO = new HashSet<String>(Arrays.asList("q", "w", "e", "r", "t"));
		HashSet<String> expected = new HashSet<String>(Arrays.asList("C", "D", "E", "Z"));
		test.add("A", testDepsA);
		test.add("C", testDepsC);
		test.add("G", testDepsG);
		test.add("O", testDepsO);
		
		assertEquals("Given 'A' and 'C' as roots and 'C' as a dependency of 'A' and both of unordered depends.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void multipleDependentRoots(){
		Dependencies test = new Dependencies();
		HashSet<String> testDepsA = new HashSet<String>(Arrays.asList("C", "Z")),
				testDepsC = new HashSet<String>(Arrays.asList("E", "D")),
				testDepsZ = new HashSet<String>(Arrays.asList("H", "J", "X")),
				testDepsJ = new HashSet<String>(Arrays.asList("q", "w", "e", "r", "t"));
		HashSet<String> expected = new HashSet<String>(Arrays.asList("C", "D", "E", "e", "H", "J", "q", "r", "t", "w", "X", "Z"));
		test.add("A", testDepsA);
		test.add("C", testDepsC);
		test.add("Z", testDepsZ);
		test.add("J", testDepsJ);
		
		assertEquals("Given 'A', 'C', 'Z', and 'J' as roots and that 'A' depends on 'C' and 'Z' and 'J' depends on 'Z'.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void circularDependencies(){ // Official Test
		Dependencies test = new Dependencies();
		HashSet<String> testDepsA = new HashSet<String>(Arrays.asList("B")),
				testDepsB = new HashSet<String>(Arrays.asList("C")),
				testDepsC = new HashSet<String>(Arrays.asList("A"));
		HashSet<String> expected = new HashSet<String>(Arrays.asList("B", "C"));
		test.add("A", testDepsA);
		test.add("B", testDepsB);
		test.add("C", testDepsC);
		
		assertEquals("Give 'A', 'B', and 'C' as roots where 'A' depends on 'B', 'B' on 'C', and 'C' on 'A'",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void noDependencies(){
		Dependencies test = new Dependencies();
		HashSet<String> testDepsA = new HashSet<String>(Arrays.asList()),
				testDepsB = new HashSet<String>(Arrays.asList("C"));
		HashSet<String> expected = new HashSet<String>(Arrays.asList());
		test.add("A", testDepsA);
		test.add("B", testDepsB);
		
		assertEquals("Give 'A' and 'B' as roots where 'A' has no dependencies.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void rootDoesNotExist(){
		Dependencies test = new Dependencies();
		HashSet<String> testDepsA = new HashSet<String>(Arrays.asList()),
				testDepsB = new HashSet<String>(Arrays.asList("C"));
		HashSet<String> expected = new HashSet<String>(Arrays.asList());
		test.add("A", testDepsA);
		test.add("B", testDepsB);
		
		assertEquals("Give 'Z' is not a root.",
				expected, test.dependsFor("Z"));
	}
	
	@Test
	public void noData(){
		Dependencies test = new Dependencies();
		HashSet<String> expected = new HashSet<String>(Arrays.asList());
		
		assertEquals("Give test has no roots.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void dependsOnRootWithNoDependencies(){
		Dependencies test = new Dependencies();
		HashSet<String> testDepsA = new HashSet<String>(Arrays.asList()),
				testDepsB = new HashSet<String>(Arrays.asList("C", "A"));
		HashSet<String> expected = new HashSet<String>(Arrays.asList("A", "C"));
		test.add("A", testDepsA);
		test.add("B", testDepsB);
		
		assertEquals("Give 'A' and 'B' as roots where 'B' depends on 'A' and 'A' has no dependencies.",
				expected, test.dependsFor("B"));
	}
	
	@Test
	public void multipleCircularDependencies(){
		Dependencies test = new Dependencies();
		HashSet<String> testDepsA = new HashSet<String>(Arrays.asList("B", "C", "D")),
				testDepsOthers = new HashSet<String>(Arrays.asList("A"));
		HashSet<String> expected = new HashSet<String>(Arrays.asList("B", "C", "D"));
		test.add("A", testDepsA);
		test.add("B", testDepsOthers);
		test.add("C", testDepsOthers);
		test.add("D", testDepsOthers);
		
		assertEquals("Give 'A' and others as roots where 'A' depends on others and all others depend on 'A'.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void multipleCircularDependenciesWithMultipleDependentRoots(){
		Dependencies test = new Dependencies();
		HashSet<String> testDepsA = new HashSet<String>(Arrays.asList("B", "C", "D")),
				testDepsC = new HashSet<String>(Arrays.asList("E", "D", "Z")),
				testDepsZ = new HashSet<String>(Arrays.asList("H", "J", "X")),
				testDepsJ = new HashSet<String>(Arrays.asList("q", "w", "e", "r", "t", "A"));
		HashSet<String> expected = new HashSet<String>(Arrays.asList("B", "C", "D", "E", "e", "H", "J", "q", "r", "t", "w", "X", "Z"));
		test.add("A", testDepsA); // depends on C
		test.add("C", testDepsC); // depends on Z
		test.add("Z", testDepsZ); // depends on J
		test.add("J", testDepsJ); // depends on A
		
		assertEquals("Give 'A', 'C', 'Z', and 'J' as roots where all are circularly dependent chain dependencies.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void complexInterdependentRoots(){
		Dependencies test = new Dependencies();
		HashSet<String> testDepsA = new HashSet<String>(Arrays.asList("B", "C", "D", "J", "Z")),
				testDepsC = new HashSet<String>(Arrays.asList("E", "D", "Z", "A", "J")),
				testDepsZ = new HashSet<String>(Arrays.asList("H", "J", "X", "C", "A")),
				testDepsJ = new HashSet<String>(Arrays.asList("q", "w", "e", "r", "t", "A", "C", "Z"));
		HashSet<String> expected = new HashSet<String>(Arrays.asList("B", "C", "D", "E", "e", "H", "J", "q", "r", "t", "w", "X", "Z"));
		test.add("A", testDepsA);
		test.add("C", testDepsC);
		test.add("Z", testDepsZ);
		test.add("J", testDepsJ);
		
		assertEquals("Give 'A', 'C', 'Z', and 'J' as roots where all depend on eachother.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void selfDependentRoot(){ // roots can't have themselves as dependencies
		Dependencies test = new Dependencies();
		HashSet<String> testDepsA = new HashSet<String>(Arrays.asList("A", "B", "C"));
		HashSet<String> expected = new HashSet<String>(Arrays.asList("B", "C"));
		test.add("A", testDepsA);
		
		assertEquals("Given 'A' as a root that is dependent on itself.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void multipleSelfDependentAndInterdependentRoots(){
		Dependencies test = new Dependencies();
		HashSet<String> testDepsA = new HashSet<String>(Arrays.asList("A", "B", "C")),
				testDepsB = new HashSet<String>(Arrays.asList("A", "B", "C")),
				testDepsC = new HashSet<String>(Arrays.asList("A", "B", "C"));
		HashSet<String> expected = new HashSet<String>(Arrays.asList("B", "C"));
		test.add("A", testDepsA);
		test.add("B", testDepsB);
		test.add("C", testDepsC);
		
		assertEquals("Given 'A', 'B', 'C' as roots that is dependent on itselves and eachother.",
				expected, test.dependsFor("A"));
	}

}
