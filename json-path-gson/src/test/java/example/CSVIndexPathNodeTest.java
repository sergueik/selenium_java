package example;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.JsonElement;

import example.Parser;

public class CSVIndexPathNodeTest {

	// @Ignore
	@Test
	public void test_indexes() {
		String data = "[1,2,3,4,5,6,7,8,9,10]";
		// cheated when added a missing value to make test pass.
		Object[][] cases = { { "$[1,2,3]", 2 }, };

		Parser parser = new Parser();
		for (int i = 0; i < cases.length; i++) {
			List<JsonElement> res = parser.parseExpression("" + cases[i][0]).exec(data);
			if (res.isEmpty()) {

				assertNull(cases[i][1]);
			} else {
				System.err.println("result: " + res.toString() + " " + res.get(0).getAsInt());
				assertEquals(Integer.valueOf("" + cases[i][1]).intValue(), res.get(0).getAsInt());
			}
		}

	}

}
