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
		List<String> testDepend = Arrays.asList("B");
		HashSet<String> expected = new HashSet<String>(testDepend);
		test.add("A",testDepend);
		
		assertEquals("Given 'A' is the root and 'B' is its dependency.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void twoRootsWithDuplicatedDependencies(){
		Dependencies test = new Dependencies();
		List<String> testDeps1 = Arrays.asList("B", "C"),
				testDeps2 = Arrays.asList("C", "D");
		HashSet<String> expected = new HashSet<String>(Arrays.asList("B", "C", "D"));
		test.add("A", testDeps1);
		test.add("B", testDeps2);
		
		assertEquals("Given 'A' and 'B' as roots and 'B' as a dependency of 'A' and both depends on 'C'.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void twoRootsWithDependencies(){
		Dependencies test = new Dependencies();
		List<String> testDeps1 = Arrays.asList("B", "C"),
				testDeps2 = Arrays.asList("D", "E");
		HashSet<String> expected = new HashSet<String>(Arrays.asList("B", "C", "D", "E"));
		test.add("A", testDeps1);
		test.add("C", testDeps2);
		
		assertEquals("Given 'A' and 'C' as roots and 'C' as a dependency of 'A'.",
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
				expected, test.listDependsFor("A"));
	}
	
	@Test
	public void multipleIndependentRoots(){
		Dependencies test = new Dependencies();
		List<String> testDepsA = Arrays.asList("C", "Z"),
				testDepsC = Arrays.asList("E", "D"),
				testDepsG = Arrays.asList("H", "J", "X"),
				testDepsO = Arrays.asList("q", "w", "e", "r", "t");
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
		List<String> testDepsA = Arrays.asList("C", "Z"),
				testDepsC = Arrays.asList("E", "D"),
				testDepsZ = Arrays.asList("H", "J", "X"),
				testDepsJ = Arrays.asList("q", "w", "e", "r", "t");
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
		List<String> testDepsA = Arrays.asList("B"),
				testDepsB = Arrays.asList("C"),
				testDepsC = Arrays.asList("A");
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
		List<String> testDepsA = Arrays.asList(),
				testDepsB = Arrays.asList("C");
		HashSet<String> expected = new HashSet<String>(Arrays.asList());
		test.add("A", testDepsA);
		test.add("B", testDepsB);
		
		assertEquals("Give 'A' and 'B' as roots where 'A' has no dependencies.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void rootDoesNotExist(){
		Dependencies test = new Dependencies();
		List<String> testDepsA = Arrays.asList(),
				testDepsB = Arrays.asList("C");
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
		List<String> testDepsA = Arrays.asList(),
				testDepsB = Arrays.asList("C", "A");
		HashSet<String> expected = new HashSet<String>(Arrays.asList("A", "C"));
		test.add("A", testDepsA);
		test.add("B", testDepsB);
		
		assertEquals("Give 'A' and 'B' as roots where 'B' depends on 'A' and 'A' has no dependencies.",
				expected, test.dependsFor("B"));
	}
	
	@Test
	public void multipleCircularDependencies(){
		Dependencies test = new Dependencies();
		List<String> testDepsA = Arrays.asList("B", "C", "D"),
				testDepsOthers = Arrays.asList("A");
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
		List<String> testDepsA = Arrays.asList("B", "C", "D"),
				testDepsC = Arrays.asList("E", "D", "Z"),
				testDepsZ = Arrays.asList("H", "J", "X"),
				testDepsJ = Arrays.asList("q", "w", "e", "r", "t", "A");
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
		List<String> testDepsA = Arrays.asList("B", "C", "D", "J", "Z"),
				testDepsC = Arrays.asList("E", "D", "Z", "A", "J"),
				testDepsZ = Arrays.asList("H", "J", "X", "C", "A"),
				testDepsJ = Arrays.asList("q", "w", "e", "r", "t", "A", "C", "Z");
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
		List<String> testDepsA = Arrays.asList("A", "B", "C");
		HashSet<String> expected = new HashSet<String>(Arrays.asList("B", "C"));
		test.add("A", testDepsA);
		
		assertEquals("Given 'A' as a root that is dependent on itself.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void multipleSelfDependentAndInterdependentRoots(){
		Dependencies test = new Dependencies();
		List<String> testDepsA = Arrays.asList("A", "B", "C"),
				testDepsB = Arrays.asList("A", "B", "C"),
				testDepsC = Arrays.asList("A", "B", "C");
		HashSet<String> expected = new HashSet<String>(Arrays.asList("B", "C"));
		test.add("A", testDepsA);
		test.add("B", testDepsB);
		test.add("C", testDepsC);
		
		assertEquals("Given 'A', 'B', 'C' as roots that is dependent on itselves and eachother.",
				expected, test.dependsFor("A"));
	}
	
	@Test
	public void complexInterdependentRootsListOutput(){
		Dependencies test = new Dependencies();
		List<String> testDepsA = Arrays.asList("B", "C", "D", "J", "Z"),
				testDepsC = Arrays.asList("E", "D", "Z", "A", "J"),
				testDepsZ = Arrays.asList("H", "J", "X", "C", "A"),
				testDepsJ = Arrays.asList("q", "w", "e", "r", "t", "A", "C", "Z"),
				expected = Arrays.asList("B", "C", "D", "E", "e", "H", "J", "q", "r", "t", "w", "X", "Z");
		test.add("A", testDepsA);
		test.add("C", testDepsC);
		test.add("Z", testDepsZ);
		test.add("J", testDepsJ);
		
		assertEquals("Give 'A', 'C', 'Z', and 'J' as roots where all depend on eachother.",
				expected, test.listDependsFor("A"));
	}

}
