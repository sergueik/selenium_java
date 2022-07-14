package com.github.sergueik.jprotractor.scripts;

/**
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */
public final class FindCssSelectorContainingText implements Script {
  @Override
  public String content() {
    return new Loader("cssContainingText").content();
  }
}
