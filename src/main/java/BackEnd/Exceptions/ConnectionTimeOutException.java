/*
 * @AUTHOR Felix
 */

package BackEnd.Exceptions;

public class ConnectionTimeOutException extends Exception{

    public ConnectionTimeOutException(String message){
        super(message);
    }

    public ConnectionTimeOutException(String message, Throwable cause){
        super(message, cause);
    }

    public ConnectionTimeOutException(Throwable cause){
        super(cause);
    }

    public ConnectionTimeOutException(){
        super();
    }


}
