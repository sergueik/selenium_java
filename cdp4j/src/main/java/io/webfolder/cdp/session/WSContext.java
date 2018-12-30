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
package io.webfolder.cdp.session;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.CountDownLatch;

import com.google.gson.JsonElement;

import io.webfolder.cdp.exception.CdpException;
import io.webfolder.cdp.exception.CommandException;

class WSContext {

    private CountDownLatch latch = new CountDownLatch(1);

    private JsonElement data;

    private CommandException error;

    void await(final int timeout) {
        try {
            latch.await(timeout, MILLISECONDS);
        } catch (InterruptedException e) {
            throw new CdpException(e);
        }
    }

    void setData(final JsonElement data) {
        this.data = data;
        latch.countDown();
    }

    JsonElement getData() {
        return data;
    }

    void setError(CommandException error) {
        this.error = error;
        latch.countDown();
    }

    CommandException getError() {
        return error;
    }
}
