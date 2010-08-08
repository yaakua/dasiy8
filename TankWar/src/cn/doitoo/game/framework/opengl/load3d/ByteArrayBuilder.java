package cn.doitoo.game.framework.opengl.load3d;

import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A utility set which will build byte arrays from specified data types.
 *
 * @author Kevin Glass
 */
public class ByteArrayBuilder {

    /**
     * A static utility to completly read an input stream and 
     * produce a byte array of the read data
     */
    public static byte[] fromStream(InputStream in) throws IOException {
        boolean finished = false;
        ByteArrayOutputStream store = new ByteArrayOutputStream();
        
        while (!finished) {
            byte[] b = new byte[in.available()];
            
            int ret = in.read(b);
            if (ret <= 0)
                finished = true;
            else
                store.write(b);
        }
        
        return store.toByteArray();
    }

}
