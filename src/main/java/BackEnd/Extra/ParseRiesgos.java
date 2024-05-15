/*
 * @AUTHOR Felix
 */

package BackEnd.Extra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseRiesgos {


    private final static String REGEX_ATENCION = "(?i)Atención";
    private final static String REGEX_INFLAMABLE = "(?i)Inflamable";
    private final static String REGEX_CORROSIVO = "(?i)Corrosivo";
    private final static String REGEX_TOXICIDAD = "(?i)Toxicidad aguda";
    private final static String REGEX_PELIGROSO_MEDIOAMBIENTE = "(?i)Peligroso para el medio ambiente";
    private final static String REGEX_NOCIVO = "(?i)Nocivo";
    private final static String REGEX_COMBURENTE = "(?i)Comburente";
    private final static String REGEX_IRRITANTE = "(?i)Irritante";
    private final static String REGEX_CARCINOGENO = "(?i)(Cancer[ií]geno|Carcin[oó]geno)";


    private final static String[] REGEX_ARR = {REGEX_ATENCION, REGEX_INFLAMABLE, REGEX_CORROSIVO,
            REGEX_TOXICIDAD, REGEX_PELIGROSO_MEDIOAMBIENTE, REGEX_NOCIVO, REGEX_COMBURENTE, REGEX_IRRITANTE, REGEX_CARCINOGENO};

    public static RIESGOS[] parseRiesgos(String riesgos) {

        ArrayList<RIESGOS> riesgosOcurrencias = new ArrayList<>();

        for (String regex : REGEX_ARR) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(riesgos);
            while (matcher.find()) {


                switch (regex) {
                    case REGEX_ATENCION:
                        riesgosOcurrencias.add(RIESGOS.ATENCION);
                        break;
                    case REGEX_INFLAMABLE:
                        riesgosOcurrencias.add(RIESGOS.INFLAMABLE);
                        break;
                    case REGEX_CORROSIVO:
                        riesgosOcurrencias.add(RIESGOS.CORROSIVO);
                        break;
                    case REGEX_TOXICIDAD:
                        riesgosOcurrencias.add(RIESGOS.TOXICIDAD);
                        break;
                    case REGEX_PELIGROSO_MEDIOAMBIENTE:
                        riesgosOcurrencias.add(RIESGOS.PELIGROSO_MEDIOAMBIENTE);
                        break;
                    case REGEX_NOCIVO:
                        riesgosOcurrencias.add(RIESGOS.NOCIVO);
                        break;
                    case REGEX_COMBURENTE:
                        riesgosOcurrencias.add(RIESGOS.COMBURENTE);
                        break;
                    case REGEX_IRRITANTE:
                        riesgosOcurrencias.add(RIESGOS.IRRITANTE);
                        break;
                    case REGEX_CARCINOGENO:
                        riesgosOcurrencias.add(RIESGOS.CANCERIGENO);
                        break;
                }



            }
        }




        RIESGOS[] riesgosArr = new RIESGOS[riesgosOcurrencias.size()];
        riesgosArr = riesgosOcurrencias.toArray(riesgosArr);

        System.out.println(Arrays.toString(riesgosArr));

        return riesgosArr;


    }






}
