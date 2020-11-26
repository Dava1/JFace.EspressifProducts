package Products;

import java.util.List;

public class DevKit extends AProduct{
	private String name;
	private String description;
	private TypeOfProducts type;
	private IDescriptionToList decrtiptionList;
	
	public DevKit(String name, String description) {
		this.name = name;
		this.description = description;
		this.type =TypeOfProducts.DevKit;
		this.decrtiptionList = new DescriptionToList();
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
