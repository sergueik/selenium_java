package jpuppeteer.chrome;

import com.alibaba.fastjson.TypeReference;
import jpuppeteer.api.browser.ExecutionContext;
import jpuppeteer.cdp.cdp.constant.runtime.RemoteObjectSubtype;
import jpuppeteer.cdp.cdp.constant.runtime.RemoteObjectType;
import jpuppeteer.cdp.cdp.domain.Runtime;
import jpuppeteer.cdp.cdp.entity.runtime.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

import static jpuppeteer.chrome.ChromeBrowser.DEFAULT_TIMEOUT;

public abstract class AbstractExecution implements ExecutionContext {

	protected final Runtime runtime;

	public AbstractExecution(Runtime runtime) {
		this.runtime = runtime;
	}

	protected abstract void intercept(CallFunctionOnRequest request);

	protected abstract void intercept(EvaluateRequest request);

	protected static CallArgument buildArgument(Object object) {
		CallArgument argument = new CallArgument();
		if (object instanceof ChromeBrowserObject) {
			argument.setObjectId(((ChromeBrowserObject) object).getObjectId());
		} else if (object instanceof CallArgument) {
			argument = (CallArgument) object;
		} else {
			argument.setValue(object);
		}
		return argument;
	}

	private static void checkException(ExceptionDetails exceptionDetails)
			throws Exception {
		if (exceptionDetails != null) {
			String error = "null";
			if (StringUtils.isNotEmpty(exceptionDetails.getText())) {
				error = exceptionDetails.getText();
			}
			if (exceptionDetails.getException() != null && StringUtils
					.isNotEmpty(exceptionDetails.getException().getDescription())) {
				error = exceptionDetails.getException().getDescription();
			}
			throw new Exception(error);
		}
	}

	private ChromeBrowserObject call0(String declaration, boolean returnJSON,
			Object... args) throws Exception {
		CallFunctionOnRequest request = new CallFunctionOnRequest();
		request.setFunctionDeclaration(declaration);
		request.setArguments(Arrays.stream(args).map(arg -> buildArgument(arg))
				.collect(Collectors.toList()));
		request.setUserGesture(true);
		request.setReturnByValue(returnJSON);
		request.setAwaitPromise(true);
		intercept(request);
		CallFunctionOnResponse response = runtime.callFunctionOn(request,
				DEFAULT_TIMEOUT);
		checkException(response.getExceptionDetails());
		if (RemoteObjectType.UNDEFINED.equals(response.getResult().getType())
				|| RemoteObjectSubtype.NULL.equals(response.getResult().getSubtype())) {
			return null;
		}
		return new ChromeBrowserObject(runtime, this, response.getResult());
	}

	private ChromeBrowserObject eval0(String experssion, boolean returnJSON)
			throws Exception {
		EvaluateRequest request = new EvaluateRequest();
		request.setTimeout(new Double(DEFAULT_TIMEOUT) * 1000);
		request.setExpression(experssion);
		request.setGeneratePreview(false);
		request.setReturnByValue(returnJSON);
		request.setUserGesture(true);
		request.setAwaitPromise(true);
		request.setIncludeCommandLineAPI(true);
		intercept(request);
		EvaluateResponse response = runtime.evaluate(request, DEFAULT_TIMEOUT);
		checkException(response.getExceptionDetails());
		if (RemoteObjectType.UNDEFINED.equals(response.getResult().getType())
				|| RemoteObjectSubtype.NULL.equals(response.getResult().getSubtype())) {
			return null;
		}
		return new ChromeBrowserObject(runtime, this, response.getResult());
	}

	@Override
	public <R> R eval(String expression, Class<R> clazz) throws Exception {
		return eval0(expression, true).toObject(clazz);
	}

	@Override
	public <R> R eval(String expression, TypeReference<R> type) throws Exception {
		return eval0(expression, true).toObject(type);
	}

	@Override
	public ChromeBrowserObject eval(String expression) throws Exception {
		return eval0(expression, false);
	}

	@Override
	public <R> R call(String declaration, Class<R> clazz, Object... args)
			throws Exception {
		return call0(declaration, true, args).toObject(clazz);
	}

	@Override
	public <R> R call(String declaration, TypeReference<R> type, Object... args)
			throws Exception {
		return call0(declaration, true, args).toObject(type);
	}

	@Override
	public ChromeBrowserObject call(String declaration, Object... args)
			throws Exception {
		return call0(declaration, false, args);
	}
}
