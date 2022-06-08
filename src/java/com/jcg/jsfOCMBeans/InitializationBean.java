/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcg.jsfOCMBeans;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Properties;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.apache.log4j.Logger;
import statics.Statics;

/**
 *
 * @author Ahmed Reda
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class InitializationBean {

    /**
     * Creates a new instance of InitializationBean
     */
    public static Logger logger;

    public InitializationBean() {

        try {
            logger = Logger.getLogger(this.getClass());
            System.out.println("Starting InitializationBean...");
            System.out.println("Loading Properties...");
            Properties prop = new Properties();
            prop.load(getClass().getClassLoader().getResourceAsStream("/prop.properties"));
            System.out.println("Finished Loading Voice CCNs Properties File...");
            Statics.setPropertiesModel(prop.getProperty("VoiceCCNs"));
            Statics.setSDPRawCommand(prop.getProperty("SDPRawCommand"));
            Statics.setHQIP(prop.getProperty("HQIP"));
            Statics.setHQPort(prop.getProperty("HQPort"));
            Statics.setHQUsername(prop.getProperty("HQUsername"));
            Statics.setHQPassword(prop.getProperty("HQPassword"));
            Statics.setRMDIP(prop.getProperty("RMDIP"));
            Statics.setRMDPort(prop.getProperty("RMDPort"));
            Statics.setRMDUsername(prop.getProperty("RMDUsername"));
            Statics.setRMDPassword(prop.getProperty("RMDPassword"));
            Statics.setFTPPort(prop.getProperty("FTPPort"));
            Statics.setFTPUsername(prop.getProperty("FTPUsername"));
            Statics.setFTPPassword(prop.getProperty("FTPPassword"));
            Statics.setFTPIP(prop.getProperty("FTPIP"));
            Statics.setAFCacheClearCommand(prop.getProperty("AFCacheClearCommand"));
            Statics.setDCCNOutputCCNsCommand(prop.getProperty("DCCNOutputCCNsCommand"));
            Statics.setDCCNOutputCommand(prop.getProperty("DCCNOutputCommand"));
            Statics.setDCCNRawCommand(prop.getProperty("DCCNRawCommand"));
            Statics.setDbCleanupCommand(prop.getProperty("DbCleanupCommand"));
            Statics.setAgileInvestigatorCommand(prop.getProperty("AGILEInvestigatorCommand"));
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}