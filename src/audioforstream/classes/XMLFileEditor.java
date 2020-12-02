/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioforstream.classes;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.blinkenlights.jid3.ID3Exception;
//import org.farng.mp3.TagException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Polack
 */
public class XMLFileEditor {
    
    public void openXMLFile(String xmlFilePath, SongTableModel stm, String nodeName) throws ParserConfigurationException, SAXException, IOException{
        
        File fileXML = new File(xmlFilePath);
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document xml = builder.parse(fileXML);
        
        NodeList songNodeList = xml.getElementsByTagName(nodeName);
        
        for (int row = 0; row < songNodeList.getLength(); row++) {
            
            Node songNode = songNodeList.item(row);
            if (songNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) songNode;
                
                Object tab[] = null;
                try {
                    tab = new MP3FileEditor().getMP3FileInfo(eElement.getAttribute("music_path"));
                } catch (ID3Exception ex) {
                    Logger.getLogger(XMLFileEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
                
//                Object tab[] = getRow(eElement.getAttribute(attrName[0]),
//                                eElement.getAttribute(attrName[1]),
//                                eElement.getAttribute(attrName[2]),
//                                eElement.getAttribute(attrName[3]),
//                                eElement.getAttribute(attrName[4]));
                 
                 stm.addRow(tab);
                
            }
        }
    }
    
    
    public void writeXMLFile(String fileName, int nbrows, int nbcols, Object[][] tab, String nodeName) {
        
        String xmlFilePath = "xml/" + fileName + ".xml";
        
        try {
            
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            
            Element root = document.createElement("List");
            document.appendChild(root);
            
            for(int row = 0; row < nbrows; row++){
                
                Element song = document.createElement(nodeName);
                root.appendChild(song);
                
//                Attr title = document.createAttribute(attrName[0]);
//                title.setValue((tab[row][0]).toString());
//                song.setAttributeNode(title);
//                System.out.println(attrName[0]);
//                System.out.println("   " + (tab[row][0]).toString());
//
//                Attr artist = document.createAttribute(attrName[1]);
//                artist.setValue((tab[row][1]).toString());
//                song.setAttributeNode(artist);
//                System.out.println(attrName[1]);
//                System.out.println("   " + (tab[row][1]).toString());
//
//                Attr album = document.createAttribute(attrName[2]);
//                album.setValue((tab[row][2]).toString());
//                song.setAttributeNode(album);
//                System.out.println(attrName[2]);
//                System.out.println("   " + (tab[row][2]).toString());
//
//                Attr art = document.createAttribute(attrName[3]);
//                art.setValue((tab[row][4]).toString());
//                song.setAttributeNode(art);
//                System.out.println(attrName[3]);
//                System.out.println("   " + (tab[row][4]).toString());

                Attr musicPath = document.createAttribute("music_path");
                musicPath.setValue((tab[row][5]).toString());
                song.setAttributeNode(musicPath);
                System.out.println("music_path");
                System.out.println("   " + (tab[row][5]).toString());
                
                
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));
            
            transformer.transform(domSource, streamResult);
            System.out.println("Done creating XML File");
            
 
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
        
//        System.out.println(path);
//        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder documentBuilder = null;
//        try {
//            documentBuilder = documentFactory.newDocumentBuilder();
//        } catch (ParserConfigurationException ex) {
//            Logger.getLogger(XMLFileEditor.class.getName()).log(Level.SEVERE, null, ex);
//        }
//       
//        Document document = documentBuilder.newDocument();
//        Element root = document.createElement("List");
//        
////        Attr name = document.createAttribute("name");
////        name.setValue(fileName);
////        root.setAttributeNode(name);
//        
//        document.appendChild(root);
//        
//        for(int row = 0; row < nbrows; row++){
//            Element song = document.createElement(nodeName);
////            for(int col = 0; col < nbcols; col++){
////                System.out.println(attrName[col] +": ");
////                Attr attr = document.createAttribute(attrName[col]);
////                System.out.println("    " + (tab[row][((col >= 3)? (col+1) : col)]).toString());
////                attr.setValue((tab[row][((col >= 3)? (col+1) : col)]).toString());
////                song.setAttributeNode(attr);
////            }
//            Attr title = document.createAttribute(attrName[0]);
//            title.setValue((tab[row][0]).toString());
//            song.setAttributeNode(title);
//            System.out.println(attrName[0]);
//            System.out.println("   " + (tab[row][0]).toString());
//            
//            Attr artist = document.createAttribute(attrName[1]);
//            artist.setValue((tab[row][1]).toString());
//            song.setAttributeNode(artist);
//            System.out.println(attrName[1]);
//            System.out.println("   " + (tab[row][1]).toString());
//            
//            Attr album = document.createAttribute(attrName[2]);
//            album.setValue((tab[row][2]).toString());
//            song.setAttributeNode(album);
//            System.out.println(attrName[2]);
//            System.out.println("   " + (tab[row][2]).toString());
//            
//            Attr art = document.createAttribute(attrName[3]);
//            art.setValue((tab[row][4]).toString());
//            song.setAttributeNode(art);
//            System.out.println(attrName[3]);
//            System.out.println("   " + (tab[row][4]).toString());
//            
//            Attr musicPath = document.createAttribute(attrName[4]);
//            musicPath.setValue((tab[row][5]).toString());
//            song.setAttributeNode(musicPath);
//            System.out.println(attrName[4]);
//            System.out.println("   " + (tab[row][5]).toString());
//            
//            root.appendChild(song);
//        }
//        
//        TransformerFactory transformerFactory = TransformerFactory.newInstance();
//        Transformer transformer = null;
//        try {
//            transformer = transformerFactory.newTransformer();
//        } catch (TransformerConfigurationException ex) {
//            Logger.getLogger(XMLFileEditor.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        DOMSource domSource = new DOMSource(document);
//        StreamResult streamResult = new StreamResult(new File(path));
//        
//        try {
//            transformer.transform(domSource, streamResult);
//        } catch (TransformerException ex) {
//            Logger.getLogger(XMLFileEditor.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//	System.out.println("File saved!");
        
    }
    
    private Object[] getRow(String title, String artist, String album, String art, String path) throws IOException{
        
        Object[] out = new Object[6];
        
        out[0] = title;
        out[1] = artist;
        out[2] = album;
        Image img = (ImageIO.read(new File(art))).getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(img);
        out[3] = icon;
        out[4] = art;
        out[5] = path;
        
        return out;
        
        
    }
    
}
