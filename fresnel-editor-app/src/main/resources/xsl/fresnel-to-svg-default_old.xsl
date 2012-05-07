<?xml version="1.0" encoding="UTF-8"?>
<!--+
    |
    | XSLT to transform the XML result of a Fresnel lens into an SVG representation
    |
    +--><xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:svg="http://www.w3.org/2000/svg" xmlns:f="http://www.w3.org/2004/09/fresnel-tree" exclude-result-prefixes="svg f" version="1.0">

    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" doctype-public="-//W3C//DTD SVG 1.1//EN" doctype-system="http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd" />
            
    <!-- Parameter definitions -->
    <xsl:param name="svg_width">
        <xsl:value-of select="$rect_res_x_ind+$rect_width+$prop_line_x3" />
    </xsl:param>
    <xsl:param name="svg_height">
        <xsl:value-of select="$all_children_count*$rect_prop_y_ind+$all_pic_count*$pic_height+$all_resource_count*$rect_res_y_ind" />
    </xsl:param>
    <xsl:param name="pic_height">200</xsl:param>    <!--HEIGHT OF PICTURES-->
    <xsl:param name="pic_width">400</xsl:param>    <!--WIDTH OF PICTURES-->
    <xsl:param name="pic_ind">
        <xsl:value-of select="$pic_height+-$rect_prop_y_ind+$rect_prop_y_ind+-$rect_height" />
    </xsl:param>
    <xsl:param name="rect_height">30</xsl:param>    <!--HEIGT OF RESOURCE OR VALUE RECTANGLE-->
    <xsl:param name="rect_width">400</xsl:param>    <!--WIDTH OF RESOURCE OR VALUE RECTANGLE-->
    <xsl:param name="rect_y_ind">20</xsl:param>     <!--GENERAL MARGIN FROM THE TOP-->
    <xsl:param name="rect_text_y_ind"><xsl:value-of select="$rect_y_ind+$font_size+7" /></xsl:param>
    <xsl:param name="rect_res_x_ind">20</xsl:param> <!--MARGIN FROM LEFT-->
    <xsl:param name="rect_res_y_ind">20</xsl:param> <!--MARGIN BETWEEN DIFFERENT RESOURCES-->
    <xsl:param name="rect_res_text_x_ind">
        <xsl:value-of select="$rect_res_x_ind+15" />
    </xsl:param>
    <xsl:param name="rect_prop_y_ind">35</xsl:param>    <!--MARGIN BETWEEN DIFFERENT VALUES-->
    <xsl:param name="rect_prop_text_x_ind">
        <xsl:value-of select="$prop_line_x3+15" />
    </xsl:param>
    <xsl:param name="prop_line_x1">
        <xsl:value-of select="$rect_width+$rect_res_x_ind" />
    </xsl:param>
    <xsl:param name="prop_line_x2">
        <xsl:value-of select="$rect_width+$rect_res_x_ind+120" />
    </xsl:param>
    <xsl:param name="prop_line_x3">
        <xsl:value-of select="$rect_width+$rect_res_x_ind+420" />
    </xsl:param>
    <xsl:param name="prop_line_text_x_ind">
        <xsl:value-of select="$rect_width+$rect_res_x_ind+123" />
    </xsl:param>
    <xsl:param name="prop_line_text_y_ind">
        <xsl:value-of select="$rect_y_ind+10" />
    </xsl:param>
    <xsl:param name="prop_line_ind">
        <xsl:value-of select="$rect_y_ind+15" />
    </xsl:param>
    <xsl:param name="font_size">11</xsl:param>    <!--FONT SIZE-->
    <xsl:variable name="all_resource_count">
        <xsl:value-of select="count(//f:resource)" />
    </xsl:variable>
    <xsl:variable name="all_children_count">
        <xsl:value-of select="count(//f:property)" />
    </xsl:variable>
    <xsl:variable name="all_pic_count">
        <xsl:value-of select="count(//f:value[@output-type='http://www.w3.org/2004/09/fresnel#image'])" />
    </xsl:variable>
    <!-- End of parameter definitions -->

    <!-- Match the root of the DOM tree -->
    <xsl:template match="/">
        <svg xmlns="http://www.w3.org/2000/svg" width="{$svg_width}" height="{$svg_height}">
            <xsl:apply-templates />
        </svg>
    </xsl:template>
    <!-- End root match template -->

    <!--  -->
    <xsl:template match="f:results">
        <g class="f:results">
            <xsl:apply-templates select="f:resource" />
        </g>
    </xsl:template>

    <!--  -->
    <xsl:template match="f:resource">
        <xsl:variable name="resource_count">
            <xsl:value-of select="count(preceding::f:resource)" />
        </xsl:variable>
        <xsl:variable name="children_count">
            <xsl:value-of select="count(./preceding::f:property)" />
        </xsl:variable>
        <xsl:variable name="pic_count">
            <xsl:value-of select="count(./preceding::f:property/f:values/f:value[@output-type='http://www.w3.org/2004/09/fresnel#image'])" />
        </xsl:variable>
        <g class="f:resource">
            <xsl:if test="f:content/f:before">
                <xsl:value-of select="f:content/f:before" />
            </xsl:if>
            <!--  -->
            <rect x="{$rect_res_x_ind}" y="{$rect_y_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$children_count+$pic_ind*$pic_count}" width="{$rect_width}" height="{$rect_height}" rx="10" ry="10" stroke-width="2px" stroke="black" fill="#ccc" />
            <text x="{$rect_res_text_x_ind}" y="{$rect_text_y_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$children_count+$pic_ind*$pic_count}" font-family="LucidaGrande" font-size="{$font_size}">
                <xsl:choose>
                    <xsl:when test="f:title/text() != ''">
                        <xsl:value-of select="f:title" />
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="@uri" />
                    </xsl:otherwise>
                </xsl:choose>
            </text>
            <!--  -->
            <xsl:apply-templates select="f:property">
                <xsl:with-param name="resource_count" select="$resource_count" />
                <xsl:with-param name="children_count" select="$children_count" />
                <xsl:with-param name="res_pic_count" select="$pic_count" />
            </xsl:apply-templates>
            <xsl:if test="f:content/f:after">
                <xsl:value-of select="f:content/f:after" />
            </xsl:if>
        </g>
    </xsl:template>

    <xsl:template match="f:property">
        <xsl:param name="resource_count" />
        <xsl:param name="children_count" />
        <xsl:param name="res_pic_count" />
        <xsl:variable name="property_count">
            <xsl:value-of select="count(preceding::f:property)" />
        </xsl:variable>
        <xsl:variable name="pic_count">
            <xsl:value-of select="count(./preceding::f:property/f:values/f:value[@output-type='http://www.w3.org/2004/09/fresnel#image'])" />
        </xsl:variable>
        <g class="f:property">
            <xsl:if test="f:label">
                <xsl:if test="f:content/f:before">
                    <xsl:value-of select="f:content/f:before" />
                </xsl:if>
                <xsl:apply-templates select="f:label">
                    <xsl:with-param name="resource_count" select="$resource_count" />
                    <xsl:with-param name="property_count" select="$property_count" />
                    <xsl:with-param name="children_count" select="$children_count" />
                    <xsl:with-param name="pic_count" select="$pic_count" />
                    <xsl:with-param name="res_pic_count" select="$res_pic_count" />
                </xsl:apply-templates>
                <xsl:if test="f:content/f:after">
                    <xsl:value-of select="f:content/f:after" />
                </xsl:if>
            </xsl:if>
            <xsl:apply-templates select="f:values">
                <xsl:with-param name="resource_count" select="$resource_count" />
                <xsl:with-param name="property_count" select="$property_count" />
                <xsl:with-param name="pic_count" select="$pic_count" />
            </xsl:apply-templates>
        </g>
    </xsl:template>

    <xsl:template match="f:label">
        <xsl:param name="resource_count" />
        <xsl:param name="property_count" />
        <xsl:param name="children_count" />
        <xsl:param name="pic_count" />
        <xsl:param name="res_pic_count" />
        <g class="f:label">
            <xsl:if test="f:content/f:before">
                <xsl:value-of select="f:content/f:before" />
            </xsl:if>
            <line x1="{$prop_line_x1}" y1="{$prop_line_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$children_count+$res_pic_count*$pic_ind}" x2="{$prop_line_x2}" y2="{$prop_line_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$property_count+$pic_count*$pic_ind}" stroke="#000" stroke-width="2" stroke-dasharray="9, 5" />
            <line x1="{$prop_line_x2}" y1="{$prop_line_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$property_count+$pic_count*$pic_ind}" x2="{$prop_line_x3}" y2="{$prop_line_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$property_count+$pic_count*$pic_ind}" stroke="#000" stroke-width="2" stroke-dasharray="9, 5" />
            <text x="{$prop_line_text_x_ind}" y="{$prop_line_text_y_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$property_count+$pic_count*$pic_ind}" font-family="LucidaGrande" font-size="{$font_size}">
                <xsl:choose>
                    <xsl:when test="f:title/text() != ''">
                        <xsl:value-of select="f:title" />
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="../@uri" />
                    </xsl:otherwise>
                </xsl:choose>
            </text>
            <xsl:if test="f:content/f:after">
                <xsl:value-of select="f:content/f:after" />
            </xsl:if>
        </g>
    </xsl:template>

    <xsl:template match="f:values">
        <xsl:param name="resource_count" />
        <xsl:param name="property_count" />
        <xsl:param name="pic_count" />
        <g class="f:values">
            <xsl:choose>
                <xsl:when test="f:content/f:first">
                    <xsl:value-of select="f:content/f:first" />
                </xsl:when>
                <xsl:when test="f:content/f:before">
                    <xsl:value-of select="f:content/f:before" />
                </xsl:when>
            </xsl:choose>
            <xsl:apply-templates select="f:value">
                <xsl:with-param name="before" select="f:content/f:before" />
                <xsl:with-param name="after" select="f:content/f:after" />
                <xsl:with-param name="resource_count" select="$resource_count" />
                <xsl:with-param name="property_count" select="$property_count" />
                <xsl:with-param name="pic_count" select="$pic_count" />
            </xsl:apply-templates>
            <xsl:choose>
                <xsl:when test="f:content/f:last">
                    <xsl:value-of select="f:content/f:last" />
                </xsl:when>
                <xsl:when test="f:content/f:after">
                    <xsl:value-of select="f:content/f:after" />
                </xsl:when>
            </xsl:choose>
        </g>
    </xsl:template>

    <xsl:template match="f:value">
        <xsl:param name="resource_count" />
        <xsl:param name="property_count" />
        <xsl:param name="pic_count" />
        <xsl:param name="before" />
        <xsl:param name="after" />
        <g class="f:value">
            <xsl:if test="position()&gt;1">
                <xsl:value-of select="$before" />
            </xsl:if>
            <!-- Handling of VALUE TYPE -->
            <xsl:choose>
                <!-- IMAGE VALUE TYPE -->
                <xsl:when test="@output-type='http://www.w3.org/2004/09/fresnel#image'">
                    <xsl:choose>
                        <xsl:when test="f:resource">
                            <rect x="{$prop_line_x3}" y="{$rect_y_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$property_count+$pic_count*$pic_ind}" width="{$rect_width}" height="{$rect_height}" stroke-width="2px" stroke="grey" fill="#eee" />
                            <image xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="{f:resource/@uri}" />
                        </xsl:when>
                        <xsl:otherwise>
                            <rect x="{$prop_line_x3}" y="{$rect_y_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$property_count+$pic_count*$pic_ind}" width="{$pic_width}" height="{$pic_height}" stroke-width="2px" stroke="grey" fill="#eee" />
                            <image xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="{f:title}" width="{-2+$pic_width}" height="{-2+$pic_height}" x="752" y="{$rect_y_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$property_count+$pic_count*$pic_ind+1}" />
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
                <!-- URI VALUE TYPE -->
                <xsl:when test="@output-type='http://www.w3.org/2004/09/fresnel#uri'">
                    <rect x="{$prop_line_x3}" y="{$rect_y_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$property_count+$pic_count*$pic_ind}" width="{$rect_width}" height="{$rect_height}" stroke-width="2px" stroke="grey" fill="#eee" />
                    <text x="{$rect_prop_text_x_ind}" y="{$rect_text_y_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$property_count+$pic_count*$pic_ind}" font-family="LucidaGrande" font-size="{$font_size}">
                        <xsl:choose>
                            <xsl:when test="f:resource">
                                <xsl:value-of select="f:resource/@uri" />
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
                                <rect x="{$prop_line_x3}" y="{$rect_y_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$property_count+$pic_count*$pic_ind}" width="{$rect_width}" height="{$rect_height}" stroke-width="2px" stroke="gray" fill="#eee" />
                                <text x="{$rect_prop_text_x_ind}" y="{$rect_text_y_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$property_count+$pic_count*$pic_ind}" font-family="LucidaGrande" font-size="{$font_size}">
                                    <xsl:value-of select="f-resource/@uri" />
                                </text>
                            </a>
                        </xsl:when>
                        <xsl:otherwise>
                            <a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="{f:title}">
                                <rect x="{$prop_line_x3}" y="{$rect_y_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$property_count+$pic_count*$pic_ind}" width="{$rect_width}" height="{$rect_height}" stroke-width="2px" stroke="gray" fill="#eee" />
                                <text x="{$rect_prop_text_x_ind}" y="{$rect_text_y_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$property_count+$pic_count*$pic_ind}" font-family="LucidaGrande" font-size="{$font_size}">
                                    <xsl:value-of select="f:title" />
                                </text>
                            </a>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:choose>
                        <xsl:when test="f:title">
                            <rect x="{$prop_line_x3}" y="{$rect_y_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$property_count+$pic_count*$pic_ind}" width="{$rect_width}" height="{$rect_height}" stroke-width="2px" stroke="gray" fill="#eee" />
                            <text x="{$rect_prop_text_x_ind}" y="{$rect_text_y_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$property_count+$pic_count*$pic_ind}" font-family="LucidaGrande" font-size="{$font_size}">
                                <xsl:value-of select="f:title" />
                            </text>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:apply-templates select="f:resource" />
                            <rect x="{$prop_line_x3}" y="10" width="{$rect_width}" height="{$rect_height}" stroke-width="2px" stroke="gray" fill="#eee" />
                            <text x="{$rect_prop_text_x_ind}" y="{$rect_text_y_ind+$rect_res_y_ind*$resource_count+$rect_prop_y_ind*$property_count+$pic_count*$pic_ind}" font-family="LucidaGrande" font-size="{$font_size}">
                                <xsl:value-of select="f:title" />
                            </text>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:if test="position()!=last()">
                <xsl:value-of select="$after" />
            </xsl:if>
        </g>
    </xsl:template>
</xsl:stylesheet>
