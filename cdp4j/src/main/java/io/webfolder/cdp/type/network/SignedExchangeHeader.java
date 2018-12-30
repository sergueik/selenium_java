/**
 * cdp4j Commercial License
 *
 * Copyright 2017, 2018 WebFolder OÜ
 *
 * Permission  is hereby  granted,  to "____" obtaining  a  copy of  this software  and
 * associated  documentation files  (the "Software"), to deal in  the Software  without
 * restriction, including without limitation  the rights  to use, copy, modify,  merge,
 * publish, distribute  and sublicense  of the Software,  and to permit persons to whom
 * the Software is furnished to do so, subject to the following conditions:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  IMPLIED,
 * INCLUDING  BUT NOT  LIMITED  TO THE  WARRANTIES  OF  MERCHANTABILITY, FITNESS  FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL  THE AUTHORS  OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.webfolder.cdp.type.network;

import io.webfolder.cdp.annotation.Experimental;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Information about a signed exchange header
 * https://wicg
 * github
 * io/webpackage/draft-yasskin-httpbis-origin-signed-exchanges-impl
 * html#cbor-representation
 */
@Experimental
public class SignedExchangeHeader {
    private String requestUrl;

    private String requestMethod;

    private Integer responseCode;

    private Map<String, Object> responseHeaders = new HashMap<>();

    private List<SignedExchangeSignature> signatures = new ArrayList<>();

    /**
     * Signed exchange request URL.
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     * Signed exchange request URL.
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    /**
     * Signed exchange request method.
     */
    public String getRequestMethod() {
        return requestMethod;
    }

    /**
     * Signed exchange request method.
     */
    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    /**
     * Signed exchange response code.
     */
    public Integer getResponseCode() {
        return responseCode;
    }

    /**
     * Signed exchange response code.
     */
    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * Signed exchange response headers.
     */
    public Map<String, Object> getResponseHeaders() {
        return responseHeaders;
    }

    /**
     * Signed exchange response headers.
     */
    public void setResponseHeaders(Map<String, Object> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    /**
     * Signed exchange response signature.
     */
    public List<SignedExchangeSignature> getSignatures() {
        return signatures;
    }

    /**
     * Signed exchange response signature.
     */
    public void setSignatures(List<SignedExchangeSignature> signatures) {
        this.signatures = signatures;
    }
}
