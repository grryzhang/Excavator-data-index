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
		
		String source = "#Template-List-start( let copindexed as $ )";
		
		source = source.replace(",", "");
		
		//String regex = "[$](\\d+\\.*\\d*).*[~-]\\W*[$](\\d+\\.*\\d*)+";
		String regex = "\\(\\s*let\\s+\\S+\\s+as\\s+\\$+.*\\)";
		Pattern reger= Pattern.compile(regex);
		
		Matcher m = reger.matcher( source );	
		while(m.find()){
			
			System.out.println( m.group() );
		}
	}
}