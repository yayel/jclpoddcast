/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jclpoddcast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author samuel
 */
public class Poddcast {
    
    String name;    
    String url;
    String fileName;
    
    ArrayList<Record> list;

    public Poddcast(String name, String url) {
        this.name = name;
        this.url = url;
        this.fileName = url.substring(url.lastIndexOf('/') + 1);
        list = new ArrayList<>();
    }
    
    
    public void downloadXML(){                   
        Download.wget(url,fileName);       
    }

    
    public void analyseXML(){
        //Analyse.scan(fileName);
        list = Analyse.add(fileName);
        System.out.println("list = " +list);
    }
    
    public void downloadList(){
        for (Record r : list){
            String filename = r.name.replaceAll("\\W+", "_");
            if (!filename.endsWith(".mp3"))
                filename += ".mp3";
            Download.wgetIfNotExistsInPath(r.URL,name,filename);
        }        
    }

    @Override
    public String toString() {
        return "Poddcast{" + "name=" + name + ", url=" + url + ", list=" + list + '}';
    }
    
    
}
