package fi.foyt.fni.cloud.persistence.jpa.dao.auth;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.auth.InternalAuth;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.auth.InternalAuth_;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User;

@RequestScoped
@DAO
public class InternalAuthDAO extends GenericDAO<InternalAuth> {

	public InternalAuth create(User user, String password) {
    EntityManager entityManager = getEntityManager();

    InternalAuth fniUserAuth = new InternalAuth();
    fniUserAuth.setPassword(password);
    fniUserAuth.setUser(user);
    entityManager.persist(fniUserAuth);
    return fniUserAuth;
  }

  public InternalAuth findByUserAndPassword(User user, String password) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<InternalAuth> criteria = criteriaBuilder.createQuery(InternalAuth.class);
    Root<InternalAuth> root = criteria.from(InternalAuth.class);
    criteria.select(root);
    criteria.where(
        criteriaBuilder.and(
          criteriaBuilder.equal(root.get(InternalAuth_.user), user),
          criteriaBuilder.equal(root.get(InternalAuth_.password), password)
        )
    );
    
    return getSingleResult(entityManager.createQuery(criteria));
  }
}
