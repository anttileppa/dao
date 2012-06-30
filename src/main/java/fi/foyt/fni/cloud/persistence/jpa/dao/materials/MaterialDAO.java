package fi.foyt.fni.cloud.persistence.jpa.dao.materials;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.Material_;
import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.Folder;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.Material;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialPublicity;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialRole;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialType;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.UserMaterialRole;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.UserMaterialRole_;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User;

@RequestScoped
@DAO
public class MaterialDAO extends GenericDAO<Material> {

	public Material findByParentFolderAndUrlName(Folder parentFolder, String urlName) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Material> criteria = criteriaBuilder.createQuery(Material.class);
    Root<Material> root = criteria.from(Material.class);
    criteria.select(root);
    criteria.where(
        criteriaBuilder.and(
          criteriaBuilder.equal(root.get(Material_.parentFolder), parentFolder),
          criteriaBuilder.equal(root.get(Material_.urlName), urlName)
        )
    );
    
    return getSingleResult(entityManager.createQuery(criteria));
  }
  
  public Material findByRootFolderAndUrlName(User creator, String urlName) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Material> criteria = criteriaBuilder.createQuery(Material.class);
    Root<Material> root = criteria.from(Material.class);
    criteria.select(root);
    criteria.where(
        criteriaBuilder.and(
          criteriaBuilder.isNull(root.get(Material_.parentFolder)),
          criteriaBuilder.equal(root.get(Material_.urlName), urlName),
          criteriaBuilder.equal(root.get(Material_.creator), creator)
        )
    );
    
    return getSingleResult(entityManager.createQuery(criteria));
  }
  
  public List<Material> listByParentFolder(Folder parentFolder) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Material> criteria = criteriaBuilder.createQuery(Material.class);
    Root<Material> root = criteria.from(Material.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.equal(root.get(Material_.parentFolder), parentFolder)
    );
    
    return entityManager.createQuery(criteria).getResultList();
  }

	public List<Material> listByParentFolderAndTypes(Folder parentFolder, Collection<MaterialType> types) {
		EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Material> criteria = criteriaBuilder.createQuery(Material.class);
    Root<Material> root = criteria.from(Material.class);
    criteria.select(root);
    criteria.where(
    	criteriaBuilder.and(
        criteriaBuilder.equal(root.get(Material_.parentFolder), parentFolder),
        root.get(Material_.type).in(types)
      )
    );
    
    return entityManager.createQuery(criteria).getResultList();
  }
	
  public List<Material> listByRootFolder() {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Material> criteria = criteriaBuilder.createQuery(Material.class);
    Root<Material> root = criteria.from(Material.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.isNull(root.get(Material_.parentFolder))
    );
    
    return entityManager.createQuery(criteria).getResultList();
  }
  
  public List<Material> listByRootFolderAndCreator(User creator) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Material> criteria = criteriaBuilder.createQuery(Material.class);
    Root<Material> root = criteria.from(Material.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.and(
        criteriaBuilder.isNull(root.get(Material_.parentFolder)),
        criteriaBuilder.equal(root.get(Material_.creator), creator)
       )
    );
    
    return entityManager.createQuery(criteria).getResultList();
  }
	
  public List<Material> listByRootFolderAndTypesAndCreator(Collection<MaterialType> types, User creator) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Material> criteria = criteriaBuilder.createQuery(Material.class);
    Root<Material> root = criteria.from(Material.class);
    criteria.select(root);
    criteria.where(
    	criteriaBuilder.and(
    	  criteriaBuilder.isNull(root.get(Material_.parentFolder)),
        root.get(Material_.type).in(types),
        criteriaBuilder.equal(root.get(Material_.creator), creator)
      )
    );
    
    return entityManager.createQuery(criteria).getResultList();
  }

	public List<Material> listByRootFolderAndUserAndRoles(User user, Collection<MaterialRole> roles) {
		EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Material> criteria = criteriaBuilder.createQuery(Material.class);
    Root<UserMaterialRole> root = criteria.from(UserMaterialRole.class);
    Join<UserMaterialRole, Material> materialJoin = root.join(UserMaterialRole_.material);
    
    criteria.select(root.get(UserMaterialRole_.material)).distinct(true);
    criteria.where(
        criteriaBuilder.and(
          criteriaBuilder.isNull(materialJoin.get(Material_.parentFolder)),
          criteriaBuilder.equal(root.get(UserMaterialRole_.user), user),
          root.get(UserMaterialRole_.role).in(roles)
        )
    );
    
    return entityManager.createQuery(criteria).getResultList();
  }

	public List<Material> listByRootFolderAndUserAndTypesAndRoles(User user, Collection<MaterialType> types, Collection<MaterialRole> roles) {
		EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Material> criteria = criteriaBuilder.createQuery(Material.class);
    Root<UserMaterialRole> root = criteria.from(UserMaterialRole.class);
    Join<UserMaterialRole, Material> materialJoin = root.join(UserMaterialRole_.material);
    
    criteria.select(root.get(UserMaterialRole_.material)).distinct(true);
    criteria.where(
        criteriaBuilder.and(
          criteriaBuilder.isNull(materialJoin.get(Material_.parentFolder)),
          criteriaBuilder.equal(root.get(UserMaterialRole_.user), user),
          materialJoin.get(Material_.type).in(types),
          root.get(UserMaterialRole_.role).in(roles)
        )
    );
    
    return entityManager.createQuery(criteria).getResultList();
  }

  public List<Material> listByModifierExcludingTypesSortByModified(User modifier, Collection<MaterialType> types, int firstResult, int maxResults) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Material> criteria = criteriaBuilder.createQuery(Material.class);
    Root<Material> root = criteria.from(Material.class);
    criteria.select(root);
    
    
    criteria.where(
      criteriaBuilder.and(
        criteriaBuilder.equal(root.get(Material_.modifier), modifier),
        criteriaBuilder.not(root.get(Material_.type).in(types))
      )
    );
    
    criteria.orderBy(criteriaBuilder.desc(root.get(Material_.modified)));
    
    TypedQuery<Material> query = entityManager.createQuery(criteria);
    query.setFirstResult(firstResult);
    query.setMaxResults(maxResults);
    
    return query.getResultList();
  }
  
  public List<Material> listByModifiedAfter(Date modifiedAfter) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Material> criteria = criteriaBuilder.createQuery(Material.class);
    Root<Material> root = criteria.from(Material.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.greaterThan(root.get(Material_.modified), modifiedAfter)
    );
    
    return entityManager.createQuery(criteria).getResultList();
  }
  
  public Material updatePublicity(Material material, MaterialPublicity publicity, User modifier) {
  	material.setPublicity(publicity);
  	material.setModified(new Date());
		material.setModifier(modifier);
		
  	getEntityManager().persist(material);
  	return material;
  }

	public Material updateParentFolder(Material material, Folder parentFolder, User modifier) {
		material.setParentFolder(parentFolder);
		material.setModified(new Date());
		material.setModifier(modifier);
		
  	getEntityManager().persist(material);
  	
  	return material;
  }

}
