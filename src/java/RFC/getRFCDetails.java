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
        rfc.sendGet("3-5EVKIQW");
        rfc.sendGet("3-5G8MVWG");
    }
    
    private void sendGet(String rfcNum) throws Exception
    {
	String url="https://support.us.oracle.com/oip/faces/secure/ml3/rfc/ViewRFC.jspx?RFCId="+rfcNum;
        
        URL obj=new URL(url);
	HttpURLConnection con=(HttpURLConnection)obj.openConnection();
        String myCookies="BIGipServerprod-support.us.oracle.com_http_pool=3803399320.24862.0000; "+
                    "JSESSIONID=12c0832f58bc1b1bdf2b28f6f6ff2f0b185aa72b0b18b706d04c27199a8afee1.e34NchyKa3yKci0La30NaNuPbhuKe0; "+
                    "OHS-support.us.oracle.com-443=78F66ABC36184B1E6545A42D0A76C9A3F0D9C067A66B510CB8B60232CFCAD785F92340C4267FAB5A904B1B6B35FDF90EEEF296429888CF8152C2441F03B6E1C59768E05E52A124CB60D3EB5751BDF9B06F556962F0747670140F2D6EA3F0B354CE53844C6320C2266342E4FCD2341801FE7948563B88C4F145948FD215E65B9C96C24D1768208251ED382755381C1F9B8C5D9FAB4C637707EBC1FDD737476E2C7DA0C9BFFF7AEC57F977983C5D9F541CDA465CFED5D6F4D616E954CCA49C60C1BB928BF39A8138A0157628D61AD1B8978AF7B68B106B3698EBC97C6805D5E8770BC28BFD2065323656CEE7F04002EC8B5A6352C04F2D351732E58BCDE6E7AD75F09315EB05A57E97; "+
                    "mos.presence=05997297-886c-45ee-45cc-d8e5e399dd69%7C1450239790200; "+
                    "oracle.uix=0^^GMT+5:30; "+
                    "OAM_ID=VERSION_4~X0wRaec7JEvpxxx5sDg2YA==~+eYXLSH9Unvp+3pgrrq+qPWyMoxwsiAKsykGdKAZ+65s/sapy4aeNCDRg6nzd95pX/ZDbwBuBfzi8pGgHasulA3ylOFqxCx0gK1MGeHbEcFuIuQd5JXJNy0rtV4jvDHIWlL/oaUv3oy+94/gmLGTg8ea76tqdCDdn1gkPpctOQAmRcoaVTEdFJSqrqSnyE06yFxy4GAdvjWF1L2drwYRXjzhkmwJ4UB1kqQlHKIjJwffNWlrwI4FbaWCOtquaUnb0C2m74x9roknh6d0Jq6eQA==; "+
                    "MOSEPSESSIONID=tT2o4pOBWRU3MAFqtzZspYcD9LmpCjG4m9Re5o4agF6SFZcOrOeA!-2106168626";
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
        String emergency=rawData.contains("Read only Checkbox Not Checked")?"No":"Yes";
        System.out.println("Emergency: "+emergency);
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