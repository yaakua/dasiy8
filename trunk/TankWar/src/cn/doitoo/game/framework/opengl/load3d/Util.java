package cn.doitoo.game.framework.opengl.load3d;


import java.io.IOException;

/**
 * A utility group
 *
 * @author Kevin Glass 
 */
public class Util {
    /**
     * Read a null terminated string
     *
     * @param in Where to read from
     * @param l The number of bytes to read
     * @return The string read
     */
    static String readString(LittleEndianDataInputStream in,int l) throws IOException {        
        byte[] temp = new byte[l];
        in.read(temp,0,l);
        
        int len = l;
        for (int i=0;i<l;i++) {
            if (temp[i] == 0) {
                len = i;
                break;
            }
        }
        
        return new String(temp,0,len);
    }
}
