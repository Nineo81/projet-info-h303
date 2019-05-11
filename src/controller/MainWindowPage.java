package controller;

import model.Trottinette;

public class MainWindowPage {

    private Main main;

    /**
     * Open a node showing history of user
     */
    public void historyAccess(){

    }

    public void trottiAccess(Trottinette trottinette){
        main.openInfoTrotti(Integer.toString(trottinette.getTID()),trottinette.getBattery(),trottinette.getPlaint());
    }

    public void setMain(Main main){
        this.main = main;
    }
}
