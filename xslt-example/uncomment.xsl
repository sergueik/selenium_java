<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xxx="http://xmlns.jcp.org/xml/ns/javaee" version="1.0">
  <xsl:output method="xml" indent="yes"/>
  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="comment()">
    <copyfilter>
    <xsl:copy>
      <xsl:apply-templates select="node()"/>
    </xsl:copy>
<xsl:for-each select="node()">
	<xsl:value-of select="name()"/>
      <xsl:apply-templates select="child::node()"/>
  <xsl:value-of select="."/>
</xsl:for-each>
      <xsl:value-of select="child::node()"/>
    </copyfilter>
  </xsl:template>
</xsl:stylesheet>
