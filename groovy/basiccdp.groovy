@Grapes([
    @Grab(group = 'com.google.code.gson', module = 'gson', version = '2.7'),
    @Grab(group = 'org.apache.httpcomponents', module = 'httpcore', version = '4.4'),
    // java.lang.ClassNotFoundException: org.apache.http.ssl.SSLContexts
    @Grab(group = 'org.gebish', module = 'geb-core', version = '0.9.3'),
    @Grab(group = 'io.webfolder', module = 'cdp4j', version = '2.1.5'),
    @GrabExclude('xerces:xercesImpl'),
    @GrabExclude('xml-apis:xml-apis'),
])

// default
// ~/.groovy/grapes/org.gebish/geb-core/jars/geb-core-0.9.3.jar

import groovy.grape.Grape

import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Method


import io.webfolder.cdp.Launcher
import io.webfolder.cdp.exception.CommandException
import io.webfolder.cdp.session.Session
import io.webfolder.cdp.session.SessionFactory
import io.webfolder.cdp.type.runtime.CallFunctionOnResult
import io.webfolder.cdp.type.runtime.RemoteObject

import java.util.concurrent.TimeUnit
import java.util.logging.Level


import io.webfolder.cdp.session.Session
import io.webfolder.cdp.exception.CommandException

import java.util.List
import java.util.concurrent.CountDownLatch
import java.util.function.Predicate

/*

*/

def baseURL =	 "https://www.google.com/gmail/about/#"
def signInLink = "a[class *= 'gmail-nav__nav-link__sign-in']"; // css
def session
def waitTimeout = 5000
def pollingInterval = 500

def  launcher = new Launcher()
def  factory = launcher.launch()
sleep(1000)
try {
  session = factory.create()
  sleep(1000)
  // install extensions
  session.installSizzle()
  session.useSizzle()
  session.clearCookies()
  session.clearCache()
  session.setUserAgent( "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/534.34 (KHTML, like Gecko) PhantomJS/1.9.7 Safari/534.34")
  session.navigate(baseURL)
} catch (CommandException e) {
  throw new RuntimeException(e)
}

// TODO: how to make groovy accept lambda syntax
// and Java 8
// groovy.lang.MissingPropertyException: No such property: urlChange for class: baiccdp
//    Predicate<Session> urlChange = session -> session.getLocation().matches(String.format("^%s.*", baseURL))
//    session.waitUntil(urlChange, 1000, 100)


sleep(4000)
println("Location:" + session.getLocation())
highlight(signInLink, session, 1000)
click(session, signInLink)
sleep(4000)

if (session != null) {
  session.stop()
  session.close()
}

protected void highlight(String selectorOfElement, Session session, long interval) {
  executeScript(session, "function() { this.style.border='3px solid yellow'; }", selectorOfElement)
  sleep(interval)
  executeScript(session, "function() { this.style.border=''; }", selectorOfElement)
}

protected void click(Session session, String selector) {
  executeScript(session, "function() { this.click(); }", selector)
}

protected Object executeScript(Session session, String script, String selectorOfElement) {
  if (!session.matches(selectorOfElement)) {
    return null
  }
  String objectId = session.getObjectId(selectorOfElement)
  Integer nodeId = session.getNodeId(selectorOfElement)
  CallFunctionOnResult functionResult = null
  RemoteObject result = null
  Object value = null;
  try {
  // NOTE: ObjectId must not be specified together with executionContextId
  functionResult = session.getCommand().getRuntime().callFunctionOn(script,
      objectId, null, null, null, null, null, null, null, null)
  if (functionResult != null) {
    result = functionResult.getResult()
    if (result != null) {
      value = result.getValue()
      session.releaseObject(result.getObjectId())
    }
  }
  } catch (Exception e) {
    println("Exception (ignored): " + e.getMessage())
  }
  // println("value: " + value)
  return value
}

// does not work
def urlChange( Session session ) {
  return session.getLocation().matches(String.format("^%s.*", baseURL))
}

def sleep(long timeoutSeconds) {
  try {
    Thread.sleep(timeoutSeconds)
  } catch (InterruptedException e) {
    e.printStackTrace()
  }
}
