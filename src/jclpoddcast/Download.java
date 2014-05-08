/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jclpoddcast;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;

/**
 *
 * @author samuel
 */
public class Download {

    private static final Logger LOGGER = Logger.getLogger(JCLPoddcast.class.getName());
    

    //creates a local file
    /**
     * Writes a String to a local file
     *
     * @param outfile the file to write to
     * @param content the contents of the file
     * @exception IOException
     */
    public static void createAFile(String outfile, String content) throws IOException {
        FileOutputStream fileoutputstream = new FileOutputStream(outfile);
        DataOutputStream dataoutputstream = new DataOutputStream(fileoutputstream);
        dataoutputstream.writeBytes(content);
        dataoutputstream.flush();
        dataoutputstream.close();
    }

    /**
     * Download a file from web
     * @param url the url of the file
     * @param fileName local name
     */
    public static void wget(String url, String fileName) {

        InputStream in;
        OutputStream out;
        try {
            in = new URL(url).openStream();
            out = new BufferedOutputStream(new FileOutputStream(fileName));
            byte[] buf = new byte[1024];
            int n;
            while ((n = in.read(buf, 0, buf.length)) > 0) {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();

        } catch (MalformedURLException ex) {
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex){
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /**
     *
     * 
     * @param url
     * @param path
     * @param fileName
     */
    public static void wgetInPath(String url, String path, String fileName){
        wget(url, path+"/"+fileName);
    }
    

    
    /**
     * Download a file from the web. The name of the local file is 
     * deduced from the url.
     * 
     * @param theUrl
     */
    public static void wget(String theUrl) {
        String fileName = theUrl.substring(theUrl.lastIndexOf('/') + 1);
        wget(theUrl, fileName);

    }
    
    /**
     * Download a file from the web only if the file does not exists. The name of the local file is 
     * deduced from the url.
     * @param theUrl
     */
    public static void wgetIfNotExists(String theUrl){
       String fileName = theUrl.substring(theUrl.lastIndexOf('/') + 1);
       wgetIfNotExists(theUrl,fileName);
        
    }
    
    /**
     * Download a file from the web only if the file does not exists.
     * @param theUrl
     * @param fileName
     */
    public static void wgetIfNotExists(String theUrl,  String fileName){        
        File f = new File(fileName);
        if (!f.exists()) {
            wget(theUrl, fileName);
        }
    }

    static void wgetIfNotExistsInPath(String url, String path, String fileName) {

        File theDir = new File(path);

        // if the directory does not exist, create it
        if (!theDir.exists()) {

            Logger.getLogger("creating directory: " + path);
            boolean result = theDir.mkdir();

            if (result) {
                Logger.getLogger("dir created");
            }
        }

        wgetIfNotExists(url, path + "/" + fileName);
    }




}
