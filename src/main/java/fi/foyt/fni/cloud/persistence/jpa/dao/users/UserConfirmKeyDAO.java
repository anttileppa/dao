package fi.foyt.fni.cloud.persistence.jpa.dao.users;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.UserConfirmKey;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.UserConfirmKey_;

@RequestScoped
@DAO
public class UserConfirmKeyDAO extends GenericDAO<UserConfirmKey> {

	public UserConfirmKey create(User user, String value) {
    EntityManager entityManager = getEntityManager();

    UserConfirmKey userConfirmKey = new UserConfirmKey();
    userConfirmKey.setValue(value);
    userConfirmKey.setUser(user);
    userConfirmKey.setCreated(new Date());
    
    entityManager.persist(userConfirmKey);
    return userConfirmKey;
  }

  public UserConfirmKey findByValue(String value) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserConfirmKey> criteria = criteriaBuilder.createQuery(UserConfirmKey.class);
    Root<UserConfirmKey> root = criteria.from(UserConfirmKey.class);
    criteria.select(root);
    criteria.where(criteriaBuilder.equal(root.get(UserConfirmKey_.value), value));

    return getSingleResult(entityManager.createQuery(criteria));
  }

}
