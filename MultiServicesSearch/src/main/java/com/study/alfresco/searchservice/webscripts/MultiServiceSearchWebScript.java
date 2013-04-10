package com.study.alfresco.searchservice.webscripts;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class MultiServiceSearchWebScript extends DeclarativeWebScript {

	private Map<String, List<String>> linksBlueprint;
	private static final Log LOG = LogFactory
			.getLog(MultiServiceSearchWebScript.class);

	private static final String SEARCH_KEY = "searchkey";
	private static final String ERROR_RESULT = "errorResult";

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req,
			Status status, Cache cache) {

		Map<String, Object> result = new HashMap<String, Object>();
		JSONObject filter = null;
		JSONObject params = null;
		String searchKey = null;

		try {
			filter = new JSONObject(req.getParameter("filter"));
			searchKey = filter.getString(SEARCH_KEY);
			params = filter.getJSONObject("parameters");
		} catch (JSONException e) {
			LOG.warn("Exception: " + e.getMessage());
			result.put(ERROR_RESULT,
					"Input not correct or search key is missing!");
		}

		String[] keysParam = JSONObject.getNames(params);
		Map<String, String> parameters = new HashMap<String, String>();

		for (String keyParam : keysParam) {
			try {
				parameters.put(keyParam, params.getString(keyParam));
			} catch (JSONException e) {
				LOG.warn("Exception: " + e.getMessage());
			}
		}

		for (String key : parameters.keySet()) {
			result.put(key, parameters.get(key));
		}

		if (linksBlueprint.containsKey(searchKey)) {
			List<String> templateList = linksBlueprint.get(searchKey);
			List<String> suggestLinks = new ArrayList<String>();

			for (String temp : templateList) {
				try {
					suggestLinks.add(process(result, temp));
				} catch (TemplateException e) {

					e.printStackTrace();
				}
			}

			result.clear();
			result.put("links", suggestLinks);
		} else {
			result.put(ERROR_RESULT, "This search is not defined!");
		}

		return result;
	}

	public static String process(Map<String, Object> model, String template)
			throws TemplateException {
		try {
			Template tmpl = new Template("links", new StringReader(template),
					new Configuration());
			Writer out = new StringWriter();
			tmpl.process(model, out);
			return out.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	public Map<String, List<String>> getLinksBlueprint() {
		return linksBlueprint;
	}

	public void setLinksBlueprint(Map<String, List<String>> linksBlueprint) {
		this.linksBlueprint = linksBlueprint;
	}
}
