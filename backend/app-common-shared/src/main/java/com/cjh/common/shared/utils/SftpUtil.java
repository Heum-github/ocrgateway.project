package com.cjh.common.shared.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SftpUtil {

    private static final Logger logger = LoggerFactory.getLogger(SftpUtil.class);
    private String host;
    private int port;
    private String username;
    private String password;

    public SftpUtil(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    private ChannelSftp setupJsch() throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(password);
        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();

        return (ChannelSftp) channel;
    }

    public boolean downloadFile(String remoteFilePath, String localFilePath) {
        ChannelSftp channelSftp = null;
        try {

            channelSftp = setupJsch();
            logger.info("SFTP 현재 디렉토리: " + channelSftp.pwd());
            channelSftp.get(remoteFilePath, localFilePath);
            logger.info("SFTP 파일 다운로드 성공: " + localFilePath);
            return true;

        } catch (Exception e) {
            logger.error("SFTP 파일 다운로드 실패: " + e.getMessage());
            return false;
        } finally {
            if (channelSftp != null) {
                try {
                    if (channelSftp.isConnected()) {
                        channelSftp.disconnect();
                    }
                    if (channelSftp.getSession() != null && channelSftp.getSession().isConnected()) {
                        channelSftp.getSession().disconnect();
                    }
                } catch (JSchException e) {
                    logger.error("SFTP 연결 해제 실패: " + e.getMessage());
                }
            }
        }
    }

    public boolean uploadFile(String localFilePath, String remoteFilePath) {
        ChannelSftp channelSftp = null;
        try {
            
            channelSftp = setupJsch();
            logger.info("SFTP 현재 디렉토리: " + channelSftp.pwd());
            channelSftp.put(localFilePath, remoteFilePath);
            logger.info("SFTP 파일 업로드 성공: " + remoteFilePath);
            return true;

        } catch (Exception e) {
            logger.error("SFTP 파일 다운로드 실패: " + e.getMessage());
            return false;
        } finally {
            if (channelSftp != null) {
                try {
                    if (channelSftp.isConnected()) {
                        channelSftp.disconnect();
                    }
                    if (channelSftp.getSession() != null && channelSftp.getSession().isConnected()) {
                        channelSftp.getSession().disconnect();
                    }
                } catch (JSchException e) {
                    logger.error("SFTP 연결 해제 실패: " + e.getMessage());
                }
            }
        }
    }
}
