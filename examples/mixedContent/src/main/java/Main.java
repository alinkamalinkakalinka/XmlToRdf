/*
Copyright 2016 ACANDO AS

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

import no.acando.xmltordf.Builder;
import org.apache.jena.rdf.model.Model;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {


        Model defaultModel = Builder.getAdvancedBuilderJena()
.forceMixedContent("http://example.org/paragraph")
            .build()
            .convertToDataset(Main.class.getClassLoader().getResourceAsStream("mixedContent.xml"))

            .getDefaultModel()
            .write(System.out, "TTL");



    }

}
