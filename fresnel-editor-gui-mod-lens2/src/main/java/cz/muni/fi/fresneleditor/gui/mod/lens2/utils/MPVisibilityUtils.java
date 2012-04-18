package cz.muni.fi.fresneleditor.gui.mod.lens2.utils;

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

			MemStatementList statements = ((MemBNode) sesVis.getDescription())
					.getSubjectStatementList();
			for (int i = 0; i < statements.size(); i++) {
				MemStatement statement = statements.get(i);
				if (statement.getPredicate().equals(
						new URIImpl(Constants.FRESNEL_NAMESPACE_URI
								+ Constants._property))) {
					return statement.getObject().stringValue();
				}
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
