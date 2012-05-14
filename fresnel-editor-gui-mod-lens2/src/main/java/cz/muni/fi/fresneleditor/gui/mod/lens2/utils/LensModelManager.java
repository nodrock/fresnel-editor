/*
 * Fresnel Editor
 */

package cz.muni.fi.fresneleditor.gui.mod.lens2.utils;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.openrdf.model.Value;

import cz.muni.fi.fresneleditor.common.ContextHolder;
import cz.muni.fi.fresneleditor.common.utils.AModelManager;
import cz.muni.fi.fresneleditor.gui.mod.lens2.LensModel;
import cz.muni.fi.fresneleditor.model.DataImportException;
import cz.muni.fi.fresneleditor.model.DataRepositoryDao;
import cz.muni.fi.fresneleditor.model.FresnelRepositoryDao;
import cz.muni.fi.fresneleditor.model.IModel;
import fr.inria.jfresnel.Group;
import fr.inria.jfresnel.Lens;
import fr.inria.jfresnel.sesame.SesameLens;

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

		// fixme igor: possible optimization here - e.g. do not create every
		// time new repository?
		FresnelRepositoryDao myRepository = new FresnelRepositoryDao(
				"testRepo1");
		try {
			myRepository.addData(model.asStatements());
		} catch (DataImportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Lens lens = myRepository.getLens(model
				.getModelUri());

		// the groups are added here manually because JFresnel parser tries to
		// load the
		// groups from the fresnel repository data - it tries to load them with
		// all properties of the group
		// but the groups are not present in our 'temporary' repository - that
		// contains
		// only statements related directly to the passed model
		LensModel lensModel = (LensModel) model;
		if (lensModel.getGroups() != null && !lensModel.getGroups().isEmpty()) {
			lens.addAssociatedGroups(new Vector<Group>(lensModel
					.getGroups()));
		}
		return lens;

	}

	public List<Map<String, Value>> execSPARQLQuery(String query) {

		DataRepositoryDao dao = ContextHolder.getInstance()
				.getDataRepositoryDao();

		return dao.execSPARQLQuery(query);

		// return super.getResourceValueProperties(uri, "");
	}
}
