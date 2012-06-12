package cz.muni.fi.fresneleditor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openrdf.model.Value;

public class TupleResult {
	// private final static Logger LOG =
	// LoggerFactory.getLogger(TupleResult.class);

	private List<Map<String, Value>> values = null;
	private List<String> bindingNames = null;

	public TupleResult(List<String> bindingNames,
			List<Map<String, Value>> values) {
		this.bindingNames = bindingNames;
		this.values = values;
	}

	public String[][] getStringValues() {
		List<Map<String, Value>> result = getValues();
		List<String> bindingNames = getBindingNames();
		List<String[]> list = new ArrayList<String[]>();

		for (Map<String, Value> val : result) {
			List<String> values = new ArrayList<String>();
			for (String bindingName : bindingNames) {
				String value = val.get(bindingName).toString();
				if (value == null) {
					value = "";
				}
				values.add(value);
			}
			list.add(values.toArray(new String[0]));
		}
		return list.toArray(new String[0][0]);
	}

	public List<Map<String, Value>> getValues() {
		return values;
	}

	public List<String> getBindingNames() {
		return bindingNames;
	}
}
