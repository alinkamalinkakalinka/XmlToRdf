import no.acando.xmltordf.Builder;
import org.apache.commons.io.IOUtils;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.query.Dataset;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class Main {


    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, URISyntaxException {

        String url = "http://www.psa.no/getfile.php/PDF/DDRS_example.xml";

        String xml = IOUtils.toString(new URI(url));
        System.out.println(xml);

        ByteArrayInputStream reportingHubXml = new ByteArrayInputStream(IOUtils.toString(new URI(url)).getBytes());

        String witsml = "http://www.witsml.org/schemas/1series#";

        Builder.AdvancedJena builder = Builder.getAdvancedBuilderJena()
            .convertComplexElementsWithOnlyAttributesAndSimpleTypeChildrenToPredicate(true)
            .convertComplexElementsWithOnlyAttributesToPredicate(true)
            .renameElement(witsml+"stratInfo", witsml+"StratInfo")
            .insertPredicate(witsml+"info").betweenAnyParentAndSpecificChild(witsml+"StratInfo")

            .autoTypeLiterals(true)

            .renameElement(witsml+"controlIncidentInfo", witsml+"ControlIncidentInfo")
            .insertPredicate(witsml+"info").betweenAnyParentAndSpecificChild(witsml+"ControlIncidentInfo")

            .renameElement(witsml+"lithShowInfo", witsml+"LithShowInfo")
            .insertPredicate(witsml+"info").betweenAnyParentAndSpecificChild(witsml+"LithShowInfo")

            .renameElement(witsml+"wellboreInfo", witsml+"WellboreInfo")
            .insertPredicate(witsml+"info").betweenAnyParentAndSpecificChild(witsml+"WellboreInfo")

            .renameElement(witsml+"formTestInfo", witsml+"FormTestInfo")
            .insertPredicate(witsml+"info").betweenAnyParentAndSpecificChild(witsml+"FormTestInfo")

            .renameElement(witsml+"wellTestInfo", witsml+"WellTestInfo")
            .insertPredicate(witsml+"info").betweenAnyParentAndSpecificChild(witsml+"WellTestInfo")

            .renameElement(witsml+"perfInfo", witsml+"PerfInfo")
            .insertPredicate(witsml+"info").betweenAnyParentAndSpecificChild(witsml+"PerfInfo")

            .renameElement(witsml+"statusInfo", witsml+"StatusInfo")
            .insertPredicate(witsml+"info").betweenAnyParentAndSpecificChild(witsml+"StatusInfo")

            .renameElement(witsml+"logInfo", witsml+"LogInfo")
            .insertPredicate(witsml+"info").betweenAnyParentAndSpecificChild(witsml+"LogInfo")

            .renameElement(witsml+"coreInfo", witsml+"CoreInfo")
            .insertPredicate(witsml+"info").betweenAnyParentAndSpecificChild(witsml+"CoreInfo")

            .renameElement(witsml+"equipFailureInfo", witsml+"EquipFailureInfo")
            .insertPredicate(witsml+"info").betweenAnyParentAndSpecificChild(witsml+"EquipFailureInfo")

            .renameElement(witsml+"gasReadingInfo", witsml+"GasReadingInfo")
            .insertPredicate(witsml+"info").betweenAnyParentAndSpecificChild(witsml+"GasReadingInfo")


            .renameElement(witsml+"fluid", witsml+"Fluid")
            .insertPredicate(witsml+"fluid").betweenAnyParentAndSpecificChild(witsml+"Fluid")

            .renameElement(witsml+"activity", witsml+"Activity")
            .insertPredicate(witsml+"activity").betweenAnyParentAndSpecificChild(witsml+"Activity")

            .renameElement(witsml+"porePressure", witsml+"PorePressure")
            .insertPredicate(witsml+"porePressure").betweenAnyParentAndSpecificChild(witsml+"PorePressure")

            .renameElement(witsml+"bitRecord", witsml+"BitRecord")
            .insertPredicate(witsml+"bitRecord").betweenAnyParentAndSpecificChild(witsml+"BitRecord")

            .renameElement(witsml+"wellDatum", witsml+"WellDatum")
            .insertPredicate(witsml+"wellDatum").betweenAnyParentAndSpecificChild(witsml+"WellDatum")

            .renameElement(witsml+"surveyStation", witsml+"SurveyStation")
            .insertPredicate(witsml+"surveyStation").betweenAnyParentAndSpecificChild(witsml+"SurveyStation")

//            .setDatatype(witsml + "dTim", XSDDatatype.XSDdateTime)
//            .setDatatype(witsml + "createDate", XSDDatatype.XSDdateTime)
//            .setDatatype(witsml + "dTimStart", XSDDatatype.XSDdateTime)
//            .setDatatype(witsml + "dTimEnd", XSDDatatype.XSDdateTime)
//            .setDatatype(witsml + "dTimRegained", XSDDatatype.XSDdateTime)
//            .setDatatype(witsml + "dTimClose", XSDDatatype.XSDdateTime)
//            .setDatatype(witsml + "dTimOpen", XSDDatatype.XSDdateTime)
//            .setDatatype(witsml + "dTimPreSpud", XSDDatatype.XSDdateTime)
//            .setDatatype(witsml + "dTimSpud", XSDDatatype.XSDdateTime)
//            .setDatatype(witsml + "dTimRepair", XSDDatatype.XSDdateTime)
//
//            .setDatatype(witsml + "defaultMeasuredDepth", XSDDatatype.XSDboolean)
            .setDatatype(witsml + "defaultVerticalDepth", XSDDatatype.XSDboolean)


            .mapTextInAttributeToUri(null, witsml+"uom", "m", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS1332674"))
            .mapTextInAttributeToUri(null, witsml+"uom", "bar", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS1314539"))
            .mapTextInAttributeToUri(null, witsml+"uom", "Pa", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS1338749"))
            .mapTextInAttributeToUri(null, witsml+"uom", "g/cm3", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS1325924"))
            .mapTextInAttributeToUri(null, witsml+"uom", "dega", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS43166353217"))
            .mapTextInAttributeToUri(null, witsml+"uom", "in", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS1326959"))
            .mapTextInAttributeToUri(null, witsml+"uom", "ppm", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS1333484"))
            .mapTextInAttributeToUri(null, witsml+"uom", "mm", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS1357739"))
            .mapTextInAttributeToUri(null, witsml+"uom", "MPa", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS1332404"))
            .mapTextInAttributeToUri(null, witsml+"uom", "m3", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS1349099"))
            .mapTextInAttributeToUri(null, witsml+"uom", "m3/d", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS1320839"))
            .mapTextInAttributeToUri(null, witsml+"uom", "m3/m3", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS1320794"))
            .mapTextInAttributeToUri(null, witsml+"uom", "mPa.s", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS11617155"))
            .mapTextInAttributeToUri(null, witsml+"uom", "M(m3)/d", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS17250792"))
            .mapTextInAttributeToUri(null, witsml+"uom", "degC", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS1322684"))
            .mapTextInAttributeToUri(null, witsml+"uom", "M(m3)", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS17251242"))
            .mapTextInAttributeToUri(null, witsml+"uom", "h", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS1326734"))
            .mapTextInAttributeToUri(null, witsml+"uom", "dm3", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS1319174"))
            .mapTextInAttributeToUri(null, witsml+"uom", "min", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS1336814"))
            .mapTextInAttributeToUri(null, witsml+"uom", "%", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS16226040"))
            .mapTextInAttributeToUri(null, witsml+"uom", "m/h", NodeFactory.createURI("http://data.posccaesar.org/rdl/RDS1351349"))


            ;

        Dataset dataset = builder.build().convertToDataset(reportingHubXml);

        dataset.getDefaultModel().write(System.out, "TTL");

    }

}
