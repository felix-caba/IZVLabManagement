package BackEnd.Extra;/*
 * @AUTHOR Felix
 */
import javax.swing.text.DefaultFormatter;
import java.util.Locale;



    public class CapitalizedColumnNameFormatter extends DefaultFormatter {

        @Override
        public String valueToString(Object value) {
            if (value == null || !(value instanceof String)) {
                return null;
            }

            String columnName = (String) value;
            StringBuilder formattedName = new StringBuilder();

            boolean isFirstChar = true;
            boolean previousCharWasLower = false;

            for (char c : columnName.toCharArray()) {
                if (isFirstChar) {
                    c = Character.toUpperCase(c);
                    isFirstChar = false;
                } else if (previousCharWasLower && Character.isUpperCase(c)) {
                    formattedName.append(' ');
                    c = Character.toUpperCase(c);
                }

                formattedName.append(c);
                previousCharWasLower = Character.isLowerCase(c);
            }

            return formattedName.toString();
        }

        @Override
        public Object stringToValue(String string) {
            // No es necesario implementar este método para este caso de uso
            return string;
        }
    }




