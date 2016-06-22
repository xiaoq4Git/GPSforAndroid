package com.bitman.btgpstest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by ABC .
 */
public class androidFile {
	private  List<Double> LatSendList = new ArrayList<Double>();
	private  List<Double> LonSendList = new ArrayList<Double>();
	
    public  List<Double> getLatList() {
		return LatSendList;
	}

	public  void setLatSendList(List<Double> latSendList) {
		LatSendList = latSendList;
	}

	public  List<Double> getLonList() {
		return LonSendList;
	}

	public  void setLonSendList(List<Double> lonSendList) {
		LonSendList = lonSendList;
	}

	public String ReadGpsFile(String strFilePath){
    	String path = strFilePath;
        String content = "";
        
        
        File file = new File(path);
        if (file.isDirectory())
        {
//            Log.d("TestFile", "The File doesn't not exist.");
        	System.out.println("The File doesn't not exist");
        }
        else{
        	try {
                InputStream instream = new FileInputStream(file); 
                if (instream != null) 
                {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //∑÷––∂¡»°
                    Pattern p = Pattern.compile("lat=\"(.+)\" lon=\"(.+)\"");

                    
                    while (( line = buffreader.readLine()) != null) {
                        content += line + "\n";
                        Matcher m = p.matcher(line);
                        if(m.find()){
                        	LatSendList.add(Double.parseDouble(m.group(1)));
                        	LonSendList.add(Double.parseDouble(m.group(2)));
                        }
                    }                
                    instream.close();
                }
            }
            catch (java.io.FileNotFoundException e) 
            {
//                Log.d("TestFile", "The File doesn't not exist.");
            	System.out.println("The File doesn't not exist.");
            } 
            catch (IOException e) 
            {
//                 Log.d("TestFile", e.getMessage());
            	System.out.println("----");
            }
        }
        
    	return content;
    }
    
    // Example of creating a new Location from test data
//    public static final Location testLocation = createLocation(LAT, LNG, ACCURACY);

//    public static void main(String[] args){
//    	String result = androidFile.ReadGpsFile("D:\\Sichuan.gpx");
////    	System.out.println(result);
//    }

}

