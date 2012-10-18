package fi.foyt.fni.cloud.persistence.jpa.dao.materials;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.Image;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User;

@RequestScoped
@DAO
public class BinaryDAO extends GenericDAO<Image> {

  public Long lengthDataByCreator(User creator) {
    // Criteria API does not support "length" operation for byte arrays
    // so we use JPQL queries
    EntityManager entityManager = getEntityManager();
    Query query = entityManager.createQuery("select coalesce(sum(length(data)), 0) from Binary where creator = :creator");
    query.setParameter("creator", creator);
    return (Long) query.getSingleResult();
  }

}
