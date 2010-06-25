package cn.doitoo.game.framework.exception;

public class ViewException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String message;

    public ViewException(String message) {
        super(message);
        this.message=message;
    }
    
    @SuppressWarnings("unchecked")
	public ViewException(Class c,String message){
    	super(message);
    	String className = "";
    	if(c!=null)
    		 className = c.getName();
        this.message=className+":"+message;
    }
    
    public ViewException(String message,Throwable cause) {
    	super(message,cause);
        this.message=message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
