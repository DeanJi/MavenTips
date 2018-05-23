package test;


import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;

/**
 * 
 * weblogic密码忘记破解方法
 * 获取到weblogic安装目录下的两个文件 SerializedSystemIni.dat、 boot.properties
 * (welogic10 SerializedSystemIni.dat在bea/user_projects/domains/base_domain/security中)
 * (welogic10 boot.properties在bea/user_projects/domains/base_domain/servers/AdminServer/security中)
 * 加入weblogic.jar （weblogic安装目录中寻找，不同版本有可能不同）文件添加至构建路径，
 * welogic10如果运行还缺少别的类可以把weblogic的/wlserver_10.3/server/lib下的jar都添加到构建路径
 * @author
 *
 */
public class WebLogicDecryptor {
 
    private static final String PREFIX = "{AES}";//查看boot.properties文件 加密方式{3DES}或者{AES}
    private static final String XPATH_EXPRESSION
        = "//node()[starts-with(text(), '" + PREFIX + "')] | //@*[starts-with(., '" + PREFIX + "')]";
 
    private static ClearOrEncryptedService ces;
 
    public static void main(String[] args) throws Exception {
      
    	//D:/Users/JIJINDING001/workspace/NewCore/yxd_domain/security 中的SerializedSystemIni.dat存放目录
    	//加密和解密必须用同一个SerializedSystemIni.dat，如果想解密服务器上的密文，则需使用服务器上的SerializedSystemIni.dat
        ces = new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService(new File("D:/Users/JIJINDING001/workspace/NewCore/yxd_domain/security").getAbsolutePath()));
        File file = new File("D:/Users/JIJINDING001/workspace/NewCore/yxd_domain/servers/AdminServer/security/boot-L0073772.properties");
        if (file.getName().endsWith(".xml")) {//有些可能是xml文件来的？
            processXml(file);
        }
        else if (file.getName().endsWith(".properties")){
            processProperties(file);
        }
    }
 
    private static void processXml(File file) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        XPathExpression expr = XPathFactory.newInstance().newXPath().compile(XPATH_EXPRESSION);
        NodeList nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            print(node.getNodeName(), node.getTextContent());
        }
    }
 
    private static void processProperties(File file) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream(file));
        for (Map.Entry p : properties.entrySet()) {
            if (p.getValue().toString().startsWith(PREFIX)) {
                print(p.getKey(), p.getValue());
            }
        }
    }
 
    private static void print(Object attributeName, Object encrypted) {
        System.out.println("Node name: " + attributeName);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + ces.decrypt((String)encrypted) + "\n");
    }
}
