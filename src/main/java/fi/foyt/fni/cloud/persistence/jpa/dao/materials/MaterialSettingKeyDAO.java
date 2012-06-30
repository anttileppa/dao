package fi.foyt.fni.cloud.persistence.jpa.dao.materials;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialSettingKey;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialSettingKey_;

@RequestScoped
@DAO
public class MaterialSettingKeyDAO extends GenericDAO<MaterialSettingKey> {

	public MaterialSettingKey create(String name) {
    EntityManager entityManager = getEntityManager();

    MaterialSettingKey materialSettingKey = new MaterialSettingKey();
    materialSettingKey.setName(name);

    entityManager.persist(materialSettingKey);

    return materialSettingKey;
  }

  public MaterialSettingKey findByName(String name) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<MaterialSettingKey> criteria = criteriaBuilder.createQuery(MaterialSettingKey.class);
    Root<MaterialSettingKey> root = criteria.from(MaterialSettingKey.class);
    criteria.select(root);
    criteria.where(criteriaBuilder.equal(root.get(MaterialSettingKey_.name), name));

    return getSingleResult(entityManager.createQuery(criteria));
  }

}
