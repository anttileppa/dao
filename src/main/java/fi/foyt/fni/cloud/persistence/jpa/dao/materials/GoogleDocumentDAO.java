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
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.Folder;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.GoogleDocument;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.GoogleDocumentType;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.GoogleDocument_;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialPublicity;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User;

@RequestScoped
@DAO
public class GoogleDocumentDAO extends GenericDAO<GoogleDocument> {

	public GoogleDocument create(User creator, Language language, Folder parentFolder,  String urlName, String title, String documentId, GoogleDocumentType documentType, MaterialPublicity publicity) {
    EntityManager entityManager = getEntityManager();

    Date now = new Date();

    GoogleDocument googleDocument = new GoogleDocument();
    googleDocument.setDocumentId(documentId);
    googleDocument.setCreated(now);
    googleDocument.setCreator(creator);
    googleDocument.setModified(now);
    googleDocument.setModifier(creator);
    googleDocument.setTitle(title);
    googleDocument.setUrlName(urlName);
    googleDocument.setDocumentType(documentType);
    googleDocument.setPublicity(publicity);

    if (language != null)
      googleDocument.setLanguage(language);

    if (parentFolder != null)
      googleDocument.setParentFolder(parentFolder);

    entityManager.persist(googleDocument);

    return googleDocument;
  }
  
  public GoogleDocument findByCreatorAndDocumentId(User creator, String documentId) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<GoogleDocument> criteria = criteriaBuilder.createQuery(GoogleDocument.class);
    Root<GoogleDocument> root = criteria.from(GoogleDocument.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.and(
          criteriaBuilder.equal(root.get(GoogleDocument_.creator), creator),
          criteriaBuilder.equal(root.get(GoogleDocument_.documentId), documentId)
      )
    );
    
    return getSingleResult(entityManager.createQuery(criteria));
  }
  
}
