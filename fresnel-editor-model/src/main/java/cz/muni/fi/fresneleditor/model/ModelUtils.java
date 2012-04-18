package cz.muni.fi.fresneleditor.model;

import java.util.ArrayList;
import java.util.List;

import org.openrdf.model.Namespace;
import org.openrdf.model.Value;
import org.springframework.util.Assert;

public class ModelUtils {

	/**
	 * Returns list of URIs which are from one of the namespaces contained in
	 * filter.
	 * 
	 * @param uris
	 * @param filter
	 * @return list of URIs which are from one of the namespaces contained in
	 *         filter
	 */
	public static List<Value> filterURIs(List<Value> uris,
			List<Namespace> filter) {
		if (filter == null || filter.isEmpty()) {
			return uris;
		}
		List<Value> filteredClasses = new ArrayList<Value>();
		if (uris != null && uris.size() > 0) {
			for (Value value : uris) {
				if (value != null && isPrefixed(value, filter)) {
					filteredClasses.add(value);
				}
			}
		}

		return filteredClasses;
	}

	/**
	 * Returns true if given value object is defined in one of the given
	 * namespaces.
	 * 
	 * @param value
	 *            object to be checked against the list of namespaces
	 * @param filter
	 *            list of allowed namespaces. Cannot be <code>null</code>
	 * @return true if given value object is defined in one of the given filter
	 *         namespace
	 */
	// fixme igor: make this filtering based on the context?
	public static boolean isPrefixed(Value value, List<Namespace> filter) {
		Assert.notNull(filter);
		for (Namespace space : filter) {
			if (value.stringValue().startsWith(space.getName())
					|| value.stringValue().startsWith(space.getPrefix())) {
				return true;
			}
		}
		return false;
	}
}
