package RFC;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.http.*;

public class getRFCDetails extends HttpServlet
{
    public static void main(String[] args) throws Exception
    {
	getRFCDetails rfc=new getRFCDetails();
        System.out.println("Testing 1 - Send Http GET request");
        rfc.sendGet("3-5EVOO0I");
        rfc.sendGet("3-5EQSJZL");
    }
    
    private void sendGet(String rfcNum) throws Exception
    {
	String url="https://support.us.oracle.com/oip/faces/secure/ml3/rfc/ViewRFC.jspx?RFCId="+rfcNum;
        
        URL obj=new URL(url);
	HttpURLConnection con=(HttpURLConnection)obj.openConnection();
        String myCookies="BIGipServerprod-support.us.oracle.com_http_pool=3753067672.24862.0000; "+
                    "JSESSIONID=e345c27acb4aafca07b5a41c88f36f3bbdbdb1638f57349ee70f7b2b487e5a76.e34NchyKah4Qbi0Mah8SbxyMaxyPe0; "+
                    "OHS-support.us.oracle.com-443=9533F1F2A900007FDE5B4348AF7E485D48FFD4DAAAD58AEB0F86123504AC4A43A04CC9E6603AF13DCA6FC20D3464B32E412AA587EFF9E666FDA5E93605555D37053525B9C3FEFBA943C8C9FDABB2127A73B98642FF916892599C7BE0C0B15311EF5814CBD0CE159F8D851FBDCAB494D2481011C66F528AC5AD4051026C35D2CDF0BD5D3A02AB7CF66774944C284CC07C73B943963548FC063D0A12F39EE28276B16E209CEC0361FAD55D717D6A8B6EF21C62D69CED8F9B3206941BBFA9D0AF6C19D312800C0286D4242C38F5FD79BA0D6CC455FCCE2659493C5A9FD2108CAD915BDAAEB83A29475FDFEF6DA8D782AC1184B01DC98E1CD1A08E1AFBDE9EEC2884ADC6A6A43528074D; "+
                    "mos.presence=d9b95911-2734-28d9-ee07-3c3e18202e53%7C1450143390329; "+
                    "oracle.uix=0^^GMT+5:30; "+
                    "OAM_ID=VERSION_4~X9Ssu80Rd5ZAbuQc+RVkPA==~xfRmC7Jo/kdJrBODbayB3xy/ydq1aScWg/e+9LLcX4ZUTGh46Aln8WY+pHajVv5PxNKjAQjahQii7GRxVUQDkPnfJC0d8KZF9Jdqo/0Tr4vVF2W/OtM+sVyu9ctROCo3f5r5jCTGyb6Hu9aP6YygQxJleUGilNoiZGjT83NDfiukUxlnpHkua2n1BcHoMqn39nSmaui31/kbHyPaZNv21bn7zVlUsHTquecNQQGzefNjGL9Pt6tih7acOp7liZIcd0cYcmW8NHxkm5O7eZIaOg==; "+
                    "MOSEPSESSIONID=JB6jQ6tzOrLuvIQprl19V9ONOPpBbGELiEvBl_noS0bvbDtxgi75!-1159030445";
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
        
        getDetails(response.toString());
    }
    
    private void getDetails(String rawData)
    {
        System.out.println("\nOverview\n");
        String rfcNumTag="<span id=\"rfcNum\" class=\"x8\">";
        System.out.println("RFC Number: "+parseDetails(rfcNumTag,rawData));
        String custUpdateStatusTag="<span id=\"customerUpdateStatus\" class=\"x8\">";
        System.out.println("Customer Update Status: "+parseDetails(custUpdateStatusTag,rawData));
        String summaryTag="<span id=\"summary\" class=\"x8\">";
        System.out.println("Summary: "+parseDetails(summaryTag,rawData));
        
        System.out.println("\nCustomer Information\n");
        String accNameTag="<span id=\"accountName\" class=\"x8\">";
        System.out.println("Account Name: "+parseDetails(accNameTag,rawData));
        String csiTag="<span id=\"csiInput\" class=\"x8\">";
        System.out.println("CSI: "+parseDetails(csiTag,rawData));
        String targetTag="<span id=\"headerTarget\" class=\"x8\">";
        System.out.println("Target: "+parseDetails(targetTag,rawData));
        String emailTag="<span id=\"contactEmail\" class=\"x8\">";
        System.out.println("Customer Email: "+parseDetails(emailTag,rawData));
        String phoneTag="<span id=\"contactPhone\" class=\"x8\">";
        System.out.println("Customer Phone: "+parseDetails(phoneTag,rawData));
        String countryTag="<span id=\"primaryCountry\" class=\"x8\">";
        System.out.println("Country: "+parseDetails(countryTag,rawData));
        String lnameTag="<span id=\"billingContactLastName\" class=\"x8\">";
        System.out.println("Customer Last Name: "+parseDetails(lnameTag,rawData));
        String fnameTag="<span id=\"billingContactFirstName\" class=\"x8\">";
        System.out.println("Customer First Name: "+parseDetails(fnameTag,rawData));
        
        
        System.out.println("\nProduct Information\n");
        String LOSTag="<span id=\"los\" class=\"x8\">";
        System.out.println("LOS: "+parseDetails(LOSTag,rawData));
        String serviceTypeTag="<span id=\"serviceTypeSelect\" class=\"x8\">";
        System.out.println("Service Type: "+parseDetails(serviceTypeTag,rawData));
        String migratedTag="<span id=\"migratedCSIOutputText\" class=\"x8\">";
        System.out.println("Migrated RFC: "+parseDetails(migratedTag,rawData));
        String categoryTag="<span id=\"rfcCategorySelect\" class=\"x8\">";
        System.out.println("Category: "+parseDetails(categoryTag,rawData));
        String rfcTypeTag="<span id=\"rfcTypeSelect\" class=\"x8\">";
        System.out.println("RFC Type: "+parseDetails(rfcTypeTag,rawData));
        String subTypeTag="<span id=\"subTypeSelect\" class=\"x8\">";
        System.out.println("Sub Type: "+parseDetails(subTypeTag,rawData));
        
        System.out.println("\nStatus Information\n");
        String ownerTag="<span id=\"rfcOwner\" class=\"x8\">";
        System.out.println("Owner: "+parseDetails(ownerTag,rawData));
        String groupTag="<span id=\"ownerGroup\" class=\"x8\">";
        System.out.println("Group: "+parseDetails(groupTag,rawData));
        String assignmentStatusTag="<span id=\"assignmentStatus\" class=\"x8\">";
        System.out.println("Assignment Status: "+parseDetails(assignmentStatusTag,rawData));
        String statusTag="<span id=\"statusSelect\" class=\"x8\">";
        System.out.println("Status: "+parseDetails(statusTag,rawData));
        String subStatusTag="<span id=\"subStatusSelect\" class=\"x8\">";
        System.out.println("Sub Status: "+parseDetails(subStatusTag,rawData));
        String severityTag="<span id=\"severitySelect\" class=\"x8\">";
        System.out.println("Severity: "+parseDetails(severityTag,rawData));
        String priorityTag="<span id=\"priority\" class=\"x8\">";
        System.out.println("Priority Score: "+parseDetails(priorityTag,rawData));
        String urgencyTag="<span id=\"urgencyCode\" class=\"x8\">";
        System.out.println("Urgency: "+parseDetails(urgencyTag,rawData));
        String emergencyTag="<span id=\"displayEmergencyFlag__xc_c\" class=\"x6\">";
        System.out.println("Emergency: "+parseDetails(emergencyTag,rawData));
        String startTag="<span id=\"startDateHeader\" class=\"x8\">";
        System.out.println("Planned Start: "+parseDetails(startTag,rawData));
        String endTag="<span id=\"endDateHeader\" class=\"x8\">";
        System.out.println("Planned End: "+parseDetails(endTag,rawData));
        String openTag="<span id=\"createdDate\" class=\"x8\">";
        System.out.println("Date Opened: "+parseDetails(openTag,rawData));
        String updatedTag="<span id=\"updated\" class=\"x8\">";
        System.out.println("Last Updated On: "+parseDetails(updatedTag,rawData));
        String durationTag="<span id=\"durationHeader\" class=\"x8\">";
        System.out.println("Duration (hrs): "+parseDetails(durationTag,rawData));
    }
    
    private String parseDetails(String tag,String rawData)
    {
        if(!rawData.contains(tag))
            return "NA";
        
        return rawData.substring(rawData.indexOf(tag)+tag.length(),rawData.indexOf("</span>",rawData.indexOf(tag)+tag.length())).trim();
    }
}