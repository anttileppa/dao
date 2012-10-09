package fi.foyt.fni.cloud.persistence.jpa.dao.materials;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.common.Language;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.DropboxFile;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.DropboxFile_;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.Folder;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialPublicity;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User;

@RequestScoped
@DAO
public class DropboxFileDAO extends GenericDAO<DropboxFile> {

  public DropboxFile create(User creator, Language language, Folder parentFolder, String urlName, String title, MaterialPublicity publicity,
      String dropboxPath, String mimeType) {
    EntityManager entityManager = getEntityManager();

    Date now = new Date();

    DropboxFile dropboxFile = new DropboxFile();
    dropboxFile.setCreated(now);
    dropboxFile.setCreator(creator);
    dropboxFile.setModified(now);
    dropboxFile.setModifier(creator);
    dropboxFile.setTitle(title);
    dropboxFile.setUrlName(urlName);
    dropboxFile.setPublicity(publicity);
    dropboxFile.setLanguage(language);
    dropboxFile.setParentFolder(parentFolder);
    dropboxFile.setDropboxPath(dropboxPath);
    dropboxFile.setMimeType(mimeType);

    entityManager.persist(dropboxFile);

    return dropboxFile;
  }

  public DropboxFile findByDropboxPath(String dropboxPath) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<DropboxFile> criteria = criteriaBuilder.createQuery(DropboxFile.class);
    Root<DropboxFile> root = criteria.from(DropboxFile.class);
    criteria.select(root);
    criteria.where(criteriaBuilder.equal(root.get(DropboxFile_.dropboxPath), dropboxPath));

    return getSingleResult(entityManager.createQuery(criteria));
  }

}
