package ar.com.todopago.api.operations;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PaymentMethodsParser {

	// From InputStream to NodeList
	public static NodeList inputStreamToNodeList(InputStream inputStream)
			throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputStream);
		doc.getDocumentElement().normalize();
		return doc.getChildNodes();
	}

	public Map<String, Object> streamToMap(InputStream inputStream)
			throws ParserConfigurationException, SAXException, IOException {

		NodeList nList = inputStreamToNodeList(inputStream);
		Pair p = parseToMap(nList);
		return p.getMap();
	}

	public Pair parseToMap(NodeList nList) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String keyVal = "";
		for (int i = 0; i < nList.getLength(); i++) {
			if (nList.item(i).hasChildNodes() && nList.item(i).getChildNodes().item(0).hasChildNodes()) {
				// Go to child
				Pair p = parseToMap(nList.item(i).getChildNodes());

				if (!p.getKey().equalsIgnoreCase("")) {
					ret.put(p.getKey(), p.getMap());
				} else {
					ret.put(nList.item(i).getNodeName(), p.getMap());
				}

			} else {
				// add value to this map
				if (nList.item(i).getNodeName().contains("Id") || nList.item(i).getNodeName().contains("ID")) {
					// Keep value
					keyVal = nList.item(i).getTextContent();
				}
				ret.put(nList.item(i).getNodeName(), nList.item(i).getTextContent());
			}
		}
		Pair aux = new Pair(keyVal, ret);
		return aux;
	}

	class Pair {
		private String key;
		private Map<String, Object> map;

		public Pair(String k, Map<String, Object> m) {
			key = k;
			map = m;
		}

		public String getKey() {
			return key;
		}

		public Map<String, Object> getMap() {
			return map;
		}

	}

}
