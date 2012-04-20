<?xml version="1.0" encoding="utf-8"?>

<!--+
   |
   | XSLT to transform the XML result of a Fresnel lens into an HTML representation   
   |
   +-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 xmlns:f="http://www.w3.org/2004/09/fresnel-tree"
 xmlns:xhtml="http://www.w3.org/1999/xhtml"
 exclude-result-prefixes="f"
 version="1.0">

   <xsl:output method="xml" version="1.0" encoding="UTF-8" doctype-public="-//W3C//DTD XHTML 1.1//EN" doctype-system="http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd" indent="yes"/>
   
   <!-- Parameter definitions -->
   <xsl:param name="pageTitle" select="'Fresnel rendered page'" />
   <xsl:param name="cssStylesheetURL" select="'http://jfresnel.gforge.inria.fr/WebContent/ResultsReporting/fresnel2html.css'" />
 <xsl:template match="@*|node()">
<xsl:copy>
<xsl:apply-templates select="@*|node()"/>
</xsl:copy>
</xsl:template>

</xsl:stylesheet>
