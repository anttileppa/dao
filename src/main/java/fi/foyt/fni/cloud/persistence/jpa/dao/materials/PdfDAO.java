package fi.foyt.fni.cloud.persistence.jpa.dao.materials;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;

import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.common.Language;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.Folder;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.MaterialPublicity;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.materials.Pdf;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.users.User;

@RequestScoped
@DAO
public class PdfDAO extends GenericDAO<Pdf> {

	public Pdf create(User creator, Date created, User modifier, Date modified, Language language, Folder parentFolder, String urlName, String title, byte[] data, MaterialPublicity publicity) {
    EntityManager entityManager = getEntityManager();

    Pdf pdf = new Pdf();
    pdf.setCreated(created);
    pdf.setCreator(creator);
    pdf.setData(data);
    pdf.setModified(modified);
    pdf.setModifier(modifier);
    pdf.setTitle(title);
    pdf.setUrlName(urlName);
    pdf.setPublicity(publicity);

    if (language != null)
      pdf.setLanguage(language);

    if (parentFolder != null)
      pdf.setParentFolder(parentFolder);

    entityManager.persist(pdf);

    return pdf;
  }
  
  public Pdf updateData(Pdf pdf, User modifier, byte[] data) {
    EntityManager entityManager = getEntityManager();

    pdf.setData(data);
    pdf.setModified(new Date());
    pdf.setModifier(modifier);

    entityManager.persist(pdf);
    
    return pdf;
  }

}
