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
        System.out.println("Testing 1 - Send Http GET request");
        eod.sendGet();
    }
    
    private void sendGet() throws Exception
    {
	String url="http://queuemonitoring.us.oracle.com/odqmon/strategic/OHSSTRATEGIC.jsp";
        
        URL obj=new URL(url);
	HttpURLConnection con=(HttpURLConnection)obj.openConnection();
        String myCookies="JSESSIONID=DyNSWvnQ1GJrzj1hk22GRMQr0rpwGnshpJznwf2cv34w4hqmH46h!2138552663; "+
                    "OAMAuthnCookie_queuemonitoring.us.oracle.com:80=Wc3pjksAF0q7bOeXetBbeMmLUzKDHj1m6%2FLTIZi1eqiocNTUl8X4qON7t4eZr%2BhCEN55luZ%2FV0EYNUEbr7dltg0VymQKIoLxTZzithFnKibOiNZFTZKZp5U1BaMB7p4%2BZdpR%2F5OU5ADpk3iGBUqz%2Boe%2BFuYgNoZbutZB5wobj3ydnQ5yKInLZUt7oL%2B9KjinCw8RYrrw06G4iPdtXfJtGPKV0a5ZgQUkYTLYFzE0rdWdHTttVx7AHW22ZS08pm3gr%2BdzmkclOrsJrgQ78OOXg9BMPGdBoGXx0W5o6uxithe5hpTG7lWrhaO92x%2BQj%2FvNSYOE%2FZimBgwfqdfu%2F4tAZltHcNFTu14B6G038qaEnHw%3D; "+
                    "ORASSO_AUTH_HINT=v1.0~20151215090650; "+
                    "ORA_UCM_INFO=3~159CD85F80E20B94E050E60A907F3104~Soumadeep~Mazumdar~soumadeep.mazumdar@oracle.com; "+
                    "custList=; "+"qmon-tz=330";
        con.setRequestProperty("Cookie",myCookies);
        con.setRequestMethod("GET");
        
        con.connect();
	int responseCode=con.getResponseCode();
        
	System.out.println("\nSending 'GET' request to URL : "+url);
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
                System.out.println(components[2]);
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
                System.out.println("SR/RFC#: "+truncateDetailsFirst("<a href=\"javascript:openRFC('","');\"",line));
                System.out.println("Title: "+truncateDetailsFirst("title=\"","\">",line));
                System.out.println();
            }
        }
    }
    
    private String truncateDetailsFirst(String opentag,String closetag,String rawData)
    {
        if(!rawData.contains(opentag) || !rawData.contains(closetag))
            return "";
        
        return rawData.substring(rawData.indexOf(opentag)+opentag.length(),rawData.indexOf(closetag,rawData.indexOf(opentag)));
    }
    
    private String truncateDetailsLast(String opentag,String closetag,String rawData)
    {
        if(!rawData.contains(opentag) || !rawData.contains(closetag))
            return "";
        
        return rawData.substring(rawData.lastIndexOf(opentag)+opentag.length(),rawData.indexOf(closetag,rawData.lastIndexOf(opentag)));
    }
}
