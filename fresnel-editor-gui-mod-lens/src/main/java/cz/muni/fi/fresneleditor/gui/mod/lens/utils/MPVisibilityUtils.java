package cz.muni.fi.fresneleditor.gui.mod.lens.utils;

import org.openrdf.model.impl.URIImpl;
import org.openrdf.sail.memory.model.MemBNode;
import org.openrdf.sail.memory.model.MemStatement;
import org.openrdf.sail.memory.model.MemStatementList;
import org.springframework.util.Assert;

import fr.inria.jfresnel.Constants;
import fr.inria.jfresnel.MPVisibility;
import fr.inria.jfresnel.sesame.SesameMPVisibility;

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
		if (visibility instanceof SesameMPVisibility) {
			SesameMPVisibility sesVis = (SesameMPVisibility) visibility;
                        if(visibility.propertyDescriptionProperties != null){
                            return visibility.propertyDescriptionProperties.property;
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
