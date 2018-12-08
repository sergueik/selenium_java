/*
 * (c) 2016 Novetta
 *
 * Created by mike
 */
package com.github.mike10004.seleniumtrafficeg;

import com.google.common.base.MoreObjects;
import net.lightbody.bmp.core.har.*;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;

public class BmpHars {
	public static String toString(@Nullable HarNameVersion h) {
		if (h == null) {
			return "null";
		}
		return MoreObjects.toStringHelper(h).add("name", h.getName())
				.add("version", h.getVersion()).add("comment", h.getComment())
				.toString();
	}

	public static String toString(@Nullable HarPage p) {
		if (p == null) {
			return "null";
		}
		return MoreObjects.toStringHelper(p).add("id", p.getId())
				.add("title", p.getTitle()).add("comment", p.getComment())
				.add("startedDateTime", p.getStartedDateTime())
				.add("pageTimings", toString(p.getPageTimings())).toString();
	}

	public static String toString(@Nullable HarEntry he) {
		if (he == null) {
			return "null";
		}
		return MoreObjects.toStringHelper(he)

				.add("pageref", he.getPageref())
				.add("startedDateTime", he.getStartedDateTime())
				.add("time", he.getTime()).add("request", toString(he.getRequest()))
				.add("response", toString(he.getResponse()))
				.add("serverIPAddress", he.getServerIPAddress())
				.add("connection", he.getConnection()).add("comment", he.getComment())
				.toString();
	}

	public static int size(@Nullable Collection<?> collection) {
		if (collection == null) {
			return -1;
		}
		return collection.size();
	}

	public static String toString(@Nullable HarRequest r) {
		return String.format("%s %s %s (%s cookies, %s headers, body size = %s)",
				r.getHttpVersion(), r.getMethod(), r.getUrl(), size(r.getHeaders()),
				size(r.getCookies()), r.getBodySize());
	}

	public static String toString(@Nullable HarResponse r) {
		return String.format("%s %s %s (%s cookies, %s headers, body size = %s)",
				r.getHttpVersion(), r.getStatus(), r.getStatusText(),
				size(r.getHeaders()), size(r.getCookies()), r.getBodySize());
	}

	public static String toString(@Nullable HarPageTimings p) {
		if (p == null) {
			return "null";
		}
		return MoreObjects.toStringHelper(p).add("onLoad", p.getOnLoad())
				.add("onContentLoad", p.getOnContentLoad()).toString();

	}

	public static void dumpInfo(net.lightbody.bmp.core.har.Har har,
			PrintStream out) throws IOException {
		net.lightbody.bmp.core.har.HarLog log = har.getLog();
		out.format("comment: %s%n", log.getComment());
		HarNameVersion browser = log.getBrowser();
		out.format("browser: %s%n", toString(browser));
		HarNameVersion creator = log.getCreator();
		out.format("creator: %s%n", toString(creator));
		List<HarEntry> entries = log.getEntries();
		out.format("%d entries:%n", entries.size());
		for (HarEntry entry : entries) {
			out.format("  %s%n", toString(entry));
		}
		List<HarPage> pages = log.getPages();
		out.format("%d pages:%n", pages.size());
		for (HarPage page : pages) {
			out.format("  %s%n", toString(page));
		}
	}
}
