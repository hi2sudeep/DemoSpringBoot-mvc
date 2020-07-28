package com.test.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.test.model.RoadMap;

/**
* The HomeController program implements an spring mvc application that
* simply get two query parameter as source city & destination city and return output as "Road connection : Yes/No" by comparing against
* predefined city connection mentioned in city.txt file.
*    
* @author  Sudeep Mandal
* @version 1.0
* @since   07/28/2020.  
*/

@Controller
public class HomeController {
	
	@RequestMapping("/connected")
	public ModelAndView home(@RequestParam("origin") String source, @RequestParam("destination") String dest) {
		String result="";
	
		try {
			//Call service method cityConnectionCheck with input as city source & destination
			result =cityConnectionCheck(source, dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//set model attribute "searchResult" with result & view as "home"
		ModelAndView mv= new ModelAndView();
		mv.addObject("searchResult",result );
		mv.setViewName("home");
		
		return mv;
	}

	
/**
 * This is the service method "cityConnectionCheck" for checking the connectivity of given input cities against predefine connection text file city.txt. 
 * @param source   This is source city extracted form query parameter "origin".  
 * @param dest     This is destination city extracted form query parameter "destination".
 * @return String  This return string as "Road connection : Yes" / No
 */	
	public static String cityConnectionCheck(String source, String dest) throws Exception { 
		String result ="Road connection : No";
	
		List<RoadMap> roadlist= new ArrayList<RoadMap>() ;
		
		//Read list of roads from txt file and assignt to Arraylist.
		roadlist = readList();

		Iterator itr= roadlist.iterator();
		String matchCity1= source.trim();
		String matchCity2= dest.trim();
		
		//Temp string variable holding current data of RoadMap model.
		String str1,str2 ;
		
		while (itr.hasNext()) {
			RoadMap rm= (RoadMap)itr.next();
			str1 = rm.getFirstCity();
			str2 = rm.getLastCity();

			if((matchCity1.equals(str1) &&  matchCity2.equals(str2) ) || (matchCity1.equals(str2) &&  matchCity2.equals(str1)) ) {
				result="Road connection : Yes";
			//	System.out.println("1 Match found: ");
			}
		}
		return result;
	}
	
	
	/**
	 * This is the service method "readList()" for reading predefined city information from "city.txt" file saved in local drive. 
	 * @return List<RoadMap>  This return list of RoadMap object holding city pair information.
	 */	
	public static List<RoadMap> readList() throws Exception{
		List<RoadMap> rl= new ArrayList<RoadMap>() ;
		//Reading file from local directory. This information is hard coded, but can be made generic by adding to properties file. 
		BufferedReader reader = new BufferedReader(new FileReader("D:/city.txt"));
		// read file line by line
		String line = null;
		Scanner scanner = null;
		int index = 0;
		
		//from every line in city.txt file, city name pairs are scnned and save as object of RoadMap model class.
		while ((line = reader.readLine()) != null) {
			RoadMap roadmap = new RoadMap();
			scanner = new Scanner(line);
			scanner.useDelimiter(",");
			while (scanner.hasNext()) {
				String data = scanner.next();
				if (index == 0)
					roadmap.setFirstCity(data);
				else if (index == 1)
					roadmap.setLastCity(data.trim());
				else
					System.out.println("invalid data" + data);
				index++;
			}
			index = 0;
			rl.add(roadmap);
		}
		//close reader
		reader.close();
		return rl;
	}
}
