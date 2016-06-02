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

package no.acando.xmltordf;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.openrdf.repository.Repository;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class XmlToRdfAdvancedSesame{

    Builder.AdvancedSesame builder;

    public XmlToRdfAdvancedSesame(Builder.AdvancedSesame builder) {
        this.builder = builder;
    }



    public Repository convertToRepository(InputStream in) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);


        SAXParser saxParser = factory.newSAXParser();

        AdvancedSaxHandlerSesame handler = new AdvancedSaxHandlerSesame(builder);


        saxParser.parse(in, handler);
        return handler.repository;
    }






    public PostProcessingSesame convertForPostProcessingSesame(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        Repository repository = convertToRepository(inputStream);
        inputStream.close();
        return new PostProcessingSesame(repository);
    }
}
