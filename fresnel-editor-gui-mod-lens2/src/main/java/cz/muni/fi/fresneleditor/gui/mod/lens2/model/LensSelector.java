package cz.muni.fi.fresneleditor.gui.mod.lens2.model;

import org.springframework.util.Assert;

import cz.muni.fi.fresneleditor.common.data.SelectorType;
import cz.muni.fi.fresneleditor.common.utils.FresnelUtils;
import cz.muni.fi.fresneleditor.gui.mod.lens2.UnknownLensSelectorValueTypeException;
import fr.inria.jfresnel.Constants;
import fr.inria.jfresnel.fsl.FSLPath;
import fr.inria.jfresnel.sparql.SPARQLQuery;

/**
 * Class that holds information about selector. It consist of selector value and
 * selector type.
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * 
 */
/*
 * The two properties (selector value and selector type) are binded together.
 * The way how this is done is defined by using a specific constructor and
 * cannot be changed later.
 */
public class LensSelector {
	// fixme igor: move this do DomainType? can it be in the same class as
	// format domain type?
	// there is this property domain for format...
	public static enum LensDomain {

		CLASS(Constants._classLensDomain), INSTANCE(
				Constants._instanceLensDomain);

		private String constant;

		private LensDomain(String constant) {
			this.constant = constant;
		}

		public String getUri() {
			return Constants.FRESNEL_NAMESPACE_URI + constant;
		}

		public boolean isInstanceDomain() {
			return INSTANCE.equals(this);
		}

	}

	private final Object value;
	private final SelectorType type;
	private final LensDomain domain;

	/**
	 * Constructs a new selector value wrapper. The type of the selector is
	 * determined from the value object.
	 * 
	 * @param domain
	 *            this lens selector domain <br>
	 *            cannot be null
	 * @param value
	 *            has to be of type: <br>{@link String} in case selector is simple
	 *            selector <br>{@link FSLPath} <br>{@link SPARQLQuery} <br>
	 *            cannot be null
	 * 
	 * @throws IllegalArgumentException
	 *             if value's type is not one of allowed types
	 */
	public LensSelector(LensDomain domain, Object value) {
		this(domain, value, null, null);
	}

	/**
	 * Constructor for custom selector. The type of this selector is determined
	 * by the passed type parameter and not from the value String object.
	 * 
	 * @param domain
	 *            this lens selector domain <br>
	 *            cannot be null
	 * @param value
	 *            string representation of the selector value <br>
	 *            cannot be null
	 * @param type
	 *            any member of {@link SelectorType} <br>
	 *            cannot be null
	 */
	public LensSelector(LensDomain domain, String value, SelectorType type) {
		this(domain, value, type, null);
	}

	private LensSelector(LensDomain domain, Object value, SelectorType type,
			Object internal) {
		Assert.notNull(domain);
		Assert.notNull(value);

		if (!(value instanceof String || value instanceof FSLPath || value instanceof SPARQLQuery)) {
			throw new IllegalArgumentException("The value was of type: "
					+ value.getClass());
		}

		this.value = value;
		this.domain = domain;

		if (type == null) {
			type = getTypeFromValue(value);
		}
		Assert.notNull(type);
		this.type = type;
	}

	/**
	 * Returns this selector type.
	 * 
	 * @return selector type
	 */
	public SelectorType getType() {
		/*
		 * if (type != null) { // the type was explicitly defined
		 * Assert.isTrue(value instanceof String, "value was of type: " +
		 * value.getClass()); return type; }
		 */
		Assert.notNull(type);

		// we have to determine the type from the value object
		if (value == null) {
			throw new UnsupportedOperationException(
					"Cannot get type for selector value = " + value);
		}
		if (value instanceof String) {
			return SelectorType.SIMPLE;
		} else if (value instanceof FSLPath) {
			return SelectorType.FSL;
		} else if (value instanceof SPARQLQuery) {
			return SelectorType.SPARQL;
		} else {
			throw new UnknownLensSelectorValueTypeException(
					"The selector value of type " + value.getClass()
							+ " is not supported");
		}
	}

	/**
	 * @param value
	 * <br>
	 *            cannot be null
	 * @return
	 */
	private static SelectorType getTypeFromValue(Object value) {
		Assert.notNull(value);
		if (value instanceof String) {
			return SelectorType.SIMPLE;
		} else if (value instanceof FSLPath) {
			return SelectorType.FSL;
		} else if (value instanceof SPARQLQuery) {
			return SelectorType.SPARQL;
		} else {
			throw new UnknownLensSelectorValueTypeException(
					"The selector value of type " + value.getClass()
							+ " is not supported");
		}
	}

	/**
	 * Return value can be of type: <br>{@link String} in case selector is simple
	 * selector <br>{@link FSLPath} <br>{@link SPARQLQuery}
	 * 
	 * @return the selector value
	 */
	public Object asObject() {
		return value;
	}

	/**
	 * Returns string that represents this selector value. This string contains
	 * no data type URI information (it contains no "^^fslSelector" in case this
	 * value is of type FSLPath.
	 * 
	 * @return
	 */
	public String asString() {
		if (value == null) {
			return null;
		} else if (value instanceof String) {
			return (String) value;
		} else if (value instanceof FSLPath) {
			return ((FSLPath) value).toString();
		} else if (value instanceof SPARQLQuery) {
			return ((SPARQLQuery) value).sparqlQuery;
		} else {
			// TODO throw some better exception
			throw new IllegalArgumentException(
					"selector value is of unsupported type: "
							+ value.getClass());
		}
	}

	/**
	 * Constructs a new selector value wrapper.
	 * 
	 * @param value
	 *            has to be of type: <br>{@link String} in case selector is simple
	 *            selector <br>{@link FSLPath} <br>{@link SPARQLQuery} <br>
	 * <br>
	 *            Can be null.
	 * 
	 * @throws IllegalArgumentException
	 *             if value's type is not one of
	 */
	public static final boolean selectorValueEquals(Object value1, Object value2) {
		if (value1 == value2)
			return true;

		if (value1 == null || value2 == null) {
			return false;
		}

		if (value1.equals(value2))
			return true;

		if (value1 instanceof String) {
			if (!(value2 instanceof String)) {
				return false;
			} else if (!value1.equals(value2)) {
				return false;
			}
		} else if (value1 instanceof FSLPath) {
			if (!FresnelUtils.fslPathEquals((FSLPath) value1, value2))
				return false;
		} else if (value1 instanceof SPARQLQuery) {
			if (!FresnelUtils.sparqlQueryEquals((SPARQLQuery) value1, value2))
				return false;
		} else {
			throw new IllegalArgumentException("value1 is of type: "
					+ (value1 != null ? value1.getClass() : null));
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof LensSelector))
			return false;
		LensSelector other = (LensSelector) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!selectorValueEquals(value, other.asObject()))
			return false;
		return true;
	}

	public LensDomain getDomain() {
		return domain;
	}

}
