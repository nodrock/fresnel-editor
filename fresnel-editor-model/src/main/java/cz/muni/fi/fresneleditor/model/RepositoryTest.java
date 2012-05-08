package cz.muni.fi.fresneleditor.model;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.openrdf.rio.RDFFormat;
import org.w3c.dom.Document;

import fr.inria.jfresnel.Format;
import fr.inria.jfresnel.FresnelDocument;
import fr.inria.jfresnel.Lens;
import fr.inria.jfresnel.sesame.SesameRenderer;
import java.util.ArrayList;
import java.util.List;

public class RepositoryTest {

	/**
	 * @param args
	 * @throws DataImportException
	 * @throws ParserConfigurationException
	 */
	public static void main(String[] args) throws DataImportException,
			ParserConfigurationException {

		DataRepositoryDao domainRepository = new DataRepositoryDao("testDao1");
		File foafFile = new File("foaf_wapxana.xml");
		domainRepository.addData(foafFile, RDFFormat.RDFXML, null);

		domainRepository.printStatements(System.out, false);

		FresnelRepositoryDao fresnelRepository = new FresnelRepositoryDao(
				"testDao2");
		File fresnelFile = new File("foaf_fresnel.n3");
		fresnelRepository.addData(fresnelFile, RDFFormat.N3, null);

		fresnelRepository.printStatements(System.out, false);

		FresnelDocument fd = fresnelRepository.getFresnelDocument();
		// Group[] groups = fd.getGroups();

		// System.out.println("Following groups found:");
		// for (Group group : groups) {
		// //System.out.println(group);
		// //System.out.println("===========GG==========");
		// }

		for (Format format : fd.getFormats()) {
			System.out.println(format);
			System.out.println("===========FF==========");
		}

		// RENDERER test
		SesameRenderer renderer = new SesameRenderer();

		FresnelDocument fdTest = new FresnelDocument();
		List<Lens> lenses = new ArrayList<Lens>();
		lenses.add(fresnelRepository.getLenses().get(0));
		fdTest.setLenses(lenses);

		Document document = renderer.render(fdTest,
				domainRepository.getRepository());

		try {
			// Prepare the DOM document for writing
			DOMSource source = new DOMSource(document);

			// Prepare the output file
			File file = new File("text-output.xml");
			Result result = new StreamResult(file);

			// Write the DOM document to the file
			Transformer xformer = TransformerFactory.newInstance()
					.newTransformer();
			xformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
		} catch (TransformerException e) {
		}
	}
}
