/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.group.utils;

import java.util.List;

import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.URIImpl;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.data.CssValueType;
import cz.muni.fi.fresneleditor.common.data.StyleGuiWrapper;
import cz.muni.fi.fresneleditor.common.data.StyleType;
import cz.muni.fi.fresneleditor.common.utils.AModelManager;
import cz.muni.fi.fresneleditor.gui.mod.group.data.GroupModel;
import cz.muni.fi.fresneleditor.model.IModel;
import fr.inria.jfresnel.Constants;
import fr.inria.jfresnel.Group;

/**
 * Class which contains functionality for building of GroupModel on the basis of
 * JFresnel Group object.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 21.4.2009
 */
public class GroupModelManager extends AModelManager<Group> {

	public static final String EXTERNAL_CSS_REFERENCE_PROPERTY = Constants.FRESNEL_NAMESPACE_URI
			+ "stylesheetLink";

	/**
	 * Default constructor.
	 */
	public GroupModelManager() {
	}

	/**
	 * {@inheritDoc}
	 */
	public GroupModel buildModel(Group group) {

		GroupModel groupModel = new GroupModel();
		URI groupUri = new URIImpl(group.getURI());

		groupModel.setUri(group.getURI());
		groupModel.setFresnelGroup(group);
		groupModel.setLabel(getResourceLabel(groupUri));
		groupModel.setComment(getResourceComment(groupUri));

		// CSS stylesheet reference
		List<Value> resultList = getResourceValueProperties(groupUri,
				EXTERNAL_CSS_REFERENCE_PROPERTY);

		Resource cssResource = null;
		if (!resultList.isEmpty()) {
			cssResource = (Resource) resultList.get(0);
		}
		if (cssResource != null && !"".equals(cssResource.stringValue())) {
			groupModel.setCssStylesheetUrl(cssResource.stringValue());
		}

		// Loading associated Lens URIs
		List<URI> lensesUris = ContextHolder.getInstance()
				.getFresnelRepositoryDao().getLensesUrisForGroup(groupUri);
		groupModel.setAssociatedLensUris(lensesUris);

		// Loading associated Format URIs
		List<URI> formatsUris = ContextHolder.getInstance()
				.getFresnelRepositoryDao().getFormatsUrisForGroup(groupUri);
		groupModel.setAssociatedFormatUris(formatsUris);

		// STYLES LOADING
		if (group.getLabelStyle() != null) {
			StyleGuiWrapper labelStyle = new StyleGuiWrapper(StyleType.LABEL);
			labelStyle.setValue(group.getLabelStyle());
			// FIXME
			labelStyle.setValueType(CssValueType.CLASS);
			groupModel.getStyles().add(labelStyle);
		}

		if (group.getValueStyle() != null) {
			StyleGuiWrapper valueStyle = new StyleGuiWrapper(StyleType.VALUE);
			valueStyle.setValue(group.getValueStyle());
			// FIXME
			valueStyle.setValueType(CssValueType.CLASS);
			groupModel.getStyles().add(valueStyle);
		}

		if (group.getPropertyStyle() != null) {
			StyleGuiWrapper propertyStyle = new StyleGuiWrapper(
					StyleType.PROPERTY);
			propertyStyle.setValue(group.getPropertyStyle());
			// FIXME
			propertyStyle.setValueType(CssValueType.CLASS);
			groupModel.getStyles().add(propertyStyle);
		}

		// Resource style loading (needs special handling - not covered by
		// JFresnel)
		StyleGuiWrapper resourceStyle = getResourceStyle(new URIImpl(
				group.getURI()));
		if (resourceStyle != null) {
			groupModel.getStyles().add(resourceStyle);
		}

		// TODO: Implement loading of additional content for Groups
		// CONTENT TYPES LOADING
		// ContentFormat labelFormat = group.getLabelFormattingInstructions();
		// groupModel.getAdditionalContents().add(createAdditionalContentGuiWrapper(AdditionalContentType.LABEL,
		// labelFormat));
		//
		// ContentFormat valueFormat = group.getValueFormattingInstructions();
		// groupModel.getAdditionalContents().add(createAdditionalContentGuiWrapper(AdditionalContentType.VALUE,
		// valueFormat));
		//
		// ContentFormat propertyFormat =
		// group.getPropertyFormattingInstructions();
		// groupModel.getAdditionalContents().add(createAdditionalContentGuiWrapper(AdditionalContentType.PROPERTY,
		// propertyFormat));
		//
		// ContentFormat resourceFormat =
		// group.getResourceFormattingInstructions();
		// groupModel.getAdditionalContents().add(createAdditionalContentGuiWrapper(AdditionalContentType.RESOURCE,
		// resourceFormat));

		return groupModel;
	}

	/**
	 * {@inheritDoc}
	 */
	public GroupModel buildNewModel() {

		GroupModel groupModel = new GroupModel();

		return groupModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Group convertModel2JFresnel(IModel model) {
		throw new UnsupportedOperationException(
				"Conversion not support for group model yet!");
	}

}
