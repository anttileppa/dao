package fi.foyt.fni.cloud.persistence.jpa.dao.common;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.foyt.fni.cloud.persistence.jpa.domainmodel.common.Tag_;
import fi.foyt.fni.cloud.persistence.jpa.dao.DAO;
import fi.foyt.fni.cloud.persistence.jpa.dao.GenericDAO;
import fi.foyt.fni.cloud.persistence.jpa.domainmodel.common.Tag;

@RequestScoped
@DAO
public class TagDAO extends GenericDAO<Tag> {

	public Tag create(String text) {
    EntityManager entityManager = getEntityManager();

    Tag tag = new Tag();
    tag.setText(text);

    entityManager.persist(tag);

    return tag;
  }

  public Tag findByText(String text) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Tag> criteria = criteriaBuilder.createQuery(Tag.class);
    Root<Tag> root = criteria.from(Tag.class);
    criteria.select(root);
    criteria.where(criteriaBuilder.equal(root.get(Tag_.text), text));

    return getSingleResult(entityManager.createQuery(criteria));
  }

}
