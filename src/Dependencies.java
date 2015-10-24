import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

public class Dependencies {
	
	private HashMap<String,List<String>> data;

	public Dependencies() {
		this.data = new HashMap<String,List<String>>();
	}
	
	public void add(String root, List<String> depends){
		this.data.put(root, depends);
	}
	
	public List<String> dependsFor(String root){
		List<String> deps = _dependsFor(root, 1);
		Collections.sort(deps, String.CASE_INSENSITIVE_ORDER);
		return new ArrayList<String>(new LinkedHashSet<String>(deps)); // handles multiple circular dependencies by removing duplicates
	}
	
	private List<String> _dependsFor(String root, int count){
		if(count > this.data.size()) // handles circular dependencies 
			return Arrays.asList();
		if(!this.data.containsKey(root)) // handles non-existing roots
			return Arrays.asList();
		
		List<String> rootDependsRef = ((List<String>) this.data.get(root));
		List<String> depends = new ArrayList<String>();
		
		rootDependsRef.forEach(item -> {
			if(this.data.containsKey(item)){
				depends.addAll(_dependsFor(item, count+1));
			}
			if(!depends.contains(item)){
				depends.add(item);
			}
		});
		
		return depends;
	}
}
