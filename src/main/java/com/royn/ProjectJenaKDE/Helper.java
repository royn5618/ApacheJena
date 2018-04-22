package com.royn.ProjectJenaKDE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.shared.JenaException;
import org.apache.jena.util.FileManager;

public class Helper {
	public static int readInput(Scanner reader)
	{
		System.out.println("Enter a query number (1 - 10) ... ");
		int n = reader.nextInt();
		return n;
	}
	
	public static void writingToTurtle(OntModel m)
	{
		try {
	          File file= new File("/Jena_Test/KD_data_turtle.txt");
	          m.write(new FileOutputStream(file), "TURTLE");
	        } 
		catch (IOException e) {
	          e.printStackTrace();
		}
	}
	
	public static OntModel loadOntoModel()
	{
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
	    try 
	    {
	        InputStream in = FileManager.get().open("/Jena_Test/KD_data_rdfxml.owl");
	        try 
	        {
	            model.read(in, null);
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
	    } 
	    catch (JenaException je) 
	    {
	        System.err.println("ERROR" + je.getMessage());
	        je.printStackTrace();
	        System.exit(0);
	    }
	    
	    return model;
	    
	    //To visualise the document in TURTLE format
	    //Helper.writingToText(ontoModel);
	    //runQuery(ontoModel);
	}
	
	public static void queryExecute(OntModel model, String sparql)
	{
		StringBuilder sb = new StringBuilder();
		Query query = QueryFactory.create(sparql);
		QueryExecution queryEx = QueryExecutionFactory.create(query, model);
		try
		{
			ResultSet results = queryEx.execSelect();
			
			while (results.hasNext())
			{
				QuerySolution sol = results.nextSolution();
				List<String> resultVariables = results.getResultVars();
				if (resultVariables.contains("city"))
				{
					String x = sol.get("city").toString();
					String[] c = x.split("@");
					if(c[0] != null && !c[0].isEmpty())
					{
						sb.append(c[0]);
						sb.append("\n");
					}		
				}	
				if (resultVariables.contains("population"))
				{
					Literal population = sol.getLiteral("population");
					int pop = population.getInt();
					sb.append(pop);
					sb.append("\n");
				}
				if (resultVariables.contains("households"))
				{
					Literal population = sol.getLiteral("households");
					int pop = population.getInt();
					sb.append(pop);
					sb.append("\n");
				}
				if (resultVariables.contains("gender"))
				{
					String gender = sol.get("gender").toString();
					sb.append(gender);
					sb.append("\n");
				}
				if (resultVariables.contains("occupancy"))
				{
					String x = sol.get("occupancy").toString();
					sb.append(x);
					sb.append("\n");
				}	
				if (resultVariables.contains("x"))
				{
					String x = sol.get("x").toString();
					sb.append(x);
					sb.append("\n");
				}
				if (resultVariables.contains("cordinate"))
				{
					String cordinate = sol.getLiteral("cordinate").toString();
					sb.append(cordinate);
					sb.append("\n");
				}if (resultVariables.contains("type"))
				{
					String cordinate = sol.getLiteral("type").toString();
					sb.append(cordinate);
					sb.append("\n");
				}
				if (resultVariables.contains("total"))
				{
					Literal population = sol.getLiteral("total");
					int pop = population.getInt();
					sb.append(pop);
					sb.append("\n");
				}
			}
		}
		finally
		{
			String queryRes = sb.toString();
			outputUI(queryRes, sparql);
			
			System.out.println("Finished!!");
			queryEx.close();
		}
	}
	
	public static String formatQuery(String query)
	{
		String prefix1 = "PREFIX";
		String prefix2 = "\nPREFIX";
		String open1 = "{";
		String open2 = "\n{";
		String close1 = "}";
		String close2 = "}\n";
		String select1 = "SELECT";
		String select2 = "\nSELECT";
		
		query = query.replace(prefix1, prefix2);
		query = query.replace(open1, open2);
		query = query.replace(close1, close2);
		query = query.replace(select1, select2);
		return query;
		
	}
	
	public static void outputUI(String result, String query)
	{
		String queryString = "Query Statement: \n\n";
		String resultString = "Output: \n\n";
		
		query = formatQuery(query);
		
		JFrame frame = new JFrame("KD Group Project");		
		
		JPanel queryPanel = new JPanel();
	    JPanel resultPanel = new JPanel();
		
	    frame.getContentPane().add(queryPanel, "North");
	    frame.getContentPane().add(resultPanel, "South");
	    
		JTextArea queryText = new JTextArea(25, 100);
		queryText.setLineWrap(true);
		queryText.setText(queryString + query);
		queryText.setEditable(false);
		
		JTextArea resultText = new JTextArea(25, 100);
		resultText.setText(resultString + result);
		resultText.setEditable(false);
		
		queryPanel.add(new JScrollPane(queryText));
		resultPanel.add(new JScrollPane(resultText));
	    
		frame.pack();
	    frame.setVisible(true);
	}
}
