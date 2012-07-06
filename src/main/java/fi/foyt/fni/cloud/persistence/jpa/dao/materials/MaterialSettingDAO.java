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
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialSetting;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialSettingKey;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialSetting_;

@RequestScoped
@DAO
public class MaterialSettingDAO extends GenericDAO<MaterialSetting> {

	public MaterialSetting create(Material material, MaterialSettingKey key, String value) {
    EntityManager entityManager = getEntityManager();

    MaterialSetting materialSetting = new MaterialSetting();
    materialSetting.setMaterial(material);
    materialSetting.setKey(key);
    materialSetting.setValue(value);
    
    entityManager.persist(materialSetting);

    return materialSetting;
  }

  public MaterialSetting findByMaterialAndKey(Material material, MaterialSettingKey key) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<MaterialSetting> criteria = criteriaBuilder.createQuery(MaterialSetting.class);
    Root<MaterialSetting> root = criteria.from(MaterialSetting.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.and(
        criteriaBuilder.equal(root.get(MaterialSetting_.material), material),
        criteriaBuilder.equal(root.get(MaterialSetting_.key), key)
      )
    );
    
    return getSingleResult(entityManager.createQuery(criteria));
  }

	public List<MaterialSetting> listByMaterial(Material material) {
		EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<MaterialSetting> criteria = criteriaBuilder.createQuery(MaterialSetting.class);
    Root<MaterialSetting> root = criteria.from(MaterialSetting.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.equal(root.get(MaterialSetting_.material), material)
    );
    
    return entityManager.createQuery(criteria).getResultList();
  }

	public MaterialSetting updateValue(MaterialSetting materialSetting, String value) {
		EntityManager entityManager = getEntityManager();

		materialSetting.setValue(value);
		
		entityManager.persist(materialSetting);
		
		return materialSetting;
  }
  

}
