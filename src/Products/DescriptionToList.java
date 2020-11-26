package Products;

import java.util.List;

public class DescriptionToList implements IDescriptionToList{
	private List<String> list;
	@Override
	public List<String> getDescriptionList() {
		return list;
	}

	@Override
	public void setDescriptionList(List<String> list) {
		this.list = list;
	}

}
