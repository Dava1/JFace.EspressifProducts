package Products;

import java.util.List;

public class SOC extends AProduct{
private String name;
private String description;
private TypeOfProducts type;
private IDescriptionToList descriptionList;
public SOC(String name,String description) {
		this.name = name;
		this.description=description;
		this.type =TypeOfProducts.SoC;
		this.descriptionList = new DescriptionToList();
}

public List<String> getListDescription(){
	return descriptionList.getDescriptionList();
}
public void setDescriptionList(List<String> list) {
	descriptionList.setDescriptionList(list);
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

}
