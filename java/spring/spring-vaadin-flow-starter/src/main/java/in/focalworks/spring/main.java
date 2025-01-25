package in.focalworks.spring;

import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.word.WordDocument;
import java.net.InetAddress;

public class Qoppa {
    public static void main(String [] args) throws Exception{

        System.out.println(PDFDocument.getVersion());
        System.out.println(WordDocument.getVersion());
        System.out.println(InetAddress.getLocalHost().getHostName());
        System.out.println(Runtime.getRuntime().availableProcessors());

        PDFDocument.KeyInfoProcess.main(new String [] {"-validatekey", "5u23vvjuk3ad1i3p9qbdc8m7hc"});

        System.out.println(PDFDocument.getVersion());
        System.out.println(WordDocument.getVersion());

    }
}

