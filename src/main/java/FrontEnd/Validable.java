/*
 * @AUTHOR Felix
 */

package FrontEnd;

public interface Validable {



    default boolean isEmpty() {


        for (java.lang.reflect.Field field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (field.get(this) instanceof javax.swing.JTextField) {
                    if (((javax.swing.JTextField) field.get(this)).getText().isEmpty()) {
                        return true;
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                return true;
            }
        }

        return false;


    }


}
