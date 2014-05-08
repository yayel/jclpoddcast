/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jclpoddcast;

/**
 *
 * @author samuel
 */
public class Record {
    
    String name;
    String URL;
    boolean isDownloaded = false;
    
    public Record(String name){
        this.name = name;
    }

    public Record(String name, String URL) {
        this.name = name;
        this.URL = URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
    
    

    @Override
    public String toString() {
        return "Record{" + "name=" + name + ", URL=" + URL + ", isDownloaded=" + isDownloaded + '}';
    }
    
    
}
