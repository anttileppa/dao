package fi.foyt.fni.cloud.persistence.jpa.dao.materials;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.common.Language;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.Folder;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialPublicity;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.File;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User;

@RequestScoped
@DAO
public class FileDAO extends GenericDAO<File> {

	public File create(User creator, Date created, User modifier, Date modified, Language language, Folder parentFolder, String urlName, String title, byte[] data, String contentType, MaterialPublicity publicity) {
    EntityManager entityManager = getEntityManager();

    File file = new File();
    file.setCreated(created);
    file.setCreator(creator);
    file.setData(data);
    file.setContentType(contentType);
    file.setModified(modified);
    file.setModifier(modifier);
    file.setTitle(title);
    file.setUrlName(urlName);
    file.setPublicity(publicity);

    if (language != null)
      file.setLanguage(language);

    if (parentFolder != null)
      file.setParentFolder(parentFolder);

    entityManager.persist(file);

    return file;
  }
  
  public File updateData(File file, User modifier, byte[] data) {
    EntityManager entityManager = getEntityManager();

    file.setData(data);
    file.setModified(new Date());
    file.setModifier(modifier);

    entityManager.persist(file);
    
    return file;
  }

  public File updateContentType(File file, User modifier, String contentType) {
    EntityManager entityManager = getEntityManager();

    file.setContentType(contentType);
    file.setModified(new Date());
    file.setModifier(modifier);

    entityManager.persist(file);
    
    return file;
  }

}
