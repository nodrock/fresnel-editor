/**
 * 
 */
package cz.muni.fi.fresneleditor.common;

import java.awt.Color;

/**
 * @author Miroslav Warchil
 * @version 24. 10. 2009
 */
public class FresnelEditorConstants {

    public static enum Transformations {

        XHTML, RDFA, MICRO, HTML5, SVG
    };
    public static final String PROP_APP_VERSION = "cz.muni.fi.fresneleditor.version";
    public static final String PROP_APP_RELEASE_DATE = "cz.muni.fi.fresneleditor.releasedate";
    public static final String PATH_FRESNEL_EDITOR_RESOURCES = "src/main/resources/";
    public static final String PATH_FRESNEL_EDITOR_PROP = PATH_FRESNEL_EDITOR_RESOURCES
            + "fresneleditor.properties";
    public static final String PATH_SAMPLE_PROJECTS = PATH_FRESNEL_EDITOR_RESOURCES
            + "samples/";
    public static final String PATH_RESOURCE_BUNDLE_COMMON = "cz/muni/fi/fresneleditor/common/resource-bundle-common";
    // TODO: Maybe add to properties configuration.
    public static final String DEFAULT_REPOSITORIES_DIR = "/defaultParrentRepositoriesDir";
    // private static final String REPOSITORY_SAMPLE_PROJECT_DATA =
    // "fe-sp-data-in-memory";
    // private static final String REPOSITORY_SAMPLE_PROJECT_FRESNEL =
    // "fe-sp-fresnel-in-memory";
    /**
     * Text on disabled Swing components will be rendered using following color
     * (in RGB).
     */
    public static final int COLOR_DISABLED_R = 101;
    public static final int COLOR_DISABLED_G = 101;
    public static final int COLOR_DISABLED_B = 101;
    public static final Color COLOR_DISABLED = new Color(COLOR_DISABLED_R,
            COLOR_DISABLED_G, COLOR_DISABLED_B);
    /**
     * Visualization related constants.
     */
    public static final String DEFAULT_CSS_STYLESHEET_URL = "src/main/resources/css/visualization-test-default-style.css";
    public static final String DEFAULT_XSL_XHTML_TEMPLATE_URL = "src/main/resources/xsl/fresnel-to-xhtml-default.xsl";
    public static final String DEFAULT_XSL_RDFA_TEMPLATE_URL = "src/main/resources/xsl/fresnel-to-rdfa-default.xsl";
    public static final String DEFAULT_XSL_MICTO_TEMPLATE_URL = "src/main/resources/xsl/fresnel-to-micro-default.xsl";
    public static final String DEFAULT_XSL_HTML5_TEMPLATE_URL = "src/main/resources/xsl/fresnel-to-html5-default.xsl";
    public static final String DEFAULT_XSL_SVG_TEMPLATE_URL = "src/main/resources/xsl/fresnel-to-svg-default.xsl";
    public static final String DEFAULT_SVG_CSS_STYLESHEET_URL = "src/main/resources/css/visualization-svg-default-style.css";
    public static final String FILENAME_INTERMEDIATE_XML = "fresnel-output-internal.xml";
    public static final String DEFAULT_FILENAME_FINAL_XHTML = "fresnel-output-final.html";
    public static final String DEFAULT_FILENAME_FINAL_SVG = "fresnel-output-final.svg";
}
