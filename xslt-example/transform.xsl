<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:uuid="java.util.UUID" version="2.0" exclude-result-prefixes="uuid">
  <xsl:output method="xml" version="1.0" encoding="utf-8" indent="yes"/>
  <xsl:template match="/">
    <xsl:for-each select="//student">
      <xsl:copy-of select="."/>
      <output>
        <xsl:variable name="random" select="uuid:randomUUID()"/>
        <xsl:value-of select="$random"/>
      </output>
    </xsl:for-each>
  </xsl:template>
</xsl:stylesheet>
