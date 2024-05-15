/*
 * @AUTHOR Felix
 */

package BackEnd.Extra;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConseguirCampos {


    /*

      MEJORA DE MI METODO GETCOLUMN NAMES. ANTES LO HACIA SOLO EN OBJETOS TIPO PRODUCTO. CON REFLEXION, LO HACE EN TODOS
      SACA LOS CAMPOS DECLARADOS DE UN CLASS SIN PARAMETRIZADO (DESCONOCIDO)
      LOS METE EN UN ARRAYLIST. PRIMERO SACA LOS DEL PADRE, LUEGO LOS DEL HIJO

    */

        public static String[] getColumnNames(Class<?> clase) {

            ArrayList<String> columnNamesList = new ArrayList<>();

            // CLASE PADRE (SUPERCLASE)

            Class<?> superClass = clase.getSuperclass();
            if (superClass != null) {
                // AGOTAR LAS ENTRADAS POR SI ACASO HAY MAS PADRES
                Field[] superFields = superClass.getDeclaredFields();
                // CAMPOS DEL PADRE
                for (Field field : superFields) {
                    columnNamesList.add(field.getName());
                }

            }

            // TERMINA CUANDO NO HAY MAS PADRES (NULL)

            // CAMPOS DEL HIJO
            Field[] fields = clase.getDeclaredFields();
            for (Field field : fields) {
                columnNamesList.add(field.getName());
            }

            return columnNamesList.toArray(new String[0]);
        }

}
