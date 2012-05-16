/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.lens2.utils;

import cz.muni.fi.fresneleditor.common.data.SelectorType;
import cz.muni.fi.fresneleditor.common.utils.AModelManager;
import cz.muni.fi.fresneleditor.gui.mod.lens2.LensModel;
import cz.muni.fi.fresneleditor.gui.mod.lens2.PropertyVisibilityWrapper;
import cz.muni.fi.fresneleditor.gui.mod.lens2.model.LensSelector;
import cz.muni.fi.fresneleditor.gui.mod.lens2.model.LensSelector.LensDomain;
import cz.muni.fi.fresneleditor.model.IModel;
import fr.inria.jfresnel.Constants;
import fr.inria.jfresnel.Lens;
import fr.inria.jfresnel.fsl.FSLNSResolver;
import fr.inria.jfresnel.fsl.FSLPath;
import fr.inria.jfresnel.sparql.SPARQLQuery;
import fr.inria.jfresnel.visibility.PropertyVisibility;
import fr.inria.jfresnel.visibility.PropertyVisibilityType;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Miroslav Warchil (warmir@mail.muni.cz)
 * @author Igor Zemsky (zemsky@mail.muni.cz)
 * @version 30.6.2009
 */
public class LensModelManager extends AModelManager<Lens> {

	@Override
	public IModel buildModel(Lens source) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Lens convertModel2JFresnel(IModel model) {

            LensModel lensModel = (LensModel) model;
            Lens lens = new Lens(model.getModelUri(), "");

            // LABEL AND COMMENT
            lens.setLabel(lensModel.getLabel());
            lens.setComment(lensModel.getComment());
            
            // DOMAIN SELECTORS
            if (lensModel.getSelector() != null){
                LensSelector selector = lensModel.getSelector();
                if(selector.getDomain() == LensDomain.CLASS){
                    if(selector.getType() == SelectorType.SIMPLE){
                        lens.addClassDomain(selector.asString());
                    }else{
                        throw new IllegalArgumentException("Invalid selector type value: "
									+ selector.getType() + "!");
	}
                }else if(selector.getDomain() == LensDomain.INSTANCE){
                    if(selector.getType() == SelectorType.SIMPLE){
                        lens.addInstanceDomain(selector.asObject(), Constants._BASIC_SELECTOR);
                    }else if(selector.getType() == SelectorType.FSL){
                        lens.addInstanceDomain(FSLPath.pathFactory(selector.asString(), new FSLNSResolver(), FSLPath.NODE_STEP), Constants._FSL_SELECTOR);
                    }else if(selector.getType() == SelectorType.SPARQL){
                        lens.addInstanceDomain(SPARQLQuery.queryFactory(selector.asString()), Constants._SPARQL_SELECTOR);
                    }else{
                        throw new IllegalArgumentException("Invalid selector type value: "
									+ selector.getType() + "!");
                    }
                }
            }

            // PURPOSE
            lens.setPurpose(lensModel.getPurpose());

            List<PropertyVisibility> propertiesToShow = new ArrayList<PropertyVisibility>();
            for(PropertyVisibilityWrapper pvw : lensModel.getShowProperties()){
//                if(pvw.getPropertyVisibility() instanceof BasicVisibility){
//                    
//                }
                propertiesToShow.add(pvw.getPropertyVisibility());
            }

            List<PropertyVisibility> propertiesToHide = new ArrayList<PropertyVisibility>();
            for(PropertyVisibilityWrapper pvw : lensModel.getHideProperties()){
//                if(pvw.getPropertyVisibility() instanceof BasicVisibility){
//                    
//                }
                propertiesToHide.add(pvw.getPropertyVisibility());
            }

            int apIndex = -1;
            for(int i=0; i<propertiesToShow.size(); i++){
                if(propertiesToShow.get(i).getType() == PropertyVisibilityType.ALLPROPERTIES){
                    apIndex = i;
                    break;
	}
}
            lens.setPropertiesVisibility(propertiesToShow, propertiesToHide, apIndex);
            
            return lens;
	}
}
