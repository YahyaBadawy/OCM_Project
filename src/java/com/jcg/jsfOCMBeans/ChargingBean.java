/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcg.jsfOCMBeans;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import static com.jcg.jsfOCMBeans.InitializationBean.logger;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author V14YBadawy
 */
@ManagedBean
@RequestScoped
public class ChargingBean implements Serializable {

    String msisdn = "";
    String fileNameValue = "";
    Date drDate;
    String drs = "";
    String dateYM = "";
    String dateDay = "";
    String dateString = "";
    String drDateWithTime = "";
    String drDateOnly = "";
    String day = "";
    String hour = "";
    String drDateWithTimeString = "";
    String vccn = "";
    String whichccns = "";
    String wholeDayMessage = "";
    int progress = 0;
    String drsType = "";
    int executionDisabledFlag = 0;
    OutputStream output;
    ArrayList<Integer> vccns;
    private byte[] exportContent;
    boolean wholeDay = false;
    boolean disableCalendar = false;
    boolean msh_3andy_hidden = false;
    boolean vccnOutput = false;

    public ChargingBean() {
        System.out.println("Starting ChargingBean constructor...");
        msh_3andy_hidden = false;
        vccnOutput = false;
        msisdn = "";
        fileNameValue = "";
        drs = "";
        dateYM = "";
        dateDay = "";
        drsType = "Raw";
        drDate = new Date();
        wholeDay = false;
        vccns = new ArrayList();
        disableCalendar = false;
    }
    
    public void retreiveSdpDrs() {
        output = null;
        progress = 0;
        System.out.println("Starting retreiveSdpDrs method...");
        logger.debug("Starting retreiveSdpDrs method...");
        if (msisdn.equals("")) {
            drs = "Please Enter the MSISDN !";
            logger.debug("Error: MSISDN is Empty !");
        } else {
            if (drsType.equals("Raw")) {
                try {
                    System.out.println(drDate);
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    dateString = format.format(drDate);
                    dateYM = dateString.substring(0, 6);
                    dateDay = dateString.substring(6, 8);
                    System.out.println(dateYM);
                    System.out.println(dateDay);
                    System.out.println("Retreiving SDP Raw DRs...");
                    drs = OcmBean.retreiveSdpDrs(msisdn, dateYM, dateDay);
                    System.out.println(drs);
                    if (drs.length() < 40) {
                        System.out.println("Setting msh_3andy_hidden to be true");
                        msh_3andy_hidden = true;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (drsType.equals("Output")) {
                System.out.println(drDate);
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                dateString = format.format(drDate);
                System.out.println("Retreiving SDP Output DRs...");
                drs = OcmBean.retreiveSdpOutputDrs(msisdn, dateString);
                if (drs.length() < 40) {
                    System.out.println("Setting msh_3andy_hidden to be true");
                    msh_3andy_hidden = true;
                }
            }
        }
    }
    
    public void retreiveDCCNDrs() {
        output = null;
        progress = 0;
        System.out.println("Starting retreiveDCCNDrs method...");
        if (msisdn.equals("")) {
            drs = "Please Enter the Missing Fields !";
        } else {
            if (drsType.equals("Raw")) {
                try {
                    System.out.println(drDate);
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
                    dateString = format.format(drDate);
                    System.out.println("Directory : " + dateString);
                    format = new SimpleDateFormat("dd");
                    day = format.format(drDate);
                    format = new SimpleDateFormat("yyMMdd-HH");

                    drDateWithTime = format.format(drDate);
                    
                    String[] dateSplit = drDateWithTime.split("-");
                    String daySplit = dateSplit[0];
                    String hrSplit  = dateSplit[1];
                    
                    
                    String drHour = String.valueOf(Integer.parseInt(hrSplit) - 2);
                    String drHour2 = String.valueOf(Integer.parseInt(hrSplit) - 3);
                    
                    drDateWithTimeString = daySplit + "-" + drHour; 
                    String drDateWithTimeString2 = daySplit + "-" + drHour2;
                    
                            
                    System.out.println("Time of DR : " + day);
                    System.out.println("Retreiving Data CCN Raw DRs...");
                    format = new SimpleDateFormat("yyyyMMdd");
                    drDateOnly = format.format(drDate);
                    whichccns = OcmBean.retreiveDCCNOutputWhichCCNs(msisdn, dateString, day);
                    System.out.println("CCNs: " + whichccns);
                    String[] whichccnsArray = whichccns.split("\\r?\\n");
                    System.out.println("CCNs Array length: " + whichccnsArray.length);
                    if (whichccnsArray.length > 0) {
                        for (int i = 0; i < whichccnsArray.length; i++) {
						    if (!whichccnsArray[i].equals("")) {
                                drs += OcmBean.retreiveDCCNRawDrs(msisdn, dateString, day, drDateWithTimeString, whichccnsArray[i], drDateOnly);
                                drs += OcmBean.retreiveDCCNRawDrs(msisdn, dateString, day, drDateWithTimeString2, whichccnsArray[i], drDateOnly);
                            }
                        }

                        if (drs.equals("") || drs == null) {
                            drs = "No Data CCNs DRs found on this date and time !";
                            if (drs.length() < 40) {
                                System.out.println("Setting msh_3andy_hidden to be true");
                                msh_3andy_hidden = true;
                            }
                        }
                    } else {
                        drs = "No Data CCNs DRs found on this date and time !";
                        if (drs.length() < 40) {
                            System.out.println("Setting msh_3andy_hidden to be true");
                            msh_3andy_hidden = true;
                        }
                    }
                    if (drs.length() < 40) {
                        System.out.println("Setting msh_3andy_hidden to be true");
                        msh_3andy_hidden = true;
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                msisdn = "20" + msisdn;
                System.out.println(drDate);
                SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
                dateString = format.format(drDate);
                System.out.println("Directory : " + dateString);
                format = new SimpleDateFormat("dd");
                day = format.format(drDate);
                System.out.println("Time of DR : " + day);
                format = new SimpleDateFormat("HH");
                hour = format.format(drDate);
                hour = hour.substring(0, 2);
                System.out.println("Retreiving Data CCN Output DRs...");
                drs = OcmBean.retreiveDCCNOutputDrs(msisdn, dateString, day, hour);
                
                if (drs.length() < 40) {
                    System.out.println("Setting msh_3andy_hidden to be true");
                    msh_3andy_hidden = true;
                }

                if (drs.equals("") || drs == null || drs.length() < 40) {
                    drs = "No Data CCNs DRs found on this date and time !";
                }
            }
        }
    }
    
    public void retreiveSdpSnapshot() {

        output = null;
        progress = 0;
        System.out.println("Starting retreiveSdpSnapshot method...");
        if (msisdn.equals("")) {
            drs = "Please Enter the MSISDN !";
        } else {

            try {
                System.out.println(drDate);
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                dateString = format.format(drDate);
                System.out.println(dateString);
                System.out.println("Retreiving SDP Snapshot...");
                drs = OcmBean.retreiveSdpSnapshot(msisdn, dateString);
                if (drs.length() < 40) {
                    System.out.println("Setting msh_3andy_hidden to be true");
                    msh_3andy_hidden = true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void retreiveAgileInvestigator() {

        output = null;
        progress = 0;
        System.out.println("Starting retreiveAgileInvestigator method...");
        if (msisdn.equals("")) {
            drs = "Please Enter the MSISDN !";
        } else {

            try {
                System.out.println(drDate);
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                dateString = format.format(drDate);
                dateYM = dateString.substring(0, 6);
                dateDay = dateString.substring(6, 8);
                System.out.println(dateYM);
                System.out.println(dateDay);
                System.out.println(dateString);
                System.out.println("Retreiving agile investigator...");
                drs = OcmBean.retreiveAgileInvestigator(msisdn, dateYM, dateDay, fileNameValue);
                if (drs.length() < 40) {
                    System.out.println("Setting msh_3andy_hidden to be true");
                    msh_3andy_hidden = true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void retreiveAirDrs() {
    output = null;
    progress = 0;
    System.out.println("Starting retreiveAirDrs method...");
    if (msisdn.equals("")) {
            drs = "Please Enter the MSISDN !";
        } else {
            if (drsType.equals("Raw")) {
                try {
                    System.out.println(drDate);
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    dateString = format.format(drDate);
                    System.out.println("Retreiving AIR Raw DRs...");
                    drs = OcmBean.retreiveAIRDrs(msisdn, dateString);
                    if (drs.length() < 40) {
                        System.out.println("Setting msh_3andy_hidden to be true");
                        msh_3andy_hidden = true;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println(drDate);
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                dateString = format.format(drDate);
                System.out.println("Retreiving AIR Output DRs...");
                drs = OcmBean.retreiveAIROutputDrs(msisdn, dateString);
                if (drs.length() < 40) {
                    System.out.println("Setting msh_3andy_hidden to be true");
                    msh_3andy_hidden = true;
                }
            }
        }
    }
    
    public void applyCacheClear() {
        output = null;
        progress = 0;
        System.out.println("Starting AF Cache Clear method...");
        try {
            System.out.println("Applying AF Cache Clear...");
            drs = OcmBean.applyCacheClear();
            if (drs.length() < 40) {
                System.out.println("Setting msh_3andy_hidden to be true");
                msh_3andy_hidden = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void dbCleanupRMD() {
        output = null;
        progress = 0;
        System.out.println("Starting RMD database cleanup...");
        try {
            System.out.println("Applying RMD DB Cleanup...");
            drs = OcmBean.applyCleanupRMD();
            
            if (drs.length() < 40) {
                System.out.println("Setting msh_3andy_hidden to be true");
                msh_3andy_hidden = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void dbCleanupHQ() {
        output = null;
        progress = 0;
        System.out.println("Starting HQ database cleanup...");
        try {
            System.out.println("Applying HQ DB Cleanup...");
            drs = OcmBean.applyCleanupHQ();
            if (drs.length() < 40) {
                System.out.println("Setting msh_3andy_hidden to be true");
                msh_3andy_hidden = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetValues() {
        msisdn = "";
        fileNameValue = "";
        drs = "";
        dateYM = "";
        dateDay = "";
        drsType = "Raw";
        drDate = new Date();
        wholeDay = false;
        disableCalendar = false;
    }

    public String getMsisdn() {
        return msisdn;
    }
    
    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getfileNameValue() {
        return fileNameValue;
    }
    
    public void setfileName(String fileNameValue) {
        this.fileNameValue = fileNameValue;    
    }
    
    public Date getDrDate() {
        return drDate;
    }

    public void setDrDate(Date drDate) {
        this.drDate = drDate;
    }

    public String getDrs() {
        return drs;
    }

    public void setDrs(String drs) {
        this.drs = drs;
    }

    public String getDateYM() {
        return dateYM;
    }

    public void setDateYM(String dateYM) {
        this.dateYM = dateYM;
    }

    public String getDateDay() {
        return dateDay;
    }

    public void setDateDay(String dateDay) {
        this.dateDay = dateDay;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getExecutionDisabledFlag() {
        return executionDisabledFlag;
    }

    public void setExecutionDisabledFlag(int executionDisabledFlag) {
        this.executionDisabledFlag = executionDisabledFlag;
    }

    public OutputStream getOutput() {
        return output;
    }

    public void setOutput(OutputStream output) {
        this.output = output;
    }

    public String getDrsType() {
        return drsType;
    }

    public void setDrsType(String drsType) {
        this.drsType = drsType;
    }

    public String getVccn() {
        return vccn;
    }

    public void setVccn(String vccn) {
        this.vccn = vccn;
    }

    public String getDrDateWithTime() {
        return drDateWithTime;
    }

    public void setDrDateWithTime(String drDateWithTime) {
        this.drDateWithTime = drDateWithTime;
    }

    public ArrayList<Integer> getVccns() {
        return vccns;
    }

    public void setVccns(ArrayList<Integer> vccns) {
        this.vccns = vccns;
    }

    public String getDrDateWithTimeString() {
        return drDateWithTimeString;
    }

    public void setDrDateWithTimeString(String drDateWithTimeString) {
        this.drDateWithTimeString = drDateWithTimeString;
    }

    public byte[] getExportContent() {
        return exportContent;
    }

    public void setExportContent(byte[] exportContent) {
        this.exportContent = exportContent;
    }

    public boolean isWholeDay() {
        return wholeDay;
    }

    public void setWholeDay(boolean wholeDay) {
        this.wholeDay = wholeDay;
    }

    public String getWholeDayMessage() {
        return wholeDayMessage;
    }

    public void setWholeDayMessage(String wholeDayMessage) {
        this.wholeDayMessage = wholeDayMessage;
    }

    public boolean isDisableCalendar() {
        return disableCalendar;
    }

    public void setDisableCalendar(boolean disableCalendar) {
        this.disableCalendar = disableCalendar;
    }

    public String getDrDateOnly() {
        return drDateOnly;
    }

    public void setDrDateOnly(String drDateOnly) {
        this.drDateOnly = drDateOnly;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getWhichccns() {
        return whichccns;
    }

    public void setWhichccns(String whichccns) {
        this.whichccns = whichccns;
    }

    public boolean isMsh_3andy_hidden() {
        return msh_3andy_hidden;
    }

    public void setMsh_3andy_hidden(boolean msh_3andy_hidden) {
        this.msh_3andy_hidden = msh_3andy_hidden;
    }

    public boolean isVccnOutput() {
        return vccnOutput;
    }

    public void setVccnOutput(boolean vccnOutput) {
        this.vccnOutput = vccnOutput;
    }
}
