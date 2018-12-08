package com.github.mike10004.seleniumtrafficeg;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.mitm.KeyStoreFileCertificateSource;
import net.lightbody.bmp.mitm.manager.ImpersonatingMitmManager;
import net.lightbody.bmp.proxy.CaptureType;
import org.apache.commons.lang3.tuple.Pair;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.MitmManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;

public abstract class TrafficEater {

	public interface TrafficGenerator {
		void generate(WebDriver driver) throws IOException;
	}

	protected abstract WebDriver createWebDriver(BrowserMobProxy proxy);

	protected Set<CaptureType> getCaptureTypes() {
		return EnumSet.allOf(CaptureType.class);
	}

	protected String getHarName() {
		return "myExample";
	}

	public Har consume(TrafficGenerator generator)
			throws IOException, WebDriverException {
		BrowserMobProxy bmp = createProxy();
		Set<CaptureType> captureTypes = getCaptureTypes();
		bmp.enableHarCaptureTypes(captureTypes);
		bmp.newHar(getHarName());
		bmp.start();
		try {
			WebDriver driver = createWebDriver(bmp);
			try {
				generator.generate(driver);
			} finally {
				driver.quit();
			}
		} finally {
			bmp.stop();
		}
		net.lightbody.bmp.core.har.Har har = bmp.getHar();
		return har;

	}

	protected @Nullable InetSocketAddress detectJvmProxy() {
		return detectJvmProxy(System.getProperties());
	}

	protected @Nullable InetSocketAddress detectJvmProxy(Properties sysprops) {
		String httpProxyHost = sysprops.getProperty("http.proxyHost");
		String httpProxyPort = sysprops.getProperty("http.proxyPort");
		String httpsProxyHost = sysprops.getProperty("http.proxyHost");
		String httpsProxyPort = sysprops.getProperty("http.proxyPort");
		if (!Objects.equals(httpProxyHost, httpsProxyHost)) {
			throw new IllegalStateException(
					"system properties define conflicting values for http.proxyHost="
							+ httpProxyHost + " and httpsProxyHost=" + httpsProxyHost);
		}
		if (!Objects.equals(httpProxyPort, httpsProxyPort)) {
			throw new IllegalStateException(
					"system properties define conflicting values for http.proxyPort="
							+ httpProxyPort + " and httpsProxyPort=" + httpsProxyPort);
		}
		if ((httpsProxyHost == null) != (httpsProxyPort == null)) {
			throw new IllegalStateException("nullness of https.proxyHost="
					+ httpsProxyHost + " and https.proxyPort=" + httpsProxyPort
					+ " system properties must be consistent");
		}
		if (httpsProxyHost != null) {
			return new InetSocketAddress(httpsProxyHost,
					Integer.parseInt(httpsProxyPort));
		} else {
			return null;
		}
	}

	static class CustomCertificate {
		public static final String KEYSTORE_TYPE = "PKCS12";
		public static final String KEYSTORE_PRIVATE_KEY_ALIAS = "key";
		public static final String KEYSTORE_PASSWORD = "password";
		public static final String KEYSTORE_RESOURCE = "/selenium-traffic-example/mitm-certificate.keystore";
	}

	protected MitmManager createMitmManager(BrowserMobProxy proxy) {
		MitmManager mitmManager = ImpersonatingMitmManager.builder()
				.rootCertificateSource(
						new KeyStoreFileCertificateSource(CustomCertificate.KEYSTORE_TYPE,
								CustomCertificate.KEYSTORE_RESOURCE,
								CustomCertificate.KEYSTORE_PRIVATE_KEY_ALIAS,
								CustomCertificate.KEYSTORE_PASSWORD))
				.build();
		return mitmManager;
	}

	protected BrowserMobProxy createProxy() {
		BrowserMobProxy bmp = new BrowserMobProxyServer();
		bmp.setMitmManager(createMitmManager(bmp));
		bmp.addLastHttpFilterFactory(new EaterFiltersSource());
		InetSocketAddress upstreamProxyAddress = detectJvmProxy();
		if (upstreamProxyAddress != null) {
			bmp.setChainedProxy(upstreamProxyAddress);
		}
		return bmp;
	}

	static class ProxyRequestFilters extends HttpFiltersAdapter {

		private final ImmutableSet<String> headersToRemove;
		private final ImmutableList<? extends Map.Entry<String, ?>> headersToReplace;
		private final ImmutableList<? extends Map.Entry<String, ?>> headersToAdd;

		public ProxyRequestFilters(HttpRequest originalRequest,
				Iterable<String> headersToRemove,
				Iterable<? extends Map.Entry<String, ?>> headersToReplace,
				Iterable<? extends Map.Entry<String, ?>> headersToAdd) {
			super(originalRequest);
			this.headersToRemove = ImmutableSet.copyOf(headersToRemove);
			this.headersToReplace = ImmutableList.copyOf(headersToReplace);
			this.headersToAdd = ImmutableList.copyOf(headersToAdd);
		}

		@Override
		public io.netty.handler.codec.http.HttpResponse proxyToServerRequest(
				io.netty.handler.codec.http.HttpObject httpObject) {
			if (httpObject instanceof io.netty.handler.codec.http.HttpRequest) {
				io.netty.handler.codec.http.HttpRequest request = ((io.netty.handler.codec.http.HttpRequest) httpObject);
				HttpHeaders headers = request.headers();
				customize(headers);
			}
			return null;
		}

		protected void customize(HttpHeaders headers) {
			for (String headerName : headersToRemove) {
				headers.remove(headerName);
			}
			for (Map.Entry<String, ?> header : headersToReplace) {
				headers.set(header.getKey(), header.getValue());
			}
			for (Map.Entry<String, ?> header : headersToAdd) {
				headers.add(header.getKey(), header.getValue());
			}
		}
	}

	static class EaterFiltersSource extends HttpFiltersSourceAdapter {

		private static final ImmutableSet<String> headersToRemove = ImmutableSet
				.of(HttpHeaders.Names.VIA);
		private static final ImmutableSet<? extends Map.Entry<String, ?>> headersToReplace = ImmutableSet
				.of();
		private static final ImmutableSet<? extends Map.Entry<String, ?>> headersToAdd = ImmutableSet
				.of(Pair.of("X-Traffic-Eater", "hungry"));

		@Override
		public HttpFilters filterRequest(
				io.netty.handler.codec.http.HttpRequest originalRequest) {
			return new ProxyRequestFilters(originalRequest, headersToRemove,
					headersToReplace, headersToAdd);
		}
	}
}
