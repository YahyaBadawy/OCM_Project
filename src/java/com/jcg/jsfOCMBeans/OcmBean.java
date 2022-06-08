package com.jcg.jsfOCMBeans;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import statics.Statics;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.io.InputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


/**
 *
 * @author V14YBadawy
 */
@ManagedBean
@RequestScoped
public class OcmBean {

    /**
     * Creates a new instance of GreetingsBean
     */
    public OcmBean() {
        System.out.println("Created GreetingsBean instance...");
    }

    public static String retreiveSdpDrs(String msisdn, String dateYM, String dateDay) {

        String drs = "";
        String sdpdr_ip = "172.30.147.20";
        String sdpdr_port = Statics.getHQPort();
        String sdpdr_username = Statics.getHQUsername();
        String sdpdr_password = Statics.getHQPassword();
        String sdpdr_command = Statics.getSDPRawCommand();
        Channel channel = null;
        Session session = null;

        try {
            JSch jsch = new JSch();
            session = jsch.getSession(sdpdr_username, sdpdr_ip, Integer.parseInt(sdpdr_port));
            session.setPassword(sdpdr_password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Creating Exec Channel...");
            channel = session.openChannel("exec");
            System.out.println("Channel Opened...");
            ((ChannelExec) channel).setCommand(sdpdr_command + " " + msisdn + " " + dateYM + " " + dateDay);
            System.out.println("Command: " + sdpdr_command + " " + msisdn + " " + dateYM + " " + dateDay);
            InputStream commandOutput = channel.getInputStream();
            System.out.println("Connecting to EMM15 HQ node...");
            channel.connect();
            System.out.println("Trying to read the response...");
            int readByte = commandOutput.read();
            System.out.println("Getting the response...");
            while (readByte != 0xffffffff) {
                drs += ((char) readByte);
                readByte = commandOutput.read();
                System.out.println(readByte);
            }

            System.out.println("DRs\n" + drs);
            return drs;
        } catch (JSchException ex) {
            System.out.println(ex);
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
            //Logger.getLogger(ChargingService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            drs = "Internal Error IO!\n\nPlease refer to Mediation & RA Support Team !";
            //Logger.getLogger(ChargingService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            drs = "Generic Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
            //Logger.getLogger(ChargingService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (channel != null) {
                channel.disconnect();
            }

            if (session != null) {
                session.disconnect();
            }

            return drs;
        }
    }
    
    
    public static String retreiveSdpOutputDrs(String msisdn, String dateString) {
        String drs = "";
        String sdpOutput_ip = "172.30.147.20";
        String sdpOutput_port = Statics.getHQPort();
        String sdpOutput_username = Statics.getHQUsername();
        String sdpOutput_password = Statics.getHQPassword();
        String sdpOutput_command = "/Backup/faisal/SDP/sdp_out_drs.sh";
        Channel channel = null;
        Session session = null;

        try {

            JSch jsch = new JSch();
            session = jsch.getSession(sdpOutput_username, sdpOutput_ip, Integer.parseInt(sdpOutput_port));
            session.setPassword(sdpOutput_password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Creating Exec Channel...");

            channel = session.openChannel("exec");

            ((ChannelExec) channel).setCommand(sdpOutput_command + " " + msisdn + " " + dateString);
            System.out.println("Command: " + sdpOutput_command + " " + msisdn + " " + dateString);
            InputStream commandOutput = channel.getInputStream();
            System.out.println("Connecting to EMM15 HQ Node...");
            channel.connect();
            System.out.println("Trying to read the response...");
            int readByte = commandOutput.read();
            System.out.println("Getting the response...");
            while (readByte != 0xffffffff) {
                drs += ((char) readByte);
                readByte = commandOutput.read();
            }

            System.out.println("DRs\n" + drs);
            return drs;
        } catch (JSchException ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
            //Logger.getLogger(ChargingService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
            //Logger.getLogger(ChargingService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
            //Logger.getLogger(ChargingService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (channel != null) {
                channel.disconnect();
            }

            if (session != null) {
                session.disconnect();
            }
            return drs;
        }
    }
    
     public static String retreiveAIRDrs(String msisdn, String dateString) {
        String drs = "";
        String airRaw_ip = "172.30.147.20";
        String airRaw_port = Statics.getHQPort();
        String airRaw_username = Statics.getHQUsername();
        String airRaw_password = Statics.getHQPassword();
        String airRaw_command = "/Backup/faisal/AIR_RAW/air_raw_drs.sh";
        Channel channel = null;
        Session session = null;

        try {

            JSch jsch = new JSch();
            session = jsch.getSession(airRaw_username, airRaw_ip, Integer.parseInt(airRaw_port));
            session.setPassword(airRaw_password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Creating Exec Channel...");

            channel = session.openChannel("exec");

            ((ChannelExec) channel).setCommand(airRaw_command + " " + msisdn + " " + dateString);
            System.out.println("Command: " + airRaw_command + " " + msisdn + " " + dateString);
            InputStream commandOutput = channel.getInputStream();
            System.out.println("Connecting to EMM15 HQ Node...");
            channel.connect();
            System.out.println("Trying to read the response...");
            int readByte = commandOutput.read();
            System.out.println("Getting the response...");
            while (readByte != 0xffffffff) {
                drs += ((char) readByte);
                readByte = commandOutput.read();
            }

            System.out.println("DRs\n" + drs);
            return drs;
        } catch (JSchException ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
        } catch (IOException ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
        } catch (Exception ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
        } finally {
            if (channel != null) {
                channel.disconnect();
            }

            if (session != null) {
                session.disconnect();
            }
            return drs;
        }
    }


    public static String retreiveAIROutputDrs(String msisdn, String dateString) {
        String drs = "";
        String airOutput_ip = "172.30.147.20";
        String airOutput_port = Statics.getHQPort();
        String airOutput_username = Statics.getHQUsername();
        String airOutput_password = Statics.getHQPassword();
        String airOutput_command = "/Backup/faisal/AIR_RAW/air_output_drs.sh";
        Channel channel = null;
        Session session = null;

        try {

            JSch jsch = new JSch();
            session = jsch.getSession(airOutput_username, airOutput_ip, Integer.parseInt(airOutput_port));
            session.setPassword(airOutput_password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Creating Exec Channel...");

            channel = session.openChannel("exec");

            ((ChannelExec) channel).setCommand(airOutput_command + " " + msisdn + " " + dateString);
            System.out.println("Command: " + airOutput_command + " " + msisdn + " " + dateString);
            InputStream commandOutput = channel.getInputStream();
            System.out.println("Connecting to EMM15 HQ Node...");
            channel.connect();
            System.out.println("Trying to read the response...");
            int readByte = commandOutput.read();
            System.out.println("Getting the response...");
            while (readByte != 0xffffffff) {
                drs += ((char) readByte);
                readByte = commandOutput.read();
            }

            System.out.println("DRs\n" + drs);
            return drs;
        } catch (JSchException ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
        } catch (IOException ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
        } catch (Exception ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";            
        } finally {
            if (channel != null) {
                channel.disconnect();
            }

            if (session != null) {
                session.disconnect();
            }
            return drs;
        }
    }


    public static String retreiveSdpSnapshot(String msisdn, String drDate) {
        String drs = "";
        String sdpSnapshot_ip = "172.30.147.20";
        String sdpSnapshot_port = Statics.getHQPort();
        String sdpSnapshot_username = Statics.getHQUsername();
        String sdpSnapshot_password = Statics.getHQPassword();
        String sdpSnapshotAF_command = "bash /Backup/faisal/SDP/afwhere_N.sh ";
        String sdpSnapshot_command = "bash /Backup/faisal/SDP/sdp_snapshot.sh";
        String sdpdr_ip = "172.30.147.20";
        String sdpdr_port = Statics.getHQPort();
        String sdpdr_username = Statics.getHQUsername();
        String sdpdr_password = Statics.getHQPassword();
        Channel channel = null;
        Session session = null;
        String afwhere = "";

        try {

            JSch jsch = new JSch();
            session = jsch.getSession(sdpdr_username, sdpdr_ip, Integer.parseInt(sdpdr_port));
            session.setPassword(sdpdr_password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Creating Exec Channel...");
            channel = session.openChannel("exec");
            System.out.println("Channel Opened...");
            ((ChannelExec) channel).setCommand(sdpSnapshotAF_command + msisdn);
            System.out.println("Command: " + sdpSnapshotAF_command + " " + msisdn);
            InputStream commandOutput = channel.getInputStream();
            System.out.println("Connecting to EMM15 HQ node...");
            channel.connect();
            System.out.println("Trying to read the response...");
            int readByte = commandOutput.read();
            System.out.println("Getting the response...");
            while (readByte != 0xffffffff) {
                afwhere += ((char) readByte);
                readByte = commandOutput.read();
            }
            
            afwhere=afwhere.replace("\n", "");
        } catch (IOException ex) {
            afwhere = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
            return ex.toString();
            //Logger.getLogger(ChargingService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            afwhere = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
            return ex.toString();
            //Logger.getLogger(ChargingService.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            JSch jsch = new JSch();
            session = jsch.getSession(sdpSnapshot_username, sdpSnapshot_ip, Integer.parseInt(sdpSnapshot_port));
            session.setPassword(sdpSnapshot_password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Creating Exec Channel...");

            channel = session.openChannel("exec");

            System.out.println("Directory Date : " + drDate);
            ((ChannelExec) channel).setCommand(sdpSnapshot_command + " " + msisdn + " " + afwhere + " " + drDate);
            System.out.println("Command: " + sdpSnapshot_command + " " + msisdn + " " + afwhere + " " + drDate);
            InputStream commandOutput = channel.getInputStream();
            System.out.println("Connecting to EMM15 HQ Node...");
            channel.connect();
            System.out.println("Trying to read the response...");
            int readByte = commandOutput.read();
            System.out.println("Getting the response...");
            while (readByte != 0xffffffff) {
                drs += ((char) readByte);
                readByte = commandOutput.read();
            }

            System.out.println("DRs\n" + drs);
            return drs;
        } catch (JSchException ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
            //Logger.getLogger(ChargingService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
            //Logger.getLogger(ChargingService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
            //Logger.getLogger(ChargingService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (channel != null) {
                channel.disconnect();
            }

            if (session != null) {
                session.disconnect();
            }

            return drs;
        }
    }
    
    
    public static String retreiveAgileInvestigator(String msisdn,  String dateYM, String dateDay, String fileName) {
        String drs = "";
        String agileInvestigator_ip = Statics.getHQIP();
        String agileInvestigator_port = Statics.getHQPort();
        String agileInvestigator_username = Statics.getHQUsername();
        String agileInvestigator_password = Statics.getHQPassword();
        String agileInvestigator_command = Statics.getAgileInvestigatorCommand();
        Channel channel = null;
        Session session = null;

        try {

            JSch jsch = new JSch();
            session = jsch.getSession(agileInvestigator_username, agileInvestigator_ip, Integer.parseInt(agileInvestigator_port));
            session.setPassword(agileInvestigator_password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Creating Exec Channel...");
            channel = session.openChannel("exec");
            System.out.println("Channel Opened...");
            ((ChannelExec) channel).setCommand(agileInvestigator_command + msisdn + dateYM + dateDay + fileName);
            System.out.println("Command: " + agileInvestigator_command + " " + msisdn + " " + dateYM + " " + fileName);
            InputStream commandOutput = channel.getInputStream();
            System.out.println("Connecting to EMM15 HQ node...");
            channel.connect();
            System.out.println("Trying to read the response...");
            int readByte = commandOutput.read();
            System.out.println("Getting the response...");
            while (readByte != 0xffffffff) {
                drs += ((char) readByte);
                readByte = commandOutput.read();
            }
            
            drs=drs.replace("\n", "");
        } catch (IOException ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
            return ex.toString();
            //Logger.getLogger(ChargingService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
            return ex.toString();
            //Logger.getLogger(ChargingService.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            if (channel != null) {
                channel.disconnect();
            }

            if (session != null) {
                session.disconnect();
            }

            return drs;
        }
    }
    
    
    
    
    
    
    public static String retreiveDCCNOutputDrs(String msisdn, String dirDate, String day, String hour) {
        String drs = "";
        String dccnOutput_ip = Statics.getHQIP();
        String dccnOutput_port = Statics.getHQPort();
        String dccnOutput_username = Statics.getHQUsername();
        String dccnOutput_password = Statics.getHQPassword();
        String dccnOutput_command = Statics.getDCCNOutputCommand();
        Channel channel = null;
        Session session = null;
        try {

            JSch jsch = new JSch();
            session = jsch.getSession(dccnOutput_username, dccnOutput_ip, Integer.parseInt(dccnOutput_port));
            session.setPassword(dccnOutput_password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Creating Exec Channel...");

            channel = session.openChannel("exec");

            ((ChannelExec) channel).setCommand(dccnOutput_command + " " + msisdn + " " + dirDate + " " + day + " " + hour);
            System.out.println("Command: " + dccnOutput_command + " " + msisdn + " " + dirDate + " " + day + " " + hour);
            InputStream commandOutput = channel.getInputStream();
            System.out.println("Connecting to EMM15 HQ Node...");
            channel.connect();
            System.out.println("Trying to read the response...");
            int readByte = commandOutput.read();
            System.out.println("Getting the response...");
            while (readByte != 0xffffffff) {
                drs += ((char) readByte);
                readByte = commandOutput.read();
            }

            System.out.println("DRs\n" + drs);
            return drs;
        } catch (JSchException ex) {
             drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
        } catch (IOException ex) {
             drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
        } finally {
            if (channel != null) {
                channel.disconnect();
            }

            if (session != null) {
                session.disconnect();
            }
            return drs;
        }
    }

    public static String retreiveDCCNOutputWhichCCNs(String msisdn, String dirDate, String day) {
        String drs = "";
        String dccnOutputCCNs_ip = Statics.getHQIP();
        String dccnOutputCCNs_port = Statics.getHQPort();
        String dccnOutputCCNs_username = Statics.getHQUsername();
        String dccnOutputCCNs_password = Statics.getHQPassword();
        String dccnOutputCCNs_command = Statics.getDCCNOutputCCNsCommand();
        Channel channel = null;
        Session session = null;

        try {

            JSch jsch = new JSch();
            session = jsch.getSession(dccnOutputCCNs_username, dccnOutputCCNs_ip, Integer.parseInt(dccnOutputCCNs_port));
            session.setPassword(dccnOutputCCNs_password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Creating Exec Channel...");

            channel = session.openChannel("exec");

            ((ChannelExec) channel).setCommand(dccnOutputCCNs_command + " " + msisdn + " " + dirDate + " " + day);
            System.out.println("Command: " + dccnOutputCCNs_command + " " + msisdn + " " + dirDate + " " + day);
            InputStream commandOutput = channel.getInputStream();
            System.out.println("Connecting to EMM15 HQ Node...");
            channel.connect();
            System.out.println("Trying to read the response...");
            int readByte = commandOutput.read();
            System.out.println("Getting the response...");
            while (readByte != 0xffffffff) {
                drs += ((char) readByte);
                readByte = commandOutput.read();
            }

            System.out.println("DRs\n" + drs);
            return drs;
        } catch (JSchException ex) {
             drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
        } catch (IOException ex) {
             drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
        } finally {
            if (channel != null) {
                channel.disconnect();
            }

            if (session != null) {
                session.disconnect();
            }
            return drs;
        }
    }

    public static String retreiveDCCNRawDrs(String msisdn, String dirDate, String day, String drtime, String dccn, String drDateOnly) {
        String drs = "";
        String dccnRaw_ip = Statics.getHQIP();
        String dccnRaw_port = Statics.getHQPort();
        String dccnRaw_username = Statics.getHQUsername();
        String dccnRaw_password = Statics.getHQPassword();
        String dccnRaw_command = Statics.getDCCNRawCommand();
        Channel channel = null;
        Session session = null;

        try {

            JSch jsch = new JSch();
            session = jsch.getSession(dccnRaw_username, dccnRaw_ip, Integer.parseInt(dccnRaw_port));
            session.setPassword(dccnRaw_password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Creating Exec Channel...");

            channel = session.openChannel("exec");

            ((ChannelExec) channel).setCommand(dccnRaw_command + " " + msisdn + " " + dirDate + " " + day + " " + drtime + " " + dccn + " " + drDateOnly);
            System.out.println("Command: " + dccnRaw_command + " " + msisdn + " " + dirDate + " " + day + " " + drtime + " " + dccn + " " + drDateOnly);

            InputStream commandOutput = channel.getInputStream();
            System.out.println("Connecting to EMM15 HQ Node...");
            channel.connect();
            System.out.println("Trying to read the response...");
            int readByte = commandOutput.read();
            System.out.println("Getting the response...");
            while (readByte != 0xffffffff) {
                drs += ((char) readByte);
                readByte = commandOutput.read();
            }

            System.out.println("DRs\n" + drs);
            return drs;
        } catch (JSchException ex) {
             drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
        } catch (IOException ex) {
             drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
        } finally {
            if (channel != null) {
                channel.disconnect();
            }

            if (session != null) {
                session.disconnect();
            }
            return drs;
        }
    }

    
    public static String applyCacheClear() {
        String drs = "";
        String CacheOutput_ip = Statics.getFTPIP();
        String CacheOutput_port = Statics.getFTPPort();
        String CacheOutput_username = Statics.getFTPUsername();
        String CacheOutput_password = Statics.getFTPPassword();
        String CacheOutput_command = Statics.getAFCacheClearCommand();
        Channel channel = null;
        Session session = null;
        
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(CacheOutput_username, CacheOutput_ip, Integer.parseInt(CacheOutput_port));
            session.setPassword(CacheOutput_password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Creating Exec Channel...");

            channel = session.openChannel("exec");

            ((ChannelExec) channel).setCommand(CacheOutput_command);
            System.out.println("Command: " + CacheOutput_command);
            InputStream commandOutput = channel.getInputStream();
            System.out.println("Connecting to FTP Server Node...");
            channel.connect();
            System.out.println("Trying to read the response...");
            int readByte = commandOutput.read();
            System.out.println("Getting the response...");
            while (readByte != 0xffffffff) {
                drs += ((char) readByte);
                readByte = commandOutput.read();
            }

            System.out.println("DRs\n" + drs);
            return drs;
        } catch (JSchException ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
        } catch (IOException ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
        } catch (Exception ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";            
        } finally {
            if (channel != null) {
                channel.disconnect();
            }

            if (session != null) {
                session.disconnect();
            }
            return drs;
        }
    }
    
    public static String applyCleanupHQ() {
        String drs = "";
        String dbCleanHQ_ip = Statics.getHQIP();
        String dbCleanHQ_port = Statics.getHQPort();
        String dbCleanHQ_username = Statics.getHQUsername();
        String dbCleanHQ_password = Statics.getHQPassword();
        String dbCleanHQ_command = Statics.getDbCleanupCommand();
        Channel channel = null;
        Session session = null;
        
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(dbCleanHQ_username, dbCleanHQ_ip, Integer.parseInt(dbCleanHQ_port));
            session.setPassword(dbCleanHQ_password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Creating Exec Channel...");

            channel = session.openChannel("exec");

            ((ChannelExec) channel).setCommand(dbCleanHQ_command);
            System.out.println("Command: " + dbCleanHQ_command);
            InputStream commandOutput = channel.getInputStream();
            System.out.println("Connecting to HQ Cluster...");
            channel.connect();
            System.out.println("Trying to read the response...");
            int readByte = commandOutput.read();
            System.out.println("Getting the response...");
            while (readByte != 0xffffffff) {
                drs += ((char) readByte);
                readByte = commandOutput.read();
            }

            System.out.println("DRs\n" + drs);
            return drs;
        } catch (JSchException ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
        } catch (IOException ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
        } catch (Exception ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";            
        } finally {
            if (channel != null) {
                channel.disconnect();
            }

            if (session != null) {
                session.disconnect();
            }
            return drs;
        }
    }
        
    public static String applyCleanupRMD() {
        
        String drs = "";
        String dbCleanRMD_ip = Statics.getRMDIP();
        String dbCleanRMD_port = Statics.getRMDPort();
        String dbCleanRMD_username = Statics.getRMDUsername();
        String dbCleanRMD_password = Statics.getRMDPassword();
        String dbCleanRMD_command = Statics.getDbCleanupCommand();
        Channel channel = null;
        Session session = null;
        
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(dbCleanRMD_username, dbCleanRMD_ip, Integer.parseInt(dbCleanRMD_port));
            session.setPassword(dbCleanRMD_password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Creating Exec Channel...");

            channel = session.openChannel("exec");

            ((ChannelExec) channel).setCommand(dbCleanRMD_command);
            System.out.println("Command: " + dbCleanRMD_command);
            InputStream commandOutput = channel.getInputStream();
            System.out.println("Connecting to RMD Cluster...");
            channel.connect();
            System.out.println("Trying to read the response...");
            int readByte = commandOutput.read();
            System.out.println("Getting the response...");
            while (readByte != 0xffffffff) {
                drs += ((char) readByte);
                readByte = commandOutput.read();
            }

            System.out.println("DRs\n" + drs);
            return drs;
        } catch (JSchException ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
        } catch (IOException ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";
        } catch (Exception ex) {
            drs = "Internal Error !\n\nPlease refer to Mediation & RA Support Team !";            
        } finally {
            if (channel != null) {
                channel.disconnect();
            }

            if (session != null) {
                session.disconnect();
            }
            return drs;
        }
    }
}