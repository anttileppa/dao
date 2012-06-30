package fi.foyt.fni.cloud.persistence.jpa.dao.message;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.message.MessageFolder;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.message.MessageFolder_;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User;

@RequestScoped
@DAO
public class MessageFolderDAO extends GenericDAO<MessageFolder> {

	public MessageFolder create(User owner, String name) {
		MessageFolder messageFolder = new MessageFolder();
		
		messageFolder.setName(name);
		messageFolder.setOwner(owner);
		
		getEntityManager().persist(messageFolder);
		
		return messageFolder;
	}
  
  public List<MessageFolder> listByOwner(User owner) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<MessageFolder> criteria = criteriaBuilder.createQuery(MessageFolder.class);
    Root<MessageFolder> root = criteria.from(MessageFolder.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.equal(root.get(MessageFolder_.owner), owner)
    );
    
    return entityManager.createQuery(criteria).getResultList();
  }
  
  public MessageFolder updateName(MessageFolder messageFolder, String name) {
  	messageFolder.setName(name);
  	getEntityManager().persist(messageFolder);
  	return messageFolder;
  }
  
}
