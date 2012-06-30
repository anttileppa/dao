package fi.foyt.fni.cloud.persistence.jpa.dao.system;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.system.SystemSetting;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.system.SystemSetting_;

@RequestScoped
@DAO
public class SystemSettingDAO extends GenericDAO<SystemSetting> {
	
	public SystemSetting create(String name, String value) {
    EntityManager entityManager = getEntityManager();

    SystemSetting systemSetting = new SystemSetting();
    systemSetting.setName(name);
    systemSetting.setValue(value);
    
    entityManager.persist(systemSetting);

    return systemSetting;
  }

  public SystemSetting findByName(String name) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<SystemSetting> criteria = criteriaBuilder.createQuery(SystemSetting.class);
    Root<SystemSetting> root = criteria.from(SystemSetting.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.equal(root.get(SystemSetting_.name), name)
    );
    
    return getSingleResult(entityManager.createQuery(criteria));
  }

  public SystemSetting updateValue(SystemSetting systemSetting, String value) {
    EntityManager entityManager = getEntityManager();
    systemSetting.setValue(value);
    
    systemSetting = entityManager.merge(systemSetting);
    return systemSetting;
  }
}
