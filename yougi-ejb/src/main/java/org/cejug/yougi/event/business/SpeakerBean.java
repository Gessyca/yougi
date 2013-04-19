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
package org.cejug.yougi.event.business;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.cejug.yougi.event.entity.Event;
import org.cejug.yougi.event.entity.Session;
import org.cejug.yougi.event.entity.Speaker;
import org.cejug.yougi.entity.EntitySupport;

/**
 * @author Hildeberto Mendonca - http://www.hildeberto.com
 */
@Stateless
@LocalBean
public class SpeakerBean {

    @PersistenceContext
    private EntityManager em;

    public Speaker findSpeaker(String id) {
        if (id != null) {
            return em.find(Speaker.class, id);
        }
        return null;
    }

    public List<Speaker> findSpeakers(Event event) {
        return em.createQuery("select ss.speaker from SpeakerSession ss where ss.session.event = :event order by ss.speaker.userAccount.firstName asc")
                 .setParameter("event", event)
                 .getResultList();
    }

    public List<Speaker> findSpeakers(Session session) {
        return em.createQuery("select ss.speaker from SpeakerSession ss where ss.session = :session order by ss.speaker.userAccount.firstName asc")
                 .setParameter("session", session)
                 .getResultList();
    }

    public void save(Speaker speaker) {
        if (EntitySupport.INSTANCE.isIdNotValid(speaker)) {
            speaker.setId(EntitySupport.INSTANCE.generateEntityId());
            em.persist(speaker);
        } else {
            em.merge(speaker);
        }
    }

    public void remove(String id) {
        Speaker speaker = em.find(Speaker.class, id);
        if (speaker != null) {
            em.remove(speaker);
        }
    }
}