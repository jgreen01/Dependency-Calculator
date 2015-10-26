import java.util.HashMap;
import java.util.HashSet;

public class Dependencies {
	
	private HashMap<String,HashSet<String>> data;
	private HashSet<String> visited;

	public Dependencies() {
		this.data = new HashMap<String,HashSet<String>>();
		this.visited = new HashSet<>();
	}
	
	public void add(String vertex, HashSet<String> edges){
		this.data.put(vertex, edges);
	}
	
	public HashSet<String> dependsFor(String root){
		if(!this.data.containsKey(root)) // handles non-existing vertices
			return new HashSet<String>();
		
		this.visited.add(root);
		HashSet<String> result = dfs(this.data.get(root));
		this.visited = new HashSet<>(); // clear visited set
		
		result.remove(root); // roots can't have themselves as dependencies
		return result;
	}
	
	private HashSet<String> dfs(HashSet<String> nextEdges){
		
		HashSet<String> edgeSet = new HashSet<String>();
		
		nextEdges.forEach(edge -> {
			if(this.visited.contains(edge)) // handles circular dependencies
				return; // skips this edge
			
			if(this.data.containsKey(edge)){
				this.visited.add(edge);
				edgeSet.addAll(dfs(this.data.get(edge)));
			}
			edgeSet.add(edge);
		});
		
		return edgeSet;
	}
}
