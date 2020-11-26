package Products;

import java.util.List;
import java.util.Map;

public abstract class AProduct {
	private String name;
	private String description;
	private TypeOfProducts type;
	private IDescriptionToList decrtiptionList;
	
	public AProduct(){		
	}	

	public String getName() {	
		return name;
	}
	public String getDescription() {
		return description;
	}

	public String getType() {
		return type.toString();
	}
	
	public String[] getItem() {
		return new String[] {getName(),getType(),getDescription()};
	}
	
	public List<String> getListDescription(){
		return decrtiptionList.getDescriptionList();
	}
	public void setDescriptionList(List<String> list) {
		decrtiptionList.setDescriptionList(list);
	}
		
	}
