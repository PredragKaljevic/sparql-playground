package swiss.sib.sparql.playground.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

import javax.activation.MimeType;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.eclipse.rdf4j.common.lang.FileFormat;
import org.eclipse.rdf4j.common.lang.service.FileFormatServiceRegistry;
import org.eclipse.rdf4j.query.QueryEvaluationException;
import org.eclipse.rdf4j.query.QueryInterruptedException;
import org.eclipse.rdf4j.query.QueryResults;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.query.TupleQueryResultHandlerException;
import org.eclipse.rdf4j.query.resultio.BooleanQueryResultWriterRegistry;
import org.eclipse.rdf4j.query.resultio.TupleQueryResultWriter;
import org.eclipse.rdf4j.query.resultio.TupleQueryResultWriterFactory;
import org.eclipse.rdf4j.query.resultio.TupleQueryResultWriterRegistry;
import org.eclipse.rdf4j.query.resultio.sparqljson.SPARQLResultsJSONWriterFactory;
import org.eclipse.rdf4j.query.resultio.sparqlxml.SPARQLResultsXMLWriterFactory;
import org.eclipse.rdf4j.query.resultio.text.csv.SPARQLResultsCSVWriterFactory;
import org.eclipse.rdf4j.query.resultio.text.tsv.SPARQLResultsTSVWriterFactory;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.repository.config.RepositoryConfigException;
import org.eclipse.rdf4j.repository.manager.RepositoryInfo;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;
import org.eclipse.rdf4j.rio.RDFWriterRegistry;
//import org.openrdf.http.server.ProtocolUtil;
//import org.openrdf.http.server.repository.BooleanQueryResultView;
//import org.openrdf.http.server.repository.GraphQueryResultView;
//import org.openrdf.http.server.repository.QueryResultView;
//import org.openrdf.http.server.repository.RepositoryController;
//import org.openrdf.http.server.repository.TupleQueryResultView;
//import org.openrdf.query.*;
//import org.openrdf.query.resultio.*;
//import org.openrdf.query.resultio.sparqljson.SPARQLResultsJSONWriterFactory;
//import org.openrdf.query.resultio.sparqlxml.SPARQLResultsXMLWriterFactory;
//import org.openrdf.query.resultio.text.csv.SPARQLResultsCSVWriterFactory;
//import org.openrdf.query.resultio.text.tsv.SPARQLResultsTSVWriterFactory;
//import org.openrdf.repository.RepositoryConnection;
//import org.openrdf.rio.RDFWriterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import swiss.sib.sparql.playground.domain.SparqlQueryType;
import swiss.sib.sparql.playground.exception.SparqlTutorialException;
import swiss.sib.sparql.playground.service.SparqlService;
//import info.aduna.lang.FileFormat;
//import info.aduna.lang.service.FileFormatServiceRegistry;

@Controller
// public class SparqlQueryController extends
// org.springframework.web.servlet.mvc.AbstractController {
// public class SparqlQueryController extends RepositoryController {
public class SparqlQueryController {
	private static final Log logger = LogFactory.getLog(SparqlController.class);
	@Autowired
	private SparqlService sparqlService;

	/*
	 * public SparqlQueryController(){ super(); //this.setRepositoryManager(repMan);
	 * }
	 */

	// Code taken from Sesame (before used to be in SparqlController)
	@RequestMapping(value = "/sparql")
	public void sparqlEndpoint(@RequestParam(value = "query", required = true) String queryStr,
			@RequestParam(value = "output", required = false) String output, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (queryStr != null) {
			synchronized (this) {

				TupleQueryResult queryResult = (TupleQueryResult) sparqlService.evaluateQuery(queryStr);

				TupleQueryResultWriterFactory factory;
				if (output != null && output.equalsIgnoreCase("csv")) {
					factory = new SPARQLResultsCSVWriterFactory();
					response.setContentType(MimeTypeUtils.TEXT_PLAIN_VALUE);
					response.setHeader("Content-Disposition", "attachment; filename=" + "result." + output);

				} else if (output != null && output.equalsIgnoreCase("tsv")) {
					factory = new SPARQLResultsTSVWriterFactory();
					response.setContentType(MimeTypeUtils.TEXT_PLAIN_VALUE);
				} else if (output != null && output.equalsIgnoreCase("xml")) {
					factory = new SPARQLResultsXMLWriterFactory();
					response.setContentType(MimeTypeUtils.APPLICATION_XML_VALUE);
				} else {
					factory = new SPARQLResultsJSONWriterFactory();
					response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
				}

				renderInternal(factory, queryResult, request, response);
			}
		} else {
			throw new SparqlTutorialException("Missing parameter: ");
		}
	}

	private void renderInternal(TupleQueryResultWriterFactory factory, TupleQueryResult tupleQueryResult,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(200);
		ServletOutputStream out = response.getOutputStream();

		try {
			TupleQueryResultWriter qrWriter = factory.getWriter(out);
			QueryResults.report(tupleQueryResult, qrWriter);
		} catch (QueryInterruptedException var16) {
			logger.error("Query interrupted", var16);
			response.sendError(503, "Query evaluation took too long");
		} catch (QueryEvaluationException var17) {
			logger.error("Query evaluation error", var17);
			response.sendError(500, "Query evaluation error: " + var17.getMessage());
		} catch (TupleQueryResultHandlerException var18) {
			logger.error("Serialization error", var18);
			response.sendError(500, "Serialization error: " + var18.getMessage());
		} finally {
			out.close();
		}

	}

	private FileFormatServiceRegistry<? extends FileFormat, ?> getRegistryInstance(SparqlQueryType queryType) {
		switch (queryType) {
		case TUPLE_QUERY:
			return TupleQueryResultWriterRegistry.getInstance();
		case GRAPH_QUERY:
			return RDFWriterRegistry.getInstance();
		case BOOLEAN_QUERY:
			return BooleanQueryResultWriterRegistry.getInstance();
		}
		return null;
	}

	// README: no reference
	// private View getView(SparqlQueryType queryType) {
	// switch(queryType){
	// case TUPLE_QUERY: return TupleQueryResultView.getInstance();
	// case GRAPH_QUERY: return GraphQueryResultView.getInstance();
	// case BOOLEAN_QUERY: return BooleanQueryResultView.getInstance();
	// }
	// return null;
	// }
}
