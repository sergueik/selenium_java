package example;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.google.gson.JsonElement;

import example.Parser;

public class ArrayIndexPathNodeTest {

	@Test
	public void test_indexes() {
		String data = "[1,2,3,4,5,6,7,8,9,10]";
		Object[][] cases = { { "$[0]", 1 }, { "$[9]", 10 }, { "$[10]", null }, { "$[-1]", 10 }, { "$[-9]", 2 },
				{ "$[-10]", 1 }, { "$[-11]", null }, };

		Parser parser = new Parser();
		for (int i = 0; i < cases.length; i++) {
			List<JsonElement> res = parser.parseExpression("" + cases[i][0]).exec(data);
			if (res.isEmpty()) {
				assertNull(cases[i][1]);
			} else {
				assertEquals(Integer.valueOf("" + cases[i][1]).intValue(), res.get(0).getAsInt());
			}
		}

	}

}
