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

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.cejug.yougi.event.business.AttendeeBean;
import org.cejug.yougi.event.business.EventBean;
import org.cejug.yougi.event.entity.Attendee;
import org.cejug.yougi.event.entity.Event;
import org.cejug.yougi.web.report.EventAttendeeReport;

/**
 * @author Hildeberto Mendonca - http://www.hildeberto.com
 */
@ManagedBean
@SessionScoped
public class AttendeeMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    static final Logger logger = Logger.getLogger(AttendeeMBean.class.getName());

    @EJB
    private EventBean eventBean;

    @EJB
    private AttendeeBean attendeeBean;

    private Event event;

    private List<Attendee> attendees;

    private Attendee[] selectedAttendees;

    private Long numberPeopleAttending;

    private Long numberPeopleAttended;

    public AttendeeMBean() {
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public Attendee[] getSelectedAttendees() {
        return selectedAttendees;
    }

    public void setSelectedAttendees(Attendee[] selectedAttendees) {
        this.selectedAttendees = selectedAttendees;
    }

    public Long getNumberPeopleAttending() {
        return numberPeopleAttending;
    }

    public void setNumberPeopleAttending(Long numberPeopleAttending) {
        this.numberPeopleAttending = numberPeopleAttending;
    }

    public void setNumberPeopleAttended(Long numberPeopleAttended) {
        this.numberPeopleAttended = numberPeopleAttended;
    }

    public Long getNumberPeopleAttended() {
        return numberPeopleAttended;
    }

    public String load(String eventId) {
        this.event = eventBean.findEvent(eventId);

        this.attendees = attendeeBean.findAttendees(this.event);
        List<Attendee> confirmedAttendees = attendeeBean.findConfirmedAttendees(event);
        if (confirmedAttendees != null) {
            this.selectedAttendees = new Attendee[confirmedAttendees.size()];
            int i = 0;
            for (Attendee atd : confirmedAttendees) {
                this.selectedAttendees[i++] = atd;
            }
        }

        this.numberPeopleAttending = attendeeBean.findNumberPeopleAttending(this.event);
        this.numberPeopleAttended = attendeeBean.findNumberPeopleAttended(this.event);

        return "attendees?faces-redirect=true";
    }

    public String confirmMembersAttended() {
        attendeeBean.confirmMembersAttendance(this.event, this.selectedAttendees);
        removeSessionScoped();
        return "events?faces-redirect=true";
    }

    private void removeSessionScoped() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("partnerBean");
    }

    /**
     * Generates a PDF with the list of registered members in the event.
     */
    public void print() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline=filename=file.pdf");

        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();

            EventAttendeeReport eventAttendeeReport = new EventAttendeeReport(document);
            eventAttendeeReport.printReport(this.attendees);

            document.close();

            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            context.responseComplete();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (DocumentException de) {
            logger.log(Level.SEVERE, de.getMessage(), de);
        }
    }
}