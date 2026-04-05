package example;

public abstract class By {

	protected final String selector;

	protected By(String selector) {
		this.selector = selector;
	}

	public String getSelector() {
		return selector;
	}

	public abstract String toCssSelector();

	public abstract boolean isXPath();

	public static By cssSelector(String selector) {
		return new ByCssSelector(selector);
	}

	public static By xpath(String xpath) {
		return new ByXPath(xpath);
	}

	public static By id(String id) {
		return new ById(id);
	}

	public static By className(String className) {
		return new ByClassName(className);
	}

	public static By tagName(String tagName) {
		return new ByTagName(tagName);
	}

	public static By name(String name) {
		return new ByName(name);
	}

	public static By linkText(String linkText) {
		return new ByLinkText(linkText);
	}

	public static By partialLinkText(String linkText) {
		return new ByPartialLinkText(linkText);
	}

	public static By chained(By... bys) {
		if (bys == null || bys.length == 0) {
			throw new IllegalArgumentException("chained() requires at least one locator");
		}
		return new ByChained(bys);
	}

	public static By index(By parent, int index) {
		return new ByIndex(parent, index);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + selector;
	}

	private static final class ByCssSelector extends By {
		ByCssSelector(String selector) {
			super(selector);
		}

		@Override
		public String toCssSelector() {
			return selector;
		}

		@Override
		public boolean isXPath() {
			return false;
		}
	}

	private static final class ByXPath extends By {
		ByXPath(String xpath) {
			super(xpath);
		}

		@Override
		public String toCssSelector() {
			return null;
		}

		@Override
		public boolean isXPath() {
			return true;
		}
	}

	private static final class ById extends By {
		ById(String id) {
			super(id);
		}

		@Override
		public String toCssSelector() {
			return "#" + selector;
		}

		@Override
		public boolean isXPath() {
			return false;
		}
	}

	private static final class ByClassName extends By {
		ByClassName(String className) {
			super(className);
		}

		@Override
		public String toCssSelector() {
			return "." + selector;
		}

		@Override
		public boolean isXPath() {
			return false;
		}
	}

	private static final class ByTagName extends By {
		ByTagName(String tagName) {
			super(tagName);
		}

		@Override
		public String toCssSelector() {
			return selector;
		}

		@Override
		public boolean isXPath() {
			return false;
		}
	}

	private static final class ByName extends By {
		ByName(String name) {
			super(name);
		}

		@Override
		public String toCssSelector() {
			return "[name=\"" + selector + "\"]";
		}

		@Override
		public boolean isXPath() {
			return false;
		}
	}

	private static final class ByLinkText extends By {

		private final String linkText;

		ByLinkText(String linkText) {
			// selector field holds the XPath so it flows through the XPath dispatch path
			super("//a[normalize-space(.)=" + xpathStringLiteral(linkText) + "]");
			this.linkText = linkText;
		}

		@Override
		public String toCssSelector() {
			return null;
		}

		@Override
		public boolean isXPath() {
			return true;
		}

		@Override
		public String toString() {
			return "ByLinkText: " + linkText;
		}
	}

	private static final class ByPartialLinkText extends By {

		private final String partialText;

		ByPartialLinkText(String partialText) {
			super("//a[contains(normalize-space(.)," + xpathStringLiteral(partialText) + ")]");
			this.partialText = partialText;
		}

		@Override
		public String toCssSelector() {
			return null;
		}

		@Override
		public boolean isXPath() {
			return true;
		}

		@Override
		public String toString() {
			return "ByPartialLinkText: " + partialText;
		}
	}

	public static final class ByChained extends By {

		private final By[] bys;
		/**
		 * Combined CSS selector when every constituent locator is CSS-based;
		 * {@code null} when at least one requires XPath or step-by-step resolution.
		 */
		private final String combinedCss;

		ByChained(By... bys) {
			super(representativeString(bys));
			this.bys = bys.clone();
			this.combinedCss = tryBuildCss(bys);
		}

		private static String representativeString(By[] bys) {
			StringBuilder sb = new StringBuilder();
			for (By by : bys) {
				if (sb.length() > 0)
					sb.append(" -> ");
				sb.append(by.toString());
			}
			return sb.toString();
		}

		private static String tryBuildCss(By[] bys) {
			StringBuilder sb = new StringBuilder();
			for (By by : bys) {
				String css = by.toCssSelector();
				if (css == null)
					return null;
				if (sb.length() > 0)
					sb.append(' ');
				sb.append(css);
			}
			return sb.toString();
		}

		@Override
		public String toCssSelector() {
			return combinedCss;
		}

		@Override
		public boolean isXPath() {
			return false;
		}

		/** Returns the constituent locators in chain order. */
		public By[] getBys() {
			return bys.clone();
		}

		@Override
		public String toString() {
			return "ByChained(" + selector + ")";
		}
	}

	public static final class ByIndex extends By {

		private final By parent;
		private final int index;

		ByIndex(By parent, int index) {
			super(parent.getSelector() + "[" + index + "]");
			this.parent = parent;
			this.index = index;
		}

		@Override
		public String toCssSelector() {
			return null;
		}

		@Override
		public boolean isXPath() {
			return false;
		}

		public By getParent() {
			return parent;
		}

		public int getIndex() {
			return index;
		}

		@Override
		public String toString() {
			return "ByIndex(" + parent + ", " + index + ")";
		}
	}

	static String xpathStringLiteral(String value) {
		if (!value.contains("'")) {
			return "'" + value + "'";
		}
		// Split on single quotes and concat the parts
		StringBuilder sb = new StringBuilder("concat(");
		String[] parts = value.split("'", -1);
		for (int i = 0; i < parts.length; i++) {
			if (i > 0) {
				sb.append(",\"'\",");
			}
			sb.append("'").append(parts[i]).append("'");
		}
		sb.append(")");
		return sb.toString();
	}
}
