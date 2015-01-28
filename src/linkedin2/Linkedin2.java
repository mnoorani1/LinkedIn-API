/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package linkedin2;

import java.io.IOException;

/**
 *
 * @author Moiz
 */
public class Linkedin2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        LinkedInConnector con = new LinkedInConnector();
        //con.getAccToken();
        //con.getProfile();
        //con.postStatus();
        //con.postP();
        //con.postC();
        con.getTopPost();
    }
    
}
