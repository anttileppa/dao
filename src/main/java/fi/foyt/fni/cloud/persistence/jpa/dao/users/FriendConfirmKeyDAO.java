package fi.foyt.fni.cloud.persistence.jpa.dao.users;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.FriendConfirmKey;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.FriendConfirmKey_;

@RequestScoped
@DAO
public class FriendConfirmKeyDAO extends GenericDAO<FriendConfirmKey> {

	public FriendConfirmKey create(User user, User friend, String value) {
    EntityManager entityManager = getEntityManager();

    FriendConfirmKey friendConfirmKey = new FriendConfirmKey();
    friendConfirmKey.setValue(value);
    friendConfirmKey.setUser(user);
    friendConfirmKey.setFriend(friend);
    friendConfirmKey.setCreated(new Date());
    
    entityManager.persist(friendConfirmKey);
    return friendConfirmKey;
  }

  public FriendConfirmKey findByValue(String value) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<FriendConfirmKey> criteria = criteriaBuilder.createQuery(FriendConfirmKey.class);
    Root<FriendConfirmKey> root = criteria.from(FriendConfirmKey.class);
    criteria.select(root);
    criteria.where(criteriaBuilder.equal(root.get(FriendConfirmKey_.value), value));

    return getSingleResult(entityManager.createQuery(criteria));
  }

	public List<FriendConfirmKey> listByUserAndFriend(User user, User friend) {
		EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<FriendConfirmKey> criteria = criteriaBuilder.createQuery(FriendConfirmKey.class);
    Root<FriendConfirmKey> root = criteria.from(FriendConfirmKey.class);
    criteria.select(root);
    
    criteria.where(criteriaBuilder.and(
    		criteriaBuilder.equal(root.get(FriendConfirmKey_.user), user),
    		criteriaBuilder.equal(root.get(FriendConfirmKey_.friend), friend)
    ));

    return entityManager.createQuery(criteria).getResultList();
  }

}
