package fi.foyt.fni.cloud.persistence.jpa.dao.materials;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.Folder;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialPublicity;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.UbuntuOneRootFolder;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.UbuntuOneRootFolder_;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User;

@RequestScoped
@DAO
public class UbuntuOneRootFolderDAO extends GenericDAO<UbuntuOneRootFolder> {

	public UbuntuOneRootFolder create(User creator, Date created, User modifier, Date modified, Folder parentFolder, String urlName, String title, MaterialPublicity publicity, Long generation, Date lastSynchronized) {
    EntityManager entityManager = getEntityManager();

    UbuntuOneRootFolder ubuntuOneRootFolder = new UbuntuOneRootFolder();
    ubuntuOneRootFolder.setCreated(created);
    ubuntuOneRootFolder.setCreator(creator);
    ubuntuOneRootFolder.setModified(modified);
    ubuntuOneRootFolder.setModifier(modifier);
    ubuntuOneRootFolder.setTitle(title);
    ubuntuOneRootFolder.setUrlName(urlName);
    ubuntuOneRootFolder.setPublicity(publicity);
    ubuntuOneRootFolder.setLanguage(null);
    ubuntuOneRootFolder.setParentFolder(parentFolder);
    ubuntuOneRootFolder.setGeneration(generation);
    ubuntuOneRootFolder.setLastSynchronized(lastSynchronized);
    
    entityManager.persist(ubuntuOneRootFolder);

    return ubuntuOneRootFolder;
  }

  public UbuntuOneRootFolder findByUser(User user) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UbuntuOneRootFolder> criteria = criteriaBuilder.createQuery(UbuntuOneRootFolder.class);
    Root<UbuntuOneRootFolder> root = criteria.from(UbuntuOneRootFolder.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.equal(root.get(UbuntuOneRootFolder_.creator), user)
    );
    
    return getSingleResult(entityManager.createQuery(criteria));
  }
  
  public List<UbuntuOneRootFolder> listAllSortByAscLastSynchronized(Integer firstResult, Integer maxResults) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UbuntuOneRootFolder> criteria = criteriaBuilder.createQuery(UbuntuOneRootFolder.class);
    Root<UbuntuOneRootFolder> root = criteria.from(UbuntuOneRootFolder.class);
    criteria.select(root);
    criteria.orderBy(criteriaBuilder.asc(root.get(UbuntuOneRootFolder_.lastSynchronized)));
    TypedQuery<UbuntuOneRootFolder> query = entityManager.createQuery(criteria);

    query.setFirstResult(firstResult);
    query.setMaxResults(maxResults);
    
    return query.getResultList();
  }
  
  public UbuntuOneRootFolder updateGeneration(UbuntuOneRootFolder ubuntuOneRootFolder, Long generation, User modifier) {
    EntityManager entityManager = getEntityManager();

    ubuntuOneRootFolder.setGeneration(generation);
    ubuntuOneRootFolder.setModifier(modifier);
    
    entityManager.persist(ubuntuOneRootFolder);
    
    return ubuntuOneRootFolder;
  }

  public UbuntuOneRootFolder updateLastSynchronized(UbuntuOneRootFolder ubuntuOneRootFolder, Date lastSynchronized, User modifier) {
    EntityManager entityManager = getEntityManager();

    ubuntuOneRootFolder.setLastSynchronized(lastSynchronized);
    ubuntuOneRootFolder.setModifier(modifier);
    
    entityManager.persist(ubuntuOneRootFolder);
    
    return ubuntuOneRootFolder;
  }
}
