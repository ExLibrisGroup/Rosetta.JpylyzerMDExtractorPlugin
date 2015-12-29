package com.exlibris.dps.repository.plugin.mdExtractor;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.exlibris.core.infra.common.exceptions.logging.ExLogger;
import com.exlibris.core.sdk.strings.StringUtils;
import com.exlibris.digitool.common.streams.ScriptUtil;
import com.exlibris.digitool.exceptions.ScriptException;
import com.exlibris.dps.sdk.techmd.MDExtractorPlugin;

public class JpylyzerExtractorPlugin implements MDExtractorPlugin {

		protected static final ExLogger log = ExLogger.getExLogger(JpylyzerExtractorPlugin.class, ExLogger.VALIDATIONSTACK);
		private static final String PLUGIN_VERSION_INIT_PARAM = "PLUGIN_VERSION_INIT_PARAM";
		private String pluginVersion = null;
		private JpylyzerSaxParser parser = new JpylyzerSaxParser();
		private String jpylyzerPath = null;
		private List<String> extractionErrors = new ArrayList<String>();
		protected List<String> args = new ArrayList<String>();



		public String runScript(String... additionalArgs) {

			for (String arg : additionalArgs) {
				args.add(arg);
			}

			String result = null ;
			try {
				result = ScriptUtil.runScript(jpylyzerPath, args);
			} catch (ScriptException e) {
				log.error("Execution of "+ jpylyzerPath + " failed : " + e.getMessage());
				extractionErrors.add(e.getMessage());
			}

			if (result != null) {
				log.info("Execution of Jpylyzer ended succesfuly.");
				log.info("Result: " + result);
			}

			return result;

		}


		@Override
		public void extract(String filePath) throws Exception {

			if(StringUtils.isEmptyString(jpylyzerPath)) {
				log.error("No Jpylyzer path defined. Please set the plugin parameter to hold your jpylyzer.py file path.");
				throw new Exception("path not found");
			}
			try {
				String result = runScript(filePath);
				if (result != null) {
					parser.parse(result);
				}
			} catch (IOException excep) {
				// OK IO error getting process output
				System.err.println("Jpylyzer problem for file: " + filePath);
				excep.printStackTrace();
				throw excep;
			}
		}

		public String getAgentName()
	    {
	    	return "JPYLYZER";
	    }

	    public String getAgent()
	    {
	    	return getAgentName() + " , Plugin Version " + pluginVersion;
	    }

	    public void initParams(Map<String, String> initParams) {
			this.pluginVersion = initParams.get(PLUGIN_VERSION_INIT_PARAM);
			if(StringUtils.notEmptyString(initParams.get("jpylyzerPath"))){
				jpylyzerPath = initParams.get("jpylyzerPath").trim();
			}
		}

		@Override
		public String getAttributeByName(String attribute) {

			return (String) parser.getAttribute(attribute);

		}

		@Override
		public List<String> getExtractionErrors() {
			return extractionErrors;
		}


		@Override
		public List<String> getSupportedAttributeNames() {

			List<String> attributes = new ArrayList<String>();
			attributes.add("Jpylyzer.FileTypeBox.BR");
			attributes.add("Jpylyzer.ContiguousCodestreamBox.Cod.MultipleComponentTransformation");
			attributes.add("Jpylyzer.ContiguousCodestreamBox.Cod.Levels");
			attributes.add("Jpylyzer.ContiguousCodestreamBox.Cod.Layers");
			attributes.add("Jpylyzer.ContiguousCodestreamBox.Cod.CodeBlockHeight");
			attributes.add("Jpylyzer.ContiguousCodestreamBox.Cod.CodeBlockWidth");
			attributes.add("Jpylyzer.ContiguousCodestreamBox.Cod.Order");
			attributes.add("Jpylyzer.ContiguousCodestreamBox.Cod.Transformation");
			attributes.add("Jpylyzer.ContiguousCodestreamBox.Cod.CodingBypass");
			attributes.add("Jpylyzer.ContiguousCodestreamBox.Cod.Precincts");
			attributes.add("Jpylyzer.ContiguousCodestreamBox.Com.Comment");
			attributes.add("Jpylyzer.ContiguousCodestreamBox.Siz.XSiz");
			attributes.add("Jpylyzer.ContiguousCodestreamBox.Siz.XTSiz");
			attributes.add("Jpylyzer.ContiguousCodestreamBox.Siz.YSiz");
			attributes.add("Jpylyzer.ContiguousCodestreamBox.Siz.YTSiz");
			attributes.add("Jpylyzer.ContiguousCodestreamBox.Siz.LSiz");
			attributes.add("Jpylyzer.ContiguousCodestreamBox.Siz.RSiz");
			attributes.add("Jpylyzer.ContiguousCodestreamBox.TileParts.TilePart.Sot.PSot");
			attributes.add("Jpylyzer.Jp2HeaderBox.ColourSpecificationBox.Approx");
			attributes.add("Jpylyzer.Jp2HeaderBox.ColourSpecificationBox.EnumCS");
			attributes.add("Jpylyzer.Jp2HeaderBox.ColourSpecificationBox.Meth");
			attributes.add("Jpylyzer.Jp2HeaderBox.ColourSpecificationBox.Prec");
			attributes.add("Jpylyzer.FileTypeBox.CL");
			attributes.add("Jpylyzer.FileTypeBox.MinV");
			attributes.add("Jpylyzer.CompressionRatio");

			return attributes;

		}

		@Override
		public boolean isWellFormed() {
			return true;
		}

		@Override
		public boolean isValid() {
			return parser.getValid();
		}
		@Override
		public String getFormatName() {
			return null;
		}

		@Override
		public String getFormatVersion() {
			return null;
		}

		@Override
		public Integer getImageCount() {
			return 0;
		}

		@Override
		public String getMimeType() {
			return null;
		}


	}