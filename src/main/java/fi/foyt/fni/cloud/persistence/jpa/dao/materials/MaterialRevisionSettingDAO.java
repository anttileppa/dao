package fi.foyt.fni.cloud.persistence.jpa.dao.materials;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialRevision;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialRevisionSetting;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialRevisionSetting_;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialSettingKey;

@RequestScoped
@DAO
public class MaterialRevisionSettingDAO extends GenericDAO<MaterialRevisionSetting> {

	public MaterialRevisionSetting create(MaterialRevision materialRevision, MaterialSettingKey key, String value) {
    EntityManager entityManager = getEntityManager();

    MaterialRevisionSetting materialRevisionSetting = new MaterialRevisionSetting();
    materialRevisionSetting.setMaterialRevision(materialRevision);
    materialRevisionSetting.setKey(key);
    materialRevisionSetting.setValue(value);
    
    entityManager.persist(materialRevisionSetting);

    return materialRevisionSetting;
  }
	
  public MaterialRevisionSetting findByMaterialRevisionAndKey(MaterialRevision materialRevision, MaterialSettingKey key) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<MaterialRevisionSetting> criteria = criteriaBuilder.createQuery(MaterialRevisionSetting.class);
    Root<MaterialRevisionSetting> root = criteria.from(MaterialRevisionSetting.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.and(
        criteriaBuilder.equal(root.get(MaterialRevisionSetting_.materialRevision), materialRevision),
        criteriaBuilder.equal(root.get(MaterialRevisionSetting_.key), key)
      )
    );
    
    return getSingleResult(entityManager.createQuery(criteria));
  }
	
  public List<MaterialRevisionSetting> listByMaterialRevision(MaterialRevision materialRevision) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<MaterialRevisionSetting> criteria = criteriaBuilder.createQuery(MaterialRevisionSetting.class);
    Root<MaterialRevisionSetting> root = criteria.from(MaterialRevisionSetting.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.equal(root.get(MaterialRevisionSetting_.materialRevision), materialRevision)
    );
    
    return entityManager.createQuery(criteria).getResultList();
  }
	
}
