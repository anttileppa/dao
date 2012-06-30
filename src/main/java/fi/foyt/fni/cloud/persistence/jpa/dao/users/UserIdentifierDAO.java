package fi.foyt.fni.cloud.persistence.jpa.dao.users;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.UserIdentifier_;
import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.UserIdentifier;

@RequestScoped
@DAO
public class UserIdentifierDAO extends GenericDAO<UserIdentifier> {

	public UserIdentifier create(User user, String identifier) {
    EntityManager entityManager = getEntityManager();

    UserIdentifier userIdentifier = new UserIdentifier();
    userIdentifier.setIdentifier(identifier);
    userIdentifier.setUser(user);
    entityManager.persist(userIdentifier);
    return userIdentifier;
  }

  public UserIdentifier findByIdentifier(String identifier) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserIdentifier> criteria = criteriaBuilder.createQuery(UserIdentifier.class);
    Root<UserIdentifier> root = criteria.from(UserIdentifier.class);
    criteria.select(root);
    criteria.where(criteriaBuilder.equal(root.get(UserIdentifier_.identifier), identifier));

    return getSingleResult(entityManager.createQuery(criteria));
  }

  public List<UserIdentifier> listByUserId(User user) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserIdentifier> criteria = criteriaBuilder.createQuery(UserIdentifier.class);
    Root<UserIdentifier> root = criteria.from(UserIdentifier.class);
    criteria.select(root);
    criteria.where(criteriaBuilder.equal(root.get(UserIdentifier_.user), user));

    return entityManager.createQuery(criteria).getResultList();
  }
}
