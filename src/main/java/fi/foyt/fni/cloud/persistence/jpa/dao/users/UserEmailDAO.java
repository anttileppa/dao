package fi.foyt.fni.cloud.persistence.jpa.dao.users;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.UserEmail_;
import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.UserEmail;

@RequestScoped
@DAO
public class UserEmailDAO extends GenericDAO<UserEmail> {

	public UserEmail create(User user, String email) {
    EntityManager entityManager = getEntityManager();

    UserEmail userEmail = new UserEmail();
    userEmail.setEmail(email);
    userEmail.setUser(user);
    entityManager.persist(userEmail);
    return userEmail;
  }

  public UserEmail findByEmail(String email) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserEmail> criteria = criteriaBuilder.createQuery(UserEmail.class);
    Root<UserEmail> root = criteria.from(UserEmail.class);
    criteria.select(root);
    criteria.where(criteriaBuilder.equal(root.get(UserEmail_.email), email));

    return getSingleResult(entityManager.createQuery(criteria));
  }

  public List<UserEmail> listByUser(User user) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserEmail> criteria = criteriaBuilder.createQuery(UserEmail.class);
    Root<UserEmail> root = criteria.from(UserEmail.class);
    criteria.select(root);
    criteria.where(criteriaBuilder.equal(root.get(UserEmail_.user), user));

    return entityManager.createQuery(criteria).getResultList();
  }

	public List<User> listUsersByEmails(List<String> emails) {
		EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
    Root<UserEmail> root = criteria.from(UserEmail.class);
    
    criteria.select(root.get(UserEmail_.user));
    criteria.where(root.get(UserEmail_.email).in(emails));
    criteria.groupBy(root.get(UserEmail_.user));

    return entityManager.createQuery(criteria).getResultList();
  }
}
