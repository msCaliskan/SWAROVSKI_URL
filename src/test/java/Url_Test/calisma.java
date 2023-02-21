package Url_Test;

import Utilities.Driver;
import Utilities.ExcelUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.io.*;
import static io.restassured.RestAssured.given;


public class calisma {


    public String[] readExcelFile(){


        ExcelUtil qa3short = new ExcelUtil("src/test/resources/Category.xlsx","Sheet1");

        String [][] dataArray = qa3short.getDataArray();
        String [] allURL=new String[qa3short.rowCount()];

        for (int i = 0; i < allURL.length; i++) {
            allURL[i]=dataArray[i][0].trim();
        }
        return allURL;

    }

@Test
    public void statusCheck() throws IOException {

String[] urls=readExcelFile();
    Writer writer = null;
    writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream("Test_results/Url_resultsAll.txt"), "utf-8"));


    for (int i = 0; i < urls.length; i++) {
        Response response = given().header("User-Agent", "PostmanRuntime/7.6.0").accept(ContentType.TEXT).accept(ContentType.HTML)
                .when().get(urls[i]);

        writer.write(response.statusCode()+"--->"+urls[i]+"   ");

        Driver.get().get(urls[i]);
        String url= Driver.get().getCurrentUrl();

//        if(urls[i]!=url) {
//            Response response1 = given().header("User-Agent", "PostmanRuntime/7.6.0").accept(ContentType.TEXT).accept(ContentType.HTML)
//                    .when().get(url);
//
//            writer.write(response1.statusCode() + "--->" + url + "  ");
//        }
        writer.write("\n");

    }


    writer.close();




}


}
