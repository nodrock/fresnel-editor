<?xml version="1.0" encoding="UTF-8"?>

<!--+
    |
    | XSLT to transform the XML result of a Fresnel lens into an SVG representation
    |
    | @author Milos Kalab
    +-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:svg="http://www.w3.org/2000/svg"
                xmlns:f="http://www.w3.org/2004/09/fresnel-tree"
                exclude-result-prefixes="svg f"
                version="1.0">
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" doctype-public="-//W3C//DTD SVG 1.1//EN" doctype-system="http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd"/>
    <!-- xsl:output method="xml" indent="yes"
                doctype-public="-//W3C//DTD SVG 20010904//EN"
                doctype-system="~http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd"/-->

<!-- Global parameter definitions -->
    <xsl:param name="svg_width">
    <!--TODO-COUNT WIDTH PROPERLY-->
        <xsl:value-of select="($rect_width+($rect_width+$prop_line_univ_lenght)*$resource_depth)"/>
    </xsl:param>
    <xsl:param name="svg_height">
    <!--TODO-COUNT HEIGHT PROPERLY-->
        <xsl:value-of select="$all_children_count*($rect_prop_y_ind+-$rect_height)+($all_children_count+-$all_pic_count)*$rect_prop_y_ind+$all_pic_count*$pic_height"/>
    </xsl:param>
    <xsl:param name="prop_line_univ_lenght">440</xsl:param>    <!--NEED TO BE SET UP ACCORDING TO $prop_line_x1,2,3 and $rect_res_x_ind -->
    <xsl:param name="pic_height">200</xsl:param>    <!--IMPORTANT SETTING-->
    <xsl:param name="pic_width">400</xsl:param>    <!--IMPORTANT SETTING-->
    <xsl:param name="pic_y_ind">
        <xsl:value-of select="$pic_height+-$rect_height"/>
    </xsl:param>
    <xsl:param name="rect_height">
    <!--TODO-DEAL WITH DYNAMIC RECT HEIGHT-->
        <xsl:value-of select="$font_size*2.7"/>
    </xsl:param>
    <xsl:param name="rect_width">400</xsl:param>    <!--IMPORTANT SETTING-->
    <xsl:param name="rect_y_ind">20</xsl:param>
    <xsl:param name="rect_text_y_ind">
        <xsl:value-of select="$rect_y_ind+$font_size+7"/>
    </xsl:param>
    <xsl:param name="rect_prop_y_ind">35</xsl:param>
    <xsl:param name="prop_line_text_y_ind">30</xsl:param>
    <xsl:param name="prop_line_ind">35</xsl:param>
    <xsl:param name="font_size">11</xsl:param>    <!--IMPORTANT SETTING-->
    <xsl:variable name="all_children_count">
        <xsl:value-of select="count(//f:value)"/>
    </xsl:variable>
    <xsl:variable name="all_pic_count">
        <xsl:value-of select="count(//f:value[@output-type='http://www.w3.org/2004/09/fresnel#image'])+count(//f:value[@output-type='http://www.w3.org/2004/09/fresnel#long-text'])"/>
    </xsl:variable>
    <xsl:variable name="resource_depth">
        <xsl:choose>
            <!-- SO FAR COUNTS MAX WITH 3 GENERATIONS OF CHILDREN -->
            <xsl:when test="count(/f:results/f:resource/f:property/f:values/f:value/f:resource/f:property/f:values/f:value/f:resource/f:property/f:values/f:value/f:resource/f:property/f:values/f:value/f:resource/f:property) &gt; 0">4</xsl:when>
            <xsl:when test="count(/f:results/f:resource/f:property/f:values/f:value/f:resource/f:property/f:values/f:value/f:resource/f:property/f:values/f:value/f:resource/f:property/f:values/f:value/f:resource/f:property) = 0">3</xsl:when>
            <xsl:when test="count(/f:results/f:resource/f:property/f:values/f:value/f:resource/f:property/f:values/f:value/f:resource/f:property/f:values/f:value/f:resource/f:property) &gt; 0">3</xsl:when>
            <xsl:when test="count(/f:results/f:resource/f:property/f:values/f:value/f:resource/f:property/f:values/f:value/f:resource/f:property/f:values/f:value/f:resource/f:property) = 0">2</xsl:when>
            <xsl:when test="count(/f:results/f:resource/f:property/f:values/f:value/f:resource/f:property/f:values/f:value/f:resource/f:property) &gt; 0">2</xsl:when>
            <xsl:when test="count(/f:results/f:resource/f:property/f:values/f:value/f:resource/f:property/f:values/f:value/f:resource/f:property )= 0">1</xsl:when>
            <xsl:when test="count(/f:results/f:resource/f:property/f:values/f:value/f:resource/f:property) &gt; 0">1</xsl:when>
            <xsl:when test="count(/f:results/f:resource/f:property/f:values/f:value/f:resource/f:property) = 0 ">0</xsl:when>
            <xsl:when test="count(/f:results/f:resource/f:property) = 0">0</xsl:when>
            <xsl:otherwise>0</xsl:otherwise>
        </xsl:choose>
    </xsl:variable>
<!-- End of global parameter definitions -->

<!-- Match the root of the DOM tree -->
    <xsl:template match="/">
        <svg:svg width="{$svg_width}" height="{$svg_height}">
            <xsl:apply-templates/>
        </svg:svg>
    </xsl:template>
<!-- End root match template -->

<!-- Match the RESULTS node of the DOM tree  -->
    <xsl:template match="f:results">
        <!-- First-level parameter definitions -->
        <xsl:param name="rect_res_x_ind">20</xsl:param>
        <xsl:param name="rect_res_text_x_ind">35</xsl:param>
        <xsl:param name="prop_line_x1">
            <xsl:value-of select="$rect_width+20"/>
        </xsl:param>
        <xsl:param name="prop_line_x2">
            <xsl:value-of select="$prop_line_x1+100"/>
        </xsl:param>
        <xsl:param name="prop_line_x3">
            <xsl:value-of select="$prop_line_x2+300"/>
        </xsl:param>
        <xsl:param name="rect_prop_text_x_ind">
            <xsl:value-of select="$prop_line_x3+15"/>
        </xsl:param>
        <xsl:param name="prop_line_text_x_ind">
            <xsl:value-of select="$prop_line_x2+3"/>
        </xsl:param>
        <xsl:param name="pic_x_ind">
            <xsl:value-of select="$prop_line_x3+2"/>
        </xsl:param>
        <!-- End of first-level parameter definitions -->
        <svg:g id="f:results">
            <xsl:apply-templates select="f:resource">
                <xsl:with-param name="rect_res_x_ind" select="$rect_res_x_ind"/>
                <xsl:with-param name="rect_res_text_x_ind" select="$rect_res_text_x_ind"/>
                <xsl:with-param name="rect_prop_text_x_ind" select="$rect_prop_text_x_ind"/>
                <xsl:with-param name="prop_line_x1" select="$prop_line_x1"/>
                <xsl:with-param name="prop_line_x2" select="$prop_line_x2"/>
                <xsl:with-param name="prop_line_x3" select="$prop_line_x3"/>
                <xsl:with-param name="prop_line_text_x_ind" select="$prop_line_text_x_ind"/>
                <xsl:with-param name="pic_x_ind" select="$pic_x_ind"/>
            </xsl:apply-templates>
        </svg:g>
    </xsl:template>
<!-- End RESULTS node match template -->

<!-- Match the RESOURCE node of the DOM tree -->
    <xsl:template match="f:resource">
        <xsl:param name="rect_res_x_ind"/>
        <xsl:param name="rect_res_text_x_ind"/>
        <xsl:param name="rect_prop_text_x_ind"/>
        <xsl:param name="prop_line_x1"/>
        <xsl:param name="prop_line_x2"/>
        <xsl:param name="prop_line_x3"/>
        <xsl:param name="prop_line_text_x_ind"/>
        <xsl:param name="pic_x_ind"/>
        <xsl:variable name="resource_count">
            <xsl:value-of select="count(preceding::f:resource)"/>
        </xsl:variable>
        <xsl:variable name="values_count">
            <xsl:value-of select="count(preceding::f:value)"/>
        </xsl:variable>
        <xsl:variable name="pic_count">
            <xsl:value-of select="count(preceding::f:value[@output-type='http://www.w3.org/2004/09/fresnel#image'])+count(preceding::f:value[@output-type='http://www.w3.org/2004/09/fresnel#long-text'])"/>
        </xsl:variable>
        <svg:g id="f:resource">
            <!-- Drawing resource node -->
            <svg:rect x="{$rect_res_x_ind}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_y_ind*$pic_count}" width="{$rect_width}" height="{$rect_height}" rx="10" ry="10" stroke-width="2px" stroke="black" fill="#ccc"/>
            <svg:text x="{$rect_res_text_x_ind}" y="{$rect_text_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_y_ind*$pic_count}" font-family="LucidaGrande" font-size="{$font_size}">
                <xsl:choose>
                    <xsl:when test="f:title/text() != ''">
                        <xsl:value-of select="f:title"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="@uri"/>
                    </xsl:otherwise>
                </xsl:choose>
            </svg:text>
            <!--  -->
            <xsl:apply-templates select="f:property">
                <xsl:with-param name="resource_count" select="$resource_count"/>
                <xsl:with-param name="res_values_count" select="$values_count"/>
                <xsl:with-param name="res_pic_count" select="$pic_count"/>
                <xsl:with-param name="rect_res_x_ind" select="$rect_res_x_ind"/>
                <xsl:with-param name="rect_res_text_x_ind" select="$rect_res_text_x_ind"/>
                <xsl:with-param name="rect_prop_text_x_ind" select="$rect_prop_text_x_ind"/>
                <xsl:with-param name="prop_line_x1" select="$prop_line_x1"/>
                <xsl:with-param name="prop_line_x2" select="$prop_line_x2"/>
                <xsl:with-param name="prop_line_x3" select="$prop_line_x3"/>
                <xsl:with-param name="prop_line_text_x_ind" select="$prop_line_text_x_ind"/>
                <xsl:with-param name="pic_x_ind" select="$pic_x_ind"/>
            </xsl:apply-templates>
        </svg:g>
    </xsl:template>
<!-- End RESOURCE node match template -->

<!-- Match the PROPERTY node of the DOM tree -->
    <xsl:template match="f:property">
        <xsl:param name="resource_count"/>
        <xsl:param name="res_values_count"/>
        <xsl:param name="res_pic_count"/>
        <xsl:param name="rect_res_x_ind"/>
        <xsl:param name="rect_res_text_x_ind"/>
        <xsl:param name="rect_prop_text_x_ind"/>
        <xsl:param name="prop_line_x1"/>
        <xsl:param name="prop_line_x2"/>
        <xsl:param name="prop_line_x3"/>
        <xsl:param name="prop_line_text_x_ind"/>
        <xsl:param name="pic_x_ind"/>
        <xsl:variable name="values_count">
            <xsl:value-of select="count(preceding::f:value)"/>
        </xsl:variable>
        <xsl:variable name="property_count">
            <xsl:value-of select="count(preceding::f:property)"/>
        </xsl:variable>
        <xsl:variable name="pic_count">
            <xsl:value-of select="count(preceding::f:value[@output-type='http://www.w3.org/2004/09/fresnel#image'])+count(preceding::f:value[@output-type='http://www.w3.org/2004/09/fresnel#long-text'])"/>
        </xsl:variable>
        <svg:g id="f:property">
            <xsl:if test="f:label">
                <xsl:apply-templates select="f:label">
                    <xsl:with-param name="resource_count" select="$resource_count"/>
                    <xsl:with-param name="property_count" select="$property_count"/>
                    <xsl:with-param name="res_values_count" select="$res_values_count"/>
                    <xsl:with-param name="values_count" select="$values_count"/>
                    <xsl:with-param name="pic_count" select="$pic_count"/>
                    <xsl:with-param name="res_pic_count" select="$res_pic_count"/>
                    <xsl:with-param name="rect_res_x_ind" select="$rect_res_x_ind"/>
                    <xsl:with-param name="rect_res_text_x_ind" select="$rect_res_text_x_ind"/>
                    <xsl:with-param name="rect_prop_text_x_ind" select="$rect_prop_text_x_ind"/>
                    <xsl:with-param name="prop_line_x1" select="$prop_line_x1"/>
                    <xsl:with-param name="prop_line_x2" select="$prop_line_x2"/>
                    <xsl:with-param name="prop_line_x3" select="$prop_line_x3"/>
                    <xsl:with-param name="prop_line_text_x_ind" select="$prop_line_text_x_ind"/>
                    <xsl:with-param name="pic_x_ind" select="$pic_x_ind"/>
                </xsl:apply-templates>
            </xsl:if>
            <xsl:apply-templates select="f:values">
                <xsl:with-param name="resource_count" select="$resource_count"/>
                <xsl:with-param name="property_count" select="$property_count"/>
                <xsl:with-param name="res_values_count" select="$values_count"/>
                <xsl:with-param name="rect_res_x_ind" select="$rect_res_x_ind"/>
                <xsl:with-param name="rect_res_text_x_ind" select="$rect_res_text_x_ind"/>
                <xsl:with-param name="rect_prop_text_x_ind" select="$rect_prop_text_x_ind"/>
                <xsl:with-param name="prop_line_x1" select="$prop_line_x1"/>
                <xsl:with-param name="prop_line_x2" select="$prop_line_x2"/>
                <xsl:with-param name="prop_line_x3" select="$prop_line_x3"/>
                <xsl:with-param name="prop_line_text_x_ind" select="$prop_line_text_x_ind"/>
                <xsl:with-param name="pic_x_ind" select="$pic_x_ind"/>
            </xsl:apply-templates>
        </svg:g>
    </xsl:template>
<!-- End PROPERTY node match template -->

<!-- Match the LABEL node of the DOM tree -->
    <xsl:template match="f:label">
        <xsl:param name="resource_count"/>
        <xsl:param name="property_count"/>
        <xsl:param name="res_values_count"/>
        <xsl:param name="values_count"/>
        <xsl:param name="pic_count"/>
        <xsl:param name="res_pic_count"/>
        <xsl:param name="rect_res_x_ind"/>
        <xsl:param name="rect_res_text_x_ind"/>
        <xsl:param name="rect_prop_text_x_ind"/>
        <xsl:param name="prop_line_x1"/>
        <xsl:param name="prop_line_x2"/>
        <xsl:param name="prop_line_x3"/>
        <xsl:param name="prop_line_text_x_ind"/>
        <xsl:param name="pic_x_ind"/>
        <svg:g id="f:label">
<!-- Drawing first part of property line and label -->
            <svg:line x1="{$prop_line_x1}" y1="{$prop_line_ind+20*$resource_count+$rect_prop_y_ind*$res_values_count+$res_pic_count*$pic_y_ind}" x2="{$prop_line_x2}" y2="{$prop_line_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind}" stroke="#000" stroke-width="2" stroke-dasharray="9, 5"/>
            <svg:text x="{$prop_line_text_x_ind}" y="{$prop_line_text_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind}" font-family="LucidaGrande" font-size="{$font_size}">
                <xsl:choose>
                    <xsl:when test="f:title/text() != ''">
                        <xsl:value-of select="f:title"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="../@uri"/>
                    </xsl:otherwise>
                </xsl:choose>
            </svg:text>
        </svg:g>
    </xsl:template>
<!-- End LABEL node match template -->

<!-- Match the VALUES node of the DOM tree -->
    <xsl:template match="f:values">
        <xsl:param name="rect_res_x_ind"/>
        <xsl:param name="rect_res_text_x_ind"/>
        <xsl:param name="rect_prop_text_x_ind"/>
        <xsl:param name="prop_line_x1"/>
        <xsl:param name="prop_line_x2"/>
        <xsl:param name="prop_line_x3"/>
        <xsl:param name="prop_line_text_x_ind"/>
        <xsl:param name="pic_x_ind"/>
        <xsl:param name="resource_count"/>
        <xsl:param name="property_count"/>
        <xsl:variable name="pic_count">
            <xsl:value-of select="count(preceding::f:value[@output-type='http://www.w3.org/2004/09/fresnel#image'])+count(preceding::f:value[@output-type='http://www.w3.org/2004/09/fresnel#long-text'])"/>
        </xsl:variable>
        <xsl:variable name="values_count">
            <xsl:value-of select="count(preceding::f:value)"/>
        </xsl:variable>
        <svg:g id="f:values">
            <xsl:apply-templates select="f:value">
                <xsl:with-param name="resource_count" select="$resource_count"/>
                <xsl:with-param name="property_count" select="$property_count"/>
                <xsl:with-param name="res_values_count" select="$values_count"/>
                <xsl:with-param name="res_pic_count" select="$pic_count"/>
                <xsl:with-param name="rect_res_x_ind" select="$rect_res_x_ind"/>
                <xsl:with-param name="rect_res_text_x_ind" select="$rect_res_text_x_ind"/>
                <xsl:with-param name="rect_prop_text_x_ind" select="$rect_prop_text_x_ind"/>
                <xsl:with-param name="prop_line_x1" select="$prop_line_x1"/>
                <xsl:with-param name="prop_line_x2" select="$prop_line_x2"/>
                <xsl:with-param name="prop_line_x3" select="$prop_line_x3"/>
                <xsl:with-param name="prop_line_text_x_ind" select="$prop_line_text_x_ind"/>
                <xsl:with-param name="pic_x_ind" select="$pic_x_ind"/>
            </xsl:apply-templates>
        </svg:g>
    </xsl:template>
<!-- End VALUES node match template -->

<!-- Match the VALUE node of the DOM tree -->
    <xsl:template match="f:value">
        <xsl:param name="rect_res_x_ind"/>
        <xsl:param name="rect_res_text_x_ind"/>
        <xsl:param name="rect_prop_text_x_ind"/>
        <xsl:param name="prop_line_x1"/>
        <xsl:param name="prop_line_x2"/>
        <xsl:param name="prop_line_x3"/>
        <xsl:param name="prop_line_text_x_ind"/>
        <xsl:param name="pic_x_ind"/>
        <xsl:param name="resource_count"/>
        <xsl:param name="property_count"/>
        <xsl:param name="res_pic_count"/>
        <xsl:param name="res_values_count"/>
        <xsl:param name="before"/>
        <xsl:param name="after"/>
        <xsl:variable name="pic_count">
            <xsl:value-of select="count(preceding::f:value[@output-type='http://www.w3.org/2004/09/fresnel#image'])+count(preceding::f:value[@output-type='http://www.w3.org/2004/09/fresnel#long-text'])"/>
        </xsl:variable>
        <xsl:variable name="values_count">
            <xsl:value-of select="count(preceding::f:value)"/>
        </xsl:variable>
        <!-- Drawing second part of property line -->
        <svg:g id="f:label">
            <svg:line x1="{$prop_line_x2}" y1="{$prop_line_ind+20*$resource_count+$rect_prop_y_ind*$res_values_count+$res_pic_count*$pic_y_ind}" x2="{$prop_line_x3}" y2="{$prop_line_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind}" stroke="#000" stroke-width="2" stroke-dasharray="9, 5"/>
        </svg:g>
        <svg:g id="f:value">
            <!-- Handling of VALUE TYPE -->
            <xsl:choose>
                <!-- IMAGE VALUE TYPE -->
                <!--TODO-vyzkouset co dela RESOURCE kolem obrazku-->
                <xsl:when test="@output-type='http://www.w3.org/2004/09/fresnel#image'">
                    <xsl:choose>
                        <xsl:when test="f:resource">
                            <!-- Drawing rectangle with uri of picture -->
                            <svg:rect x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind}" width="{$rect_width}" height="{$rect_height}" stroke-width="2px" stroke="grey" fill="#eee"/>
                            <svg:image xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="{f:resource/@uri}"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <!-- Drawing rectangle with picture -->
                            <svg:rect x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind}" width="{$pic_width}" height="{$pic_height}" stroke-width="2px" stroke="grey" fill="#eee"/>
                            <svg:image xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="{f:title}" width="{-2+$pic_width}" height="{-2+$pic_height}" x="{$pic_x_ind}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind+1}"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
                <!-- LONG-TEXT VALUE TYPE -->
                <!--TODO-is current URI OK? and handle more lengths of text-->
                <xsl:when test="@output-type='http://www.w3.org/2004/09/fresnel#long-text'">
                    <xsl:choose>
                        <xsl:when test="f:resource">
                            <!-- Drawing rectangle with long text -->
                            <svg:rect x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind}" width="{$rect_width}" height="{$rect_height}" stroke-width="2px" stroke="grey" fill="#eee"/>
                            <svg:foreignObject x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind}" width="{$rect_width}" height="{$pic_height}">
                                <xhtml:body xmlns:xhtml="http://www.w3.org/1999/xhtml">
                                    <xhtml:div style="font-size:{$font_size}px;padding:6px 6px 6px 6px">
                                        <xsl:value-of select="f:resource/@uri"/>
                                    </xhtml:div>
                                </xhtml:body>
                            </svg:foreignObject>
                        </xsl:when>
                        <xsl:otherwise>
                            <!-- Drawing rectangle with long text -->
                            <svg:rect x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind}" width="{$pic_width}" height="{$pic_height}" stroke-width="2px" stroke="grey" fill="#eee"/>
                            <svg:foreignObject x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind}" width="{$pic_width}" height="{$pic_height}">
                                <xhtml:body xmlns:xhtml="http://www.w3.org/1999/xhtml">
                                    <xhtml:div style="font-size:{$font_size}px; padding:6px 6px 6px 6px">
                                        <xsl:value-of select="f:title"/>
                                    </xhtml:div>
                                </xhtml:body>
                            </svg:foreignObject>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
                <!-- URI VALUE TYPE -->
                <xsl:when test="@output-type='http://www.w3.org/2004/09/fresnel#uri'">
                    <svg:rect x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind}" width="{$rect_width}" height="{$rect_height}" stroke-width="2px" stroke="grey" fill="#eee"/>
                    <svg:text x="{$rect_prop_text_x_ind}" y="{$rect_text_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind}" width="{$rect_width}" font-family="LucidaGrande" font-size="{$font_size}">
                        <xsl:choose>
                            <xsl:when test="f:resource">
                                <xsl:value-of select="f:resource/@uri"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="f:title" />
                            </xsl:otherwise>
                        </xsl:choose>
                    </svg:text>
                </xsl:when>
                <!-- EXTERNAL LINK VALUE TYPE -->
                <xsl:when test="@output-type='http://www.w3.org/2004/09/fresnel#externalLink'">
                    <xsl:choose>
                        <xsl:when test="f:resource">
                            <svg:a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="{f:resource/@uri}">
                                <svg:rect x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind}" width="{$rect_width}" height="{$rect_height}" stroke-width="2px" stroke="gray" fill="#eee"/>
                                <svg:text x="{$rect_prop_text_x_ind}" y="{$rect_text_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind}" width="{$rect_width}" font-family="LucidaGrande" font-size="{$font_size}">
                                    <xsl:value-of select="f-resource/@uri"/>
                                </svg:text>
                            </svg:a>
                        </xsl:when>
                        <xsl:otherwise>
                            <svg:a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="{f:title}">
                                <svg:rect x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind}" width="{$rect_width}" height="{$rect_height}" stroke-width="2px" stroke="gray" fill="#eee"/>
                                <svg:text x="{$rect_prop_text_x_ind}" y="{$rect_text_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind}" width="{$rect_width}" font-family="LucidaGrande" font-size="{$font_size}">
                                    <xsl:value-of select="f:title"/>
                                </svg:text>
                            </svg:a>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:choose>
                        <xsl:when test="f:title">
                            <svg:rect x="{$prop_line_x3}" y="{$rect_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind}" width="{$rect_width}" height="{$rect_height}" stroke-width="2px" stroke="gray" fill="#eee"/>
                            <svg:text x="{$rect_prop_text_x_ind}" y="{$rect_text_y_ind+20*$resource_count+$rect_prop_y_ind*$values_count+$pic_count*$pic_y_ind}" width="{$rect_width}" font-family="LucidaGrande" font-size="{$font_size}">
                                <xsl:value-of select="f:title"/>
                            </svg:text>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:apply-templates select="f:resource">
                                <xsl:with-param name="rect_res_x_ind" select="$prop_line_x3"/>
                                <xsl:with-param name="rect_res_text_x_ind" select="$prop_line_x3+15"/>
                                <xsl:with-param name="rect_prop_text_x_ind" select="$prop_line_x3+$rect_width+435"/>
                                <xsl:with-param name="prop_line_x1" select="$prop_line_x3+$rect_width"/>
                                <xsl:with-param name="prop_line_x2" select="$prop_line_x3+$rect_width+120"/>
                                <xsl:with-param name="prop_line_x3" select="$prop_line_x3+$rect_width+420"/>
                                <xsl:with-param name="prop_line_text_x_ind" select="$prop_line_x3+$rect_width+123"/>
                                <xsl:with-param name="pic_x_ind" select="$prop_line_x3+$rect_width+422"/>
                            </xsl:apply-templates>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
        </svg:g>
    </xsl:template>
<!-- End VALUE node match template -->
</xsl:stylesheet>
