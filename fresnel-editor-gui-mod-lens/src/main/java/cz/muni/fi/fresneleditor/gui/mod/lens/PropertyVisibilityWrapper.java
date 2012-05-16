package cz.muni.fi.fresneleditor.gui.mod.lens;

import java.util.ArrayList;
import java.util.List;

import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.gui.mod.lens.utils.MPVisibilityUtils;
import fr.inria.jfresnel.visibility.AllPropertiesVisibility;
import fr.inria.jfresnel.visibility.BasicVisibility;
import fr.inria.jfresnel.Constants;
import fr.inria.jfresnel.visibility.FSLVisibility;
import fr.inria.jfresnel.Format;
import fr.inria.jfresnel.Group;
import fr.inria.jfresnel.visibility.MPVisibility;
import fr.inria.jfresnel.visibility.PropertyVisibility;
import fr.inria.jfresnel.visibility.SPARQLVisibility;

/**
 * Class for holding informations about fresnel:property subject. It can
 * represent either simple URI or a complex fresnel:PropertyDescription object.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
public class PropertyVisibilityWrapper implements Cloneable {

	private static final String ALL_PROPERTIES_URI = Constants._allProperties;
	private static final String MP_PREFIX = "MP ";
	private static final String FRESNEL_NAMESPACE_URI = Constants.FRESNEL_NAMESPACE_URI;
	public static final int MAX_DEPTH_NOT_DEFINED = 0;

	public static PropertyVisibilityWrapper ALL_PROPERTIES = new PropertyVisibilityWrapper(
			new AllPropertiesVisibility());				

	/**
	 * URI of property on which this property description applies
	 */
	private String fresnelPropertyValueURI;

	/**
	 * Specifies a set of sublenses that should be used for rendering a property
	 */
	private List<URI> sublensesURIs;

	/**
	 * fresnel:Group or fresnel:Format which is applied to the lens.
	 * 
	 * <br>
	 * Can be null if this property has no object
	 */
	private Object fresnelUse;

	/**
	 * Specifies a maximum recursion depth for displaying data using sublens
	 */
	private int maxDepth = MAX_DEPTH_NOT_DEFINED;

	/**
	 * JFresnel object that was used for initialising this property description
	 */
	private final PropertyVisibility fresnelVisibility;

	/**
	 * Creates a wrapper whose values are initialised from the given
	 * propertyVisibility.
	 * 
	 * @param propertyVisibility
	 *            that is used to initialise this wrapper
	 */
	// currently only SesameMPVisibility is supported
	public PropertyVisibilityWrapper(PropertyVisibility propertyVisibility) {
		// fixme igor: implement a lazy initialisation here?
		this.fresnelVisibility = propertyVisibility;
		initFresnelPropertyValueURI();
		if (propertyVisibility instanceof MPVisibility) {
			initConfiguration((MPVisibility) propertyVisibility);
		}
	}

        public PropertyVisibility getPropertyVisibility(){
            return fresnelVisibility;
        }
        
	/**
	 * Creates an empty (not-initialised) property description. Its values can
	 * be set later.
	 */
	// fixme igor: is this needed?
	public PropertyVisibilityWrapper() {
		this(null);
	}

	public PropertyVisibilityWrapper getCopy() {
		try {
			return (PropertyVisibilityWrapper) clone();
		} catch (CloneNotSupportedException e) {
			// do nothing
		}
		return null;
	}

	protected Object clone() throws CloneNotSupportedException {
		Object superClone = super.clone();

		PropertyVisibilityWrapper clone = (PropertyVisibilityWrapper) superClone;

		clone.setFresnelPropertyValueURI(getFresnelPropertyValueURI());
		clone.setMaxDepth(getMaxDepth());

		List<URI> sublenses = new ArrayList<URI>();
		for (URI uri : getSublensesURIs()) {
			sublenses.add(uri);
		}
		clone.setSublensesURIs(sublenses);

		clone.setFresnelUse(sublenses);

		// fixme igor: is this ok?
		clone.setFresnelUse(getFresnelUse());

		return clone;
	}

	/**
	 * Returns true if this is the wrapper for fresnel:allProperties property.
	 * 
	 * @return true if this is the wrapper for fresnel:allProperties property
	 */
	public boolean isAllProperties() {
		return fresnelVisibility instanceof AllPropertiesVisibility;				
	}

	public void setFresnelPropertyValueURI(String fresnelPropertyValueURI) {
		this.fresnelPropertyValueURI = fresnelPropertyValueURI;
	}

	public String getFresnelPropertyValueURI() {
		return fresnelPropertyValueURI;
	}

	public void setSublensesURIs(List<URI> sublensesURIs) {
		this.sublensesURIs = sublensesURIs;
	}

	/**
	 * Cannot return null. Returns either existing sublenses URI list or newly
	 * created list.
	 * 
	 * @return
	 */
	public List<URI> getSublensesURIs() {
		if (sublensesURIs == null) {
                    return new ArrayList<URI>();
		}
                               
		return sublensesURIs;
	}

	public void setFresnelUse(Object fresnelUse) {
		this.fresnelUse = fresnelUse;
	}

	public Object getFresnelUse() {
		return fresnelUse;
	}

	/**
	 * Returns the URI of the fresnel:use object associated with this property.
	 * If there is no such object returns null (=> this property has no
	 * fresnel:use defined).
	 * 
	 * @return
	 */
	public String getFresnelUseUri() {
		if (fresnelUse == null) {
			return null;
		} else if (fresnelUse instanceof Group) {
			return ((Group) fresnelUse).getURI();
		} else if (fresnelUse instanceof Format) {
			return ((Format) fresnelUse).getURI();
		} else if (URI.class.isAssignableFrom(fresnelUse.getClass())) {
			return ((URI) fresnelUse).stringValue();
		} else {
			throw new UnsupportedOperationException(
					"Cannot get fresnel:use object URI for fresnelUse object: '"
							+ fresnelUse + "' which is of type: "
							+ fresnelUse.getClass());
		}
	}

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	@Override
	public String toString() {
		if (fresnelVisibility == null) {
			return getFresnelPropertyValueURI();
		}

		if (fresnelVisibility instanceof MPVisibility) {
			return MP_PREFIX
					+ MPVisibilityUtils
							.getFresnelPropertyValueURI((MPVisibility) fresnelVisibility);
		} else {
			return fresnelVisibility.toString();
		}
	}

	private void initConfiguration(MPVisibility propertyVisibility) {
            if(propertyVisibility.getPropertyDescriptionProperties() != null){
                setMaxDepth(propertyVisibility.getPropertyDescriptionProperties().depth);	
            }else{
                setMaxDepth(-1);
            }
            
            initFresnelUse(propertyVisibility);
            initSublenses(propertyVisibility);
	}

	/**
	 * Initialises the fresnel:property predicate value URI.
	 * 
	 * @return the fresnel:property predicate value URI
	 */
	private void initFresnelPropertyValueURI() {
		if (fresnelVisibility == null) {
			this.setFresnelPropertyValueURI(null);
		} else if (fresnelVisibility instanceof BasicVisibility) {
			this.setFresnelPropertyValueURI(((BasicVisibility) fresnelVisibility).getConstraint());
		} else if (fresnelVisibility instanceof FSLVisibility) {
			this.setFresnelPropertyValueURI(((FSLVisibility) fresnelVisibility).getConstraint());
		} else if (fresnelVisibility instanceof MPVisibility) {
			this.setFresnelPropertyValueURI(MPVisibilityUtils
					.getFresnelPropertyValueURI((MPVisibility) fresnelVisibility));
		} else if (fresnelVisibility instanceof SPARQLVisibility) {
			this.setFresnelPropertyValueURI(((SPARQLVisibility) fresnelVisibility).getConstraint());
                } else if (fresnelVisibility instanceof AllPropertiesVisibility) {
			this.setFresnelPropertyValueURI("fresnel:allProperties");
		} else {
			// fixme igor review the exception hierarchy
			throw new IllegalArgumentException("visibility is of type "
					+ fresnelVisibility.getClass()
					+ " which is currently not supported");
		}
	}

	private void initSublenses(MPVisibility visibility) {
		List<URI> sublenses = new ArrayList<URI>();
//		MemURI fresnelSublensURI = new MemURI(null, FRESNEL_NAMESPACE_URI,
//				Constants._sublens);

		// fixme igor: this will not work when there are no data in the repo
		// we should be able to obtain the information from passed visibility
		// instance
                if(visibility.getPropertyDescriptionProperties() != null && visibility.getPropertyDescriptionProperties().sublens != null){
                    for(String sublens : visibility.getPropertyDescriptionProperties().sublens)
                    sublenses.add(new URIImpl(sublens));
                }

		
		setSublensesURIs(sublenses);
	}

	/**
	 * Sets the {@link #fresnelUse} property based on given visibility
	 * parameter. Reads {@link Group} or {@link Format} that is binded to this
	 * property via fresnel:use property. <br>
	 * If the required group or format is not defined in the repository returns
	 * {@link URI} object representing the fresnel:use property value.
	 * 
	 * @return the group or format referenced by the fresnel:use property if it
	 *         exists in the repository <br>
	 *         resource URI if the referenced group or format is not defined in
	 *         the working repository <br>
	 *         null in case there is no group nor format defined for this
	 *         property
	 */
	private void initFresnelUse(MPVisibility visibility) {
		
            if(visibility.getPropertyDescriptionProperties() != null &&
                    visibility.getPropertyDescriptionProperties().use != null){

                    // fixme igor: is the particular format/group needed here? is
                    // not the URI enough?

                    // try the format first
                    Object fresnelUseObject = ContextHolder.getInstance().getFresnelDocumentDao().getFormat(visibility.getPropertyDescriptionProperties().use);

                    if (fresnelUseObject == null) {
                            fresnelUseObject = ContextHolder.getInstance().getFresnelDocumentDao().getGroup(visibility.getPropertyDescriptionProperties().use);
                    }

                    setFresnelUse(fresnelUseObject);

            }else{
                setFresnelUse(null);
            }
	}

	/**
	 * Returns true this wrapper defines any of complex settings for this
	 * property visibility (fresnel:maxDepth, fresnel:sublens, fresnel:use). <br>
	 * Returns false if this is simple property defined by its URI only.
	 * 
	 * @return
	 */
	public boolean isComplexPropertyDescription() {
		return getMaxDepth() > MAX_DEPTH_NOT_DEFINED
				|| !getSublensesURIs().isEmpty() || getFresnelUse() != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((fresnelPropertyValueURI == null) ? 0
						: fresnelPropertyValueURI.hashCode());
		result = prime * result
				+ ((fresnelUse == null) ? 0 : fresnelUse.hashCode());
		result = prime * result + maxDepth;
		result = prime * result
				+ ((sublensesURIs == null) ? 0 : sublensesURIs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PropertyVisibilityWrapper))
			return false;
		PropertyVisibilityWrapper other = (PropertyVisibilityWrapper) obj;
		if (fresnelPropertyValueURI == null) {
			if (other.fresnelPropertyValueURI != null)
				return false;
		} else if (!fresnelPropertyValueURI
				.equals(other.fresnelPropertyValueURI))
			return false;
		if (fresnelUse == null) {
			if (other.fresnelUse != null)
				return false;
		} else if (!fresnelUse.equals(other.fresnelUse))
			return false;
		if (maxDepth != other.maxDepth)
			return false;
		if (sublensesURIs == null) {
			if (other.sublensesURIs != null)
				return false;
		} else if (!sublensesURIs.equals(other.sublensesURIs))
			return false;
		return true;
	}

}
