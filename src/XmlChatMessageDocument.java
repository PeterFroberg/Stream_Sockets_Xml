import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XmlChatMessageDocument {

    public XmlChatMessageDocument(){
    }

    /**
     * Creates a xml document
     * @param name - name of sender
     * @param email - email of sender
     * @param homepage - sender homepage
     * @param body - body
     * @return return the XML document
     */
    public Document  createXmlChatMessageDocument(String name, String email, String homepage, String body){
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

        DocType docType = new DocType("message","1//PW//Example//123","https://atlas.dsv.su.se/~pierre/i/05_ass/ip1/2/2.1.3/message.dtd");
        return new Document(messageElement, docType);
    }

    public String convertToString(Document doc){
        Format format = Format.getCompactFormat();
        format.setLineSeparator("");
        XMLOutputter xmlOutputter = new XMLOutputter(format);

        return xmlOutputter.outputString(doc);
    }

}
