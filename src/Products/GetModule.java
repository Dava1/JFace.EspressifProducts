package Products;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetModule implements IGetProductFrom{

	@Override
	public List<AProduct> getProduct(Map<String, List<String>> map) {
		List<AProduct> list = new ArrayList<AProduct>();
		List<String> dList = new ArrayList<String>();
		for(String p:map.keySet() ) {
		StringBuffer temp =new StringBuffer();	
			for(String v:map.get(p)) {
				dList.add(v);
				temp.append(v+"\n");
			}
			Module module = new Module(p, temp.toString());
			module.setDescriptionList(dList);
			list.add(module);
		}
		return list;
	}



}
