package fi.foyt.fni.cloud.persistence.jpa.dao.materials;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.common.Language;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.Document;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.DocumentRevision;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.DocumentRevision_;

@RequestScoped
@DAO
public class DocumentRevisionDAO extends GenericDAO<DocumentRevision> {

	public DocumentRevision create(Document document, Long revision, Date created, Boolean compressed, Boolean completeRevision, byte[] data, String title, Language language) {
    EntityManager entityManager = getEntityManager();

    DocumentRevision documentRevision = new DocumentRevision();
    documentRevision.setCreated(created);
    documentRevision.setCompleteRevision(completeRevision);
    documentRevision.setCompressed(compressed);
    documentRevision.setCreated(created);
    documentRevision.setData(data);
    documentRevision.setTitle(title);
    documentRevision.setLanguage(language);
    documentRevision.setDocument(document);
    documentRevision.setRevision(revision);
    
    entityManager.persist(documentRevision);

    return documentRevision;
  }
	
	public List<DocumentRevision> listByDocument(Document document) {
		EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<DocumentRevision> criteria = criteriaBuilder.createQuery(DocumentRevision.class);
    Root<DocumentRevision> root = criteria.from(DocumentRevision.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.equal(root.get(DocumentRevision_.document), document)
    );
    
    return entityManager.createQuery(criteria).getResultList();
	}

	public  List<DocumentRevision> listByDocumentAndRevisionGreaterThan(Document document, Long revision) {
		EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<DocumentRevision> criteria = criteriaBuilder.createQuery(DocumentRevision.class);
    Root<DocumentRevision> root = criteria.from(DocumentRevision.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.and(
    		criteriaBuilder.equal(root.get(DocumentRevision_.document), document),
    		criteriaBuilder.greaterThan(root.get(DocumentRevision_.revision), revision)
    	)
    );
    
    return entityManager.createQuery(criteria).getResultList();
  }
  
  public Long maxRevisionByDocument(Document document) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = criteriaBuilder.createQuery(Long.class);
    Root<DocumentRevision> root = criteria.from(DocumentRevision.class);
    criteria.select(criteriaBuilder.max(root.get(DocumentRevision_.revision)));
    criteria.where(
      criteriaBuilder.equal(root.get(DocumentRevision_.document), document)
    );
    
    return entityManager.createQuery(criteria).getSingleResult();
  }
  
  public DocumentRevision updateTitle(DocumentRevision documentRevision, String title) {
    EntityManager entityManager = getEntityManager();
	
    documentRevision.setTitle(title);
    
    entityManager.persist(documentRevision);
    
    return documentRevision;
  }
  
  public DocumentRevision updateLanguage(DocumentRevision documentRevision, Language language) {
    EntityManager entityManager = getEntityManager();
	
    documentRevision.setLanguage(language);
    
    entityManager.persist(documentRevision);
    
    return documentRevision;
  }
}
