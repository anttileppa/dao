package fi.foyt.fni.cloud.persistence.jpa.dao.users;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.UserSettingKey_;
import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.UserSettingKey;

@RequestScoped
@DAO
public class UserSettingKeyDAO extends GenericDAO<UserSettingKey> {

	public UserSettingKey create(String key) {
    EntityManager entityManager = getEntityManager();

    UserSettingKey userSettingKey = new UserSettingKey();
    userSettingKey.setKey(key);
    entityManager.persist(userSettingKey);
    return userSettingKey;
  }

  public UserSettingKey findByKey(String key) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserSettingKey> criteria = criteriaBuilder.createQuery(UserSettingKey.class);
    Root<UserSettingKey> root = criteria.from(UserSettingKey.class);
    criteria.select(root);
    criteria.where(criteriaBuilder.equal(root.get(UserSettingKey_.key), key));

    return getSingleResult(entityManager.createQuery(criteria));
  }
}
