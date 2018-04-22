package com.royn.ProjectJenaKDE;

import java.util.Scanner;
import com.royn.Queries.Query;
//import com.royn.ProjectJenaKDE.Helper;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner reader = new Scanner(System.in);
		Integer n = Helper.readInput(reader);
		while (n <1 || n > 10 )
		{
			System.out.println("Please enter a valid number...");
			n = Helper.readInput(reader);
		}
		reader.close();
		String query = null;
		switch(n){
			case 1:  query = Query.queryString1;
			break;
			case 2:  query = Query.queryString2;
			break;
			case 3:  query = Query.queryString3;
			break;
			case 4:  query = Query.queryString4;
			break;
			case 5:  query = Query.queryString5;
			break;
			case 6:  query = Query.queryString6;
			break;
			case 7:  query = Query.queryString7;
			break;
			case 8:  query = Query.queryString8;
			break;
			case 9:  query = Query.queryString9;
			break;
			case 10:  query = Query.queryString10;
			break;
			default: query = Query.queryString1;
            break;
		}
		OntModel model = Helper.loadOntoModel();
		//Helper.writingToTurtle(model);
		Helper.queryExecute(model, query);
	}

}
