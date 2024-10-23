 package com.prod2.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.prod2.entity.ProductPojo;

@RestController
@RequestMapping(value="/product2")
public class Product2Controller {
	
	@Autowired
	RestTemplate rt;
	
	@GetMapping(value="/getproduct")
	public List<ProductPojo> getProduct() {
		String url1="http://localhost:9091/GST/getpercentage/";
		String url2="http://localhost:9095/product/getproducts";
		
		ResponseEntity<List<ProductPojo>> re2= rt.exchange(url2, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductPojo>>() {});
		List<ProductPojo> products = re2.getBody();
		
		products.forEach(x->{
			int h = x.getHsncode();
			ResponseEntity<Integer> re1 = rt.exchange(url1+h, HttpMethod.GET, null,Integer.class);
			int tax=re1.getBody();
			x.setPrice(x.getPrice()+(x.getPrice()*tax/100));
		});
		
		return products;
	}

}
