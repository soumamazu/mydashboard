package OMP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class getOMPDetails
{
    private final ArrayList <String> trash;
    
    public static void main(String[] args) throws Exception
    {
        getOMPDetails omp=new getOMPDetails();
        omp.sendGet("TUCS4O");
    }
    
    getOMPDetails()
    {
        trash=new ArrayList <> ();
        trash.add("</td>");
        trash.add("</tr>");
        trash.add("<tr>");
        trash.add("<td>");
        trash.add("</div>");
        trash.add("<div class=\"xtv\">");
    }
    
    private void sendGet(String instance) throws Exception
    {
        String url="https://ompnextgen.oracleoutsourcing.com/OperationsGuide/faces/OpsGuide.jspx?instance="+instance;
        
        URL obj=new URL(url);
	HttpURLConnection con=(HttpURLConnection)obj.openConnection();
        String myCookies="OHS-ompnextgen.oracleoutsourcing.com-443=6C9D9BCB8AFBCF8AD7CF4AAA07E872AFCA51C55A19FCBC63C54B83D13C0B6E4FAA583C691F3118048FCA9E777DA835AC54C9FC80C10C2F92F0B5DC9A59A9E97298F7D0144DBCEE79A025D2EF58CFFF06765FA14D9F0F5E2637FD7AA938B9994AC7203CCAE8AB20850877D5D8C2685BDB50FF1398CB76841D5523B7B4E0501799F0BDD59386EF3E8EA99C2002BC1F9B9F2CF3B1459B4A0C2DBE78B7DEA5B371B3AFE595F19B7C7C4AA1FC37CB0638639CCDC0F718EC93BE604405DAFE1B84A881BEB2897465F07C42D730DEE814BAFC0E240CDA689F590C3342BFB13A58204ABC9ACB4491420CC880A8FCBFEE08E4A9388A8AE4C59E7F446182528B099AB9A265~; "+
                "OMPUNIFIED=LNCpLGb84ryJMSDDeEEVOV98QZ2sF1LRtgv_-U_5DhH_5gI5N_UC!-2048095872; "+
                "_WL_AUTHCOOKIE_OMPUNIFIED=9vaVOmgkcdyGEPu9U5iY";
        con.setRequestProperty("Cookie",myCookies);
        con.setRequestMethod("GET");
        
        con.connect();
	int responseCode=con.getResponseCode();
        
	System.out.println("\nURL : "+url);
	System.out.println("Response Code : "+responseCode+"\n\n");

        StringBuffer response;
        try(BufferedReader in=new BufferedReader(new InputStreamReader(con.getInputStream())))
        {
            String inputLine;
            response=new StringBuffer();
            while((inputLine=in.readLine())!=null)
                response.append(inputLine).append("\n");
        }
        
        getDetails(response.toString());
    }
    
    private void getDetails(String rawData)
    {
        String sequence=truncateDetails("Database Startup / Shutdown</td>","</table>",rawData);
        System.out.println(clearTrash(sequence).replace("<br/>","\n"));
    }
    
    private String truncateDetails(String opentag,String closetag,String rawData)
    {
        if(!rawData.contains(opentag) || !rawData.contains(closetag))
            return "";
        
        return rawData.substring(rawData.indexOf(opentag)+opentag.length(),rawData.indexOf(closetag,rawData.indexOf(opentag)));
    }
    
    private String clearTrash(String str)
    {
        for(String word:trash)
            str=str.replace(word,"");
        
        return str;
    }
}
