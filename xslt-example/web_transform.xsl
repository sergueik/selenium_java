<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xxx="http://xmlns.jcp.org/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="1.0">
  <xsl:output method="xml" indent="yes"/>
  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="xxx:filter[xxx:filter-name[text()='httpHeaderSecurity']]">
    <xsl:comment>
      <!-- this does not work -->
      <xsl:copy-of select="."/>
    </xsl:comment>
    <!-- this does work, though ranked as a hack -->
    <xsl:text disable-output-escaping="yes">&lt;!--</xsl:text>
    <xsl:copy-of select="."/>
    <xsl:text disable-output-escaping="yes">--&gt;</xsl:text>
  </xsl:template>
</xsl:stylesheet>
