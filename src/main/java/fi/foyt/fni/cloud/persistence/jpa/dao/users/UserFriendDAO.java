package fi.foyt.fni.cloud.persistence.jpa.dao.users;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.UserFriend;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.UserFriend_;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User_;

@RequestScoped
@DAO
public class UserFriendDAO extends GenericDAO<UserFriend> {

	public UserFriend create(User user, User friend, Boolean confirmed) {
    EntityManager entityManager = getEntityManager();
    
    UserFriend userFriend = new UserFriend();
    userFriend.setFriend(friend);
    userFriend.setUser(user);
    userFriend.setConfirmed(confirmed);
    
    entityManager.persist(userFriend);
    
    return userFriend;
  }

  public UserFriend findByUserAndFriend(User user, User friend) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserFriend> criteria = criteriaBuilder.createQuery(UserFriend.class);
    Root<UserFriend> root = criteria.from(UserFriend.class);
    criteria.select(root);
    criteria.where(
        criteriaBuilder.and(
            criteriaBuilder.equal(root.get(UserFriend_.user), user),
            criteriaBuilder.equal(root.get(UserFriend_.friend), friend)
        )
    );

    return getSingleResult(entityManager.createQuery(criteria));
  }

  public List<UserFriend> listByUser(User user) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserFriend> criteria = criteriaBuilder.createQuery(UserFriend.class);
    Root<UserFriend> root = criteria.from(UserFriend.class);
    criteria.select(root);
    criteria.where(criteriaBuilder.equal(root.get(UserFriend_.user), user));

    return entityManager.createQuery(criteria).getResultList();
  }

  public List<UserFriend> listByUserAndConfirmed(User user, Boolean confirmed) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserFriend> criteria = criteriaBuilder.createQuery(UserFriend.class);
    Root<UserFriend> root = criteria.from(UserFriend.class);
    criteria.select(root);
    criteria.where(
        criteriaBuilder.and(
            criteriaBuilder.equal(root.get(UserFriend_.user), user),
            criteriaBuilder.equal(root.get(UserFriend_.confirmed), confirmed)
        )
    );

    return entityManager.createQuery(criteria).getResultList();
  }

  public List<UserFriend> listByFriend(User friend) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserFriend> criteria = criteriaBuilder.createQuery(UserFriend.class);
    Root<UserFriend> root = criteria.from(UserFriend.class);
    criteria.select(root);
    criteria.where(criteriaBuilder.equal(root.get(UserFriend_.friend), friend));

    return entityManager.createQuery(criteria).getResultList();
  }

  public List<UserFriend> listByFriendAndConfirmed(User friend, Boolean confirmed) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserFriend> criteria = criteriaBuilder.createQuery(UserFriend.class);
    Root<UserFriend> root = criteria.from(UserFriend.class);
    criteria.select(root);
    criteria.where(
        criteriaBuilder.and(
            criteriaBuilder.equal(root.get(UserFriend_.friend), friend),
            criteriaBuilder.equal(root.get(UserFriend_.confirmed), confirmed)
        )
    );

    return entityManager.createQuery(criteria).getResultList();
  }
  
  public List<User> listFriendUsersByConfirmed(User user, Boolean confirmed) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder friendUsersCriteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> friendUsersCriteria = friendUsersCriteriaBuilder.createQuery(Long.class);
    Root<UserFriend> friendUsersRoot = friendUsersCriteria.from(UserFriend.class);
    Join<UserFriend, User> friendUsersUserJoin = friendUsersRoot.join(UserFriend_.user);
    friendUsersCriteria.select(friendUsersUserJoin.get(User_.id));
    friendUsersCriteria.where(
        friendUsersCriteriaBuilder.and(
            friendUsersCriteriaBuilder.equal(friendUsersRoot.get(UserFriend_.friend), user),
            friendUsersCriteriaBuilder.equal(friendUsersRoot.get(UserFriend_.confirmed), confirmed)
        )
    );
    
    CriteriaBuilder userFriendsCriteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> userFriendsCriteria = userFriendsCriteriaBuilder.createQuery(Long.class);
    Root<UserFriend> userFriendsRoot = userFriendsCriteria.from(UserFriend.class);
    Join<UserFriend, User> usersFriendsUserJoin = userFriendsRoot.join(UserFriend_.friend);
    userFriendsCriteria.select(usersFriendsUserJoin.get(User_.id));
    userFriendsCriteria.where(
        userFriendsCriteriaBuilder.and(
            userFriendsCriteriaBuilder.equal(userFriendsRoot.get(UserFriend_.user), user),
            userFriendsCriteriaBuilder.equal(userFriendsRoot.get(UserFriend_.confirmed), confirmed)
        )
    );
    
    List<Long> friendIds = entityManager.createQuery(userFriendsCriteria).getResultList();
    friendIds.addAll(entityManager.createQuery(friendUsersCriteria).getResultList());
    
    CriteriaBuilder userCriteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> userCriteria = userCriteriaBuilder.createQuery(User.class);
    Root<User> userRoot = userCriteria.from(User.class);
    userCriteria.select(userRoot);
    userCriteria.where(
    	userRoot.get(User_.id).in(friendIds)
    );
    
    return entityManager.createQuery(userCriteria).getResultList();
  }

  public UserFriend updateConfirmed(UserFriend userFriend, Boolean confirmed) {
    EntityManager entityManager = getEntityManager();
    userFriend.setConfirmed(confirmed);
    entityManager.persist(userFriend);
    return userFriend;
  }
}
