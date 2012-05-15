/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.fresneleditor.common;

import cz.muni.fi.fresneleditor.model.NSConstants;
import cz.muni.fi.fresneleditor.model.ProjectInfo;
import fr.inria.jfresnel.Constants;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.ValueFactory;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nodrock
 */
public class ProjectInfoParser {

    private final static Logger LOG = LoggerFactory.getLogger(ProjectInfoParser.class);
    protected static final Resource[] NULL_CONTEXTS = new Resource[]{};

    public static ProjectInfo getProjectInfo(Repository repository) {
        ProjectInfo projectInfo = new ProjectInfo();
        RepositoryConnection connection = null;
        try {
            connection = repository.getConnection();
            ValueFactory vf = connection.getValueFactory();
            RepositoryResult<Statement> statements = connection.getStatements(null,
                    vf.createURI(Constants.RDF_NAMESPACE_URI, Constants._type),
                    vf.createURI(NSConstants._Dataset), false, NULL_CONTEXTS);
            if (statements.hasNext()) {
                Statement st = statements.next();
                projectInfo.setUri(st.getSubject().stringValue());
                RepositoryResult<Statement> rr = connection.getStatements(st.getSubject(),
                        vf.createURI(NSConstants.DCTERMS_NAMESPACE_URI, NSConstants._title),
                        null, false, NULL_CONTEXTS);
                if (rr.hasNext()) {
                    projectInfo.setTitle(rr.next().getObject().stringValue());
                } else {
                    return null;
                }
                rr = connection.getStatements(st.getSubject(),
                        vf.createURI(NSConstants.DCTERMS_NAMESPACE_URI, NSConstants._description),
                        null, false, NULL_CONTEXTS);
                if (rr.hasNext()) {
                    projectInfo.setDescription(rr.next().getObject().stringValue());
                }
            } else {
                return null;
            }
        } catch (RepositoryException ex) {
            LOG.error("Error loading project info!", ex);
        } finally {
            closeConnection(connection);
        }
        return projectInfo;
    }

    public static boolean writeProjectInfo(Repository repository, ProjectInfo projectInfo) {
        RepositoryConnection connection = null;
        try {
            connection = repository.getConnection();
            ValueFactory vf = connection.getValueFactory();
            connection.setAutoCommit(false);

            connection.add(vf.createStatement(vf.createURI(projectInfo.getUri()),
                    vf.createURI(Constants.RDF_NAMESPACE_URI, Constants._type),
                    vf.createURI(NSConstants._Dataset)), NULL_CONTEXTS);
            connection.add(vf.createStatement(vf.createURI(projectInfo.getUri()),
                    vf.createURI(NSConstants.DCTERMS_NAMESPACE_URI, NSConstants._title),
                    vf.createLiteral(projectInfo.getTitle())), NULL_CONTEXTS);
            if (projectInfo.getDescription() != null) {
                connection.add(vf.createStatement(vf.createURI(projectInfo.getUri()),
                        vf.createURI(NSConstants.DCTERMS_NAMESPACE_URI, NSConstants._description),
                        vf.createLiteral(projectInfo.getDescription())), NULL_CONTEXTS);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (RepositoryException ex) {
            LOG.error("Error saving project info!", ex);
        } finally {
            closeConnection(connection);
        }
        return true;
    }

    private static void closeConnection(RepositoryConnection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (RepositoryException e) {
                LOG.warn("Error during closing connection to repository ");
            }
        }
    }
}
