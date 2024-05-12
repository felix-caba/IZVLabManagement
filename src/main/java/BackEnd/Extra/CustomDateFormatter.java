/*
 * @AUTHOR Felix
 */

package BackEnd.Extra;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CustomDateFormatter {


    private static final String REGEX_MMYYYY = "\\d{1,2}-\\d{4}";
    private static final String REGEX_DDMMYYYY = "\\d{1,2}-\\d{1,2}-\\d{4}";
    private static final String[] REGEX_A_CHEQUEAR = {REGEX_MMYYYY, REGEX_DDMMYYYY};
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d-M-yyyy");


    private static String[] aplicarRegex(String input) {

        List<String> ocurrencias = new ArrayList<>();


            // mira si es o una u otra

            Pattern pattern = Pattern.compile(String.join("|", REGEX_A_CHEQUEAR));
            Matcher matcher = pattern.matcher(input);


            while (matcher.find()) {

                // mientras haya finds dentro de la cadena, sigue sacando y las mete. Solo hace una comprobación

                ocurrencias.add(matcher.group());

            }





        String[] ocurrenciasArr= new String[ocurrencias.size()];

        return ocurrencias.toArray(ocurrenciasArr);

    }


    public static LocalDate formatear(String input) {


        String[] ocurrencias = aplicarRegex(input);

        if (ocurrencias.length == 0){
            return null;
        }


        LocalDate[] fechaFormateada = new LocalDate[ocurrencias.length];




        for (int i = 0; i < ocurrencias.length ; i++) {

            if (ocurrencias[i].matches(REGEX_MMYYYY)) {

                // SI ES MM-YYYY le meto un 01- como predeterminau

                ocurrencias[i] = ("01-" + ocurrencias[i]);

                fechaFormateada[i] = LocalDate.parse(ocurrencias[i], FORMATTER);

            } else if (ocurrencias[i].matches(REGEX_DDMMYYYY)) {

                fechaFormateada[i] = LocalDate.parse(ocurrencias[i], FORMATTER);

            } else {

                return null;

            }

        }

        // sort para mostrar la menor

        fechaFormateada = Arrays.stream(fechaFormateada).sorted().toArray(LocalDate[]::new);

        return fechaFormateada[0];

    }


    @Deprecated
    public static LocalDate convertToLocalDate(String date) {


        if (date == null) {
            return null;
        }

        try {

            String[] parts = date.split("-");

            System.out.println(parts.length);

            if (parts.length >= 4) {

                return null;

            } else if (parts.length > 2) {


                System.out.println("parte 2:" + parts[2] + " " +  "parte 1" +parts[1] + " " + parts[0]);

                return LocalDate.of(Integer.parseInt(parts[2]), Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));

            } else if (parts.length > 1) {

                System.out.println(parts[1] + " " + parts[0]);

                return LocalDate.of(Integer.parseInt(parts[1]), Integer.parseInt(parts[0]), 1);

            } else {

                return null;

            }

        } catch (Exception e) {

            return null;

        }

    }




}
