import no.acando.xmltordf.Builder;
import no.acando.xmltordf.ComplexClassTransform;
import org.apache.commons.io.IOUtils;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDFS;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {
	public static void main(String[] args) throws URISyntaxException, IOException, ParserConfigurationException, SAXException {
		String ns = "http://brreg.no/";

		Model naeringskode = ModelFactory.createDefaultModel();

		for (int i = 1; true; i++) {
			ByteArrayInputStream naeringskodeXml = new ByteArrayInputStream(IOUtils.toString(new URI("https://hotell.difi.no/api/xml/brreg/naeringskode?page=" + i)).getBytes());

			Model naeringskodeTemp = Builder.getAdvancedBuilderJena()
					.setBaseNamespace(ns, Builder.AppliesTo.bothElementsAndAttributes)

					.renameElement("http://brreg.no/naerk_tekst", RDFS.label.getURI())
					.renameElement("http://brreg.no/entry", "http://brreg.no/Naeringskode")

					.build().convertForPostProcessing(naeringskodeXml)
					.mustacheTransform(new ByteArrayInputStream(("" +
							"delete{?a ?b ?c. ?d ?e ?a} insert {?newA ?b ?c.} where {?a <http://brreg.no/naerk> ?naerk. ?a ?b ?c. ?d ?e ?a. BIND(IRI(concat(\"{{{namespace}}}\", ?naerk)) as ?newA )}").getBytes()), new Object() {
						String namespace = "http://brreg.no/naeringskode/";
					})
					.mustacheTransform(new ByteArrayInputStream("delete {?a ?b ?c} where {?a ?b ?c. filter(isBlank(?a))}" .getBytes()), new Object())
					.getModel();

			naeringskode.add(naeringskodeTemp);

			if (naeringskodeTemp.isEmpty()) {
				break;
			}

		}


		ByteArrayInputStream orgFormXml = new ByteArrayInputStream(IOUtils.toString(new URI("https://hotell.difi.no/api/xml/brreg/organisasjonsform?")).getBytes());

		Model enhetstyper = Builder.getAdvancedBuilderJena()
				.setBaseNamespace(ns, Builder.AppliesTo.bothElementsAndAttributes)

				.renameElement("http://brreg.no/enhetstype_tekst", RDFS.label.getURI())
				
				.renameElement("http://brreg.no/entry", "http://brreg.no/Enhetstype")

				.build().convertForPostProcessing(orgFormXml)
				.mustacheTransform(new ByteArrayInputStream(("" +
						"delete{?a ?b ?c. ?d ?e ?a} insert {?newA ?b ?c.} where {?a <http://brreg.no/enhetstype> ?enhetsType. ?a ?b ?c. ?d ?e ?a. BIND(IRI(concat(\"{{{namespace}}}\", ?enhetsType)) as ?newA )}").getBytes()), new Object() {
					String namespace = "http://brreg.no/enhetstype/";
				})
				.mustacheTransform(new ByteArrayInputStream("delete {?a ?b ?c} where {?a ?b ?c. filter(isBlank(?a))}" .getBytes()), new Object())
				.getModel();


		ByteArrayInputStream brregXml = new ByteArrayInputStream(IOUtils.toString(new URI("https://hotell.difi.no/api/xml/brreg/enhetsregisteret?")).getBytes());


		ComplexClassTransform convertDate = e -> {
			if (e.hasValue != null) {
				String s = e.hasValue.toString();
				String[] split = s.split("\\.");
				e.hasValue = new StringBuilder(split[2]).append("-").append(split[1]).append("-").append(split[0]);
			}

		};

		ComplexClassTransform convertBoolean = e -> {
			if (e.hasValue != null) {
				String s = e.hasValue.toString();
				if (s.equals("N")) {
					e.hasValue = new StringBuilder("false");
				}
				if (s.equals("J")) {
					e.hasValue = new StringBuilder("true");
				}
			}

		};

		Builder.AdvancedJena brregXmlBuilder = Builder.getAdvancedBuilderJena()
				.setBaseNamespace(ns, Builder.AppliesTo.bothElementsAndAttributes)
				.renameElement("http://brreg.no/entry", "http://brreg.no/Enhet")
				.renameElement("http://brreg.no/nkode1", "http://brreg.no/naeringskode")
				.renameElement("http://brreg.no/nkode2", "http://brreg.no/naeringskode")


				.setDatatype("http://brreg.no/ansatte_antall", XSDDatatype.XSDinteger)
				.addComplexElementTransformAtEndOfElement("http://brreg.no/regdato", convertDate)
				.addComplexElementTransformAtEndOfElement("http://brreg.no/stiftelsesdato", convertDate)
				.addComplexElementTransformAtEndOfElement("http://brreg.no/ansatte_dato", convertDate)

				.setDatatype("http://brreg.no/regdato", XSDDatatype.XSDdate)
				.setDatatype("http://brreg.no/stiftelsesdato", XSDDatatype.XSDdate)
				.setDatatype("http://brreg.no/ansatte_dato", XSDDatatype.XSDdate)

				.addComplexElementTransformAtEndOfElement("http://brreg.no/tvangsavvikling", convertBoolean)
				.setDatatype("http://brreg.no/tvangsavvikling", XSDDatatype.XSDboolean)

				.addComplexElementTransformAtEndOfElement("http://brreg.no/konkurs", convertBoolean)
				.setDatatype("http://brreg.no/konkurs", XSDDatatype.XSDboolean)

				.addComplexElementTransformAtEndOfElement("http://brreg.no/regiaa", convertBoolean)
				.setDatatype("http://brreg.no/regiaa", XSDDatatype.XSDboolean)

				.addComplexElementTransformAtEndOfElement("http://brreg.no/regifr", convertBoolean)
				.setDatatype("http://brreg.no/regifr", XSDDatatype.XSDboolean)

				.renameElement("http://brreg.no/regifriv", "http://brreg.no/registrert-i-frivillighets-register")
				.addComplexElementTransformAtEndOfElement("http://brreg.no/registrert-i-frivillighets-register", convertBoolean)
				.setDatatype("http://brreg.no/registrert-i-frivillighets-register", XSDDatatype.XSDboolean)

				.renameElement("http://brreg.no/regimva", "http://brreg.no/registrert-i-merverdiavgiftsregisteret")
				.addComplexElementTransformAtEndOfElement("http://brreg.no/registrert-i-Merverdiavgiftsregisteret", convertBoolean)
				.setDatatype("http://brreg.no/registrert-i-Merverdiavgiftsregisteret", XSDDatatype.XSDboolean)

				.mapTextInElementToUri(ns + "forradrland", "Norge", NodeFactory.createURI("http://dbpedia.org/resource/Norway"));


		StmtIterator resIterator = enhetstyper.listStatements(null, enhetstyper.getProperty("http://brreg.no/enhetstype"), (RDFNode) null);
		while (resIterator.hasNext()) {
			Statement statement = resIterator.nextStatement();
			brregXmlBuilder.mapTextInElementToUri("http://brreg.no/organisasjonsform", statement.getObject().toString(), statement.getSubject().asNode());
		}

		StmtIterator naeringskodeIterator = naeringskode.listStatements(null, enhetstyper.getProperty("http://brreg.no/naerk"), (RDFNode) null);
		while (naeringskodeIterator.hasNext()) {
			Statement statement = naeringskodeIterator.nextStatement();
			brregXmlBuilder.mapTextInElementToUri("http://brreg.no/naeringskode", statement.getObject().toString(), statement.getSubject().asNode());

		}

		brregXmlBuilder
				.build().convertForPostProcessing(brregXml)
				.mustacheTransform(new ByteArrayInputStream(("" +
						"delete{?a ?b ?c. ?d ?e ?a} insert {?newA ?b ?c. ?d ?e ?newA} where {?a <http://brreg.no/orgnr> ?orgnr. ?a ?b ?c. ?d ?e ?a. BIND(IRI(concat(\"{{{namespace}}}\", ?orgnr)) as ?newA )}").getBytes()), new Object() {
					String namespace = "http://brreg.no/";
				})
				.getModel()
				.add(enhetstyper)
				.add(naeringskode)
				.write(System.out, "TTL");

	}

}
