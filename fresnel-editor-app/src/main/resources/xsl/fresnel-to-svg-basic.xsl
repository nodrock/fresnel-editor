<?xml version="1.0" encoding="UTF-8"?>

<!--+
    |
    | XSLT to transform the XML result of a Fresnel lens into an SVG representation
    |
    +-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:svg="http://www.w3.org/2000/svg"
                xmlns:f="http://www.w3.org/2004/09/fresnel-tree"
                exclude-result-prefixes="svg f"
                version="1.0">

    <xsl:output method="xml" version="1.0" encoding="utf-8" indent="yes" doctype-public="-//W3C//DTD SVG 1.1//EN" doctype-system="http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd"/>

    <!-- Parameter definitions -->
    <!-- End of parameter definitions -->

    <!-- Match the root of the DOM tree -->
    <xsl:template match="/">
        <svg width="1090" height="1730" xmlns="http://www.w3.org/2000/svg">
            <xsl:apply-templates/>
        </svg>
    </xsl:template>
    <!-- End root match template -->

    <!--  -->
    <xsl:template match="f:results">
        <g class="f:results">
            <xsl:apply-templates select="f:resource"/>
        </g>
    </xsl:template>

    <!--  -->
    <xsl:template match="f:resource">
        <xsl:variable name="resource_count">
            <xsl:value-of select="count(preceding::f:resource)"/>
        </xsl:variable>
        <xsl:variable name="children_count">
            <xsl:value-of select="count(./preceding::f:property)"/>
        </xsl:variable>
        <xsl:variable name="picture_count">
            <xsl:value-of select="count(./preceding::f:property/f:values/f:value[@output-type='http://www.w3.org/2004/09/fresnel#image'])"/>
        </xsl:variable>
        <g class="f:resource">
            <xsl:if test="f:content/f:before">
                <xsl:value-of select="f:content/f:before"/>
            </xsl:if>
            <!--  -->
            <rect x="20" y="{20+20*$resource_count+35*$children_count+70*$picture_count}" width="330" height="30" rx="10" ry="10" stroke-width="2px" stroke="black" fill="#ccc"/>
            <text x="35" y="{38+20*$resource_count+35*$children_count+70*$picture_count}" font-family="LucidaGrande" font-size="11px">
                <xsl:choose>
                    <xsl:when test="f:title/text() != ''">
                        <xsl:value-of select="f:title"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="@uri"/>
                    </xsl:otherwise>
                </xsl:choose>
            </text>
            <!--  -->
            <xsl:apply-templates select="f:property">
                <xsl:with-param name="resource_count" select="$resource_count"/>
                <xsl:with-param name="children_count" select="$children_count"/>
                <xsl:with-param name="res_pic_count" select="$picture_count"/>
            </xsl:apply-templates>
            <xsl:if test="f:content/f:after">
                <xsl:value-of select="f:content/f:after"/>
            </xsl:if>
        </g>
    </xsl:template>

    <xsl:template match="f:property">
        <xsl:param name="resource_count"/>
        <xsl:param name="children_count"/>
        <xsl:param name="res_pic_count"/>
        <xsl:variable name="property_count">
            <xsl:value-of select="count(preceding::f:property)"/>
        </xsl:variable>
        <xsl:variable name="picture_count">
            <xsl:value-of select="count(./preceding::f:property/f:values/f:value[@output-type='http://www.w3.org/2004/09/fresnel#image'])"/>
        </xsl:variable>
        <g class="f:property">
            <xsl:if test="f:label">
                <xsl:if test="f:content/f:before">
                    <xsl:value-of select="f:content/f:before"/>
                </xsl:if>
                <xsl:apply-templates select="f:label">
                    <xsl:with-param name="resource_count" select="$resource_count"/>
                    <xsl:with-param name="property_count" select="$property_count"/>
                    <xsl:with-param name="children_count" select="$children_count"/>
                    <xsl:with-param name="picture_count" select="$picture_count"/>
                    <xsl:with-param name="res_pic_count" select="$res_pic_count"/>
                </xsl:apply-templates>
                <xsl:if test="f:content/f:after">
                    <xsl:value-of select="f:content/f:after"/>
                </xsl:if>
            </xsl:if>
            <xsl:apply-templates select="f:values">
                <xsl:with-param name="resource_count" select="$resource_count"/>
                <xsl:with-param name="property_count" select="$property_count"/>
                <xsl:with-param name="picture_count" select="$picture_count"/>
            </xsl:apply-templates>
        </g>
    </xsl:template>

    <xsl:template match="f:label">
        <xsl:param name="resource_count"/>
        <xsl:param name="property_count"/>
        <xsl:param name="children_count"/>
        <xsl:param name="picture_count"/>
        <xsl:param name="res_pic_count"/>
        <g class="f:label">
            <xsl:if test="f:content/f:before">
                <xsl:value-of select="f:content/f:before"/>
            </xsl:if>
            <line x1="350" y1="{35+20*$resource_count+35*$children_count+$res_pic_count*70}" x2="450" y2="{35+20*$resource_count+35*$property_count+$picture_count*70}" stroke="#000" stroke-width="2" stroke-dasharray="9, 5"/>
            <line x1="450" y1="{35+20*$resource_count+35*$property_count+$picture_count*70}" x2="750" y2="{35+20*$resource_count+35*$property_count+$picture_count*70}" stroke="#000" stroke-width="2" stroke-dasharray="9, 5"/>
            <text x="453" y="{30+20*$resource_count+35*$property_count+$picture_count*70}" font-family="LucidaGrande" font-size="11px">
                <xsl:choose>
                    <xsl:when test="f:title/text() != ''">
                        <xsl:value-of select="f:title"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="../@uri"/>
                    </xsl:otherwise>
                </xsl:choose>
            </text>
            <xsl:if test="f:content/f:after">
                <xsl:value-of select="f:content/f:after"/>
            </xsl:if>
        </g>
    </xsl:template>

    <xsl:template match="f:values">
        <xsl:param name="resource_count"/>
        <xsl:param name="property_count"/>
        <xsl:param name="picture_count"/>
        <g class="f:values">
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
                <xsl:with-param name="resource_count" select="$resource_count"/>
                <xsl:with-param name="property_count" select="$property_count"/>
                <xsl:with-param name="picture_count" select="$picture_count"/>
            </xsl:apply-templates>
            <xsl:choose>
                <xsl:when test="f:content/f:last">
                    <xsl:value-of select="f:content/f:last"/>
                </xsl:when>
                <xsl:when test="f:content/f:after">
                    <xsl:value-of select="f:content/f:after"/>
                </xsl:when>
            </xsl:choose>
        </g>
    </xsl:template>

    <xsl:template match="f:value">
        <xsl:param name="resource_count"/>
        <xsl:param name="property_count"/>
        <xsl:param name="picture_count"/>
        <xsl:param name="before"/>
        <xsl:param name="after"/>
        <g class="f:value">
            <xsl:if test="position()&gt;1">
                <xsl:value-of select="$before"/>
            </xsl:if>
            <!-- Handling of VALUE TYPE -->
            <xsl:choose>
                <!-- IMAGE VALUE TYPE -->
                <xsl:when test="@output-type='http://www.w3.org/2004/09/fresnel#image'">
                    <xsl:choose>
                        <xsl:when test="f:resource">
                            <rect x="750" y="{20+20*$resource_count+35*$property_count+$picture_count*70}" width="330" height="30" stroke-width="2px" stroke="grey" fill="#eee"/>
                            <image xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="{f:resource/@uri}"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <rect x="750" y="{20+20*$resource_count+35*$property_count+$picture_count*70}" width="100" height="100" stroke-width="2px" stroke="grey" fill="#eee"/>
                            <image xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="{f:title}" width="98" height="98" x="752" y="{20+20*$resource_count+35*$property_count+$picture_count*70+1}"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
                <!-- URI VALUE TYPE -->
                <xsl:when test="@output-type='http://www.w3.org/2004/09/fresnel#uri'">
                    <rect x="750" y="{20+20*$resource_count+35*$property_count+$picture_count*70}" width="330" height="30" stroke-width="2px" stroke="grey" fill="#eee"/>
                    <text x="765" y="{38+20*$resource_count+35*$property_count+$picture_count*70}" font-family="LucidaGrande" font-size="11px">
                        <xsl:choose>
                            <xsl:when test="f:resource">
                                <xsl:value-of select="f:resource/@uri"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="f:title" />
                            </xsl:otherwise>
                        </xsl:choose>
                    </text>
                </xsl:when>
                <!-- EXTERNAL LINK VALUE TYPE -->
                <xsl:when test="@output-type='http://www.w3.org/2004/09/fresnel#externalLink'">
                    <xsl:choose>
                        <xsl:when test="f:resource">
                            <a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="{f:resource/@uri}">
                                <rect x="750" y="{20+20*$resource_count+35*$property_count+$picture_count*70}" width="330" height="30" stroke-width="2px" stroke="gray" fill="#eee"/>
                                <text x="765" y="{38+20*$resource_count+35*$property_count+$picture_count*70}" font-family="LucidaGrande" font-size="11px">
                                    <xsl:value-of select="f-resource/@uri"/>
                                </text>
                            </a>
                        </xsl:when>
                        <xsl:otherwise>
                            <a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="{f:title}">
                                <rect x="750" y="{20+20*$resource_count+35*$property_count+$picture_count*70}" width="330" height="30" stroke-width="2px" stroke="gray" fill="#eee"/>
                                <text x="765" y="{38+20*$resource_count+35*$property_count+$picture_count*70}" font-family="LucidaGrande" font-size="11px">
                                    <xsl:value-of select="f:title"/>
                                </text>
                            </a>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:choose>
                        <xsl:when test="f:title">
                            <rect x="750" y="{20+20*$resource_count+35*$property_count+$picture_count*70}" width="330" height="30" stroke-width="2px" stroke="gray" fill="#eee"/>
                            <text x="765" y="{38+20*$resource_count+35*$property_count+$picture_count*70}" font-family="LucidaGrande" font-size="11px">
                                <xsl:value-of select="f:title"/>
                            </text>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:apply-templates select="f:resource"/>
                            <rect x="750" y="10" width="330" height="30" stroke-width="2px" stroke="gray" fill="#eee"/>
                            <text x="765" y="{38+20*$resource_count+35*$property_count+$picture_count*70}" font-family="LucidaGrande" font-size="11px">
                                <xsl:value-of select="f:title"/>
                            </text>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:if test="position()!=last()">
                <xsl:value-of select="$after"/>
            </xsl:if>
        </g>
    </xsl:template>

</xsl:stylesheet>
