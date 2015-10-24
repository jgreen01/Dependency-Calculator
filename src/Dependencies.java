import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Dependencies {
	
	private HashMap<String,HashSet<String>> data;

	public Dependencies() {
		this.data = new HashMap<String,HashSet<String>>();
	}
	
	public void add(String root, HashSet<String> depends){
		this.data.put(root, depends);
	}
	
	public HashSet<String> dependsFor(String root){
		if(!this.data.containsKey(root)) // handles non-existing roots
			return new HashSet<String>();
		
		HashSet<String> result = _dependsFor(this.data.get(root), 1);
		result.remove(root); // roots can't have themselves as dependencies
		return result;
	}
	
	private HashSet<String> _dependsFor(HashSet<String> set, int count){
		if(count > this.data.size()) // handles runaway recursion in circular dependencies
			return new HashSet<String>();
		
		HashSet<String> depends = new HashSet<String>();
		
		set.forEach(item -> {
			if(this.data.containsKey(item)){
				depends.addAll(_dependsFor(this.data.get(item), count+1));
			}
			depends.add(item);
		});
		
		return depends;
	}
}
