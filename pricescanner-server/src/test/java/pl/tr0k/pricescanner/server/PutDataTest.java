package pl.tr0k.pricescanner.server;

import org.apache.commons.io.IOUtils;
import org.springframework.web.client.RestTemplate;
import pl.tr0k.pricescanner.common.dto.ProductDTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

public class PutDataTest {

    public static final String SERVER_URL = "http://localhost:8080/pricescanner/";

    public static final String POST_PRODUCT_URL = SERVER_URL + "products";

    public static final String POST_IMAGE_URL = SERVER_URL + "products/{ean}/image";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        String[] str1 = {"Pomidory", "Ogórki", "Kukurydza", "Groszek", "Kuskus", "Kasza", "Kabaczek"};

        String[] str2 = {"zielony", "czerwony", "niebieski", "różowy", "pomarańczowy", "żółty"};

        String[] str3 = {"długi", "krótki", "średni"};

        Long imageId = new Long(132);

        for (String s : str1) {
            for (String s1 : str2) {
                for (String s2 : str3) {
                    String name = s + " " + s1 + " " + s2;
                    restTemplate.postForObject(POST_PRODUCT_URL, new ProductDTO(name, name + " " + new Random().nextInt(1000) + "g", (long) new Random().nextInt(2000), String.valueOf(new Random().nextInt(200000)), imageId), ProductDTO.class);

                }
            }
        }

        restTemplate.postForObject(POST_PRODUCT_URL, new ProductDTO("Pomidory", "Pomidory w puszcze 200g, Łowicz", 490L, "006", imageId), ProductDTO.class);
        restTemplate.postForObject(POST_PRODUCT_URL, new ProductDTO("Groszek", "Groszek w puszcze 200g, Łowicz", 290L, "003", imageId), ProductDTO.class);
        restTemplate.postForObject(POST_PRODUCT_URL, new ProductDTO("Kukurydza", "Kukurydza w puszcze 500g, Łowicz", 890L, "004", imageId), ProductDTO.class);

        try {
            byte[] bytes = IOUtils.toByteArray(new FileInputStream(new File("C:/Tools/pomidory.png")));
            restTemplate.postForObject(POST_IMAGE_URL, bytes, byte[].class, "004");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
