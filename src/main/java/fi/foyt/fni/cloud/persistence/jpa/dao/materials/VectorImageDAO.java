package fi.foyt.fni.cloud.persistence.jpa.dao.materials;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.common.Language;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.Folder;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialPublicity;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.VectorImage;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User;

@RequestScoped
@DAO
public class VectorImageDAO extends GenericDAO<VectorImage> {

	public VectorImage create(User creator, Language language, Folder parentFolder,  String urlName, String title, String data, MaterialPublicity publicity) {
    EntityManager entityManager = getEntityManager();

    Date now = new Date();

    VectorImage vectorImage = new VectorImage();
    vectorImage.setCreated(now);
    vectorImage.setCreator(creator);
    vectorImage.setData(data);
    vectorImage.setLanguage(language);
    vectorImage.setModified(now);
    vectorImage.setModifier(creator);
    vectorImage.setParentFolder(parentFolder);
    vectorImage.setTitle(title);
    vectorImage.setUrlName(urlName);
    vectorImage.setPublicity(publicity);
    
    entityManager.persist(vectorImage);

    return vectorImage;
  }


  public VectorImage updateTitle(VectorImage vectorImage, User modifier, String title) {
    EntityManager entityManager = getEntityManager();

    vectorImage.setTitle(title);
    vectorImage.setModified(new Date());
    vectorImage.setModifier(modifier);
    
    entityManager.persist(vectorImage);
    return vectorImage;
  }

  public VectorImage updateData(VectorImage vectorImage, User modifier, String data) {
    EntityManager entityManager = getEntityManager();

    vectorImage.setData(data);
    vectorImage.setModified(new Date());
    vectorImage.setModifier(modifier);
    
    entityManager.persist(vectorImage);
    return vectorImage;
  }

  public VectorImage updateLanguage(VectorImage vectorImage, User modifier, Language language) {
    EntityManager entityManager = getEntityManager();

    vectorImage.setLanguage(language);
    vectorImage.setModified(new Date());
    vectorImage.setModifier(modifier);
    
    entityManager.persist(vectorImage);
    return vectorImage;
  }
}
