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
        String myCookies="BIGipServerprod-support.us.oracle.com_http_pool=3853730968.24862.0000; "+
                    "JSESSIONID=9347132dc6adad6ecec986821488b63a04f96c52f70c1279b5506cfd015821cb.e34NchyKahmRbO0Lch0Nb3yQax4Oe0; "+
                    "OHS-support.us.oracle.com-443=240D2A66F4509B1FD169C7501BDE6623AF728AAC8B3FB571D8195A76811465B16449EB2A6E7275AF9CB14F8DF2242DC0D7ADA9EAC41DD69404892D8B62E886D6369E95230EF6D6298A304922CF824EF89489AE6345696B4CE5BD4FA0A44F2F0D85991CD1A3F84F12F27FBDAC72D80EFD1B33B8881D2354399FE8425F096B8B7820999623D58E28F8FE1317A0DB3E51DF8882B901FD30B295211C9AC521F7E28EBACDA0DBE649936D9E583AD6AD0B1D769F5D51DC6964370E752F74A9F3356653DA8BE8744329B67C5538C9E34F603F091AD55AE57F3C3FF1013239877316745E7F590BA184AE0C0C1056BF73B77B52E9B2D20F4FAA795BDD163ADB2A90D2ACCB4C4908EB24DE3E21; "+
                    "mos.presence=91852950-c869-de7b-71fc-1f7dd5128265%7C1449989486833; "+
                    "oracle.uix=0^^GMT+5:30; "+
                    "OAM_ID=VERSION_4~zfi0GBoqsRGKKH6aeSe33Q==~1iHzYg7TwrYsT34AJtyFmjKtdL6tQer+WYZ7gZayCBG9esCPFg7Pd5X2fk+AFVcbVD3EPwAGT0iuZQKxkGrk2/XvmE9KWv8rfqTuDwzMaWy6m8K0JePFEX0M7uXL3bFCb+93P4F2k3coODQbOgawAChuLCB8H5+0jsxSCC+s7OwTHN54L+iIUtF0t3CGp97BaFyljyTorVr8vWsv5aaXu8eHy7QeVouEpgUstAvL8+N11+G2Y1aJdGlDUvO/UHoNhuXwhxwyKy14+S6lCJHllQ==; "+
                    "MOSEPSESSIONID=sGaZxKhKvrG2QnmQRB8YdVFr5SfXvYGJBF-ao5bg0Ltn8COfL8vg!-466099770";
        con.setRequestProperty("Cookie",myCookies);
        con.setRequestMethod("GET");
        
        con.connect();
	int responseCode=con.getResponseCode();
        
	System.out.println("\nSending 'GET' request to URL : "+url);
	System.out.println("Response Code : "+responseCode);

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