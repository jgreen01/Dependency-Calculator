import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Dependencies {
	
	private HashMap<String,List<String>> data;

	public Dependencies() {
		this.data = new HashMap<String,List<String>>();
	}
	
	public void add(String root, List<String> depends){
		this.data.put(root, depends);
	}
	
	public List<String> listDependsFor(String root){
		HashSet<String> result = _dependsFor(root, 1);
		result.remove(root); // roots can't have themselves as dependencies
		List<String> depList = new ArrayList<String>(result);
		Collections.sort(depList, String.CASE_INSENSITIVE_ORDER);
		return depList;
	}
	
	public HashSet<String> dependsFor(String root){
		HashSet<String> result = _dependsFor(root, 1);
		result.remove(root); // roots can't have themselves as dependencies
		return result;
	}
	
	private HashSet<String> _dependsFor(String root, int count){
		if(count > this.data.size()) // handles runaway recursion in circular dependencies
			return new HashSet<String>();
		if(!this.data.containsKey(root)) // handles non-existing roots
			return new HashSet<String>();
		
		List<String> rootDependsRef = ((List<String>) this.data.get(root));
		HashSet<String> depends = new HashSet<String>();
		
		rootDependsRef.forEach(item -> {
			if(this.data.containsKey(item)){
				depends.addAll(_dependsFor(item, count+1));
			}
			depends.add(item);
		});
		
		return depends;
	}
}
