package BackEnd;

public class MySQL{

    // Singleton Patron

    private static MySQL instance = null;

    private MySQL() {

    }

    public static MySQL getInstance() {
        if (instance == null) {
            instance = new MySQL();
        }
        return instance;
    }

    public void connect() {
        // TODO Hacer Conexión
    }

    public void disconnect() {
        // TODO Hacer DesConexión
    }

}
