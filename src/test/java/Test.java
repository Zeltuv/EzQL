import tr.zeltuv.ezql.objects.*;
import tr.zeltuv.ezql.settings.EzqlCredentials;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class Test {

    public static void main(String[] args) {
        EzqlCredentials ezqlCredentials = new EzqlCredentials(
                "localhost",
                "ezql",
                "root",
                "",
                10,
                3306,
                true
        );

        EzqlDatabase ezqlDatabase = new EzqlDatabase(ezqlCredentials);

        ezqlDatabase.connect();

        EzqlTable ezqlTable = ezqlDatabase.addTable("test",
                EzqlColumn.get(DataType.VARCHAR, "uuid", 36, true),
                EzqlColumn.get(DataType.VARCHAR, "username", 16),
                EzqlColumn.get(DataType.TIMESTAMP, "last_connection"),
                EzqlColumn.get(DataType.INT, "age")
        );


        String uuid = UUID.randomUUID().toString();

        ezqlTable.pushRow(uuid, "Zeltuv", new Timestamp(new Date().getTime()), 17);

        ezqlTable.updateRow("username","Zeltuv",new EzqlRow("age",15));

        ezqlDatabase.disconnect();
    }
}
