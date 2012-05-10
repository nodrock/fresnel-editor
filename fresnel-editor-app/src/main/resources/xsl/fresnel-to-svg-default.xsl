<?xml version="1.0" encoding="UTF-8"?>
<!--+
    |
    | XSLT to transform the XML result of a Fresnel lens into an SVG representation
    |
    | @author Milos Kalab
    | @version 5. 5. 2012
    +--><xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:svg="http://www.w3.org/2000/svg" xmlns:f="http://www.w3.org/2004/09/fresnel-tree" exclude-result-prefixes="svg f" version="1.0">

    <xsl:output method="xml" indent="yes" doctype-public="-//W3C//DTD SVG 1.1//EN" doctype-system="http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd" />
    
<!-- Global parameter definitions -->

    <xsl:param name="pageTitle" select="'Fresnel rendered SVG image'" />
    <xsl:param name="cssStylesheetURL" select="'./fresnel-to-svg-default.css'" />
    <xsl:param name="svg_width">
        <xsl:value-of select="($rect_width+($rect_width+$prop_line_univ_lenght)*$resource_depth)+2*$rect_res_x_ind" />
    </xsl:param>
    <xsl:param name="svg_height">
        <xsl:value-of select="$all_children_count*($rect_prop_y_ind+-$rect_height)+($all_children_count+-$all_pic_count+-$all_long_text_3+-$all_long_text_6+-$all_long_text_9+-$all_long_text_x)*$rect_prop_y_ind+$all_pic_count*$pic_height+$all_long_text_3*60+$all_long_text_6*100+$all_long_text_9*155+$all_long_text_x*300" />
    </xsl:param>
    <xsl:param name="prop_line_univ_lenght">
        <xsl:value-of select="$prop_line_ind_1+$prop_line_ind_2+2*$rect_res_x_ind" />
    </xsl:param>
    <xsl:param name="pic_height">200</xsl:param>    <!--IMPORTANT SETTING-->
    <xsl:param name="pic_width">200</xsl:param>    <!--IMPORTANT SETTING-->
    <xsl:param name="pic_y_ind">
        <xsl:value-of select="$pic_height+-$rect_height" />
    </xsl:param>
    <xsl:param name="long_text_3_y_ind">
        <xsl:value-of select="65+-$rect_height" />
    </xsl:param>
    <xsl:param name="long_text_6_y_ind">
        <xsl:value-of select="100+-$rect_height" />
    </xsl:param>
    <xsl:param name="long_text_9_y_ind">
        <xsl:value-of select="155+-$rect_height" />
    </xsl:param>
    <xsl:param name="long_text_x_y_ind">
        <xsl:value-of select="300+-$rect_height" />
    </xsl:param>
    <xsl:param name="rect_height">
        <xsl:value-of select="$font_size*2.7" />
    </xsl:param>
    <xsl:param name="rect_prop_y_ind">
        <xsl:value-of select="$rect_height+15" />
    </xsl:param>
    <xsl:param name="prop_line_ind_1">100</xsl:param>   
    <xsl:param name="prop_line_ind_2">300</xsl:param>   <!--IMPORTANT SETTING-->
    <xsl:param name="rect_width">200</xsl:param>    <!--IMPORTANT SETTING-->
    <xsl:param name="rect_y_ind">20</xsl:param>
    <xsl:param name="rect_res_x_ind">20</xsl:param>
    <xsl:param name="rect_text_y_ind">
        <xsl:value-of select="$rect_y_ind+$font_size+7" />
    </xsl:param>
    <!--<xsl:param name="rect_prop_y_ind">35</xsl:param>-->
    <xsl:param name="prop_line_text_y_ind">30</xsl:param>
    <xsl:param name="prop_line_ind">35</xsl:param>
    <xsl:param name="font_size">11</xsl:param>    <!--IMPORTANT SETTING-->
    <xsl:variable name="all_children_count">
        <xsl:value-of select="count(//value)+2" />
    </xsl:variable>
    <xsl:variable name="all_pic_count">
        <xsl:value-of select="count(//value[@output-type='http://www.w3.org/2004/09/fresnel#image'])+count(//value[@output-type='http://www.w3.org/2004/09/fresnel#long-text'])" />
    </xsl:variable>
    <xsl:variable name="resource_depth">
        <xsl:choose>
            <!-- DEPTH IS LIMITED -->
            <xsl:when test="count(/results/resource/property/values/value/resource/property/values/value/resource/property/values/value/resource/property/values/value/resource/property) &gt; 0">5</xsl:when>
            <xsl:when test="count(/results/resource/property/values/value/resource/property/values/value/resource/property/values/value/resource/property) &gt; 0">4</xsl:when>
            <xsl:when test="count(/results/resource/property/values/value/resource/property/values/value/resource/property) &gt; 0">3</xsl:when>
            <xsl:when test="count(/results/resource/property/values/value/resource/property) &gt; 0">2</xsl:when>
            <xsl:when test="count(/results/resource/property) &gt; 0">1</xsl:when>
            <xsl:when test="count(/results/resource) &gt; 0">0</xsl:when>
        </xsl:choose>
    </xsl:variable>
    <xsl:variable name="all_long_text_3">
        <xsl:value-of select="count(//value[@long-text-rows='2'])+count(//value[@long-text-rows='3'])" />
    </xsl:variable>
    <xsl:variable name="all_long_text_6">
        <xsl:value-of select="count(//value[@long-text-rows='4'])+count(//value[@long-text-rows='5'])" />
    </xsl:variable>
    <xsl:variable name="all_long_text_9">
        <xsl:value-of select="count(//value[@long-text-rows='6'])+count(//value[@long-text-rows='7'])+count(//value[@long-text-rows='8'])" />
    </xsl:variable>
    <xsl:variable name="all_long_text_x">
        <xsl:value-of select="count(//value[@long-text-rows&gt; 8])" />
    </xsl:variable>
<!-- End of global parameter definitions -->

<!-- Match the root of the DOM tree -->
    <xsl:template match="/">
        <svg:svg width="{$svg_width}" height="{$svg_height}">
           <!-- <svg:style type="text/css">@import url(./fresnel-to-svg-default.css);</svg:style>-->
            <svg:style type="text/css">
                <xsl:value-of select="concat('@import url(',$cssStylesheetURL, ');')" />
                
            </svg:style>
            <xsl:apply-templates />
        </svg:svg>
    </xsl:template>
<!-- End root match template -->

<!-- Match the RESULTS node of the DOM tree  -->
    <xsl:template match="results">
        <!-- First-level parameter definitions -->
        <xsl:param name="rect_res_x_ind">20</xsl:param>
        <xsl:param name="rect_res_text_x_ind">35</xsl:param>
        <xsl:param name="prop_line_x1">
            <xsl:value-of select="$rect_width+$rect_res_x_ind" />
        </xsl:param>
        <xsl:param name="prop_line_x2">
            <xsl:value-of select="$prop_line_x1+$prop_line_ind_1" />
        </xsl:param>
        <xsl:param name="prop_line_x3">
            <xsl:value-of select="$prop_line_x2+$prop_line_ind_2" />
        </xsl:param>
        <xsl:param name="rect_prop_text_x_ind">
            <xsl:value-of select="$prop_line_x3+15" />
        </xsl:param>
        <xsl:param name="prop_line_text_x_ind">
            <xsl:value-of select="$prop_line_x2+3" />
        </xsl:param>
        <xsl:param name="pic_x_ind">
            <xsl:value-of select="$prop_line_x3+2" />
        </xsl:param>
        <!-- End of first-level parameter definitions -->
        <svg:g class="f-results">
            <xsl:apply-templates select="resource">
                <xsl:with-param name="rect_res_x_ind" select="$rect_res_x_ind" />
                <xsl:with-param name="rect_res_text_x_ind" select="$rect_res_text_x_ind" />
                <xsl:with-param name="rect_prop_text_x_ind" select="$rect_prop_text_x_ind" />
                <xsl:with-param name="prop_line_x1" select="$prop_line_x1" />
                <xsl:with-param name="prop_line_x2" select="$prop_line_x2" />
                <xsl:with-param name="prop_line_x3" select="$prop_line_x3" />
                <xsl:with-param name="prop_line_text_x_ind" select="$prop_line_text_x_ind" />
                <xsl:with-param name="pic_x_ind" select="$pic_x_ind" />
            </xsl:apply-templates>
        </svg:g>
    </xsl:template>
<!-- End RESULTS node match template -->

<!-- Match the RESOURCE node of the DOM tree -->
    <xsl:template match="resource">
        <xsl:param name="rect_res_x_ind" />
        <xsl:param name="rect_res_text_x_ind" />
        <xsl:param name="rect_prop_text_x_ind" />
        <xsl:param name="prop_line_x1" />
        <xsl:param name="prop_line_x2" />
        <xsl:param name="prop_line_x3" />
        <xsl:param name="prop_line_text_x_ind" />
        <xsl:param name="pic_x_ind" />
        <xsl:variable name="resource_count">
            <xsl:value-of select="count(preceding::resource)" />
        </xsl:variable>
        <xsl:variable name="values_count">
            <xsl:value-of select="count(preceding::value)" />
        </xsl:variable>
        <xsl:variable name="pic_count">
            <xsl:value-of select="count(preceding::value[@output-type='http://www.w3.org/2004/09/fresnel#image'])+count(preceding::value[@output-type='http://www.w3.org/2004/09/fresnel#long-text'])" />
        </xsl:variable>
        <xsl:variable name="long_text_3">
            <xsl:value-of select="count(preceding::value[@long-text-rows='2'])+count(preceding::value[@long-text-rows='3'])" />
        </xsl:variable>
        <xsl:variable name="long_text_6">
            <xsl:value-of select="count(preceding::value[@long-text-rows='4'])+count(preceding::value[@long-text-rows='5'])" />
        </xsl:variable>
        <xsl:variable name="long_text_9">
            <xsl:value-of select="count(preceding::value[@long-text-rows='6'])+count(preceding::value[@long-text-rows='7'])+count(preceding::value[@long-text-rows='8'])" />
        </xsl:variable>
        <xsl:variable name="long_text_x">
            <xsl:value-of select="count(preceding::value[@long-text-rows&gt; 8])" />
        </xsl:variable>
        <svg:g class="f-resource">
            <!-- Drawing resource node -->
            <svg:rect x="{$rect_res_x_ind}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_y_ind*$pic_count+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" width="{$rect_width}" height="{$rect_height}" rx="10" ry="10" />
            <xsl:choose>
                <xsl:when test="@long-text-rows ='1'">
                    <svg:text x="{$rect_res_text_x_ind}" y="{$rect_text_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_y_ind*$pic_count+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" full-value="{@full-uri}" font-size="{$font_size}">
                  
                        <xsl:choose>
                            <xsl:when test="title/text() != ''">
                                <xsl:value-of select="title" />
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="@uri" />
                            </xsl:otherwise>
                        </xsl:choose>
                    </svg:text>
                </xsl:when>
                <xsl:otherwise>
                    <svg:text x="{$rect_res_text_x_ind}" y="{$rect_text_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_y_ind*$pic_count+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" font-size="{$font_size}">
                        <xsl:choose>
                            <xsl:when test="title/text() != ''">
                                <xsl:value-of select="title" />
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="@uri" />
                            </xsl:otherwise>
                        </xsl:choose>
                    </svg:text>
                </xsl:otherwise>
            </xsl:choose>
                    
            <!--  -->
            <xsl:apply-templates select="property">
                <xsl:with-param name="resource_count" select="$resource_count" />
                <xsl:with-param name="res_values_count" select="$values_count" />
                <xsl:with-param name="res_pic_count" select="$pic_count" />
                <xsl:with-param name="res_long_text_3" select="$long_text_3" />
                <xsl:with-param name="res_long_text_6" select="$long_text_6" />
                <xsl:with-param name="res_long_text_9" select="$long_text_9" />
                <xsl:with-param name="res_long_text_x" select="$long_text_x" />
                <xsl:with-param name="rect_res_x_ind" select="$rect_res_x_ind" />
                <xsl:with-param name="rect_res_text_x_ind" select="$rect_res_text_x_ind" />
                <xsl:with-param name="rect_prop_text_x_ind" select="$rect_prop_text_x_ind" />
                <xsl:with-param name="prop_line_x1" select="$prop_line_x1" />
                <xsl:with-param name="prop_line_x2" select="$prop_line_x2" />
                <xsl:with-param name="prop_line_x3" select="$prop_line_x3" />
                <xsl:with-param name="prop_line_text_x_ind" select="$prop_line_text_x_ind" />
                <xsl:with-param name="pic_x_ind" select="$pic_x_ind" />
            </xsl:apply-templates>
        </svg:g>
    </xsl:template>
<!-- End RESOURCE node match template -->

<!-- Match the PROPERTY node of the DOM tree -->
    <xsl:template match="property">
        <xsl:param name="resource_count" />
        <xsl:param name="res_values_count" />
        <xsl:param name="res_pic_count" />
        <xsl:param name="res_long_text_3" />
        <xsl:param name="res_long_text_6" />
        <xsl:param name="res_long_text_9" />
        <xsl:param name="res_long_text_x" />
        <xsl:param name="rect_res_x_ind" />
        <xsl:param name="rect_res_text_x_ind" />
        <xsl:param name="rect_prop_text_x_ind" />
        <xsl:param name="prop_line_x1" />
        <xsl:param name="prop_line_x2" />
        <xsl:param name="prop_line_x3" />
        <xsl:param name="prop_line_text_x_ind" />
        <xsl:param name="pic_x_ind" />
        <xsl:variable name="values_count">
            <xsl:value-of select="count(preceding::value)" />
        </xsl:variable>
        <xsl:variable name="property_count">
            <xsl:value-of select="count(preceding::property)" />
        </xsl:variable>
        <xsl:variable name="pic_count">
            <xsl:value-of select="count(preceding::value[@output-type='http://www.w3.org/2004/09/fresnel#image'])+count(preceding::value[@output-type='http://www.w3.org/2004/09/fresnel#long-text'])" />
        </xsl:variable>
        <xsl:variable name="long_text_3">
            <xsl:value-of select="count(preceding::value[@long-text-rows='2'])+count(preceding::value[@long-text-rows='3'])" />
        </xsl:variable>
        <xsl:variable name="long_text_6">
            <xsl:value-of select="count(preceding::value[@long-text-rows='4'])+count(preceding::value[@long-text-rows='5'])" />
        </xsl:variable>
        <xsl:variable name="long_text_9">
            <xsl:value-of select="count(preceding::value[@long-text-rows='6'])+count(preceding::value[@long-text-rows='7'])+count(preceding::value[@long-text-rows='8'])" />
        </xsl:variable>
        <xsl:variable name="long_text_x">
            <xsl:value-of select="count(preceding::value[@long-text-rows&gt; 8])" />
        </xsl:variable>
        <svg:g class="f-property">
            <xsl:if test="label">
                <xsl:apply-templates select="label">
                    <xsl:with-param name="resource_count" select="$resource_count" />
                    <xsl:with-param name="property_count" select="$property_count" />
                    <xsl:with-param name="res_values_count" select="$res_values_count" />
                    <xsl:with-param name="res_long_text_3" select="$res_long_text_3" />
                    <xsl:with-param name="res_long_text_6" select="$res_long_text_6" />
                    <xsl:with-param name="res_long_text_9" select="$res_long_text_9" />
                    <xsl:with-param name="res_long_text_x" select="$res_long_text_x" />
                    <xsl:with-param name="values_count" select="$values_count" />
                    <xsl:with-param name="pic_count" select="$pic_count" />
                    <xsl:with-param name="long_text_3" select="$long_text_3" />
                    <xsl:with-param name="long_text_6" select="$long_text_6" />
                    <xsl:with-param name="long_text_9" select="$long_text_9" />
                    <xsl:with-param name="long_text_x" select="$long_text_x" />
                    <xsl:with-param name="res_pic_count" select="$res_pic_count" />
                    <xsl:with-param name="rect_res_x_ind" select="$rect_res_x_ind" />
                    <xsl:with-param name="rect_res_text_x_ind" select="$rect_res_text_x_ind" />
                    <xsl:with-param name="rect_prop_text_x_ind" select="$rect_prop_text_x_ind" />
                    <xsl:with-param name="prop_line_x1" select="$prop_line_x1" />
                    <xsl:with-param name="prop_line_x2" select="$prop_line_x2" />
                    <xsl:with-param name="prop_line_x3" select="$prop_line_x3" />
                    <xsl:with-param name="prop_line_text_x_ind" select="$prop_line_text_x_ind" />
                    <xsl:with-param name="pic_x_ind" select="$pic_x_ind" />
                </xsl:apply-templates>
            </xsl:if>
            <xsl:apply-templates select="values">
                <xsl:with-param name="resource_count" select="$resource_count" />
                <xsl:with-param name="property_count" select="$property_count" />
                <xsl:with-param name="res_values_count" select="$values_count" />
                <xsl:with-param name="rect_res_x_ind" select="$rect_res_x_ind" />
                <xsl:with-param name="rect_res_text_x_ind" select="$rect_res_text_x_ind" />
                <xsl:with-param name="rect_prop_text_x_ind" select="$rect_prop_text_x_ind" />
                <xsl:with-param name="prop_line_x1" select="$prop_line_x1" />
                <xsl:with-param name="prop_line_x2" select="$prop_line_x2" />
                <xsl:with-param name="prop_line_x3" select="$prop_line_x3" />
                <xsl:with-param name="prop_line_text_x_ind" select="$prop_line_text_x_ind" />
                <xsl:with-param name="pic_x_ind" select="$pic_x_ind" />
            </xsl:apply-templates>
        </svg:g>
    </xsl:template>
<!-- End PROPERTY node match template -->

<!-- Match the LABEL node of the DOM tree -->
    <xsl:template match="label">
        <xsl:param name="resource_count" />
        <xsl:param name="property_count" />
        <xsl:param name="res_values_count" />
        <xsl:param name="res_long_text_3" />
        <xsl:param name="res_long_text_6" />
        <xsl:param name="res_long_text_9" />
        <xsl:param name="res_long_text_x" />
        <xsl:param name="values_count" />
        <xsl:param name="pic_count" />
        <xsl:param name="long_text_3" />
        <xsl:param name="long_text_6" />
        <xsl:param name="long_text_9" />
        <xsl:param name="long_text_x" />
        <xsl:param name="res_pic_count" />
        <xsl:param name="rect_res_x_ind" />
        <xsl:param name="rect_res_text_x_ind" />
        <xsl:param name="rect_prop_text_x_ind" />
        <xsl:param name="prop_line_x1" />
        <xsl:param name="prop_line_x2" />
        <xsl:param name="prop_line_x3" />
        <xsl:param name="prop_line_text_x_ind" />
        <xsl:param name="pic_x_ind" />
        <svg:g class="f-label">
<!-- Drawing first part of property line and label -->
            <svg:line x1="{$prop_line_x1}" y1="{$prop_line_ind+20*$resource_count+$rect_prop_y_ind*$res_values_count+$res_pic_count*$pic_y_ind+$res_long_text_3*$long_text_3_y_ind+$res_long_text_6*$long_text_6_y_ind+$res_long_text_9*$long_text_9_y_ind+$res_long_text_x*$long_text_x_y_ind}" x2="{$prop_line_x2}" y2="{$prop_line_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" />
            <xsl:choose>
                <xsl:when test="@long-text-rows != '1'">
                    <svg:text x="{$prop_line_text_x_ind}" y="{$prop_line_text_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" full-value="{title/@full-label}" font-size="{$font_size}">
                        <xsl:choose>
                            <xsl:when test="title/text() != ''">
                                <xsl:value-of select="title" />
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="../@uri" />
                            </xsl:otherwise>
                        </xsl:choose>
                    </svg:text>
                </xsl:when>
                <xsl:otherwise>
                    <svg:text x="{$prop_line_text_x_ind}" y="{$prop_line_text_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" font-size="{$font_size}">
                        <xsl:choose>
                            <xsl:when test="title/text() != ''">
                                <xsl:value-of select="title" />
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="../@uri" />
                            </xsl:otherwise>
                        </xsl:choose>
                    </svg:text>
                </xsl:otherwise>
            </xsl:choose>
        </svg:g>
    </xsl:template>
<!-- End LABEL node match template -->

<!-- Match the VALUES node of the DOM tree -->
    <xsl:template match="values">
        <xsl:param name="rect_res_x_ind" />
        <xsl:param name="rect_res_text_x_ind" />
        <xsl:param name="rect_prop_text_x_ind" />
        <xsl:param name="prop_line_x1" />
        <xsl:param name="prop_line_x2" />
        <xsl:param name="prop_line_x3" />
        <xsl:param name="prop_line_text_x_ind" />
        <xsl:param name="pic_x_ind" />
        <xsl:param name="resource_count" />
        <xsl:param name="property_count" />
        <xsl:variable name="pic_count">
            <xsl:value-of select="count(preceding::value[@output-type='http://www.w3.org/2004/09/fresnel#image'])+count(preceding::value[@output-type='http://www.w3.org/2004/09/fresnel#long-text'])" />
        </xsl:variable>
        <xsl:variable name="long_text_3">
            <xsl:value-of select="count(preceding::value[@long-text-rows='2'])+count(preceding::value[@long-text-rows='3'])" />
        </xsl:variable>
        <xsl:variable name="long_text_6">
            <xsl:value-of select="count(preceding::value[@long-text-rows='4'])+count(preceding::value[@long-text-rows='5'])" />
        </xsl:variable>
        <xsl:variable name="long_text_9">
            <xsl:value-of select="count(preceding::value[@long-text-rows='6'])+count(preceding::value[@long-text-rows='7'])+count(preceding::value[@long-text-rows='8'])" />
        </xsl:variable>
        <xsl:variable name="long_text_x">
            <xsl:value-of select="count(preceding::value[@long-text-rows&gt; 8])" />
        </xsl:variable>
        <xsl:variable name="values_count">
            <xsl:value-of select="count(preceding::value)" />
        </xsl:variable>
        <svg:g class="f-values">
            <xsl:apply-templates select="value">
                <xsl:with-param name="resource_count" select="$resource_count" />
                <xsl:with-param name="property_count" select="$property_count" />
                <xsl:with-param name="res_values_count" select="$values_count" />
                <xsl:with-param name="res_pic_count" select="$pic_count" />
                <xsl:with-param name="res_long_text_3" select="$long_text_3" />
                <xsl:with-param name="res_long_text_6" select="$long_text_6" />
                <xsl:with-param name="res_long_text_9" select="$long_text_9" />
                <xsl:with-param name="res_long_text_x" select="$long_text_x" />
                <xsl:with-param name="rect_res_x_ind" select="$rect_res_x_ind" />
                <xsl:with-param name="rect_res_text_x_ind" select="$rect_res_text_x_ind" />
                <xsl:with-param name="rect_prop_text_x_ind" select="$rect_prop_text_x_ind" />
                <xsl:with-param name="prop_line_x1" select="$prop_line_x1" />
                <xsl:with-param name="prop_line_x2" select="$prop_line_x2" />
                <xsl:with-param name="prop_line_x3" select="$prop_line_x3" />
                <xsl:with-param name="prop_line_text_x_ind" select="$prop_line_text_x_ind" />
                <xsl:with-param name="pic_x_ind" select="$pic_x_ind" />
            </xsl:apply-templates>
        </svg:g>
    </xsl:template>
<!-- End VALUES node match template -->

<!-- Match the VALUE node of the DOM tree -->
    <xsl:template match="value">
        <xsl:param name="rect_res_x_ind" />
        <xsl:param name="rect_res_text_x_ind" />
        <xsl:param name="rect_prop_text_x_ind" />
        <xsl:param name="prop_line_x1" />
        <xsl:param name="prop_line_x2" />
        <xsl:param name="prop_line_x3" />
        <xsl:param name="prop_line_text_x_ind" />
        <xsl:param name="pic_x_ind" />
        <xsl:param name="resource_count" />
        <xsl:param name="property_count" />
        <xsl:param name="res_pic_count" />
        <xsl:param name="res_long_text_3" />
        <xsl:param name="res_long_text_6" />
        <xsl:param name="res_long_text_9" />
        <xsl:param name="res_long_text_x" />
        <xsl:param name="res_values_count" />
        <xsl:param name="before" />
        <xsl:param name="after" />
        <xsl:variable name="pic_count">
            <xsl:value-of select="count(preceding::value[@output-type='http://www.w3.org/2004/09/fresnel#image'])+count(preceding::value[@output-type='http://www.w3.org/2004/09/fresnel#long-text'])" />
        </xsl:variable>
        <xsl:variable name="long_text_3">
            <xsl:value-of select="count(preceding::value[@long-text-rows='2'])+count(preceding::value[@long-text-rows='3'])" />
        </xsl:variable>
        <xsl:variable name="long_text_6">
            <xsl:value-of select="count(preceding::value[@long-text-rows='4'])+count(preceding::value[@long-text-rows='5'])" />
        </xsl:variable>
        <xsl:variable name="long_text_9">
            <xsl:value-of select="count(preceding::value[@long-text-rows='6'])+count(preceding::value[@long-text-rows='7'])+count(preceding::value[@long-text-rows='8'])" />
        </xsl:variable>
        <xsl:variable name="long_text_x">
            <xsl:value-of select="count(preceding::value[@long-text-rows&gt; 8])" />
        </xsl:variable>
        <xsl:variable name="values_count">
            <xsl:value-of select="count(preceding::value)" />
        </xsl:variable>
        <!-- Drawing second part of property line -->
       
        <svg:g class="f-label">
            <svg:line x1="{$prop_line_x2}" y1="{$prop_line_ind+20*$resource_count+$rect_prop_y_ind*$res_values_count+$res_pic_count*$pic_y_ind+$res_long_text_3*$long_text_3_y_ind+$res_long_text_6*$long_text_6_y_ind+$res_long_text_9*$long_text_9_y_ind+$res_long_text_x*$long_text_x_y_ind}" x2="{$prop_line_x3}" y2="{$prop_line_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" />
        </svg:g>
        <svg:g class="f-value">
            <!-- Handling of VALUE TYPE -->
            <xsl:choose>
                <!-- IMAGE VALUE TYPE -->
                <xsl:when test="@output-type='http://www.w3.org/2004/09/fresnel#image'">
                    <xsl:choose>
                        <xsl:when test="resource">
                            <!-- Drawing rectangle with uri of picture -->
                            <svg:rect x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" width="{$rect_width}" height="{$rect_height}" />
                            <svg:image xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="{resource/@uri}" />
                        </xsl:when>
                        <xsl:otherwise>
                            <!-- Drawing rectangle with picture -->
                            <svg:rect x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" width="{$pic_width}" height="{$pic_height}" />
                            <svg:image xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="{title}" width="{-2+$pic_width}" height="{-2+$pic_height}" x="{$pic_x_ind}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind+1}" />
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
                <!-- URI VALUE TYPE -->
                <xsl:when test="@output-type='http://www.w3.org/2004/09/fresnel#uri'">
                    <svg:rect x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" width="{$rect_width}" height="{$rect_height}" />
                    <svg:text x="{$rect_prop_text_x_ind}" y="{$rect_text_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" width="{$rect_width}" font-size="{$font_size}">
                        <xsl:choose>
                            <xsl:when test="resource">
                                <xsl:value-of select="resource/@uri" />
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="title" />
                            </xsl:otherwise>
                        </xsl:choose>
                    </svg:text>
                </xsl:when>
                <!-- EXTERNAL LINK VALUE TYPE -->
                <xsl:when test="@output-type='http://www.w3.org/2004/09/fresnel#externalLink'">
                    <xsl:choose>
                        <xsl:when test="resource">
                            <svg:a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="{resource/@full-uri}">
                                <svg:rect x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" width="{$rect_width}" height="{$rect_height}" />
                                <svg:text x="{$rect_prop_text_x_ind}" y="{$rect_text_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" width="{$rect_width}" font-size="{$font_size}">
                                    <xsl:value-of select="f-resource/@uri" />
                                </svg:text>
                            </svg:a>
                        </xsl:when>
                        <xsl:otherwise>
                            <svg:a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="{title/@full-value}">
                                <svg:rect x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" width="{$rect_width}" height="{$rect_height}" />
                                <svg:text x="{$rect_prop_text_x_ind}" y="{$rect_text_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" width="{$rect_width}" font-size="{$font_size}">
                                    <xsl:value-of select="title" />
                                </svg:text>
                            </svg:a>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
                <xsl:otherwise>
                   
                    <!-- LONG-TEXT VALUE TYPE -->
                    <xsl:choose>
                        <xsl:when test="title">
                            <xsl:choose>
                                <xsl:when test="@long-text-rows = 1">
                                    <svg:rect x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" width="{$rect_width}" height="{$rect_height}" />
                                    <svg:text x="{$rect_prop_text_x_ind}" y="{$rect_text_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" font-size="{$font_size}">
                                        <xsl:value-of select="title" />
                                    </svg:text>
                                </xsl:when>
                                <xsl:when test="@long-text-rows  &lt; 4">
                                    <!-- vyska  rect 65px-->
                                    <svg:rect x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" width="{$rect_width}" height="65px" />
                                    <svg:foreignObject x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" width="{$rect_width}" height="{$pic_height}">
                                        <xhtml:body xmlns:xhtml="http://www.w3.org/1999/xhtml">
                                            <xhtml:div style="font-size:{$font_size}px; padding:0px 6px 0px 6px">
                                                <xsl:choose>
                                                    <xsl:when test="resource">
                                                        <xsl:value-of select="resource/@uri" />
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <xsl:value-of select="title/@full-value" />
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </xhtml:div>
                                        </xhtml:body>
                                    </svg:foreignObject>
                                </xsl:when>
                                <xsl:when test="@long-text-rows  &lt; 6">
                                    <!-- vyska 100px -->
                                    <svg:rect x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" width="{$rect_width}" height="100px" />
                                    <svg:foreignObject x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" width="{$rect_width}" height="{$pic_height}">
                                        <xhtml:body xmlns:xhtml="http://www.w3.org/1999/xhtml">
                                            <xhtml:div style="font-size:{$font_size}px; padding:0px 6px 0px 6px">
                                                <xsl:choose>
                                                    <xsl:when test="resource">
                                                        <xsl:value-of select="resource/@uri" />
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <xsl:value-of select="title/@full-value" />
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </xhtml:div>
                                        </xhtml:body>
                                    </svg:foreignObject>
                                </xsl:when>
                                <xsl:when test="@long-text-rows  &lt; 9">
                                    <!-- vyska 155px -->
                                    <svg:rect x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" width="{$rect_width}" height="155px" />
                                    <svg:foreignObject x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" width="{$rect_width}" height="{$pic_height}">
                                        <xhtml:body xmlns:xhtml="http://www.w3.org/1999/xhtml">
                                            <xhtml:div style="font-size:{$font_size}px; padding:0px 6px 0px 6px">
                                                <xsl:choose>
                                                    <xsl:when test="resource">
                                                        <xsl:value-of select="resource/@uri" />
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <xsl:value-of select="title/@full-value" />
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </xhtml:div>
                                        </xhtml:body>
                                    </svg:foreignObject>
                                </xsl:when>
                                <xsl:otherwise>
                                    <!-- vyska 300px -->
                                    <svg:rect x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" width="{$rect_width}" height="300px" />
                                    <svg:foreignObject x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+$long_text_3*$long_text_3_y_ind+$long_text_6*$long_text_6_y_ind+$long_text_9*$long_text_9_y_ind+$long_text_x*$long_text_x_y_ind}" width="{$rect_width}" height="{$pic_height}">
                                        <xhtml:body xmlns:xhtml="http://www.w3.org/1999/xhtml">
                                            <xhtml:div style="font-size:{$font_size}px; padding:0px 6px 0px 6px">
                                                <xsl:choose>
                                                    <xsl:when test="resource">
                                                        <xsl:value-of select="resource/@uri" />
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <xsl:value-of select="title/@full-value" />
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </xhtml:div>
                                        </xhtml:body>
                                    </svg:foreignObject>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:apply-templates select="resource">
                                <xsl:with-param name="rect_res_x_ind" select="$prop_line_x3" />
                                <xsl:with-param name="rect_res_text_x_ind" select="$prop_line_x3+15" />
                                <xsl:with-param name="rect_prop_text_x_ind" select="$prop_line_x3+$rect_width+435" />
                                <xsl:with-param name="prop_line_x1" select="$prop_line_x3+$rect_width" />
                                <xsl:with-param name="prop_line_x2" select="$prop_line_x3+$rect_width+120" />
                                <xsl:with-param name="prop_line_x3" select="$prop_line_x3+$rect_width+420" />
                                <xsl:with-param name="prop_line_text_x_ind" select="$prop_line_x3+$rect_width+123" />
                                <xsl:with-param name="pic_x_ind" select="$prop_line_x3+$rect_width+422" />
                            </xsl:apply-templates>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
        </svg:g>
    </xsl:template>
<!-- End VALUE node match template -->
</xsl:stylesheet>
