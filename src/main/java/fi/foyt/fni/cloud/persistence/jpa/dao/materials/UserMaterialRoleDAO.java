package fi.foyt.fni.cloud.persistence.jpa.dao.materials;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.Material;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialRole;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.UserMaterialRole;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.UserMaterialRole_;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User;

@RequestScoped
@DAO
public class UserMaterialRoleDAO extends GenericDAO<UserMaterialRole> {

	public UserMaterialRole create(Material material, User user, MaterialRole role) {
    EntityManager entityManager = getEntityManager();

    UserMaterialRole userMaterialRole = new UserMaterialRole();
    userMaterialRole.setMaterial(material);
    userMaterialRole.setUser(user);
    userMaterialRole.setRole(role);

    entityManager.persist(userMaterialRole);

    return userMaterialRole;
  }

	public UserMaterialRole findByMaterialAndUser(Material material, User user) {
		EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserMaterialRole> criteria = criteriaBuilder.createQuery(UserMaterialRole.class);
    Root<UserMaterialRole> root = criteria.from(UserMaterialRole.class);
    criteria.select(root);
    criteria.where(
        criteriaBuilder.and(
          criteriaBuilder.equal(root.get(UserMaterialRole_.material), material),
          criteriaBuilder.equal(root.get(UserMaterialRole_.user), user)
        )
    );
    
    return getSingleResult(entityManager.createQuery(criteria));
  }

	public List<UserMaterialRole> listByMaterial(Material material) {
		EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserMaterialRole> criteria = criteriaBuilder.createQuery(UserMaterialRole.class);
    Root<UserMaterialRole> root = criteria.from(UserMaterialRole.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.equal(root.get(UserMaterialRole_.material), material)
    );
    
    return entityManager.createQuery(criteria).getResultList();
  }

	public List<UserMaterialRole> listByMaterialAndRole(Material material, MaterialRole role) {
		EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserMaterialRole> criteria = criteriaBuilder.createQuery(UserMaterialRole.class);
    Root<UserMaterialRole> root = criteria.from(UserMaterialRole.class);
    criteria.select(root);
    criteria.where(
        criteriaBuilder.and(
          criteriaBuilder.equal(root.get(UserMaterialRole_.material), material),
          criteriaBuilder.equal(root.get(UserMaterialRole_.role), role)
        )
    );
    
    return entityManager.createQuery(criteria).getResultList();
  }

	public UserMaterialRole updateRole(UserMaterialRole userMaterialRole, MaterialRole role) {
		userMaterialRole.setRole(role);
    getEntityManager().persist(userMaterialRole);
    return userMaterialRole;
  }

}
