package solarEclipse.helper;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import solarEclipse.gui.R;
import android.content.Context;

public class GlossarParser {

	private static final int RESID = R.raw.glossar;
	
	private String wikilink;
	private Document xmlDocument;
	private int elements;
	private String[] content;
	private String[] keys;
	
	public String[] getKeyNames () {
		if( this.keys == null ) {
			Element element;
			this.keys = new String[this.elements];
			for (int i = 0 ; i < this.elements ; i++) {
				element = this.xmlDocument.getElementById(Integer.toString(i + 1));
				this.keys[i] = element.getAttribute("key");
			}			
		}
		return this.keys;
	}
	
	public GlossarParser(Context context) throws Exception {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = domFactory.newDocumentBuilder();
		this.xmlDocument = documentBuilder.parse(context.getResources().openRawResource(GlossarParser.RESID));
		this.elements = this.xmlDocument.getElementsByTagName("entry").getLength();
		this.content = new String[3];
	}

	public String[] getContentById(final Context context, final int value) {
		try {
			Element element = this.xmlDocument.getElementById(Integer.toString(value));
			this.content[0] = element.getAttribute("key");
			this.content[1] = element.getTextContent();
			if (element.getAttribute("wiki").length() > 0) {
				this.content[2] = element.getAttribute("wiki");
			} else {
				this.content[2] = null;
			}
			this.wikilink = content[2];
		} catch (Exception e) {
			this.content[0] = "Error";
			this.content[1] = context.getResources().getString(R.string.err_parseErr);
			this.content[2] = null;
		}
		return content;
	}
	
	public String getWikiLink() {
		return this.wikilink;
	}
	
	public boolean idHasPrev(int id) {
		if (id > 1) {
			return true;
		}
		return false;
	}
	
	public boolean idHasNext(int id) {
		if (id < this.elements) {
			return true;
		}
		return false;
	}
	
	public boolean idHasWikiLink(int id) {
		if (this.wikilink != null) {
			return true;
		}
		return false;
	}
	
	public String getWikipediaLink(int id) {
		return this.wikilink;
	}
}
