package com.pairs.netty.bt;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created on 2017年07月31日12:00
 * <p>
 * Title:[]
 * </p >
 * <p>
 * Description :[]
 * </p >
 * Company:武汉灵达科技有限公司
 *
 * @author [hupeng]
 * @version 1.0
 **/
public class DClient {

    public static void main(String[] args) throws Exception {
        Client client = new Client(
                InetAddress.getLocalHost(),
                SharedTorrent.fromFile(
                        new File("D:\\down\\CentOS-6.9-i386-minimal.torrent"),
                        new File("E:\\")));
        client.setMaxDownloadRate(50.0);
        client.setMaxUploadRate(50.0);
        client.download();
        client.waitForCompletion();
    }
}
