/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jclpoddcast;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samuel
 */
public class JCLPoddcast{
       
        // for logging
    private static final Logger LOGGER = Logger.getLogger(JCLPoddcast.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        
        LOGGER.log(Level.INFO, "Application starts");
        Analyse.constructList("MyPodcastList.txt");
        
        //Analyse.scan("/tmp/rss_11464.modif.xml");
        //Analyse.scan("rss_11464.xml");
        LOGGER.log(Level.INFO, "Application end");
    }
    
}
