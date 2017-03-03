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

public class RegTester{
	
	@Test
	public void testReg(){
		
		String source = "2016 2.5 Million";
		
		source = source.replace(",", "");
		
		//String regex = "[$](\\d+\\.*\\d*).*[~-]\\W*[$](\\d+\\.*\\d*)+";
		String regex = "(\\d+\\.*\\d*)\\s*[million|Million|MILLION]";
		Pattern reger= Pattern.compile(regex);
		
		Matcher m = reger.matcher( source );	
		while(m.find()){
			
			System.out.println( m.group() );
		}
	}
}