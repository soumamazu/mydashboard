package EOD;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class getDashboardDetails
{
    public static void main(String[] args) throws Exception
    {
	getDashboardDetails eod=new getDashboardDetails();
        eod.sendGet();
    }
    
    private void sendGet() throws Exception
    {
	String url="http://queuemonitoring.us.oracle.com/odqmon/strategic/OHSSTRATEGIC.jsp";
        
        URL obj=new URL(url);
	HttpURLConnection con=(HttpURLConnection)obj.openConnection();
        String myCookies="JSESSIONID=hz2xWw5FtQZGQ1zGVQpclLxpb5n36ScSk27G12MXfYxczZ6xlJmf!2138552663; "+
                    "OAMAuthnCookie_queuemonitoring.us.oracle.com:80=Afg1BM9JbGV6v3wz2kp03AHJj3BDZDNzXqucEg2pMX2zbrGj8o5DdYtIhbgbPoIPXSpy4M0lsdN9JwJ%2F41U0Sklw6n95Q%2FRytUBnJteHRSjIkfzTOUKUCAZLAQQOqn%2FDEIZXQ1q5OaMvTDEsxrC2QFG3uvtdyB4PYuDMvNx7P5SOwQiLD5jp5krzvoGk5vlM9yPBchpqiVZUHJ2Y6JtCyrY1znQlZ7Wz8kj%2BWow24ShV%2Bw0v5zGbqgBxzGWcyDRknyUTA8SjpY285ZnDWLn%2F4JW0Yuc0DLG0n%2BHIc9ptmkR32dU5lt0CIZBQijNrH4F3hW3qoiIMEju5opjBBg8h%2BqFiODNne354bVZIwfSOiwU%3D; "+
                    "ORASSO_AUTH_HINT=v1.0~20151216090645; "+
                    "ORA_UCM_INFO=3~159CD85F80E20B94E050E60A907F3104~Soumadeep~Mazumdar~soumadeep.mazumdar@oracle.com; "+
                    "custList=; "+"qmon-tz=330";
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
        
        getSCHDetails(response.toString());
        getNEWDetails(response.toString());
    }
    
    private void getNEWDetails(String rawData)
    {
        System.out.println("NEW & Callbacks RFC");
        String tableTag="<!-- CENTERAL TABLE ENDS -->";
        rawData=(rawData.substring(rawData.indexOf(tableTag),rawData.indexOf("</table>",rawData.indexOf(tableTag))));
        
        String[] lines=rawData.split("\\n");
        for(String line:lines)
        {
            if(line.trim().startsWith("<tr onMouseOver="))
            {
                String[] components=line.split("<td nowrap>");
                System.out.println("SR/RFC#: "+truncateDetails("<a href=\"javascript:openRFC('","');\"",components[2]));
                System.out.println("Title: "+truncateDetails("title=\"","\" style=\"",components[2]));
                System.out.println();
            }
        }
    }
    
    private void getSCHDetails(String rawData)
    {
        System.out.println("Scheduled and Awaiting to be Scheduled SR/RFC");
        String tableTag="<!-- CENTERAL TABLE -->";
        rawData=(rawData.substring(rawData.indexOf(tableTag),rawData.indexOf("</table>",rawData.indexOf(tableTag))));
        
        String[] lines=rawData.split("\\n");
        for(String line:lines)
        {
            if(line.startsWith("<td nowrap>"))
            {
                System.out.println("SR/RFC#: "+truncateDetails("<a href=\"javascript:openRFC('","');\"",line));
                System.out.println("Title: "+truncateDetails("title=\"","\">",line));
                System.out.println();
            }
        }
    }
    
    private String truncateDetails(String opentag,String closetag,String rawData)
    {
        if(!rawData.contains(opentag) || !rawData.contains(closetag))
            return "";
        
        return rawData.substring(rawData.indexOf(opentag)+opentag.length(),rawData.indexOf(closetag,rawData.indexOf(opentag)));
    }
}
