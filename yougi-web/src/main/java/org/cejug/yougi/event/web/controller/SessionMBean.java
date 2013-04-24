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
package org.cejug.yougi.event.web.controller;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import org.cejug.yougi.event.business.EventBean;
import org.cejug.yougi.event.business.SessionBean;
import org.cejug.yougi.event.entity.Event;
import org.cejug.yougi.event.entity.Session;

/**
 * @author Hildeberto Mendonca - http://www.hildeberto.com
 */
@ManagedBean
@RequestScoped
public class SessionMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private SessionBean sessionBean;

    @EJB
    private EventBean eventBean;

    @ManagedProperty(value = "#{param.id}")
    private String id;

    @ManagedProperty(value = "#{param.eventId}")
    private String eventId;

    private Event event;

    private Session session;

    private List<Event> events;

    private List<Session> sessions;

    private List<Session> relatedSessions;

    private String selectedEvent;

    public SessionMBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public List<Session> getSessions() {
        if (this.sessions == null) {
            this.sessions = sessionBean.findSessionsWithSpeakers(this.event);
        }
        return this.sessions;
    }

    public List<Session> getRelatedSessions() {
        if (this.relatedSessions == null) {
            this.relatedSessions = sessionBean.findRelatedSessions(this.session);
        }
        return this.relatedSessions;
    }

    public String getSelectedEvent() {
        return this.selectedEvent;
    }

    public void setSelectedEvent(String selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public List<Event> getEvents() {
        if (this.events == null) {
            this.events = eventBean.findParentEvents();
        }
        return this.events;
    }

    public Session getPreviousSession() {
        return sessionBean.findPreviousSession(this.session);
    }

    public Session getNextSession() {
        return sessionBean.findNextSession(this.session);
    }

    @PostConstruct
    public void load() {
        if (this.eventId != null && !this.eventId.isEmpty()) {
            this.event = eventBean.findEvent(eventId);
            this.selectedEvent = this.event.getId();
        }

        if (this.id != null && !this.id.isEmpty()) {
            this.session = sessionBean.findSession(id);
            this.selectedEvent = this.session.getEvent().getId();
        } else {
            this.session = new Session();
        }
    }

    public String save() {
        Event evt = eventBean.findEvent(selectedEvent);
        this.session.setEvent(evt);

        sessionBean.save(this.session);
        return "sessions?faces-redirect=true&eventId=" + evt.getId();
    }

    public String remove() {
        sessionBean.remove(this.session.getId());
        return "sessions?faces-redirect=true&eventId=" + this.event.getId();
    }
}