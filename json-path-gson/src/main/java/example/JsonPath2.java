package example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import example.iterators.ArrayListPeekableIterator;
import example.iterators.ExecIterator;
import example.iterators.PeekableIterator;
import example.nodetypes.PathNode;

public class JsonPath2 {

	static public class Expression {

		final List<PathNode> nodes;

		public Expression(List<PathNode> nodes) {
			this.nodes = nodes;
		}

		// XXX: for using same expression with few objects in same time this list should
		// be immutable.
		// Possible solutions:
		// 1) make list unchangeable and all PathNodes immutable
		// 2) make list unchangeable and return deep copy of obtained object each time
		public List<PathNode> getNodes() {
			return nodes;
		}

		List<JsonElement> exec(String strJson) {
			return exec(new JsonParser().parse(strJson));
		}

		List<JsonElement> exec(JsonElement obj) {
			ArrayList<JsonElement> list = new ArrayList<JsonElement>();
			list.add(obj);
			int filterPosition = 0;
			PeekableIterator<JsonElement> iterator = exec(new ArrayListPeekableIterator<JsonElement>(list),
					filterPosition);
			List<JsonElement> res = new ArrayList<JsonElement>();
			while (iterator.hasNext()) {
				res.add(iterator.next());
			}
			return res;
		}

		PeekableIterator<JsonElement> exec(final PeekableIterator<JsonElement> in, final int filterPosition) {

			// System.out.println(getStackOffset()+"start exec on iter:" + in+ "filter
			// is:"+filterPosition + " val is:"
			// +(filterPosition>=nodes.size()?"-":nodes.get(filterPosition)));
			// in - ����� ������� ������, �� ������� �������� ����� ������� ����� ���������
			// ��������� � ������� ���������� � ���� ������
			//
			PeekableIterator<JsonElement> res = new ExecIterator(this, in, filterPosition);
			return res;
		}

	}

	public static void main(String[] args) throws JsonPathException {

		System.out.println("ok start");

		String json = "{'c':[{'v':5}, {'v': 'lold'}]}";
		Expression expression = new Parser().parseExpression("$.c[*].v");
//		Expression expression = new Parser().parseExpression("$.c");
		System.out.println("$.c[*].v");
		for (PathNode e : expression.getNodes()) {
			System.out.println("path node is:" + e);
		}
		List<JsonElement> elements = expression.exec(json);
		for (JsonElement e : elements) {
			System.out.println("el: " + e);
		}

		assertEquals(2, elements.size());
		assertEquals(5, elements.get(0).getAsInt());
		assertEquals("lold", elements.get(1).getAsString());

		System.out.println("ok2");
	}

	public static void assertEquals(Object a, Object b) {
		System.out.println(a + " is " + b);
		if ((a == null && b != null) || !a.equals(b)) {
			throw new RuntimeException("<" + a + "> is not same as <" + b + ">");
		}
	}

	public static void assertTrue(Boolean a) {
		if (a == null) {
			throw new RuntimeException("null is not same as True");
		}
		if (!a.booleanValue()) {
			throw new RuntimeException("<" + a + "> is not True");
		}
	}

	public static String getStackOffset() {
		StringBuilder sb = new StringBuilder();
		for (int i = 6; i < Thread.currentThread().getStackTrace().length; i++) {
			sb.append("\t");
		}
		return sb.toString();
	}
}
