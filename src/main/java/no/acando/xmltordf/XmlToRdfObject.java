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


public class XmlToRdfObject {

    Builder.ObjectBased builder;

    public XmlToRdfObject(Builder.ObjectBased builder) {
        this.builder = builder;
    }

    public void objectBasedConvertToStream(InputStream in, OutputStream out) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);


        SAXParser saxParser = factory.newSAXParser();

        ObjectBasedSaxHandler handler = new ObjectBasedSaxHandler(out, builder);


        saxParser.parse(in, handler);


    }


    public Dataset objectBasedConvertToDataset(InputStream in) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);


        SAXParser saxParser = factory.newSAXParser();

//        ObjectBasedSaxHandler2 handler = new ObjectBasedSaxHandler2(builder);
        ObjectBasedSaxHandlerJena handler = new ObjectBasedSaxHandlerJena(builder);


        saxParser.parse(in, handler);
        return handler.dataset;
    }


    public Repository objectBasedConvertToRepository(InputStream in) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);


        SAXParser saxParser = factory.newSAXParser();

        ObjectBasedSaxHandlerSesame handler = new ObjectBasedSaxHandlerSesame(builder);


        saxParser.parse(in, handler);
        return handler.repository;
    }




    public PostProcessingJena convertForPostProcessingJena(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        Model model = objectBasedConvertToDataset(inputStream).getDefaultModel();
        inputStream.close();
        return new PostProcessingJena(model);
    }

    public PostProcessingSesame convertForPostProcessingSesame(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        Repository repository = objectBasedConvertToRepository(inputStream);
        inputStream.close();
        return new PostProcessingSesame(repository);
    }
}
