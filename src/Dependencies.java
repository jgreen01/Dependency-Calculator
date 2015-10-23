import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Dependencies {
	
	private HashMap data = new HashMap<String,List<String>>();

	public Dependencies() {
		
	}
	
	public void add(String root, List<String> depends){
		this.data.put(root, depends);
	}
	
	public List<String> dependsFor(String root){
		List<String> rootDependsRef = ((List<String>) this.data.get(root));
		List<String> depends = new ArrayList(rootDependsRef);
		
		rootDependsRef.forEach(item -> {
			if(this.data.containsKey(item)){
				((List<String>) this.data.get(item)).forEach(d -> {
					System.out.println(d.toString());
					if(!depends.contains(d)){
						depends.add(d);
					}
				});
			}
		});
		Collections.sort(depends, String.CASE_INSENSITIVE_ORDER);
		System.out.println(depends.toString());
		return depends;
	}
}
