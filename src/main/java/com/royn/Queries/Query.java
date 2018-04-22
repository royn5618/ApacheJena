package com.royn.Queries;

public interface Query {
		public String prefixGeo = "PREFIX geo: <http://www.opengis.net/ont/geosparql#>";
		public String prefixGeof = "PREFIX geof: <http://www.opengis.net/def/function/geosparql#>";
		public String prefixRdf = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
		public String prefixOwl = "PREFIX owl: <http://www.w3.org/2002/07/owl#>";
		public String prefixRdfs = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
		public String prefixCsoProp = "PREFIX csoProperty: <http://data.cso.ie/census-2011/property/>";
		public String prefixCube = "PREFIX cube: <http://purl.org/linked-data/cube#>";
		public String prefixSkos = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>";
		public String prefixXsd = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
		public String prefixDim = "PREFIX  dimension:<http://purl.org/linked-data/sdmx/2009/dimension#>";
		public String topThreeCitiesFilter = "FILTER(?x IN (\"Dublin@en\",\"Cork@en\", \"Limerick@en\" ) )";
		
		public String allPrefixes = prefixGeo + prefixGeof + prefixRdf + prefixOwl + 
				prefixRdfs+ prefixCsoProp + prefixCube + prefixSkos + prefixXsd + prefixDim ;
		
		public String queryString1 =  allPrefixes +
				"SELECT   DISTINCT ?cordinate WHERE { ?a rdfs:label ?label. FILTER(?notation =?label ){?id cube:dataSet ?ds .}"+
				"FILTER(  ?ds = <http://data.cso.ie/census-2011/dataset/irish-speakers/cty> )"+
				"{ ?id    csoProperty:population-aged-3-or-over  ?population ; dimension:refArea ?area_code ;}"+
				"FILTER(  ?population < 10000) ?area_code skos:notation ?notation . ?id2 rdfs:label ?notation ; geo:hasGeometry [  a geo:Geometry ; geo:asWKT ?cordinate ] }";
		
		public String queryString2 = allPrefixes +
				"SELECT   DISTINCT ?cordinate WHERE { ?a rdfs:label ?label. FILTER(?notation =?label ) { ?id cube:dataSet ?ds . }"+ 
				"FILTER(  ?ds = <http://data.cso.ie/census-2011/dataset/age-group-gender-population/cty> )"+
				"{ ?id    csoProperty:population  ?population ; dimension:refArea ?area_code ;"+
				"csoProperty:age-group  ?age_group ."+ "} FILTER(   ?age_group = <http://data.cso.ie/census-2011/classification/age-group/all>   && ?population > 100000)"+
				"?area_code skos:notation ?notation ."+
				"?id2 rdfs:label ?notation ; geo:hasGeometry [  a geo:Geometry ; geo:asWKT ?cordinate ] }";
		
		String queryString3 = allPrefixes +  
				"SELECT   DISTINCT ?cordinate ?population WHERE { ?a rdfs:label ?label. FILTER(?notation =?label ){?id cube:dataSet ?ds .}"+
				"FILTER(  ?ds = <http://data.cso.ie/census-2011/dataset/households-by-type-of-occupancy/cty>  )"+
				"{ ?id    csoProperty:type-of-occupancy ?households ; dimension:refArea ?area_code ; csoProperty:households ?population.}"+
				"FILTER(  ?households = <http://data.cso.ie/census-2011/classification/type-of-occupancy/Owner_Occupier_with_Mortgage> && ?population > 5000) "
				+ "?area_code skos:notation ?notation . ?id2 rdfs:label ?notation ; geo:hasGeometry [  a geo:Geometry ; geo:asWKT ?cordinate ] }";
		
		String queryString4 = allPrefixes +  
	            "SELECT ?cordinate WHERE { ?a rdfs:label ?label. FILTER(?notation =?label ){?id cube:dataSet ?ds .}"+
	            "FILTER(  ?ds = <http://data.cso.ie/census-2011/dataset/households-internet/cty> )"+
	            "{ ?id csoProperty:internet ?internet; dimension:refArea ?area_code ; csoProperty:households ?number.}"+ 
	            "FILTER( ?internet = <http://data.cso.ie/census-2011/classification/internet/broadband> && ?number > 10000 ) "
	            + "?area_code skos:notation ?notation . ?id2 rdfs:label ?notation ; geo:hasGeometry [  a geo:Geometry ; geo:asWKT ?cordinate ] }";

		
		String queryString5 = allPrefixes + "SELECT (STR(?s) as ?city) ?x ?population  WHERE "
				+ "{?cty dimension:refArea ?x ; csoProperty:population-aged-3-or-over ?population ;  csoProperty:ability-to-speak-irish <http://data.cso.ie/census-2011/classification/ability-to-speak-irish/yes> ."
				+ "{SELECT ?x ?s WHERE {?x skos:notation ?s . "
				+ "FILTER(?s IN (\"DUBLIN CITY@en\" ,\"CORK CITY@en\", \"LIMERICK CITY@en\" ) ) } "
				+ "LIMIT 3 }}";
		
		String queryString6 = allPrefixes + "SELECT ?city (STR(?c) as ?cordinate) ?population  WHERE " +
				"{ ?a rdfs:label ?label. FILTER(?city =?label ){?id cube:dataSet ?ds .}"+
				"FILTER(  ?ds = <http://data.cso.ie/census-2011/dataset/irish-speakers/cty> )"+
				"{ ?id    csoProperty:population-aged-3-or-over  ?population ; dimension:refArea ?x ; csoProperty:ability-to-speak-irish  ?speakboth . }"+
				"FILTER(  ?speakboth = <http://data.cso.ie/census-2011/classification/ability-to-speak-irish/all>   && ?population < 80000) " +
				"?x skos:notation ?city . ?id2 rdfs:label ?city ; geo:hasGeometry [  a geo:Geometry ; geo:asWKT ?c ] }"
				+ "ORDER BY ASC(?population) LIMIT 1" ;
		
		String queryString7 = allPrefixes + "SELECT (STR(?s) as ?city) ?x ?population ?occupancy WHERE "
				+ "{?cty dimension:refArea ?x ; csoProperty:households ?population ;  csoProperty:type-of-occupancy ?occupancy ."
				+ "{SELECT ?x ?s WHERE {?x skos:notation ?s . "
				+ "FILTER(?s IN (\"MAYO@en\", \"MONAGHAN@en\", \"CAVAN@en\", \"LEITRIM@en\" ) ) } "
				+ "ORDER BY ?city }}";
		
		String queryString8 = allPrefixes +  
				"SELECT ?cordinate WHERE { ?a rdfs:label ?label. FILTER(?notation =?label ){?id cube:dataSet ?ds .}"+
	            "FILTER(  ?ds = <http://data.cso.ie/census-2011/dataset/households-internet/cty> )"+
	            "{ ?id csoProperty:internet ?nointernet ; dimension:refArea ?area_code .}"+ 
	            "FILTER( ?nointernet = <http://data.cso.ie/census-2011/classification/internet/no>) "
	            + "?area_code skos:notation ?notation . ?id2 rdfs:label ?notation ; geo:hasGeometry [  a geo:Geometry ; geo:asWKT ?cordinate ] }";
		
		String queryString9 = allPrefixes +  
				"SELECT ?cordinate ?households ?city WHERE { ?a rdfs:label ?label. FILTER(?city =?label ){?id cube:dataSet ?ds .}"+
	            "FILTER(  ?ds = <http://data.cso.ie/census-2011/dataset/households-internet/cty> )"+
	            "{ ?id csoProperty:internet ?nointernet ; dimension:refArea ?area_code ; csoProperty:households ?households .}"+ 
	            "FILTER( ?nointernet = <http://data.cso.ie/census-2011/classification/internet/no>) "
	            + "?area_code skos:notation ?city . ?id2 rdfs:label ?notation ; geo:hasGeometry [  a geo:Geometry ; geo:asWKT ?cordinate ] }"
	            +"ORDER BY DESC(?households) LIMIT 1";
		
		String queryString10 = allPrefixes +
				"SELECT (SUM(?population) as ?total) ?x  WHERE "
				+
				"{"
				+ "{?id csoProperty:households ?population ; cube:dataSet ?x . FILTER(?x = <http://data.cso.ie/census-2011/dataset/households-by-type-of-occupancy/cty>)} "
				+ "UNION "
				+ "{?id csoProperty:households ?population ; cube:dataSet ?x . FILTER(?x = <http://data.cso.ie/census-2011/dataset/households-internet/cty>)} "
				+ "} GROUP BY ?x";

}
