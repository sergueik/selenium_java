<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xxx="http://xmlns.jcp.org/xml/ns/javaee" version="1.0">
  <xsl:output method="xml" indent="yes"/>
  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="xxx:filter-mapping[last()]">
    <!-- adding filter-mapping before the last node -->
    <filter-mapping>
      <filter-name>customFilter</filter-name>
      <url-pattern>/*</url-pattern>
      <dispatcher>REQUEST</dispatcher>
    </filter-mapping>
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="xxx:filter[xxx:filter-name[text()='httpHeaderSecurity']]">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
    <filter>
      <filter-name>customFilter</filter-name>
      <filter-class>example.CustomFilter</filter-class>
      <async-supported>true</async-supported>
    </filter>
  </xsl:template>
</xsl:stylesheet>
