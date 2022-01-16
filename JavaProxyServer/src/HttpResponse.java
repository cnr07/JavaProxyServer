public class HttpResponse {
    int statusCode;
    String reasonPhrase;
    MimeHeader mh;

    public HttpResponse(String response) {
        // ADD -> parse the http response first line
    	int firstLineEnd=response.indexOf("\n");
    	String firstLinee=response.substring(0,firstLineEnd);
    	
    	int firstLineFirstEmpty=firstLinee.indexOf(' ');
    	int firstLineSecondEmpty=firstLinee.indexOf(' ',firstLineFirstEmpty+1);
        String sc=firstLinee.substring(firstLineFirstEmpty+1,firstLineSecondEmpty);
    	statusCode =Integer.parseInt(sc);//ADD -> get statusCode from first line
        reasonPhrase =firstLinee.substring(firstLineSecondEmpty+1); //ADD -> get reasonPhrase from first line
        String raw_mime_header = response.substring(firstLineEnd+1);//ADD-> the rest of the response
        mh = new MimeHeader(raw_mime_header);
    }

    public HttpResponse(int code, String reason, MimeHeader m) {
        statusCode = code;
        reasonPhrase = reason;
        mh = m;
        mh.put("Connection", "close");
    }

    public String toString() {
        return "HTTP/1.1 " + statusCode + " " + reasonPhrase + "\r\n" + mh + "\r\n";
    }
}