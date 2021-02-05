package example;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.google.gson.JsonElement;

import example.JsonPathException;
import example.Parser;
import example.nodetypes.SlicePathNode;

public class SlicePathNodeTest {
	
	SlicePathNode node = null;

	@Test(expected=JsonPathException.class)
	public void test_RangeIndexPathNode_can_not_have_zero_step() {
		new SlicePathNode(0, 10, 0);
	}
	
	@Test
	public void test_all_fine(){
		String json = "[1,2,3,4,5,6,7,8,9,10]";
		
		Object[][] cases = new Object[][]{
				// exp   ,   count, value of first(optional)
				{"$[0:9]",       9,   1},
				{"$[:9]",        9,   1},
				{"$[0:9]",       9,   1},
				{"$[:9]",        9,   1},
				{"$[0:-1]",      9,   1},
				{"$[:-1]",       9,   1},
				
				{"$[1:]",        9,   2},
				{"$[-9:]",       9,   2},
				{"$[::]",        10,  1},

				{"$[2:4]",       2,   3},
				

				{"$[2:-6]",      2,   3},
				{"$[-8:-6]",     2,   3},
				{"$[-8:4]",      2,   3},
				{"$[-4:-2]",     2,   7},


				{"$[4:2:-1]",    2,   5},
				
				{"$[9::-1]",     10, 10},
				{"$[-1::-1]",    10, 10},
				{"$[-1:-11:-1]", 10, 10},
				{"$[:6:2]",      3,   1},

				{"$[1:9:3]",     3,   2},
				{"$[:5:-1]",     4,  10},
				{"$[:-4:-1]",    3,  10},
				{"$[3::-1]",     4,   4},
				{"$[-8::-1]",    3,   3},
				{"$[3:7:2]",     2,   4}, // 4 6
		};
		process_data(json, cases);
	}

	/**
	 * 
	 * @param json
	 * @param cases - array of arrays {expression, result count, value of first(optional - use null)
	 */
	private void process_data(String json, Object[][] cases) {
		Parser parser = new Parser();
		for (int i = 0; i < cases.length; i++) {
			Object[] data = cases[i];
			if(data.length<2){
				continue;
			}
			// System.out.println(data[0].toString());
			List<JsonElement> nodes = parser.parseExpression(data[0].toString()).exec(json);
			assertEquals(Integer.valueOf(data[1].toString()).intValue(), nodes.size());
			if(nodes.size()>0 && data.length>2 && data[2]!=null){
				assertEquals(Integer.valueOf(data[2].toString()).intValue(), nodes.get(0).getAsInt());				
			}
		}
	} 
	
	@Test
	public void test_all_empty_ranges(){
		String json = "[1,2,3,4,5,6,7,8,9,10]";
		
		Object[][] cases = new Object[][]{
			    // expression, count, value of first(optional)
				{"$[10:]",        0, null},
				{"$[:-10]",       0, null},
				{"$[2:1]",        0, null},
				{"$[-1:-2]",      0, null},
				
				{"$[-11::-1]",    0, null},
				{"$[:9:-1]",      0, null},
				{"$[1:2:-1]",     0, null},
				{"$[-2:-1:-1]",   0, null},
		};
		process_data(json, cases);
	}
	
	
	@Test
	public void test_all_bigger_ranges(){
		String json = "[1,2,3,4,5,6,7,8,9,10]";
		// @formatter:off
		
		Object[][] cases = new Object[][]{
			    // expression, count, value of first(optional)
				{"$[0:13]",       10,    1},
				{"$[:13]",        10,    1},
				{"$[:14]",        10,    1},
				{"$[-13:-1]",      9,    1},
				{"$[:-15]",        0, null},
				
				{"$[100:]",        0, null},
				{"$[-99:]",       10,    1},
				{"$[::]",         10,    1},

				{"$[20:40]",       0, null},

				{"$[20:-60]",      0, null},
				{"$[-80:-60]",     0, null},
				{"$[-80:40]",     10,    1},
				{"$[-40:-20]",     0, null},

				
				{"$[40:20:-10]",   0, null},
				
				{"$[90::-10]",     1,   10},
				{"$[-10::-10]",    1,    1},
				{"$[-10:-110:-10]",1,    1},
				{"$[:60:20]",      1,    1},

				{"$[1:90:30]",     1,    2},
				{"$[:50:-10]",     0, null},
				{"$[:-40:-10]",    1,   10},
				{"$[30::-10]",     1,   10},
				{"$[-80::-10]",    0, null},
				{"$[30:70:20]",    0, null}, // 4 6
		};
		// @formatter:on
		process_data(json, cases);
	}
	
	
}
