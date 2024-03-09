package helpers;

import com.exa.unicen.arbolado.domain.Arbol;
import java.util.List;

// import org.apache.commons.csv.CSVFormat;
// import org.apache.commons.csv.CSVPrinter;
// import org.apache.commons.csv.QuoteMode;

public class CSVHelper {

    public static boolean tutorialsToCSV(List<Arbol> arboles) {
        // final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        // try (
        // ByteArrayOutputStream out = new ByteArrayOutputStream();
        // CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);
        // ) {
        // for (Arbol arbol : arboles) {
        // List<String> data = Arrays.asList(String.valueOf(arbol.getId()),
        // arbol.getEstado(), arbol.getDescripcion());

        // csvPrinter.printRecord(data);
        // }

        // csvPrinter.flush();
        // return new ByteArrayInputStream(out.toByteArray());
        return false;
        // } catch (IOException e) {
        // throw new RuntimeException("fail to import data to CSV file: " +
        // e.getMessage());
        // }
    }
}
