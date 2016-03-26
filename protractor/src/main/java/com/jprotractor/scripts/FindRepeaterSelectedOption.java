package com.jprotractor.scripts;

/**
 * @author Kouzmine Serguei (kouzmine_serguei@yahoo.com)
 */
public final class FindRepeaterSelectedOption implements Script {
  @Override
  public String content() {
    return new Loader("repeaterSelectedOption").content();
  }
}
