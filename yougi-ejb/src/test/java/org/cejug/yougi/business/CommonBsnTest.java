/* Yougi is a web application conceived to manage user groups or
 * communities focused on a certain domain of knowledge, whose members are
 * constantly sharing information and participating in social and educational
 * events. Copyright (C) 2011 Ceara Java User Group - CEJUG.
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
 *
 */
package org.cejug.yougi.business;

import org.cejug.yougi.AllTests;
import org.cejug.yougi.entity.AccessGroup;
import org.cejug.yougi.entity.Country;
import org.cejug.yougi.entity.Language;
import org.cejug.yougi.entity.MessageTemplate;
import org.junit.Before;
import org.junit.Test;


/**
 * Test class CommonBsnTest.
 *
 * @author Helio Frota - http://www.heliofrota.com
 */
public class CommonBsnTest {

    private static CommonBean commonBsn;

    /**
     * Lookup for the CommonBean from embedded server.
     * @throws Exception exception
     */
    @Before
    public void setUp() throws Exception {
        commonBsn = (CommonBean) AllTests.ejbContainer.getContext().lookup("java:global/classes/CommonBean");
    }


    @Test
    public void findAllOrderedBy() {

        // The method findAccessGroups() can be removed.
        System.out.println(commonBsn.findAllOrderedBy(AccessGroup.class, "name", true));

        // The method findLanguages() can be removed.
        System.out.println(commonBsn.findAllOrderedBy(Language.class, "name", true));

        // The method findCountries() can be removed.
        System.out.println(commonBsn.findAllOrderedBy(Country.class, "name", true));

        // The method findMessageTemplates() can be removed.
        System.out.println(commonBsn.findAllOrderedBy(MessageTemplate.class, "title", true));

    }

}
