import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Dependencies {
	
	private HashMap<String,HashSet<String>> data;

	public Dependencies() {
		this.data = new HashMap<String,HashSet<String>>();
	}
	
	public void add(String vertex, HashSet<String> edges){
		this.data.put(vertex, edges);
	}
	
	public HashSet<String> dependsFor(String root){
		if(!this.data.containsKey(root)) // handles non-existing vertices
			return new HashSet<String>();
		
		HashSet<String> result = dfs(this.data.get(root), 
				new HashSet<String>(Arrays.asList(root)));
		result.remove(root); // roots can't have themselves as dependencies
		return result;
	}
	
	private HashSet<String> dfs(HashSet<String> nextEdges, HashSet<String> visited){
		
		HashSet<String> edgeSet = new HashSet<String>();
		
		nextEdges.forEach(edge -> {
			if(visited.contains(edge)) // handles circular dependencies
				return; // skips this edge
			
			if(this.data.containsKey(edge)){
				visited.add(edge);
				edgeSet.addAll(dfs(this.data.get(edge), visited));
			}
			edgeSet.add(edge);
		});
		
		return edgeSet;
	}
}
