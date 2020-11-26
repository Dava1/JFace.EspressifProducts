package Products;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetDevKitS implements IGetProductFrom {

	@Override
	public List<AProduct> getProduct(Map<String, List<String>> map) {
		List<AProduct> list = new ArrayList<AProduct>();
		List<String> dList = new ArrayList<String>();
		for( String e : map.keySet() ) {
			StringBuffer temp = new StringBuffer();
			for(String s:map.get(e)) {
				dList.add(s);
				temp.append(s+"\n");
			};
			DevKit devKit =new DevKit(e,temp.toString()); 
			devKit.setDescriptionList(dList);
			list.add(devKit);
		}
		return list;
	}

}
