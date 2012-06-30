package fi.foyt.fni.cloud.persistence.jpa.dao.chat;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.chat.MultiUserChatMessage;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.chat.MultiUserChatMessage_;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.chat.XmppUser;

@RequestScoped
@DAO
public class MultiUserChatMessageDAO extends GenericDAO<MultiUserChatMessage> {

	MultiUserChatMessageDAO() {
  }
	
  public MultiUserChatMessage create(String roomJid, XmppUser from, String body, String subject, Date sent) {
    MultiUserChatMessage multiUserChatMessage = new MultiUserChatMessage();
    
    multiUserChatMessage.setBody(body);
    multiUserChatMessage.setFrom(from);
    multiUserChatMessage.setSent(sent);
    multiUserChatMessage.setSubject(subject);
    multiUserChatMessage.setRoomJid(roomJid);
    
    getEntityManager().persist(multiUserChatMessage);
    
    return multiUserChatMessage;
  }

  public List<MultiUserChatMessage> listByRoomJid(String roomJid) {
    EntityManager entityManager = getEntityManager();
    
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<MultiUserChatMessage> criteria = criteriaBuilder.createQuery(MultiUserChatMessage.class);
    Root<MultiUserChatMessage> root = criteria.from(MultiUserChatMessage.class);
    criteria.select(root);
    criteria.where(criteriaBuilder.equal(root.get(MultiUserChatMessage_.roomJid), roomJid));
    
    return entityManager.createQuery(criteria).getResultList();
  }
  
  public Long countByTo(String roomJid) {
    EntityManager entityManager = getEntityManager();
    
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = criteriaBuilder.createQuery(Long.class);
    Root<MultiUserChatMessage> root = criteria.from(MultiUserChatMessage.class);
    criteria.select(criteriaBuilder.count(root));
    criteria.where(criteriaBuilder.equal(root.get(MultiUserChatMessage_.roomJid), roomJid));
    
    return entityManager.createQuery(criteria).getSingleResult();
  }
}