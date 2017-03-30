/**
 * 
 */
/**
 * @author zhanghuanping
 *
 */
package FunctionalTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.zhongzhou.Excavator.dataIndex.model.WebDataMongoData;

import junit.framework.Assert;

public class RegTester{
	
	@Test
	public void testReg(){
		
		String sources [] = { "test", "testjpg" , "test.jpg" } ;
		
		//String regex = "[$](\\d+\\.*\\d*).*[~-]\\W*[$](\\d+\\.*\\d*)+";
		String regex = "^(?!.*?\\.jpg).*$";
		Pattern reger= Pattern.compile(regex);
		
		for( String source : sources ){
			
			source = source.replace(",", "");
			Matcher m = reger.matcher( source );	
			String test = null;
			while(m.find()){
				test = m.group();
				System.out.println( m.group() );
			}
		}
	}
}