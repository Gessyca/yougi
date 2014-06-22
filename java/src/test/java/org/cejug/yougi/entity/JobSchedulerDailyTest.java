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
package org.cejug.yougi.entity;

import org.cejug.yougi.exception.BusinessLogicException;
import org.junit.Test;

/**
 * @author [name of the person who wrote this test] - [github profile url or personal website]
 */
public class JobSchedulerDailyTest {

    @Test
    public void testGetNextJobExecution() throws Exception {
        JobScheduler jobScheduler = new JobSchedulerDaily();
        jobScheduler.setActive(true);
        jobScheduler.setDefaultOwner(new UserAccount("Hildeberto", "Mendonca", "me@hildeberto.com"));
        jobScheduler.setDescription("test");
        jobScheduler.setFrequency(1);
    }

    @Test(expected = BusinessLogicException.class)
    public void testGetNextJobExecutionException() throws Exception {
        JobScheduler jobScheduler = new JobSchedulerDaily();
        jobScheduler.setActive(true);
    }
}
