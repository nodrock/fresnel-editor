<?xml version="1.0" encoding="utf-8"?>

<!--+
    |
    | XSLT to transform the XML result of a Fresnel lens into an HTML representation   
    |
    +-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:f="http://www.w3.org/2004/09/fresnel-tree"
  exclude-result-prefixes="f"
  version="1.0">

    <xsl:output indent="yes" method="xml" omit-xml-declaration="yes" />
    
    <!-- Parameter definitions -->
    <xsl:param name="pageTitle" select="'Fresnel rendered page'" />
    <xsl:param name="cssStylesheetURL" select="'http://jfresnel.gforge.inria.fr/WebContent/ResultsReporting/fresnel2html.css'" />

    <!-- Match the root of the DOM tree -->
    <xsl:template match="/">
        <xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html&gt;&#10;</xsl:text>
        <html>
            <head lang="cs">
                <meta  charset="utf-8" />
                <title>
                    <xsl:value-of select="$pageTitle" />
                </title>
                <!-- TODO: Replace link to CSS stylesheet by parameter -->
                <link rel="stylesheet" type="text/css" href="{$cssStylesheetURL}"/>
            </head>
            <body>
                <!-- Extract styling information to CSS stylesheet -->
                <div class="div-top">
                    <!--Process children of DOM root-->
                    <xsl:apply-templates/>
                </div>
            </body>
        </html>  
    </xsl:template>
    <!-- End root match template -->

    <!--  -->
    <xsl:template match="f:results">
        <article class="fresnel-results">
            <header>
              <h1>
                <xsl:value-of select="$pageTitle" />
              </h1>
            </header>
            <xsl:apply-templates select="f:resource"/>
        </article>
    </xsl:template>

    <!--  -->
    <xsl:template match="f:resource">
        <section>
            <!--  -->
            <xsl:choose>
                <xsl:when test="@class">
                    <xsl:attribute name="class">
                        <xsl:value-of select="@class"/>
                    </xsl:attribute>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:attribute name="class">
                        <xsl:value-of select="'f-resource'"/>
                    </xsl:attribute>
                </xsl:otherwise>
            </xsl:choose>
            <!--  -->
            <xsl:if test="f:content/f:before">
                <xsl:value-of select="f:content/f:before"/>
            </xsl:if>
            <!--  -->
            <header>
            <h2 class="f-title-resource">
                <xsl:choose>
                    <xsl:when test="f:title/text() != ''">
                        <xsl:value-of select="f:title"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="@uri"/>
                    </xsl:otherwise>
                </xsl:choose>
            </h2>
            </header>
            <!--  -->
            <xsl:apply-templates select="f:property"/>
            <xsl:if test="f:content/f:after">
                <xsl:value-of select="f:content/f:after"/>
            </xsl:if>
        </section>
    </xsl:template>

    <xsl:template match="f:property">
        <div>
            <xsl:choose>
                <xsl:when test="@class">
                    <xsl:attribute name="class">
                        <xsl:value-of select="@class"/>
                    </xsl:attribute>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:attribute name="class">
                        <xsl:value-of select="'f-property'"/>
                    </xsl:attribute>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:if test="f:label">
                <xsl:if test="f:content/f:before">
                    <xsl:value-of select="f:content/f:before"/>
                </xsl:if>
                <div>
                    <xsl:choose>
                        <xsl:when test="f:label/@class">
                            <xsl:attribute name="class">
                                <xsl:value-of select="f:label/@class"/>
                            </xsl:attribute>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:attribute name="class">
                                <xsl:value-of select="'f-label'"/>
                            </xsl:attribute>
                        </xsl:otherwise>
                    </xsl:choose>
                    <xsl:apply-templates select="f:label"/>
                    <xsl:if test="f:content/f:after">
                        <xsl:value-of select="f:content/f:after"/>
                    </xsl:if>
                </div>
            </xsl:if>
            <xsl:apply-templates select="f:values"/>
        </div>
    </xsl:template>

    <xsl:template match="f:label">
        <xsl:if test="f:content/f:before">
            <xsl:value-of select="f:content/f:before"/>
        </xsl:if>
        <xsl:choose>
            <xsl:when test="f:title/text() != ''">
                <xsl:value-of select="f:title"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="../@uri"/>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:if test="f:content/f:after">
            <xsl:value-of select="f:content/f:after"/>
        </xsl:if>
    </xsl:template>

    <xsl:template match="f:values">
        <xsl:choose>
            <xsl:when test="f:content/f:first">
                <xsl:value-of select="f:content/f:first"/>
            </xsl:when>
            <xsl:when test="f:content/f:before">
                <xsl:value-of select="f:content/f:before"/>
            </xsl:when>
        </xsl:choose>
        <xsl:apply-templates select="f:value">
             <xsl:with-param name="before" select="f:content/f:before"/>
             <xsl:with-param name="after" select="f:content/f:after"/>
        </xsl:apply-templates>
        <xsl:choose>
            <xsl:when test="f:content/f:last">
                <xsl:value-of select="f:content/f:last"/>
            </xsl:when>
            <xsl:when test="f:content/f:after">
                <xsl:value-of select="f:content/f:after"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="f:value">
        <xsl:param name="before"/>
        <xsl:param name="after"/>
        <div>
            <xsl:choose>
                <xsl:when test="@class">
                    <xsl:attribute name="class">
                        <xsl:value-of select="@class"/>
                    </xsl:attribute>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:attribute name="class">
                        <xsl:value-of select="'f-value'"/>
                    </xsl:attribute>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:if test="position()&gt;1">
                <xsl:value-of select="$before"/>
            </xsl:if>
            <!-- Handling of VALUE TYPE -->
            <xsl:choose>
            	<!-- IMAGE VALUE TYPE -->
                <xsl:when test="@output-type='http://www.w3.org/2004/09/fresnel#image'">
                    <div>
                        <xsl:choose>
                            <xsl:when test="f:resource/@class">
                                <xsl:attribute name="class">
                                    <xsl:value-of select="f:resource/@class"/>
                                </xsl:attribute>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:attribute name="class">
                                    <xsl:value-of select="'f-resource'"/>
                                </xsl:attribute>
                            </xsl:otherwise>
                        </xsl:choose>
                        <xsl:choose>
                            <xsl:when test="f:resource">
                              <figure>
                                <img src="{f:resource/@uri}" alt="{f:resource/f:title}" />
                              </figure>
                            </xsl:when>
                            <xsl:otherwise>
                              <figure>
                                <img src="{f:title}" />
                              </figure>
                            </xsl:otherwise>
                        </xsl:choose>
                    </div>
                </xsl:when>
                <!-- URI VALUE TYPE -->
                <xsl:when test="@output-type='http://www.w3.org/2004/09/fresnel#uri'">
                    <div>
                        <xsl:choose>
                            <xsl:when test="f:resource/@class">
                                <xsl:attribute name="class">
                                    <xsl:value-of select="f:resource/@class"/>
                                </xsl:attribute>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:attribute name="class">
                                    <xsl:value-of select="'f-resource'"/>
                                </xsl:attribute>
                            </xsl:otherwise>
                        </xsl:choose>
                        <div class="f-title">
                            <xsl:choose>
                                <xsl:when test="f:resource">
                                    <xsl:value-of select="f:resource/@uri"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="f:title" />
                                </xsl:otherwise>
                            </xsl:choose>
                        </div>
                    </div>
                </xsl:when>
                <!-- EXTERNAL LINK VALUE TYPE -->
                <!-- TODO: Clarify if f:resource element can be omitted here. -->
                <xsl:when test="@output-type='http://www.w3.org/2004/09/fresnel#externalLink'">
               <!-- <div>
                        <xsl:choose>
                            <xsl:when test="f:resource/@class">
                                <xsl:attribute name="class">
                                    <xsl:value-of select="f:resource/@class"/>
                                </xsl:attribute>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:attribute name="class">
                                    <xsl:value-of select="'f-resource'"/>
                                </xsl:attribute>
                            </xsl:otherwise>
                        </xsl:choose> -->
                        <div class="f-title">
                            <xsl:choose>
                                <xsl:when test="f:resource">
                                    <a href="{f:resource/@uri}" title="{f:resource/f:title}"><xsl:value-of select="f-resource/@uri"/></a>
                                </xsl:when>
                                <xsl:otherwise>
                                    <a href="{f:title}"><xsl:value-of select="f:title"/></a>
                                </xsl:otherwise>
                            </xsl:choose>
                        </div>
               <!-- </div> -->
                </xsl:when>
                <xsl:otherwise>
                    <xsl:choose>
                        <xsl:when test="f:title">
                            <div class="f-title">
                                <xsl:value-of select="f:title"/>
                            </div>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:apply-templates select="f:resource"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:if test="position()!=last()">
                <xsl:value-of select="$after"/> 
            </xsl:if>
        </div>
    </xsl:template>
 
</xsl:stylesheet>
