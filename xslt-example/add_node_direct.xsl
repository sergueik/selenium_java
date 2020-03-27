<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<!-- based on: https://stackoverflow.com/questions/3649195/adding-element-in-middle-of-xml-using-xslt/3649513 -->   
	
	<!-- Identity transform -->
   <xsl:template match="@* | node()">
      <xsl:copy>
         <xsl:apply-templates select="@* | node()"/>
      </xsl:copy>
   </xsl:template>

   <xsl:template match="filter">
	   <!-- <xsl:template match="filter[last()]"> -->
      <xsl:copy-of select="."/>
      <!-- verbatim data -->
      <filter>
        <filter-name>customFilter</filter-name>
        <filter-class>example.CustomFilter</filter-class>
        <async-supported>true</async-supported>
      </filter>
      <Domain>Insurance</Domain>
   </xsl:template>
</xsl:stylesheet>
