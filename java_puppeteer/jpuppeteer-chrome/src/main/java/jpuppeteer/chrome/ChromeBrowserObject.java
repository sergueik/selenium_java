package jpuppeteer.chrome;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import jpuppeteer.api.browser.BrowserObject;
import jpuppeteer.api.browser.ExecutionContext;
import jpuppeteer.cdp.cdp.constant.runtime.RemoteObjectSubtype;
import jpuppeteer.cdp.cdp.constant.runtime.RemoteObjectType;
import jpuppeteer.cdp.cdp.domain.Runtime;
import jpuppeteer.cdp.cdp.entity.runtime.*;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jpuppeteer.chrome.ChromeBrowser.DEFAULT_TIMEOUT;

public class ChromeBrowserObject implements BrowserObject {

    private static final String NEGATIVE_ZERO = "-0";

    private static final String NAN = "NaN";

    private static final String INFINITY = "Infinity";

    private static final String NEGATIVE_INFINITY = "-Infinity";

    protected Runtime runtime;

    protected ExecutionContext executionContext;

    protected AbstractExecution execution;

    protected String objectId;

    protected RemoteObjectType type;

    protected RemoteObjectSubtype subType;

    protected RemoteObject object;

    public ChromeBrowserObject(Runtime runtime, ExecutionContext executionContext, RemoteObject object) {
        this.runtime = runtime;
        this.executionContext = executionContext;
        this.execution = new AbstractExecution(runtime) {
            @Override
            protected void intercept(CallFunctionOnRequest request) {
                request.setObjectId(objectId);
            }

            @Override
            protected void intercept(EvaluateRequest request) {
                throw new UnsupportedOperationException();
            }
        };
        this.objectId = object.getObjectId();
        this.type = RemoteObjectType.findByValue(object.getType());
        this.subType = RemoteObjectSubtype.findByValue(object.getSubtype());
        this.object = object;
    }

    public String getObjectId() {
        return objectId;
    }

    @Override
    public ExecutionContext executionContext() {
        return executionContext;
    }

    @Override
    public List<ChromeBrowserObject> getProperties() throws Exception {
        GetPropertiesRequest request = new GetPropertiesRequest();
        request.setObjectId(objectId);
        request.setOwnProperties(true);
        GetPropertiesResponse response = runtime.getProperties(request, DEFAULT_TIMEOUT);
        if (response.getExceptionDetails() != null) {
            throw new Exception(response.getExceptionDetails().getException().getDescription());
        }
        List<ChromeBrowserObject> objects = new ArrayList<>(response.getResult().size());
        for (PropertyDescriptor descriptor : response.getResult()) {
            if (!descriptor.getEnumerable()) {
                continue;
            }
            objects.add(new ChromeBrowserObject(runtime, executionContext, descriptor.getValue()));
        }
        return objects;
    }

    @Override
    public <R> R call(String declaration, Class<R> clazz, Object... args) throws Exception {
        return execution.call(declaration, clazz, args);
    }

    @Override
    public <R> R call(String declaration, TypeReference<R> type, Object... args) throws Exception {
        return execution.call(declaration, type, args);
    }

    @Override
    public ChromeBrowserObject call(String declaration, Object... args) throws Exception {
        return execution.call(declaration, args);
    }

    @Override
    public ChromeBrowserObject getProperty(String name) throws Exception {
        return call("function(name){return this[name]}", name);
    }

    @Override
    public void release() throws Exception {
        ReleaseObjectRequest request = new ReleaseObjectRequest();
        request.setObjectId(objectId);
        runtime.releaseObject(request, DEFAULT_TIMEOUT);
    }

    @Override
    public Object value() {
        if (object.getValue() != null) {
            return object.getValue();
        }
        if (
                RemoteObjectType.OBJECT.equals(this.type) &&
                        RemoteObjectSubtype.DATE.equals(this.subType)) {
            synchronized (this) {
                //修正日期类型
                if (object.getValue() != null) {
                    try {
                        object.setValue(call("function(){return this.toJSON()}").value());
                    } catch (Exception e) {
                        throw new RuntimeException("get date failed, error=" + e.getMessage(), e);
                    }
                }
                return object.getValue();
            }
        }
        String origin = object.getUnserializableValue();
        if (NEGATIVE_ZERO.equals(origin)) {
            return -0;
        } else if (origin == null || NAN.equals(origin) || INFINITY.equals(origin) || NEGATIVE_INFINITY.equals(origin)) {
            //解析不了的数字, 正无穷, 负无穷 全部返回null
            return null;
        } else {
            //BigInt
            return new BigInteger(StringUtils.removeEnd(origin, "n"));
        }
    }

    @Override
    public Boolean toBoolean() {
        return TypeUtils.castToBoolean(value());
    }

    @Override
    public boolean toBooleanValue() {
        Boolean value = toBoolean();
        return value != null ? value.booleanValue() : false;
    }

    @Override
    public Short toShort() {
        return TypeUtils.castToShort(value());
    }

    @Override
    public short toShortValue() {
        Short value = toShort();
        return value != null ? value.shortValue() : 0;
    }

    @Override
    public Integer toInteger() {
        return TypeUtils.castToInt(value());
    }

    @Override
    public int toIntValue() {
        Integer value = toInteger();
        return value != null ? value.intValue() : 0;
    }

    @Override
    public Long toLong() {
        return TypeUtils.castToLong(value());
    }

    @Override
    public long toLongValue() {
        Long value = toLong();
        return value != null ? value.longValue() : 0;
    }

    @Override
    public Float toFloat() {
        return TypeUtils.castToFloat(value());
    }

    @Override
    public float toFloatValue() {
        Float value = toFloat();
        return value != null ? value.floatValue() : 0;
    }

    @Override
    public Double toDouble() {
        return TypeUtils.castToDouble(value());
    }

    @Override
    public double toDoubleValue() {
        Double value = toDouble();
        return value != null ? value.doubleValue() : 0;
    }

    @Override
    public BigDecimal toBigDecimal() {
        return TypeUtils.castToBigDecimal(value());
    }

    @Override
    public BigInteger toBigInteger() {
        return TypeUtils.castToBigInteger(value());
    }

    @Override
    public String toStringValue() {
        return TypeUtils.castToString(value());
    }

    @Override
    public Date toDate() {
        return TypeUtils.castToDate(value());
    }

    @Override
    public <T> T toObject(Class<T> clazz) {
        Object value = value();
        if (value == null) {
            return null;
        }
        return TypeUtils.cast(value(), clazz, ParserConfig.getGlobalInstance());
    }

    @Override
    public <T> T toObject(TypeReference<T> type) {
        Object value = value();
        if (value == null) {
            return null;
        }
        return TypeUtils.cast(value(), type.getType(), ParserConfig.getGlobalInstance());
    }
}
