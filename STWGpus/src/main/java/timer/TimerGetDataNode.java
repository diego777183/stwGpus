/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package timer;

import java.util.Date;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author Diego
 */
@Stateless
public class TimerGetDataNode {
    private WebTarget webTarget;
    private static final String BASE_URI = "http://192.168.1.59:4000/";
    
    
    
    @Schedule(hour = "*", minute = "*/5", persistent = false)
    
    public void myTimer() {
        System.out.println("Timer event: " + new Date());
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
