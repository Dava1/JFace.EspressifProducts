package Products;

import java.util.List;
import java.util.Map;

public interface IGetProductFrom {
	public List<AProduct>  getProduct (Map<String,List<String>> map);
}
