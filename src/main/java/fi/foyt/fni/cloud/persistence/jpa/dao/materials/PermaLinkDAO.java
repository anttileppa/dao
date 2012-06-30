package fi.foyt.fni.cloud.persistence.jpa.dao.materials;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.PermaLink_;
import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.Material;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.PermaLink;

@RequestScoped
@DAO
public class PermaLinkDAO extends GenericDAO<PermaLink> {

	public PermaLink create(Material material, String path) {
    EntityManager entityManager = getEntityManager();

    PermaLink starredMaterial = new PermaLink();
    starredMaterial.setMaterial(material);
    starredMaterial.setPath(path);
    
    entityManager.persist(starredMaterial);

    return starredMaterial;
  }

  public PermaLink findByPath(String path) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<PermaLink> criteria = criteriaBuilder.createQuery(PermaLink.class);
    Root<PermaLink> root = criteria.from(PermaLink.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.equal(root.get(PermaLink_.path), path)
    );

    return getSingleResult(entityManager.createQuery(criteria));
  }

	public List<PermaLink> listByMaterial(Material material) {
		EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<PermaLink> criteria = criteriaBuilder.createQuery(PermaLink.class);
    Root<PermaLink> root = criteria.from(PermaLink.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.equal(root.get(PermaLink_.material), material)
    );

    return entityManager.createQuery(criteria).getResultList();
  }
  
}
