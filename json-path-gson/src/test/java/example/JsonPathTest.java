package example;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import example.BracketsParser;
import example.JsonPathException;
import example.Parser;
import example.JsonPath2.Expression;
import example.iterators.PeekableIterator;
import example.nodetypes.ArrayIndexPathNode;
import example.nodetypes.CSVIndexPathNode;
import example.nodetypes.PathNode;
import example.nodetypes.RecursiveDescentPathNode;
import example.nodetypes.SlicePathNode;
import example.nodetypes.WildcardPathNode;

public class JsonPathTest {

	@Test
	public void test_parse_expressions() {
		String str = "$.aasas['.asd adsf adsf .asd asdf. asdf'].b.s.bb.c[1][2]['asas[2].hg'][0]";
		Parser jpath = new Parser();
		Expression expression = new Parser().parseExpression(str);
		assertEquals(10, expression.getNodes().size());

		str = "$.qwe.rty";
		List<PathNode> nodes = jpath.parseExpression(str).getNodes();
		assertEquals(2, nodes.size());

		str = "$.c['asas[2].hg']['0']['1\\'s\\\\ds\\ds']"; // java needs to double "\\" when inserting "\"
		assertEquals(4, jpath.parseExpression(str).getNodes().size());

		str = "$.s['1'][0]['d']";
		assertEquals(4, jpath.parseExpression(str).getNodes().size());

		str = "$['1']";
		assertEquals(1, jpath.parseExpression(str).getNodes().size());

		str = "$['1'][ 0]['d'].s.a[56 ].d['f']";
		nodes = jpath.parseExpression(str).getNodes();
		assertEquals(8, nodes.size());
		assertEquals("\"a\"", "" + nodes.get(4));
		assertEquals("" + 56, "" + nodes.get(5));
		assertEquals("\"d\"", "" + nodes.get(6));
	}

	@Test
	public void test_get_all_in_array() {
		String json = "[5,2,3,4]";
		Expression expression = new Parser().parseExpression("$[*]");
		List<JsonElement> nodes = expression.exec(json);
		assertEquals(4, nodes.size());
		assertEquals(5, nodes.get(0).getAsInt());
		assertEquals(2, nodes.get(1).getAsInt());
		assertEquals(3, nodes.get(2).getAsInt());
		assertEquals(4, nodes.get(3).getAsInt());

		json = "{'c':{'a':'d', 'c': 'e'}}";
		expression = new Parser().parseExpression("$.c[ * ]");// wrong! or not? :)
		assertEquals(2, expression.getNodes().size());
		nodes = expression.exec(json);
		assertEquals("\"d\"", "" + nodes.get(0));
		assertEquals("\"e\"", "" + nodes.get(1));

		List<JsonElement> elements;

		json = "{'c':[{'v':5},{'v':51},{'v':52},{'v': 'lold'}]}";
		expression = new Parser().parseExpression("$.c[*].v");
		elements = expression.exec(json);

		assertEquals(4, elements.size());
		assertEquals(5, elements.get(0).getAsInt());
		assertEquals(51, elements.get(1).getAsInt());
		assertEquals(52, elements.get(2).getAsInt());
		assertEquals("lold", elements.get(3).getAsString());

		json = "{'c':[{'v':5},{'v':51},{'v':52},{'v':{'d': 777}}]}";
		expression = new Parser().parseExpression("$.c[*].v.d");
		elements = expression.exec(json);
		assertEquals(1, elements.size());
		assertEquals(777, elements.get(0).getAsInt());

		json = "{'c':[{'v':5},{'v':{'d':{'x':'lol', 'c':2}}},{'v':52},{'v':{'d': [1,3,4]}}]}";
		expression = new Parser().parseExpression("$.c[*].v.d[*]");
		elements = expression.exec(json);
		assertEquals(5, elements.size());
		assertEquals("lol", elements.get(0).getAsString());
		assertEquals(4, elements.get(4).getAsInt());
	}

	@Test
	public void test_recursive_elemets_path_node_filter() {
		String json = "{'c':[{'v':5},{'v':51},{'v':52},{'v':{'d': 777}}]}";
		RecursiveDescentPathNode node = new RecursiveDescentPathNode();
		PeekableIterator<JsonElement> list = node.filter(new JsonParser().parse(json));
		ArrayList<JsonElement> res = new ArrayList<JsonElement>();
		while (list.hasNext()) {
			res.add(list.next());
		}
		assertEquals(10, res.size());

		assertEquals(5, res.get(2).getAsInt());
		assertEquals(777, res.get(9).getAsInt());
	}

	@Test
	public void test_recursive_elements_expression() {
		String json = "{'c':[{'v':5},{'v':51},{'v':52},{'c':{'v': 777}}], 'v':1}";
		Expression expression = new Parser().parseExpression("$.c..v");

		List<JsonElement> elements = expression.exec(json);

		assertEquals(4, elements.size());
		assertEquals(5, elements.get(0).getAsInt());
		assertEquals(51, elements.get(1).getAsInt());
		assertEquals(52, elements.get(2).getAsInt());
		assertEquals(777, elements.get(3).getAsInt());

		json = "{'c':[{'v':5},{'v':51},{'v':52},{'v':{'d': 777}}]}";
		expression = new Parser().parseExpression("$..v");
		elements = expression.exec(json);

		assertEquals(4, elements.size());

		expression = new Parser().parseExpression("$.c..v[1].d");
		assertEquals(5, expression.getNodes().size());
	}

	@Test
	public void test_descent_path_iterator() {
		RecursiveDescentPathNode node = new RecursiveDescentPathNode();
		PeekableIterator<JsonElement> test = node.filter(new JsonParser()
				.parse("{" + "'a':'b', " + "'c':'d', " + "'e':{}, " + "'f':{'g':'h'}, " + "'i':{'j':'k', 'l':'m'}, "
						+ "'n':{'o':'p', 'q':{'r':'s', 't':['y', {'v':'w', 'x':{'y':'z'}}]}, 'aa':'ab'}" + "}"));
		assertEquals("b", test.next().getAsJsonPrimitive().getAsString()); // "b"
		assertEquals("d", test.next().getAsJsonPrimitive().getAsString()); // "d"
		assertEquals(true, test.next().getAsJsonObject().entrySet().isEmpty()); // {}
		assertEquals("h", test.next().getAsJsonObject().get("g").getAsString()); // {"g":"h"}
		assertEquals("h", test.next().getAsJsonPrimitive().getAsString()); // "h"
		assertEquals("m", test.next().getAsJsonObject().get("l").getAsString()); // {"j":"k","l":"m"}
		assertEquals("k", test.next().getAsJsonPrimitive().getAsString()); // "k"
		assertEquals("m", test.next().getAsJsonPrimitive().getAsString()); // "m"
		assertEquals("p", test.next().getAsJsonObject().get("o").getAsString()); // {"o":"p","q":{"r":"s","t":["y",{"v":"w","x":{"y":"z"}}]},"aa":"ab"}
		assertEquals("p", test.next().getAsJsonPrimitive().getAsString()); // "p"
		/*
		 * {"r":"s","t":["y",{"v":"w","x":{"y":"z"}}]} "s" ["y",{"v":"w","x":{"y":"z"}}]
		 * "y" {"v":"w","x":{"y":"z"}} "w" {"y":"z"} "z" "ab"
		 */
		ArrayList<JsonElement> list = new ArrayList<JsonElement>();
		while (test.hasNext()) {
			list.add(test.next());
		}
		assertEquals(9, list.size());
	}

	@Test
	public void test_dot_after_brecket() {
		String str = "$.[0].[0]";
		Expression expression = new Parser().parseExpression(str);

		assertEquals(2, expression.getNodes().size());

		str = "$.a['b']['c']";
		expression = new Parser().parseExpression(str);
		assertEquals(3, expression.getNodes().size());
	}

	@Test(expected = JsonPathException.class)
	public void test_invalid_content_exception() {
		String str = "$.a[]";
		new Parser().parseExpression(str);
	}

	@Test(expected = JsonPathException.class)
	public void test_invalid_content_exception2() {
		String str = "$.a[   ]";
		new Parser().parseExpression(str);
	}

	@Test(expected = JsonPathException.class)
	public void test_invalid_content_exception3() {
		String str = "$.a[   ].d";
		new Parser().parseExpression(str);
	}

	public void test_brecket_parser_wildcard() {
		BracketsParser a = new BracketsParser(0);
		a.consumeAll("*]");
		assertTrue(WildcardPathNode.class.isInstance(a.getResult()));

		a = new BracketsParser(0);
		a.consumeAll("* ]");
		assertTrue(WildcardPathNode.class.isInstance(a.getResult()));
	}

	@Test
	public void test_brecket_parser_arrs() {

		BracketsParser a = new BracketsParser(0);
		//

		a = new BracketsParser(0);
		a.consumeAll("0]");
		assertTrue(ArrayIndexPathNode.class.isInstance(a.getResult()));

		a = new BracketsParser(0);
		a.consumeAll("0 ]");
		assertTrue(ArrayIndexPathNode.class.isInstance(a.getResult()));

		a = new BracketsParser(0);
		a.consumeAll("0,1]");
		assertTrue(CSVIndexPathNode.class.isInstance(a.getResult()));
		assertEquals(2, CSVIndexPathNode.class.cast(a.getResult()).getIndexes().size());

		a = new BracketsParser(0);
		a.consumeAll("0,1 ]");
		assertTrue(CSVIndexPathNode.class.isInstance(a.getResult()));
		assertEquals(2, CSVIndexPathNode.class.cast(a.getResult()).getIndexes().size());

		a = new BracketsParser(0);
		a.consumeAll("0, 1 ]");
		assertTrue(CSVIndexPathNode.class.isInstance(a.getResult()));
		assertEquals(2, CSVIndexPathNode.class.cast(a.getResult()).getIndexes().size());

		a = new BracketsParser(0);
		a.consumeAll("0 , 1 ]");
		assertTrue(CSVIndexPathNode.class.isInstance(a.getResult()));
		assertEquals(2, CSVIndexPathNode.class.cast(a.getResult()).getIndexes().size());

		a = new BracketsParser(0);
		a.consumeAll("0,1 ,2, 3, 4 ,5 ]");
		assertTrue(CSVIndexPathNode.class.isInstance(a.getResult()));
		assertEquals(6, CSVIndexPathNode.class.cast(a.getResult()).getIndexes().size());

		a = new BracketsParser(0);
		try {
			a.consumeAll("0,]");
			assertTrue(false);
		} catch (JsonPathException e) {
		}

		a = new BracketsParser(0);
		try {
			a.consumeAll("0,1, ]");
		} catch (JsonPathException e) {
		}

	}

	@Test
	public void test_ranges() {
		BracketsParser a = new BracketsParser(0);
		SlicePathNode casted;

		a.consumeAll(":22]");
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));

		a = new BracketsParser(0);
		a.consumeAll(":21 ]");
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));

		a = new BracketsParser(0);
		a.consumeAll(": 20 ]");
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));

		a = new BracketsParser(0);
		a.consumeAll(": 20 ]");
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));

		try {
			a = new BracketsParser(0);
			a.consumeAll(": 20a]");
			assertTrue(false);
		} catch (JsonPathException e) {
		}

		a = new BracketsParser(0);
		a.consumeAll("20:]");
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));

		a = new BracketsParser(0);
		a.consumeAll("20: ]");
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));

		a = new BracketsParser(0);
		a.consumeAll("20 : ]");
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));

		try {
			a = new BracketsParser(0);
			a.consumeAll("20,10: ]");
			assertTrue(false);
		} catch (JsonPathException e) {
		}

		try {
			a = new BracketsParser(0);
			a.consumeAll("20,10 : ]");
			assertTrue(false);
		} catch (JsonPathException e) {
		}

		a = new BracketsParser(0);
		a.consumeAll("20:13:5]");
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));
		casted = SlicePathNode.class.cast(a.getResult());
		assertTrue(casted.getFrom() == 20);
		assertTrue(casted.getTo() == 13);
		assertTrue(casted.getStep() == 5);

		a = new BracketsParser(0);
		a.consumeAll("20 : 13 : 5]");
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));
		casted = SlicePathNode.class.cast(a.getResult());
		assertTrue(casted.getFrom() == 20);
		assertTrue(casted.getTo() == 13);
		assertTrue(casted.getStep() == 5);

		a = new BracketsParser(0);
		a.consumeAll("20:13:5 ]");
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));
		casted = SlicePathNode.class.cast(a.getResult());
		assertTrue(casted.getFrom() == 20);
		assertTrue(casted.getTo() == 13);
		assertTrue(casted.getStep() == 5);

		// minus handling
		a = new BracketsParser(0);
		a.consumeAll("-20:13:2]");
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));
		casted = SlicePathNode.class.cast(a.getResult());
		assertTrue(casted.getFrom() == -20);
		assertTrue(casted.getTo() == 13);
		assertTrue(casted.getStep() == 2);

		a = new BracketsParser(0);
		a.consumeAll("20 : -13 : 3]");
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));
		casted = SlicePathNode.class.cast(a.getResult());
		assertTrue(casted.getFrom() == 20);
		assertTrue(casted.getTo() == -13);
		assertTrue(casted.getStep() == 3);

		a = new BracketsParser(0);
		a.consumeAll("20:-13:3 ]");
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));
		casted = SlicePathNode.class.cast(a.getResult());
		assertTrue(casted.getFrom() == 20);
		assertTrue(casted.getTo() == -13);
		assertTrue(casted.getStep() == 3);

		a = new BracketsParser(0);
		a.consumeAll("-20:-13]");
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));
		casted = SlicePathNode.class.cast(a.getResult());
		assertTrue(casted.getFrom() == -20);
		assertTrue(casted.getTo() == -13);

		a = new BracketsParser(0);
		a.consumeAll("-20 : -13]");
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));
		casted = SlicePathNode.class.cast(a.getResult());
		assertTrue(casted.getFrom() == -20);
		assertTrue(casted.getTo() == -13);

		a = new BracketsParser(0);
		a.consumeAll(":-1]"); // same as 0:-1 same as all items except last
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));

		a = new BracketsParser(0);
		a.consumeAll(": -1]"); // same as 0:-1 same as all items except last
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));

		a = new BracketsParser(0);
		a.consumeAll("-1:]"); // same as -1:(size) same as last item in range (from -1, first from end to end)
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));

		a = new BracketsParser(0);
		a.consumeAll("-1 : ]"); // same as -1:(size) same as last item in range (from -1, first from end to end)
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));
		casted = SlicePathNode.class.cast(a.getResult());
		assertTrue(casted.getFrom() == -1);
		assertTrue(casted.getStep() == 1);

		a = new BracketsParser(0);
		a.consumeAll("::2]"); // same as 0:(size):2
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));
		casted = SlicePathNode.class.cast(a.getResult());
		assertTrue(casted.getFrom() == null);
		assertTrue(casted.getStep() == 2);

		a = new BracketsParser(0);
		a.consumeAll(" : :  4  ]"); // same as 0:(size):4
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));
		casted = SlicePathNode.class.cast(a.getResult());
		assertTrue(casted.getStep() == 4);

		a = new BracketsParser(0);
		a.consumeAll("::]"); // same as 0:(size):1
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));
		casted = SlicePathNode.class.cast(a.getResult());
		assertEquals(1, casted.getStep().intValue());

		a = new BracketsParser(0);
		a.consumeAll(" : : ]"); // same as 0:(size):1
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));
		casted = SlicePathNode.class.cast(a.getResult());
		assertEquals(1, casted.getStep().intValue());
	}

	@Test
	public void test_wildcard_range() {

		String json = "{'c':[{'v':5},{'v':51},{'v':52},{'c':{'v': 777}}]}";
		Parser parser = new Parser();

		Expression expression = parser.parseExpression("$.c[::].v");

		List<JsonElement> elements = expression.exec(json);

		assertEquals(3, elements.size());

		assertEquals(5, elements.get(0).getAsJsonPrimitive().getAsInt());
		assertEquals(51, elements.get(1).getAsJsonPrimitive().getAsInt());
		assertEquals(52, elements.get(2).getAsJsonPrimitive().getAsInt());

		json = "{'c':{'d':{'v':5},'r':{'v':51}}}";
		expression = parser.parseExpression("$.c[ : : ].v");

		elements = expression.exec(json);

		assertEquals(2, elements.size());

		assertEquals(5, elements.get(0).getAsJsonPrimitive().getAsInt());
		assertEquals(51, elements.get(1).getAsJsonPrimitive().getAsInt());

		expression = parser.parseExpression("$.c[ :  ].v");
		elements = expression.exec(json);

		assertEquals(2, elements.size());

		assertEquals(5, elements.get(0).getAsJsonPrimitive().getAsInt());
		assertEquals(51, elements.get(1).getAsJsonPrimitive().getAsInt());

		expression = parser.parseExpression("$.c[:].v");
		elements = expression.exec(json);

		assertEquals(2, elements.size());

		assertEquals(5, elements.get(0).getAsJsonPrimitive().getAsInt());
		assertEquals(51, elements.get(1).getAsJsonPrimitive().getAsInt());

	}

}
