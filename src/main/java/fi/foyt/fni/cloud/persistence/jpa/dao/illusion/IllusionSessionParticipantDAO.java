package fi.foyt.fni.cloud.persistence.jpa.dao.illusion;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.illusion.IllusionSession;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.illusion.IllusionSessionParticipant;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.illusion.IllusionSessionParticipantRole;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.illusion.IllusionSessionParticipant_;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User;

@RequestScoped
@DAO
public class IllusionSessionParticipantDAO extends GenericDAO<IllusionSessionParticipant> {

	public IllusionSessionParticipant create(IllusionSession session, User participant, IllusionSessionParticipantRole role) {
    EntityManager entityManager = getEntityManager();

    IllusionSessionParticipant illusionSessionParticipant = new IllusionSessionParticipant();
    illusionSessionParticipant.setSession(session);
    illusionSessionParticipant.setParticipant(participant);
    illusionSessionParticipant.setRole(role);
    
    entityManager.persist(illusionSessionParticipant);

    return illusionSessionParticipant;
  }

  public List<IllusionSessionParticipant> listByParticipant(User participant) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<IllusionSessionParticipant> criteria = criteriaBuilder.createQuery(IllusionSessionParticipant.class);
    Root<IllusionSessionParticipant> root = criteria.from(IllusionSessionParticipant.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.equal(root.get(IllusionSessionParticipant_.participant), participant)
    );

    return entityManager.createQuery(criteria).getResultList();
  }

  public List<IllusionSession> listSessionByParticipant(User participant) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<IllusionSession> criteria = criteriaBuilder.createQuery(IllusionSession.class);
    Root<IllusionSessionParticipant> root = criteria.from(IllusionSessionParticipant.class);
    criteria.select(root.get(IllusionSessionParticipant_.session));
    criteria.where(
      criteriaBuilder.equal(root.get(IllusionSessionParticipant_.participant), participant)
    );

    return entityManager.createQuery(criteria).getResultList();
  }
}