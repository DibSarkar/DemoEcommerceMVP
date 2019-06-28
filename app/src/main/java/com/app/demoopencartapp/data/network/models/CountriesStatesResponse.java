package com.app.demoopencartapp.data.network.models;

import java.util.List;

public class CountriesStatesResponse {


    /**
     * responseCode : 1
     * responseText : Successfully Data Found.
     * data : [{"id":"1","name":"Afghanistan"},{"id":"2","name":"Albania"},{"id":"3","name":"Algeria"},{"id":"4","name":"American Samoa"},{"id":"5","name":"Andorra"},{"id":"6","name":"Angola"},{"id":"7","name":"Anguilla"},{"id":"8","name":"Antarctica"},{"id":"9","name":"Antigua and Barbuda"},{"id":"10","name":"Argentina"},{"id":"11","name":"Armenia"},{"id":"12","name":"Aruba"},{"id":"13","name":"Australia"},{"id":"14","name":"Austria"},{"id":"15","name":"Azerbaijan"},{"id":"16","name":"Bahamas"},{"id":"17","name":"Bahrain"},{"id":"18","name":"Bangladesh"},{"id":"19","name":"Barbados"},{"id":"20","name":"Belarus"},{"id":"21","name":"Belgium"},{"id":"22","name":"Belize"},{"id":"23","name":"Benin"},{"id":"24","name":"Bermuda"},{"id":"25","name":"Bhutan"},{"id":"26","name":"Bolivia"},{"id":"27","name":"Bosnia and Herzegovina"},{"id":"28","name":"Botswana"},{"id":"29","name":"Bouvet Island"},{"id":"30","name":"Brazil"},{"id":"31","name":"British Indian Ocean Territory"},{"id":"32","name":"Brunei Darussalam"},{"id":"33","name":"Bulgaria"},{"id":"34","name":"Burkina Faso"},{"id":"35","name":"Burundi"},{"id":"36","name":"Cambodia"},{"id":"37","name":"Cameroon"},{"id":"38","name":"Canada"},{"id":"39","name":"Cape Verde"},{"id":"40","name":"Cayman Islands"},{"id":"41","name":"Central African Republic"},{"id":"42","name":"Chad"},{"id":"43","name":"Chile"},{"id":"44","name":"China"},{"id":"45","name":"Christmas Island"},{"id":"46","name":"Cocos (Keeling) Islands"},{"id":"47","name":"Colombia"},{"id":"48","name":"Comoros"},{"id":"49","name":"Congo"},{"id":"50","name":"Cook Islands"},{"id":"51","name":"Costa Rica"},{"id":"52","name":"Cote D'Ivoire"},{"id":"53","name":"Croatia"},{"id":"54","name":"Cuba"},{"id":"55","name":"Cyprus"},{"id":"56","name":"Czech Republic"},{"id":"57","name":"Denmark"},{"id":"58","name":"Djibouti"},{"id":"59","name":"Dominica"},{"id":"60","name":"Dominican Republic"},{"id":"61","name":"East Timor"},{"id":"62","name":"Ecuador"},{"id":"63","name":"Egypt"},{"id":"64","name":"El Salvador"},{"id":"65","name":"Equatorial Guinea"},{"id":"66","name":"Eritrea"},{"id":"67","name":"Estonia"},{"id":"68","name":"Ethiopia"},{"id":"69","name":"Falkland Islands (Malvinas)"},{"id":"70","name":"Faroe Islands"},{"id":"71","name":"Fiji"},{"id":"72","name":"Finland"},{"id":"74","name":"France, Metropolitan"},{"id":"75","name":"French Guiana"},{"id":"76","name":"French Polynesia"},{"id":"77","name":"French Southern Territories"},{"id":"78","name":"Gabon"},{"id":"79","name":"Gambia"},{"id":"80","name":"Georgia"},{"id":"81","name":"Germany"},{"id":"82","name":"Ghana"},{"id":"83","name":"Gibraltar"},{"id":"84","name":"Greece"},{"id":"85","name":"Greenland"},{"id":"86","name":"Grenada"},{"id":"87","name":"Guadeloupe"},{"id":"88","name":"Guam"},{"id":"89","name":"Guatemala"},{"id":"90","name":"Guinea"},{"id":"91","name":"Guinea-Bissau"},{"id":"92","name":"Guyana"},{"id":"93","name":"Haiti"},{"id":"94","name":"Heard and Mc Donald Islands"},{"id":"95","name":"Honduras"},{"id":"96","name":"Hong Kong"},{"id":"97","name":"Hungary"},{"id":"98","name":"Iceland"},{"id":"99","name":"India"},{"id":"100","name":"Indonesia"},{"id":"101","name":"Iran (Islamic Republic of)"},{"id":"102","name":"Iraq"},{"id":"103","name":"Ireland"},{"id":"104","name":"Israel"},{"id":"105","name":"Italy"},{"id":"106","name":"Jamaica"},{"id":"107","name":"Japan"},{"id":"108","name":"Jordan"},{"id":"109","name":"Kazakhstan"},{"id":"110","name":"Kenya"},{"id":"111","name":"Kiribati"},{"id":"112","name":"North Korea"},{"id":"113","name":"South Korea"},{"id":"114","name":"Kuwait"},{"id":"115","name":"Kyrgyzstan"},{"id":"116","name":"Lao People's Democratic Republic"},{"id":"117","name":"Latvia"},{"id":"118","name":"Lebanon"},{"id":"119","name":"Lesotho"},{"id":"120","name":"Liberia"},{"id":"121","name":"Libyan Arab Jamahiriya"},{"id":"122","name":"Liechtenstein"},{"id":"123","name":"Lithuania"},{"id":"124","name":"Luxembourg"},{"id":"125","name":"Macau"},{"id":"126","name":"FYROM"},{"id":"127","name":"Madagascar"},{"id":"128","name":"Malawi"},{"id":"129","name":"Malaysia"},{"id":"130","name":"Maldives"},{"id":"131","name":"Mali"},{"id":"132","name":"Malta"},{"id":"133","name":"Marshall Islands"},{"id":"134","name":"Martinique"},{"id":"135","name":"Mauritania"},{"id":"136","name":"Mauritius"},{"id":"137","name":"Mayotte"},{"id":"138","name":"Mexico"},{"id":"139","name":"Micronesia, Federated States of"},{"id":"140","name":"Moldova, Republic of"},{"id":"141","name":"Monaco"},{"id":"142","name":"Mongolia"},{"id":"143","name":"Montserrat"},{"id":"144","name":"Morocco"},{"id":"145","name":"Mozambique"},{"id":"146","name":"Myanmar"},{"id":"147","name":"Namibia"},{"id":"148","name":"Nauru"},{"id":"149","name":"Nepal"},{"id":"150","name":"Netherlands"},{"id":"151","name":"Netherlands Antilles"},{"id":"152","name":"New Caledonia"},{"id":"153","name":"New Zealand"},{"id":"154","name":"Nicaragua"},{"id":"155","name":"Niger"},{"id":"156","name":"Nigeria"},{"id":"157","name":"Niue"},{"id":"158","name":"Norfolk Island"},{"id":"159","name":"Northern Mariana Islands"},{"id":"160","name":"Norway"},{"id":"161","name":"Oman"},{"id":"162","name":"Pakistan"},{"id":"163","name":"Palau"},{"id":"164","name":"Panama"},{"id":"165","name":"Papua New Guinea"},{"id":"166","name":"Paraguay"},{"id":"167","name":"Peru"},{"id":"168","name":"Philippines"},{"id":"169","name":"Pitcairn"},{"id":"170","name":"Poland"},{"id":"171","name":"Portugal"},{"id":"172","name":"Puerto Rico"},{"id":"173","name":"Qatar"},{"id":"174","name":"Reunion"},{"id":"175","name":"Romania"},{"id":"176","name":"Russian Federation"},{"id":"177","name":"Rwanda"},{"id":"178","name":"Saint Kitts and Nevis"},{"id":"179","name":"Saint Lucia"},{"id":"180","name":"Saint Vincent and the Grenadines"},{"id":"181","name":"Samoa"},{"id":"182","name":"San Marino"},{"id":"183","name":"Sao Tome and Principe"},{"id":"184","name":"Saudi Arabia"},{"id":"185","name":"Senegal"},{"id":"186","name":"Seychelles"},{"id":"187","name":"Sierra Leone"},{"id":"188","name":"Singapore"},{"id":"189","name":"Slovak Republic"},{"id":"190","name":"Slovenia"},{"id":"191","name":"Solomon Islands"},{"id":"192","name":"Somalia"},{"id":"193","name":"South Africa"},{"id":"194","name":"South Georgia &amp; South Sandwich Islands"},{"id":"195","name":"Spain"},{"id":"196","name":"Sri Lanka"},{"id":"197","name":"St. Helena"},{"id":"198","name":"St. Pierre and Miquelon"},{"id":"199","name":"Sudan"},{"id":"200","name":"Suriname"},{"id":"201","name":"Svalbard and Jan Mayen Islands"},{"id":"202","name":"Swaziland"},{"id":"203","name":"Sweden"},{"id":"204","name":"Switzerland"},{"id":"205","name":"Syrian Arab Republic"},{"id":"206","name":"Taiwan"},{"id":"207","name":"Tajikistan"},{"id":"208","name":"Tanzania, United Republic of"},{"id":"209","name":"Thailand"},{"id":"210","name":"Togo"},{"id":"211","name":"Tokelau"},{"id":"212","name":"Tonga"},{"id":"213","name":"Trinidad and Tobago"},{"id":"214","name":"Tunisia"},{"id":"215","name":"Turkey"},{"id":"216","name":"Turkmenistan"},{"id":"217","name":"Turks and Caicos Islands"},{"id":"218","name":"Tuvalu"},{"id":"219","name":"Uganda"},{"id":"220","name":"Ukraine"},{"id":"221","name":"United Arab Emirates"},{"id":"222","name":"United Kingdom"},{"id":"223","name":"United States"},{"id":"224","name":"United States Minor Outlying Islands"},{"id":"225","name":"Uruguay"},{"id":"226","name":"Uzbekistan"},{"id":"227","name":"Vanuatu"},{"id":"228","name":"Vatican City State (Holy See)"},{"id":"229","name":"Venezuela"},{"id":"230","name":"Viet Nam"},{"id":"231","name":"Virgin Islands (British)"},{"id":"232","name":"Virgin Islands (U.S.)"},{"id":"233","name":"Wallis and Futuna Islands"},{"id":"234","name":"Western Sahara"},{"id":"235","name":"Yemen"},{"id":"237","name":"Democratic Republic of Congo"},{"id":"238","name":"Zambia"},{"id":"239","name":"Zimbabwe"},{"id":"242","name":"Montenegro"},{"id":"243","name":"Serbia"},{"id":"244","name":"Aaland Islands"},{"id":"245","name":"Bonaire, Sint Eustatius and Saba"},{"id":"246","name":"Curacao"},{"id":"247","name":"Palestinian Territory, Occupied"},{"id":"248","name":"South Sudan"},{"id":"249","name":"St. Barthelemy"},{"id":"250","name":"St. Martin (French part)"},{"id":"251","name":"Canary Islands"},{"id":"252","name":"Ascension Island (British)"},{"id":"253","name":"Kosovo, Republic of"},{"id":"254","name":"Isle of Man"},{"id":"255","name":"Tristan da Cunha"},{"id":"256","name":"Guernsey"},{"id":"257","name":"Jersey"}]
     */

    private int responseCode;
    private String responseText;
    private List<DataBean> data;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : Afghanistan
         */

        private String id;
        private String name;

        public DataBean(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
