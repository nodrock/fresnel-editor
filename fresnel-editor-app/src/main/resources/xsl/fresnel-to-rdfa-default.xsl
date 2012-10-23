<?xml version="1.0" encoding="utf-8"?>
<!--+ | | XSLT to transform the XML result of a Fresnel lens into an HTML representation | +-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:f="http://www.w3.org/2004/09/fresnel-tree"
                xmlns:xhtml="http://www.w3.org/1999/xhtml"
                xmlns:exsl="http://exslt.org/common"
                extension-element-prefixes="exsl"
                exclude-result-prefixes="f"
                version="1.0">


    <xsl:output method="xml" version="1.0" encoding="UTF-8" doctype-public="-//W3C//DTD XHTML+RDFa 1.0//EN" doctype-system="http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd" indent="yes"/>

    <xsl:namespace-alias stylesheet-prefix="xhtml" result-prefix="xsl"/>
    <!-- Parameter definitions -->
    <xsl:param name="pageTitle" select="'Fresnel rendered page'" />
    <xsl:param name="cssStylesheetURL" select="'http://jfresnel.gforge.inria.fr/WebContent/ResultsReporting/fresnel2html.css'" />

    <!-- inicializacia pouzitych namespasov -->
    <xsl:param name='usednamespaces'>
        <xsl:call-template name="init-namespaces" />
    </xsl:param>

    <xsl:template match="/">

        <html xmlns="http://www.w3.org/1999/xhtml" version="XHTML+RDFa 1.0">
            <xsl:for-each select="exsl:node-set($usednamespaces)/usednamespace">
                <xsl:attribute name="{concat('xmlns:',./prefix)}">
                    <xsl:value-of select="./adress" />
                </xsl:attribute>
            </xsl:for-each>

            <head>
                <title>
                    <xsl:value-of select="$pageTitle" />
                </title>
                <!-- TODO: Replace link to CSS stylesheet by parameter -->
                <link rel="stylesheet" type="text/css" href="{$cssStylesheetURL}"/>
                <meta http-equiv="content-Language" content="cs" />
                <meta http-equiv="content-type" content="application/xhtml+xml; charset=utf-8"/>
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

    <xsl:template name="results" match="f:results">
        <div class="fresnel-results">
            <h1>
                <xsl:value-of select="$pageTitle" />
            </h1>
            <xsl:apply-templates select="f:resource"/>
        </div>
    </xsl:template>



    <xsl:template match="f:results">
        <div class="fresnel-results">
            <h1>
                <xsl:value-of select="$pageTitle" />
            </h1>
            <xsl:apply-templates select="f:resource"/>
        </div>
    </xsl:template>

    <!--  -->
    <xsl:template match="f:resource">
        <div>
            <xsl:if test="@uri">
                <xsl:attribute name="about">
                    <xsl:value-of select="@uri"/>
                </xsl:attribute>
            </xsl:if>
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
            <div class="f-title-resource">
                <xsl:choose>
                    <xsl:when test="f:title/text() != ''">
                        <xsl:value-of select="f:title"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="@uri"/>
                    </xsl:otherwise>
                </xsl:choose>
            </div>
            <!--  -->
            <xsl:apply-templates select="f:property"/>
            <xsl:if test="f:content/f:after">
                <xsl:value-of select="f:content/f:after"/>
            </xsl:if>
        </div>
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
        <!--feviz2: zistí k akej vlastnosti patrí hodnota-->
        <xsl:variable name="subtype">
            <xsl:call-template name="get-namespace-subtype">
                <xsl:with-param name="uri" select="./../../@uri" />
            </xsl:call-template>
        </xsl:variable>
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
                                <img src="{f:resource/@uri}" alt="{f:resource/f:title}" />
                            </xsl:when>
                            <xsl:otherwise>
                                <img rel="{$subtype}" alt="{f:title}" src="{f:title}" />
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
                                    <xsl:attribute name="property">
                                        <xsl:value-of select="$subtype"/>
                                    </xsl:attribute>
                                    <xsl:value-of select="f:title" />
                                </xsl:otherwise>
                            </xsl:choose>
                        </div>
                    </div>
                </xsl:when>
                <!-- EXTERNAL LINK VALUE TYPE -->
                <!-- TODO: Clarify if f:resource element can be omitted here. -->
                <xsl:when test="@output-type='http://www.w3.org/2004/09/fresnel#externalLink'">
                    <div class="f-title">
                        <xsl:choose>
                            <xsl:when test="f:resource">
                                <a href="{f:resource/@uri}" title="{f:resource/f:title}">
                                    <xsl:value-of select="f-resource/@uri"/>
                                </a>
                            </xsl:when>
                            <xsl:otherwise>
                                <a href="{f:title}">
                                    <xsl:attribute name="rel">
                                        <xsl:value-of select="$subtype"/>
                                    </xsl:attribute>
                                    <xsl:value-of select="f:title"/>
                                </a>
                            </xsl:otherwise>
                        </xsl:choose>
                    </div>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:choose>
                        <xsl:when test="f:title">
                            <div class="f-title">
                                <xsl:attribute name="property">
                                    <xsl:value-of select="$subtype"/>
                                </xsl:attribute>
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


    <!--feviz2-->

    <!-- ziska zoznam pouzitych namespasov-->
    <xsl:template name="init-namespaces">
        <xsl:variable name="rawnamespaces">
            <xsl:for-each select="//f:property">
                <rawnamespace>

                    <xsl:call-template name="get-namespace-adress">
                        <xsl:with-param name="uri" select="@uri" />
                    </xsl:call-template>

                </rawnamespace>
            </xsl:for-each>
        </xsl:variable>
        <xsl:for-each select="exsl:node-set($rawnamespaces)/rawnamespace[not(following-sibling::*=.)]">
            <usednamespace>
                <prefix>
                    <xsl:text>ns</xsl:text>
                    <xsl:value-of select="position()" />
                </prefix>
                <adress>
                    <xsl:value-of select="." />
                </adress>
            </usednamespace>
        </xsl:for-each>
    </xsl:template>

    <!-- ziska adresu namespasu z uri v proterty-->
    <xsl:template name="get-namespace-adress">
        <xsl:param name="uri"/>
        <xsl:if test="contains($uri,'#')">
            <xsl:value-of select="substring-before($uri, '#')"/>
            <xsl:text>#</xsl:text>
        </xsl:if>
        <xsl:if test="not(contains($uri,'#'))">
            <xsl:call-template name="substring-before-last">
                <xsl:with-param name="input" select="$uri" />
                <xsl:with-param name="substr" select="'/'" />
            </xsl:call-template>
            <xsl:text>/</xsl:text>
        </xsl:if>
    </xsl:template>

    <!-- zisti prefix namespasu podla uri-->
    <xsl:template name="get-usednamespace-prefix">
        <xsl:param name="urix"/>
        <xsl:variable name="nsp">
            <xsl:call-template name="get-namespace-adress">
                <xsl:with-param name="uri" select="$urix" />
            </xsl:call-template>
        </xsl:variable>
        <xsl:value-of select="exsl:node-set($usednamespaces)/usednamespace/prefix[../adress=$nsp]" />
    </xsl:template>

    <!-- zisti typ zaznamu-->
    <xsl:template name="get-namespace-subtype">
        <xsl:param name="uri"/>
        <xsl:call-template name="get-usednamespace-prefix">
            <xsl:with-param name="urix" select="$uri"/>
        </xsl:call-template>
        <xsl:text>:</xsl:text>
        <xsl:if test="contains($uri,'#')">
            <xsl:value-of select="substring-after($uri, '#')"/>
        </xsl:if>
        <xsl:if test="not(contains($uri,'#'))">
            <xsl:call-template name="substring-after-last">
                <xsl:with-param name="input" select="$uri" />
                <xsl:with-param name="substr" select="'/'" />
            </xsl:call-template>
        </xsl:if>
    </xsl:template>

    <!-- Pomocne templaty na pracu so stringom -->
    <!-- cely retazec pred poslednym vyskytom zadaneho retazca -->
    <xsl:template name="substring-before-last">
        <xsl:param name="input" />
        <xsl:param name="substr" />
        <xsl:if test="$substr and contains($input, $substr)">
            <xsl:variable name="temp" select="substring-after($input, $substr)" />
            <xsl:value-of select="substring-before($input, $substr)" />
            <xsl:if test="contains($temp, $substr)">
                <xsl:value-of select="$substr" />
                <xsl:call-template name="substring-before-last">
                    <xsl:with-param name="input" select="$temp" />
                    <xsl:with-param name="substr" select="$substr" />
                </xsl:call-template>
            </xsl:if>
        </xsl:if>
    </xsl:template>

    <!-- cely retazec pred poslednym vyskytom zadaneho retazca -->
    <xsl:template name="substring-after-last">
        <xsl:param name="input"/>
        <xsl:param name="substr"/>
        <xsl:variable name="temp" select="substring-after($input,$substr)"/>
        <xsl:choose>
            <xsl:when test="$substr and contains($temp,$substr)">
                <xsl:call-template name="substring-after-last">
                    <xsl:with-param name="input" select="$temp"/>
                    <xsl:with-param name="substr" select="$substr"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$temp"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>
