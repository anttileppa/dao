package fi.foyt.fni.cloud.persistence.jpa.dao.illusion;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.illusion.IllusionSession;

@RequestScoped
@DAO
public class IllusionSessionDAO extends GenericDAO<IllusionSession> {

	public IllusionSession create(String name) {
    EntityManager entityManager = getEntityManager();

    IllusionSession illusionSession = new IllusionSession();
    illusionSession.setName(name);
    
    entityManager.persist(illusionSession);

    return illusionSession;
  }
  
}
