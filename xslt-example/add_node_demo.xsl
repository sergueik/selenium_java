<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
   <!-- Identity transform -->
   <xsl:template match="@* | node()">
      <xsl:copy>
         <xsl:apply-templates select="@* | node()"/>
      </xsl:copy>
   </xsl:template>

   <xsl:template match="Name">
      <xsl:copy-of select="."/>
      <Age>34</Age>
   </xsl:template>

   <xsl:template match="Dept">
      <xsl:copy-of select="."/>
      <Domain>Insurance</Domain>
   </xsl:template>
</xsl:stylesheet>