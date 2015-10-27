package starwarsrunner;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 * Created by slawek on 2015-10-25.
 */
public class Menu extends JFrame {
    private JButton STARTButton;
    private JPanel root;
    private JTabbedPane tabbedPane1;
    private JComboBox playerBox;
    private JTextField textField1;
    private JButton addPlayerButton;
    private JComboBox mapBox;

    public volatile boolean start = false;

    private String map;
    public String player;

    public Menu() {
        super("Star Wars Runners");
        setContentPane(root);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addPlayers();
        this.addMaps();

        STARTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start = true;
                map = mapBox.getSelectedItem().toString();
                player = playerBox.getSelectedItem().toString();
                setVisible(false);
            }
        });



        setVisible(true);
    }

    private void addPlayers() {
        try {
            File fXmlFile = new File("players.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("player");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    playerBox.addItem(eElement.getAttribute("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMaps() {
        File[] files = new File(".").listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().contains(".tmx")) {
                mapBox.addItem(file.getName());
            }
        }
    }
}
