package sst.licences.main;

import sst.licences.model.Country;
import sst.licences.model.CountryList;

public class LicencesConstants {

    public static final Country DEFAULT_PAYS_CODE = CountryList.belgium;
    public static final String DEFAULT_LANGUE = "FR";
    public static final String APPLICATION_TITLE = "Berger Club Arlonais - Licences";
    public static final String ENV_TEST_MODE = "TEST_MODE";
    public static final String ENV_MAIL_PWD = "MAIL_PWD";
    public static final String CHARSET_ANSI_CP_1252 = "Cp1252";

    private LicencesConstants() {
    }

    public static final String WORKING_FOLDER = "C:\\Users\\steph\\OneDrive\\Documents\\bca\\membres\\";
    public static final String DATA_LEDEN_MEMBRES_TEMPLATE_CSV = WORKING_FOLDER + "leden-membres-template.csv";
    public static final String MEMBRES_JSON_FILE = WORKING_FOLDER + "membres.json";
    public static final String COMITE_JSON_FILE = WORKING_FOLDER + "comite.json";

    public static final String REPORT_FOLDER = WORKING_FOLDER + "Reports";

    public static final String NEW_MEMBRES_XLSX = WORKING_FOLDER + "new-membres{date}.xlsx";
    public static final String ALL_MEMBRES_XLSX = WORKING_FOLDER + "all-membres{date}.xlsx";
}