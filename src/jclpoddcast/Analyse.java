/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jclpoddcast;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.io.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author samuel
 */
public class Analyse {

    // for logging
    private static final Logger LOGGER = Logger.getLogger(Analyse.class.getName());

    private static final ArrayList<Poddcast> poddcastList = new ArrayList<>();
    
    private static ArrayList<Record> temp;
    
    public static void addNode(Node node) {

        //System.out.println("Type de noeud : "+node.getNodeType() + node);                                    
        if (node.getNodeType() == Node.TEXT_NODE) {
            if (node.getParentNode().getNodeName().equals("title")) {
                String title = node.getTextContent();
                System.out.println("création d'un titre " + title);
                temp.add(new Record(title));
                //Download.wgetIfNotExists(fileName);
            }

            if (node.getParentNode().getNodeName().equals("guid")) {
                String fileName = node.getTextContent();
                System.out.println("ajout de l'URL "+fileName);
                temp.get(temp.size()-1).setURL(fileName);
                //Download.wgetIfNotExists(fileName);
            }
            
        }

        NodeList nodes = node.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            addNode(n);
        }
    }

    public static void printNode(Node node) {

        //System.out.println("Type de noeud : "+node.getNodeType());                                    
        if (node.getNodeType() == Node.TEXT_NODE) {
            if (node.getParentNode().getNodeName().equals("guid")) {
                String fileName = node.getTextContent();
                System.out.println(fileName);
                Download.wgetIfNotExists(fileName);
            }
        }

        NodeList nodes = node.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            printNode(n);
        }
    }

    public static void printDocument(Document document) {

        Element racine = document.getDocumentElement();
        printNode(racine);

    }

    public static void scan(String s) {

        LOGGER.log(Level.INFO, "file : {0}", s);

        try {
            // création d'une fabrique de documents                                                     
            DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();

            // création d'un constructeur de documents                                                  
            DocumentBuilder constructeur = fabrique.newDocumentBuilder();

            // lecture du contenu d'un fichier XML avec DOM                                             
            File xml = new File(s);
            Document document = constructeur.parse(xml);

            //traitement du document                                                                    
            //voir ExempleDOM.zip                                                                       
            printNode(document);

        } catch (ParserConfigurationException pce) {
            LOGGER.severe("DOM configuration Error");
            System.out.println("Erreur de configuration du parseur DOM");
            System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
        } catch (SAXException se) {
            LOGGER.severe("Error while parsing document");
            System.out.println("Erreur lors du parsing du document");
            System.out.println("lors de l'appel à construteur.parse(xml)");
        } catch (IOException ioe) {
            LOGGER.severe("Input/output error");
            System.out.println("Erreur d'entrée/sortie");
            System.out.println("lors de l'appel à construteur.parse(xml)");
        }

    }

    static void constructList(String podcastListName) {

        String name = null;
        String url = null;

        try {
            Scanner sc = new Scanner(new File(podcastListName));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                // if it's the url 
                if (line.startsWith("http://")) {
                    url = line;
                } else if (!line.startsWith("#")) {
                    // if it's not a comment
                    name = line;
                }

                /* if a name an a poddcast were found 
                 construct a new poddcast
                 */
                if (name != null && url != null) {
                    Poddcast p = new Poddcast(name, url);
                    poddcastList.add(p);
                    name = null;
                    url = null;
                }

            //System.out.println(line);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Analyse.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(poddcastList);
        for (Poddcast p : poddcastList) {
            p.downloadXML();
            p.analyseXML();
            p.downloadList();
        }
    }

    static ArrayList<Record> add(String fileName) {
               LOGGER.log(Level.INFO, "file : {0}", fileName);
               temp = new ArrayList<>();

        try {
            // création d'une fabrique de documents                                                     
            DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();

            // création d'un constructeur de documents                                                  
            DocumentBuilder constructeur = fabrique.newDocumentBuilder();

            // lecture du contenu d'un fichier XML avec DOM                                             
            File xml = new File(fileName);
            Document document = constructeur.parse(xml);

            //traitement du document                                                                    
            //voir ExempleDOM.zip                                                                       
            addNode(document);
            return temp;

        } catch (ParserConfigurationException pce) {
            LOGGER.severe("DOM configuration Error");
            System.out.println("Erreur de configuration du parseur DOM");
            System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
        } catch (SAXException se) {
            LOGGER.severe("Error while parsing document");
            System.out.println("Erreur lors du parsing du document");
            System.out.println("lors de l'appel à construteur.parse(xml)");
        } catch (IOException ioe) {
            LOGGER.severe("Input/output error");
            System.out.println("Erreur d'entrée/sortie");
            System.out.println("lors de l'appel à construteur.parse(xml)");
        } 
        return null;
    }
}
