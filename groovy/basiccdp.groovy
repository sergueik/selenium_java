@Grapes([
  // @Grab(group = 'com.google.code.gson', module = 'gson', version = '2.7'),
  //  @Grab(group = 'org.apache.httpcomponents', module = 'httpcore', version = '4.4'),
  // java.lang.ClassNotFoundException: org.apache.http.ssl.SSLContexts
  // @Grab(group = 'org.gebish', module = 'geb-core', version = '0.9.3'),
  @Grab(group = 'io.webfolder', module = 'cdp4j', version = '2.2.2'), // 2.1.5
  // @GrabExclude('xerces:xercesImpl'),
  // @GrabExclude('xml-apis:xml-apis'),
])

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
 * the CDP driven gmail login test converted from Java.
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
*/

String baseURL = 'https://www.google.com/gmail/about/#'
String signInLink = "a[class *= 'gmail-nav__nav-link__sign-in']"; // css
String accountsURL = "https://accounts.google.com/signin/v2/identifier\\?continue=";
String signingURL = "https://accounts.google.com/signin/";

String identifier = "#identifierId" // css
String identifierNextButton = "//*[@id='identifierNext']/content/span[contains(text(),'Next')]" // xpath
String passwordInput = "#password input" // css
String passwordNextButton = "//*[@id='passwordNext']/content/span[contains(text(),'Next')]" // xpath
String profileImage = "//a[contains(@href,'https://accounts.google.com/SignOutOptions')]" // xpath
String signOutButton = "//a[contains(text(), 'Sign out')][contains(@href, 'https://mail.google.com/mail/logout')]" // xpath
String userName = 'automationnewuser24@gmail.com'

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


session.waitUntil(new Predicate<Session>() { boolean test(Session s) { s.getLocation().matches(String.format("^%s.*", baseURL)) } }, 1000, 100)

// TODO: how to make groovy accept Java 8 syntax
// Predicate<Session> urlChange = session -> session.getLocation().matches(String.format("^%s.*", baseURL))
// session.waitUntil(urlChange, 1000, 100)

println('Location: ' + session.getLocation())
highlight(session, signInLink, 1000)
click(session, signInLink)

//  goes to accounts or sign in link URL
session.waitUntil({ s -> s.getLocation().matches(String.format('^(?:%s|%s).*', accountsURL, signingURL)) }, 1000, 100)

println("Locatiion: " + session.getLocation());
// assertTrue(session.getLocation().matches(String.format("^(?:%s|%s).*", accountsURL, signingURL)));
// Enter existing email id
enterData(session, identifier, userName);
// Click on next button
clickNextButton(session,identifierNextButton);

// Enter the valid password
enterData(session, passwordInput, 'automationnewuser2410');

// Click on next button
clickNextButton(session,passwordNextButton);

session.waitUntil({ o ->  println 'Checking if mail page is loaded...' ; return checkPage(o)}, 1000, 100)

// Wait until form is rendered
session.waitUntil( { o -> ((String) o.evaluate('document.readyState')).matches('complete') }, 1000, 100)

println 'Click on profile image'

// Click on profile image
if (session.waitUntil({o -> o.matches(profileImage)}, 1000, 10)) {
  click(profileImage)
}

// Wait until form is rendered
session.waitUntil({ o -> (boolean) o.evaluate('return document.readyState == "complete"') }, 1000, 100);

// Sign out
println 'Sign out'

sleep(1000)
// <a target="_top" id="gb_71" href="https://mail.google.com/mail/logout?hl=en" class="gb4">Sign out</a>

// highlight(session, '#gb_71', 1000)
highlight(session, signOutButton, 10000)
println("Sign out button text: " + session.getText( signOutButton))

click(session, signOutButton)

sleep(10000)

if (session != null) {
  session.stop()
  session.close()
}

def enterData(io.webfolder.cdp.session.Session session, String selector, String data) {
  session.waitUntil({s -> isVisible(s, selector)}, 1000, 100)
  try {
    session.focus(selector)
    session.sendKeys(data)
  } catch (CommandException e) {
    // Element is not focusable ?
    println("Exception in enerData: " + e.getMessage())
  }
}

protected boolean isVisible(io.webfolder.cdp.session.Session session, String selectorOfElement) {
  (boolean) (session.matches(selectorOfElement)
  && (boolean) executeScript(session, 'function() { return(this.offsetWidth > 0 || this.offsetHeight > 0); }', selectorOfElement))
}

protected void highlight( Session session, String selectorOfElement, long interval) {
  executeScript(session, 'function() { this.style.border="3px solid yellow"; }', selectorOfElement)
  sleep(interval)
  executeScript(session, 'function() { this.style.border=""; }', selectorOfElement)
}

protected void click(Session session, String selector) {
  executeScript(session, 'function() { this.click(); }', selector)
}

protected Object executeScript(Session session, String script, String selectorOfElement) {
  if (!session.matches(selectorOfElement)) {
    return null
  }
  String objectId = session.getObjectId(selectorOfElement)
  Integer nodeId = session.getNodeId(selectorOfElement)
  CallFunctionOnResult functionResult = null
  RemoteObject result = null
  Object value = null
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

private void clickNextButton(io.webfolder.cdp.session.Session session, String selector) {
  session.waitUntil({s -> isVisible(s, selector) }, 1000, 100)
  try {
    session.focus(selector);
  } catch (CommandException e) {
    println('Exception in clickNextButton: ' + e.getMessage())
  }
  highlight(session, selector, 1000)
  executeScript(session, "function() { this.click(); }", selector)
}


def sleep(long timeoutSeconds) {
  try {
    Thread.sleep(timeoutSeconds)
  } catch (InterruptedException e) {
    e.printStackTrace()
  }
}

private Boolean checkPage(Session session) {
  session.getLocation().matches('^https://mail.google.com/mail.*')
}
