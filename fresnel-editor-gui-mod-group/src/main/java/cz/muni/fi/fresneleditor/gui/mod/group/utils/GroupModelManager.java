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
import fr.inria.jfresnel.Format;
import fr.inria.jfresnel.Group;
import fr.inria.jfresnel.Lens;
import java.util.ArrayList;

/**
 * Class which contains functionality for building of GroupModel on the basis of
 * JFresnel Group object.
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @version 21.4.2009
 */
public class GroupModelManager extends AModelManager<Group> {

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
		groupModel.setLabel(group.getLabel());
		groupModel.setComment(group.getComment());

		// CSS stylesheet reference
                
		List<String> resultList = new ArrayList<String>(group.getStylesheetLinks());

                if(!resultList.isEmpty()){
                    groupModel.setCssStylesheetUrl(resultList.get(0));
                }


		// Loading associated Lens URIs
                List<Lens> lenses = new ArrayList<Lens>(group.getLenses());
		List<URI> lensesUris = new ArrayList<URI>();
                for(Lens l : lenses){
                    lensesUris.add(new URIImpl(l.getURI()));
                }
		groupModel.setAssociatedLensUris(lensesUris);

		// Loading associated Format URIs
                List<Format> formats = new ArrayList<Format>(group.getFormats());
		List<URI> formatsUris = new ArrayList<URI>();
		for(Format f : formats){
                    formatsUris.add(new URIImpl(f.getURI()));
                }
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
                
                if (group.getResourceStyle() != null) {
			StyleGuiWrapper resourceStyle = new StyleGuiWrapper(
					StyleType.RESOURCE);
			resourceStyle.setValue(group.getResourceStyle());
			// FIXME
			resourceStyle.setValueType(CssValueType.CLASS);
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
		GroupModel groupModel = (GroupModel) model;
		Group group = new Group(groupModel.getModelUri(), "");
                
                group.setLabel(groupModel.getLabel());
                group.setComment(groupModel.getComment());
                
                List<URI> associatedLensUris = groupModel.getAssociatedLensUris();
                for(URI lensUri : associatedLensUris){
                    Lens lens = ContextHolder.getInstance().getFresnelDocumentDao().getLens(lensUri.toString());
                    if(lens != null){
                        group.addLens(lens);
                    }
                }
                
                
                List<URI> associatedFormatUris = groupModel.getAssociatedFormatUris();
                for(URI formatUri : associatedFormatUris){
                    Format format = ContextHolder.getInstance().getFresnelDocumentDao().getFormat(formatUri.toString());
                    if(format != null){
                        group.addFormat(format);
                    }
                }
                
                // Styles
		for (StyleGuiWrapper style : groupModel.getStyles()) {
			switch (style.getType()) {
			case LABEL:
				if (style.getType().equals(StyleType.LABEL)) {
					group.setLabelStyle(style.getValue());
				}
				break;
			case VALUE:
				if (style.getType().equals(StyleType.VALUE)) {
					group.setValueStyle(style.getValue());
				}
				break;
			case PROPERTY:
				if (style.getType().equals(StyleType.PROPERTY)) {
					group.setPropertyStyle(style.getValue());
				}
				break;
			case RESOURCE:
				if (style.getType().equals(StyleType.RESOURCE)) {
					// Resource style not supported by JFresnel 0.3.2
				}
				break;
			default:
				throw new IllegalArgumentException("Style type: "
						+ style.getType());
			}
		}     
                
                String cssStylesheetUrl = groupModel.getCssStylesheetUrl();
                if(cssStylesheetUrl != null){
                    group.addStylesheetLink(cssStylesheetUrl);
                }
                
                return group;
	}

}
