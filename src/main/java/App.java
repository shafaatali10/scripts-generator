import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class App {
    public static void main(String args[]) throws IOException
    {
        //obtaining input bytes from a file
        App tc = new App();

        InputStream fis = tc.getClass().getClassLoader().getResourceAsStream("Data_Dictionary.xlsx");

        //creating workbook instance that refers to .xls file
        XSSFWorkbook wb=new XSSFWorkbook(fis);
        //creating a Sheet object to retrieve the object
        XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object
        Iterator<Row> itr = sheet.iterator();    //iterating over excel file

        String prevTableName = "";

        int rowCount = 0;

        StringBuilder sb = new StringBuilder();
        StringBuilder foreignKeyBuilder = new StringBuilder();

        while (itr.hasNext())
        {

            Row row = itr.next();

            // Skip first row. It's header
            if(rowCount == 0){
                rowCount++;
                continue;
            }

            String tempTableName = "";



            tempTableName = row.getCell(0).getStringCellValue();

            if(tempTableName !=null && tempTableName.length() > 0){

                if(!tempTableName.equals(prevTableName)){


                    System.out.println(_finishOffCreate(sb.append(foreignKeyBuilder.toString())));
                    sb = new StringBuilder();
                    foreignKeyBuilder = new StringBuilder();


                    sb.append(Constants.CREATE_TABLE +
                            tempTableName +
                            Constants.BRACKET_OPEN +
                            Constants.NEW_LINE);

                    sb.append(Constants.constructColumn(
                            "id",
                            "bigint,\n"
                    ));


                    sb.append(Constants.constructColumn(
                            row.getCell(1).getStringCellValue(),
                            row.getCell(2).getStringCellValue()
                    ));
                    sb.append(Constants.COMMA + Constants.NEW_LINE);

                    _buildForeignKeys(row, foreignKeyBuilder);

                    prevTableName = tempTableName;
                }else{
                    sb.append(Constants.constructColumn(
                            row.getCell(1).getStringCellValue(),
                            row.getCell(2).getStringCellValue()
                    ));

                    _buildForeignKeys(row, foreignKeyBuilder);

                    sb.append(Constants.COMMA + Constants.NEW_LINE);
                }

            }
            rowCount++;
        }
    }

    private static void _buildForeignKeys(Row row, StringBuilder foreignKeyBuilder) {
        // Check for any foreign keys
        if( "Y".equalsIgnoreCase(row.getCell(3).getStringCellValue()) ){
            String currentTableName = row.getCell(0).getStringCellValue();
            String parentTableName = row.getCell(4).getStringCellValue();

            foreignKeyBuilder.append(
                    Constants.SPACE + Constants.SPACE + Constants.SPACE +

                    Constants.CONSTRAINT + Constants.SPACE +

                    (Constants.FK_PREFIX + Constants.UNDERSCORE +
                            currentTableName.substring(0,8) + Constants.UNDERSCORE +
                            parentTableName.substring(0,8) +
                            Constants.SPACE + Constants.NEW_LINE ) +

                    Constants.SPACE + Constants.SPACE + Constants.SPACE + Constants.SPACE +

                    Constants.FOREIGN_KEY+

                    (Constants.BRACKET_OPEN + row.getCell(1).getStringCellValue() + Constants.BRACKET_CLOSE)+ Constants.SPACE +

                    Constants.REFERENCES + Constants.SPACE +

                    (row.getCell(5).getStringCellValue() + Constants.BRACKET_OPEN + row.getCell(1).getStringCellValue() + Constants.BRACKET_CLOSE)+

                    Constants.COMMA + Constants.NEW_LINE);
        }
    }

    private static String _finishOffCreate(StringBuilder createStatement){

        if(createStatement.length() > 0){
//            System.out.println(createStatement.toString());
            int lastIndexOfComma=createStatement.lastIndexOf(Constants.COMMA);

            createStatement.deleteCharAt(lastIndexOfComma);

            createStatement.append( Constants.BRACKET_CLOSE + Constants.SEMI_COLON + Constants.NEW_LINE);


            createStatement.replace(lastIndexOfComma, lastIndexOfComma, "");
            return createStatement.toString();
        }
        return "";
    }
}
