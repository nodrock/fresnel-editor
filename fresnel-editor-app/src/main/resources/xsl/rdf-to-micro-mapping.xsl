<?xml version="1.0" encoding="utf-8"?>
<!-- 
V súbore sa nachádza mapovanie jednotlivých namespacov z rdf na mikroformaty.
Konkrétne sa jedná o foaf na xnf a vcard ... ďalšie pridávajte sem ..
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:param name='mapping'>
        <mapping>
            <entryset microid="vcard" tags="div[class]">
                <entry rdfns="http://xmlns.com/foaf/0.1/name" microproperty="fn" tags="span[class]"/>
                <entry rdfns="http://xmlns.com/foaf/0.1/nick" microproperty="nickname" tags="span[class]"/>
                <entry rdfns="http://xmlns.com/foaf/0.1/familyName" microproperty="family-name" tags="span[class]"/>
                <entry rdfns="http://xmlns.com/foaf/0.1/family_name" microproperty="family-name" tags="span[class]"/>
                <entry rdfns="http://xmlns.com/foaf/0.1/surname" microproperty="family-name" tags="span[class]"/>
                <entry rdfns="http://xmlns.com/foaf/0.1/lastName" microproperty="family-name" tags="span[class]"/>
                <entry rdfns="http://xmlns.com/foaf/0.1/firstName" microproperty="given-name" tags="span[class]"/>
                <entry rdfns="http://xmlns.com/foaf/0.1/givenName" microproperty="given-name" tags="span[class]"/>
                <entry rdfns="http://xmlns.com/foaf/0.1/givenname" microproperty="given-name" tags="span[class]"/>
                <entry rdfns="http://xmlns.com/foaf/0.1/title" microproperty="honorific-prefix" tags="span[class]"/>
                <entry rdfns="http://xmlns.com/foaf/0.1/birthday" microproperty="bday" tags="span[class]"/>
                <entry rdfns="http://xmlns.com/foaf/0.1/mbox" microproperty="email" tags="span[class] a[class]"/>
                <entry rdfns="http://xmlns.com/foaf/0.1/depiction" microproperty="photo" tags="img[class]"/>
                <entry rdfns="http://xmlns.com/foaf/0.1/img" microproperty="photo" tags="img[class]"/>
                <entry rdfns="http://xmlns.com/foaf/0.1/page" microproperty="url" tags="span[class]"/>
                <entry rdfns="http://xmlns.com/foaf/0.1/phone" microproperty="tel" tags="span[class]"/>
            </entryset>
            <entryset microid="xnf">
                <entry rdfns="http://xmlns.com/foaf/0.1/homepage" microproperty="me url" tags="a[rel] span[class]"/>
                <entry rdfns="http://xmlns.com/foaf/0.1/knows" microproperty="friend" tags="a[rel] span[class]"/>
            </entryset>
        </mapping>
    </xsl:param>
</xsl:stylesheet>
