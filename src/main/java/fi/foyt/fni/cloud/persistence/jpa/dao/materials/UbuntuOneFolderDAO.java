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
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.UbuntuOneFolder;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.UbuntuOneFolder_;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.Folder;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialPublicity;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User;

@RequestScoped
@DAO
public class UbuntuOneFolderDAO extends GenericDAO<UbuntuOneFolder> {

  public UbuntuOneFolder create(User creator, Language language, Folder parentFolder, String urlName, String title, MaterialPublicity publicity, String ubuntuOneKey,
      Long generation, String contentPath) {
    EntityManager entityManager = getEntityManager();

    Date now = new Date();

    UbuntuOneFolder ubuntuOneFile = new UbuntuOneFolder();
    ubuntuOneFile.setCreated(now);
    ubuntuOneFile.setCreator(creator);
    ubuntuOneFile.setModified(now);
    ubuntuOneFile.setModifier(creator);
    ubuntuOneFile.setTitle(title);
    ubuntuOneFile.setUrlName(urlName);
    ubuntuOneFile.setPublicity(publicity);
    ubuntuOneFile.setLanguage(language);
    ubuntuOneFile.setParentFolder(parentFolder);
    ubuntuOneFile.setUbuntuOneKey(ubuntuOneKey);
    ubuntuOneFile.setGeneration(generation);
    ubuntuOneFile.setContentPath(contentPath);

    entityManager.persist(ubuntuOneFile);

    return ubuntuOneFile;
  }

  public UbuntuOneFolder findByUbuntuOneKey(String ubuntuOneKey) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UbuntuOneFolder> criteria = criteriaBuilder.createQuery(UbuntuOneFolder.class);
    Root<UbuntuOneFolder> root = criteria.from(UbuntuOneFolder.class);
    criteria.select(root);
    criteria.where(criteriaBuilder.equal(root.get(UbuntuOneFolder_.ubuntuOneKey), ubuntuOneKey));

    return getSingleResult(entityManager.createQuery(criteria));
  }

  public UbuntuOneFolder findByContentPath(String contentPath) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UbuntuOneFolder> criteria = criteriaBuilder.createQuery(UbuntuOneFolder.class);
    Root<UbuntuOneFolder> root = criteria.from(UbuntuOneFolder.class);
    criteria.select(root);
    criteria.where(criteriaBuilder.equal(root.get(UbuntuOneFolder_.contentPath), contentPath));

    return getSingleResult(entityManager.createQuery(criteria));
  }

  public UbuntuOneFolder updateGeneration(UbuntuOneFolder ubuntuOneFolder, Long generation, User modifier) {
    ubuntuOneFolder.setGeneration(generation);
    ubuntuOneFolder.setModifier(modifier);
    getEntityManager().persist(ubuntuOneFolder);
    return ubuntuOneFolder;
  }

  public UbuntuOneFolder updateContentPath(UbuntuOneFolder ubuntuOneFolder, String contentPath, User modifier) {
    ubuntuOneFolder.setContentPath(contentPath);
    ubuntuOneFolder.setModifier(modifier);
    getEntityManager().persist(ubuntuOneFolder);
    return ubuntuOneFolder;
  }

}
