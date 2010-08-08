package cn.doitoo.game.framework.opengl.load3d;

/**
 * Thrown when an error occurs during loading a model
 *
 * @author Kevin Glass
 */
public class ModelLoadingException extends Exception {
    /**
     * Constructs an <code>ModelLoadingException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ModelLoadingException(String msg) {
        super(msg);
    }
}


