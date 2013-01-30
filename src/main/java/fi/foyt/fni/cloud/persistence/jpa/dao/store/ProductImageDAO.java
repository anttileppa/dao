package fi.foyt.fni.cloud.persistence.jpa.dao.store;

import javax.enterprise.context.RequestScoped;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.store.ProductImage;

@RequestScoped
@DAO
public class ProductImageDAO extends GenericDAO<ProductImage> {
  
}
