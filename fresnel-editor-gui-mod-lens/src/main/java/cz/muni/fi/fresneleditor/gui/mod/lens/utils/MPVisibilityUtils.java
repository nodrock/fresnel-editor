package cz.muni.fi.fresneleditor.gui.mod.lens.utils;

import org.springframework.util.Assert;

import fr.inria.jfresnel.visibility.MPVisibility;

/**
 * 
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class MPVisibilityUtils {

    /**
     * Extracts the URI of the value of the fresnel:property predicate defined
     * by the given property visibility.
     * 
     * @return URI of the value of the fresnel:property predicate
     */
    public static String getFresnelPropertyValueURI(MPVisibility visibility) {
        // fixme igor: sesame dependency
        if (visibility instanceof MPVisibility) {
            if (visibility.getPropertyDescriptionProperties() != null) {
                return visibility.getProperty();
            }

            Assert.isTrue(false,
                    "there is no fresnel:property definition in this property visibility: "
                    + visibility);
            return null;
        } else {
            throw new IllegalArgumentException("Unsupported visibility type: "
                    + visibility.getClass());
        }
    }
}
