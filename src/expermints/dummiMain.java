/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expermints;

import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.activation.MimetypesFileTypeMap;

/**
 *
 * @author stefandanielsen
 */
public class dummiMain {

    public static void main(String[] args) throws FileNotFoundException {
    final FileInputStream fis = new FileInputStream("C:/Users/Daniel Jensen/mime.types");
    final MimetypesFileTypeMap mFTMap = new MimetypesFileTypeMap(fis);
        System.out.println(mFTMap.getContentType("lsakls.wee"));
    
    
}
}