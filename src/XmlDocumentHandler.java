import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.IOException;
import java.io.StringReader;

public class XmlDocumentHandler {

    public XmlDocumentHandler() {
    }

    /**
     * Creates a xml document
     *
     * @param name     - name of sender
     * @param email    - email of sender
     * @param homepage - sender homepage
     * @param body     - body
     * @return return the XML document
     */
    public Document createXmlChatMessageDocument(String name, String email, String homepage, String body) {
        Element messageElement = new Element("message");
        Element headerElement = new Element("header");
        Element protocolElement = new Element("protocol");
        Element typeElement = new Element("type");
        Element versionElement = new Element("version");
        Element commandElement = new Element("command");
        Element idElement = new Element("id");
        Element nameElement = new Element("name");
        Element emailElement = new Element("email");
        Element homepageElement = new Element("homepage");
        Element hostElement = new Element("host");
        Element bodyElement = new Element("body");

        //Sets endnode element values
        typeElement.addContent("CTTP");
        versionElement.addContent("1.0");
        commandElement.addContent("MESS");
        hostElement.addContent("unknown");
        nameElement.addContent(name);
        emailElement.addContent(email);
        homepageElement.addContent(homepage);
        bodyElement.addContent(body);

        //combine elments
        messageElement.addContent(headerElement);
        messageElement.addContent(bodyElement);
        headerElement.addContent(protocolElement);
        headerElement.addContent(idElement);
        protocolElement.addContent(typeElement);
        protocolElement.addContent(versionElement);
        protocolElement.addContent(commandElement);
        idElement.addContent(nameElement);
        idElement.addContent(emailElement);
        idElement.addContent(homepageElement);
        idElement.addContent(hostElement);

        DocType docType = new DocType("message", "1//PW//Example//123", "https://atlas.dsv.su.se/~pierre/i/05_ass/ip1/2/2.1.3/message.dtd");
        Document doc = new Document(messageElement, docType);

        return doc;
    }

    /**
     * Converts an XMLDocument to a string
     * @param doc
     * @return
     */
    public String convertToString(Document doc) {
        Format format = Format.getCompactFormat();
        format.setLineSeparator("");
        XMLOutputter xmlOutputter = new XMLOutputter(format);

        return xmlOutputter.outputString(doc);
    }

    /**
     * Prints a XML document to the console in pretty format
     * @param doc - document to print
     */
    public void printXmlDoc(Document doc){

        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        try {
            System.out.println("Sending XML:");
            xmlOutputter.output(doc, System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse out the message from an xmlString and print the XML document to the console
     * @param xmlString - the xmlString to get the message from
     * @param attribute - Which atttribut to read the message from
     * @return returns the text message found in the attribute specified by the attribute parameter
     */
    public String parseXML(String xmlString, String attribute){
        SAXBuilder saxBuilder = new SAXBuilder(XMLReaders.DTDVALIDATING);
        Document doc = null;
        try {
            doc = saxBuilder.build(new StringReader(xmlString));
            System.out.println("Recived XML:");
            printXmlDoc(doc);
            Element root = doc.getRootElement();
            return root.getChildText(attribute);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";


    }

}
