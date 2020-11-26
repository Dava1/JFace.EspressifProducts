package Products;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import HtmlGrabber.ParserHtmlProducts;
import HtmlGrabber.ParserHtmlSeriesOfProducts;


public class ProductsHandler {
	
	private String path;
	private List<AProduct> products;
	private IGetProductFrom getProductSocs;
	private IGetProductFrom getProductModule;
	private IGetProductFrom getProductDev;
	private ParserHtmlProducts parser;
	private String idOfMain = "block-system-main";
	
	public ProductsHandler(String url) throws Exception {
		products = new ArrayList<AProduct>();
		parser = new ParserHtmlProducts(url,idOfMain);
		products = getAllProducts();
	}
	
	public Map<String,String> getSeriesSoc (String url, String idOfMain) throws Exception{
		ParserHtmlSeriesOfProducts temp = new ParserHtmlSeriesOfProducts(url, idOfMain);
		return temp.getSocsProduct();
	}
	public Map<String,String> getSeriesModules (String url, String idOfMain) throws Exception{
		ParserHtmlSeriesOfProducts temp = new ParserHtmlSeriesOfProducts(url, idOfMain);
		return temp.getModulesProduct();
	}
	
	public Map<String,String> getSeriesDevkits (String url, String idOfMain) throws Exception{
		ParserHtmlSeriesOfProducts temp = new ParserHtmlSeriesOfProducts(url, idOfMain);
		return temp.getDevKitsProduct();
	}
	
	
	public List<AProduct> getSocs() throws Exception{
	getProductSocs = new GetSoCs();
	Map<String, List<String>> map = parser.getSocsProduct();
	return getProductSocs.getProduct(map);
	}

	
	public List<AProduct> getDevKits() throws Exception{
		getProductDev = new GetDevKitS();
		Map<String, List<String>> map = parser.getDevKitsProduct();
		return getProductDev.getProduct(map);
		}
	
	public List<AProduct> getModules() throws Exception{
		getProductModule = new GetModule();
		Map<String, List<String>> map = parser.getModulesProduct();
		return getProductModule.getProduct(map);
		}
//done dont touch this!
	public void makeJson(List<AProduct> list) throws Exception {
		JSONObject obj = new JSONObject();
		JSONArray prop = new JSONArray();
		FileWriter file = new FileWriter("/home/davinci/file.json");
		StringBuffer json= new StringBuffer();
		for(AProduct str : list) {
			obj.put("Type",str.getType());
			obj.put("Name",str.getName());
			prop = new JSONArray(str.getListDescription());
			obj.put("properties", prop);
			json.append(obj.toString()+"\n");
		}
		try {
		 
		file.write(json.toString());
		}catch(IOException ex) {
			ex.printStackTrace();
		}finally {
		try {
			file.flush();
			file.close();	
		}catch(IOException e) {
			e.printStackTrace();
		}
		}
	}		
	
	public StringBuffer loadObjects() {
		StringBuffer temp = new StringBuffer();
		try {
		FileReader file = new FileReader("src/file.json");
		int i = 0;
		while((i = file.read())!=-1) {
			temp.append((char)i);
			}
			file.close();						
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	} 
	


	private static String subStringType(StringBuffer str,String start,String end) {
		int s = str.indexOf(start);
		int e = str.indexOf(end);
		StringBuffer temp = new StringBuffer(str.substring(s,e));
		temp.toString().replaceAll("\"", "");
		return temp.toString().replaceAll(":","");
	}

	public AProduct makeProductFromJson(StringBuffer sb) {
		String type = subStringType(sb, ":", ",");
		String properties =subStringType(sb, "[", "]");
		String name = subStringType(sb, "]", "}");
		name=name.replaceAll("],", "");
		name = replaceExcess(name);
		properties =replaceExcess(properties);
		Module mod = new Module(name.replaceAll("\"", ""), properties.replaceAll("\"", ""));
		return mod;
	}
	public String replaceExcess(String s) {
		String replacement ="";
		String g =s.replaceAll("Name", replacement);
		return g.trim();
	}
	

	
	public List<AProduct> getAllProducts() throws Exception{
		products.addAll(getSocs());
		products.addAll(getModules());
	products.addAll(getDevKits());
	return products;	
	}
	
	public List<AProduct> findByText(String n,String t,String d){	
		return products.stream().filter(p -> p.getName().contains(n))
								.filter(p->p.getDescription().contains(d))
								.filter(p -> p.getType().contains(t))
								.collect(Collectors.toList());
	}
}
