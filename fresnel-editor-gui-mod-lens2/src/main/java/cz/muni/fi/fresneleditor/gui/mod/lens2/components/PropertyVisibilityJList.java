/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.fresneleditor.gui.mod.lens2.components;

import java.awt.Component;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import org.openrdf.model.Namespace;
import org.openrdf.model.URI;
import org.springframework.util.StringUtils;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.guisupport.ExtendedJList;
import cz.muni.fi.fresneleditor.common.utils.FresnelUtils;
import cz.muni.fi.fresneleditor.gui.mod.lens2.PropertyVisibilityWrapper;

/**
 * 
 * 
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 */
public class PropertyVisibilityJList extends
		ExtendedJList<PropertyVisibilityWrapper> {

	// private final static Logger LOG =
	// LoggerFactory.getLogger(BaseRepositoryDao.class);

	public PropertyVisibilityJList() {
		super();
		setCellRenderer(new PropertyVisibilityLMCellRenderer());
	}

	public static class PropertyVisibilityLMCellRenderer extends
			DefaultListCellRenderer {

		private static PropertyVisibilityLMCellRenderer instance;

		PropertyVisibilityLMCellRenderer getInstance() {
			if (instance == null) {
				instance = new PropertyVisibilityLMCellRenderer();
			}
			return instance;
		}

		public PropertyVisibilityLMCellRenderer() {
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			Component component = super.getListCellRendererComponent(list,
					value, index, isSelected, cellHasFocus);

			PropertyVisibilityWrapper propVisibility = null;
			if (value instanceof PropertyVisibilityWrapper) {
				propVisibility = (PropertyVisibilityWrapper) value;
				if (component instanceof JLabel) {
					JLabel label = (JLabel) component;

					// if (propVisibility.isComplexPropertyDescription()) {
					// // set multiple line text
					// String complexPropertyLabel =
					// getComplexPropertyLabel(propVisibility);
					// label.setText(complexPropertyLabel);
					// } else {
					// // set one line text
					// label.setText(FresnelUtils.replaceNamespace(propVisibility
					// .getFresnelPropertyValueURI(),
					// ContextHolder.getInstance()
					// .getBothDaosNamespaces()));
					// }

					label.setText(propVisibility2Label(propVisibility));

					label.setToolTipText(propVisibility2Tooltip(propVisibility));
				}
			} else {
				// this is the branch that netbeans go when trying to add the
				// this list
				// component to another component
				/*
				 * throw new IllegalArgumentException("Value has to be of type "
				 * + Lens.class + " but it was of type: " + value.getClass());
				 */
			}
			return component;
		}

		/**
		 * Function converts propVisibility to label
		 */
		public static String propVisibility2Label(
				PropertyVisibilityWrapper propVisibility) {
			if (propVisibility.isComplexPropertyDescription()) {
				// set multiple line text
				return getComplexPropertyLabel(propVisibility);

			} else {
				// set one line text
				return FresnelUtils.replaceNamespace(
						propVisibility.getFresnelPropertyValueURI(),
						ContextHolder.getInstance().getBothDaosNamespaces());
			}
		}

		public static String propVisibility2Tooltip(
				PropertyVisibilityWrapper propVisibility) {
			return propVisibility.getFresnelPropertyValueURI();
		}

		/**
		 * [&nbsp property foaf:knows<br>
		 * &nbsp&nbsp sublens :foafPersonDefaultLens<br>
		 * &nbsp&nbsp depth 5<br>
		 * &nbsp&nbsp use :foafGroup &nbsp]
		 * 
		 * @param visibility
		 * @return
		 */
		private static String getComplexPropertyLabel(
				PropertyVisibilityWrapper visibility) {
			String doubleSpace = "&nbsp;&nbsp;";
			StringBuilder label = new StringBuilder();
			label.append("<html>[");
			if (StringUtils.hasText(visibility.getFresnelPropertyValueURI())) {
				label.append(doubleSpace)
						.append("property ")
						.append(shortenUri(visibility
								.getFresnelPropertyValueURI())).append("<br>");
			}
			if (visibility.getMaxDepth() > PropertyVisibilityWrapper.MAX_DEPTH_NOT_DEFINED) {
				label.append(doubleSpace).append("depth ")
						.append(visibility.getMaxDepth()).append("<br>");
			}
			if (StringUtils.hasText(visibility.getFresnelUseUri())) {
				label.append(doubleSpace).append("use ")
						.append(shortenUri(visibility.getFresnelUseUri()))
						.append("<br>");
			}
			if (!visibility.getSublensesURIs().isEmpty()) {
				for (URI uri : visibility.getSublensesURIs()) {
					label.append(doubleSpace).append("sublens ")
							.append(shortenUri(uri.stringValue()))
							.append("<br>");
				}
			}
			label.append("]</html>");

			// replace the first double space with single space only
			// there is the '[' bracket character which occupies one place
			return label.toString().replaceFirst("&nbsp;", "");
		}

		private static String shortenUri(String uri) {
			List<Namespace> namespaces = ContextHolder.getInstance()
					.getBothDaosNamespaces();
			return FresnelUtils.replaceNamespace(uri, namespaces);
		}
	}

}
