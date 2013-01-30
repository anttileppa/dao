package fi.foyt.fni.cloud.persistence.jpa.dao.store;

import javax.enterprise.context.RequestScoped;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.common.MultilingualString;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.store.StoreTag;

@RequestScoped
@DAO
public class StoreTagDAO extends GenericDAO<StoreTag> {
  
	public StoreTag create(MultilingualString text) {
		StoreTag storeTag = new StoreTag();
		storeTag.setText(text);
		getEntityManager().persist(storeTag);
		return storeTag;
	}
  
}
