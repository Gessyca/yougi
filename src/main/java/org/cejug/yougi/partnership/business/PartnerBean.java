/* Yougi is a web application conceived to manage user groups or
 * communities focused on a certain domain of knowledge, whose members are
 * constantly sharing information and participating in social and educational
 * events. Copyright (C) 2011 Hildeberto Mendonça.
 *
 * This application is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This application is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * There is a full copy of the GNU Lesser General Public License along with
 * this library. Look for the file license.txt at the root level. If you do not
 * find it, write to the Free Software Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA 02111-1307 USA.
 * */
package org.cejug.yougi.partnership.business;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.cejug.yougi.partnership.entity.Partner;
import org.cejug.yougi.entity.EntitySupport;

/**
 * Manages partners of the user group.
 *
 * @author Hildeberto Mendonca - http://www.hildeberto.com
 */
@Stateless
@LocalBean
public class PartnerBean {

    @PersistenceContext
    private EntityManager em;

    public Partner findPartner(String id) {
        if(id != null) {
            return em.find(Partner.class, id);
        }
        else {
            return null;
        }
    }

    public List<Partner> findPartners() {
        return em.createQuery("select p from Partner p order by p.name asc")
                 .getResultList();
    }

    public void save(Partner partner) {
    	if(EntitySupport.INSTANCE.isIdNotValid(partner)) {
            partner.setId(EntitySupport.INSTANCE.generateEntityId());
            em.persist(partner);
        }
        else {
            em.merge(partner);
        }
    }

    public void remove(String id) {
        Partner partner = em.find(Partner.class, id);
        if(partner != null) {
            em.remove(partner);
        }
    }
}