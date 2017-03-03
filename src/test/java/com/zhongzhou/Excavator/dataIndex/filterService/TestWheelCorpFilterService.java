/**
 * 
 */
/**
 * @author zhanghuanping
 *
 */
package com.zhongzhou.Excavator.dataIndex.filterService;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.zhongzhou.common.util.BeanUtil;

public class TestWheelCorpFilterService{
	
	@Test
	public void test() throws JsonParseException, JsonMappingException, IOException{
		
		String mainProductsFilters = ""
				+ "[\"Alloy wheel\",\"Steel wheel\",\"steel rim\",\"wire wheel\",\"trailer wheel\","
				+ "\"steel wheel rims\",\"trailer wheel rims\",\"wheel/rim\",\"Forklift Wheel\",\"Tubeless Wheel\", "
				+ "\"Tractor Wheel\",\"Wheel Rim\",\"Wheel Hub\",\"Automobile Wheel Hub\"]";
		
		List<String> test = BeanUtil.beanJaksonUnSerializer( mainProductsFilters.getBytes(), List.class );
		
		System.out.println( test );
	}
}