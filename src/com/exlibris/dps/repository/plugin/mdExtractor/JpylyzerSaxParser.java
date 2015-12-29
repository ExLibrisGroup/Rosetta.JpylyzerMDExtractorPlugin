package com.exlibris.dps.repository.plugin.mdExtractor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


public class JpylyzerSaxParser extends DefaultHandler{
	private String currentElementVal;
	private boolean valid = false;
	Map<String, String> attributes = new HashMap<String, String>();;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		// Just reset the current element var.
		this.currentElementVal = "";
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		if (qName.equals("br")) {
			attributes.put("Jpylyzer.FileTypeBox.BR", this.currentElementVal.trim());
		} else if (qName.equals("multipleComponentTransformation")) {
			attributes.put("Jpylyzer.ContiguousCodestreamBox.Cod.MultipleComponentTransformation", this.currentElementVal.trim());
		} else if (qName.equals("levels")) {
			attributes.put("Jpylyzer.ContiguousCodestreamBox.Cod.Levels", this.currentElementVal.trim());
		} else if (qName.equals("layers")) {
			attributes.put("Jpylyzer.ContiguousCodestreamBox.Cod.Layers", this.currentElementVal.trim());
		} else if (qName.equals("comment")) {
			attributes.put("Jpylyzer.ContiguousCodestreamBox.Com.Comment", this.currentElementVal.trim());
		} else if (qName.equals("xsiz")) {
			attributes.put("Jpylyzer.ContiguousCodestreamBox.Siz.XSiz", this.currentElementVal.trim());
		} else if (qName.equals("xTsiz")) {
			attributes.put("Jpylyzer.ContiguousCodestreamBox.Siz.XTSiz", this.currentElementVal.trim());
		} else if (qName.equals("ysiz")) {
			attributes.put("Jpylyzer.ContiguousCodestreamBox.Siz.YSiz", this.currentElementVal.trim());
		} else if (qName.equals("yTsiz")) {
			attributes.put("Jpylyzer.ContiguousCodestreamBox.Siz.YTSiz", this.currentElementVal.trim());
		} else if (qName.equals("lsiz")) {
			attributes.put("Jpylyzer.ContiguousCodestreamBox.Siz.LSiz", this.currentElementVal.trim());
		} else if (qName.equals("rsiz")) {
			attributes.put("Jpylyzer.ContiguousCodestreamBox.Siz.RSiz", this.currentElementVal.trim());
		} else if (qName.equals("psot")) {
			attributes.put("Jpylyzer.ContiguousCodestreamBox.TileParts.TilePart.Sot.PSot", this.currentElementVal.trim());
		} else if (qName.equals("approx")) {
			attributes.put("Jpylyzer.Jp2HeaderBox.ColourSpecificationBox.Approx", this.currentElementVal.trim());
		} else if (qName.equals("enumCS")) {
			attributes.put("Jpylyzer.Jp2HeaderBox.ColourSpecificationBox.EnumCS", this.currentElementVal.trim());
		} else if (qName.equals("meth")) {
			attributes.put("Jpylyzer.Jp2HeaderBox.ColourSpecificationBox.Meth", this.currentElementVal.trim());
		} else if (qName.equals("prec")) {
			attributes.put("Jpylyzer.Jp2HeaderBox.ColourSpecificationBox.Prec", this.currentElementVal.trim());
		} else if (qName.equals("cL")) {
			attributes.put("Jpylyzer.FileTypeBox.CL", this.currentElementVal.trim());
		} else if (qName.equals("minV")) {
			attributes.put("Jpylyzer.FileTypeBox.MinV", this.currentElementVal.trim());
		} else if (qName.equals("compressionRatio")) {
			attributes.put("Jpylyzer.CompressionRatio", this.currentElementVal.trim());
		} else if (qName.equals("codeBlockHeight")) {
			attributes.put("Jpylyzer.ContiguousCodestreamBox.Cod.CodeBlockHeight", this.currentElementVal.trim());
		} else if (qName.equals("codeBlockWidth")) {
			attributes.put("Jpylyzer.ContiguousCodestreamBox.Cod.CodeBlockWidth", this.currentElementVal.trim());
		} else if (qName.equals("order")) {
			attributes.put("Jpylyzer.ContiguousCodestreamBox.Cod.Order", this.currentElementVal.trim());
		} else if (qName.equals("transformation")) {
			attributes.put("Jpylyzer.ContiguousCodestreamBox.Cod.Transformation", this.currentElementVal.trim());
		} else if (qName.equals("codingBypass")) {
			attributes.put("Jpylyzer.ContiguousCodestreamBox.Cod.CodingBypass", this.currentElementVal.trim());
		} else if (qName.equals("precincts")) {
			attributes.put("Jpylyzer.ContiguousCodestreamBox.Cod.Precincts", this.currentElementVal.trim());
		} else if (qName.equals("isValidJP2")) {
			this.setValid(Boolean.valueOf(this.currentElementVal.trim()));
		}

	}

	// Save the current element value
	@Override
	public void characters(char[] ch, int start, int length) {
		this.currentElementVal += new String(ch, start, length);
	}

	/**
	 *
	 * @param xml- the xml itself in a string var
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void parse(String xml) throws ParserConfigurationException, SAXException, IOException {

		SAXParserFactory spfac = SAXParserFactory.newInstance();
        SAXParser sp = spfac.newSAXParser();
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        try {
        	//Finally, tell the parser to parse the input and notify the handler
        	sp.parse(is, this);
        }finally {
        	is.close();
        }

	}

	public String getAttribute(String att) {
		return attributes.get(att);
	}


	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

}
