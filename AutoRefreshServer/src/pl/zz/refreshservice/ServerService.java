/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.zz.refreshservice;

import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.plaf.metal.MetalIconFactory;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.lookup.ServiceProvider;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.VertxFactory;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.ServerWebSocket;
import sun.awt.IconInfo;

/**
 *
 * @author zulk
 */
@ServiceProvider(service = ZZMessageService.class)
public final class ServerService implements ZZMessageService {

    Vertx v;
    final String refresh = "refresh";
    HttpServer websocketHandler;

    @Override
    public void pushMessage(String message) {
        Set<String> connected = v.sharedData().getSet(refresh);
        for (String userId : connected) {
            System.out.println("PUSH "+message+" to "+userId);
            v.eventBus().send(userId, message);
        }
    }

    public ServerService() {
        v = VertxFactory.newVertx();
        startWsVertx();
    }

    void startWsVertx() {
        websocketHandler = v.createHttpServer().websocketHandler(new wsHanlder());
        websocketHandler.listen(8888);
        System.out.println("VERX Started");
    }

    class wsHanlder implements Handler<ServerWebSocket> {

        @Override
        public void handle(final ServerWebSocket e) {
            final String textHandlerID = e.textHandlerID();
            v.sharedData().getSet(refresh).add(textHandlerID);
            
            NotificationDisplayer.getDefault().notify("Refresh", new MetalIconFactory.FileIcon16(), "Browser connected "+e.remoteAddress().getHostName(), null);
            e.dataHandler(new Handler<Buffer>() {

                @Override
                public void handle(Buffer e) {
                    System.out.println("MESSAGE " + e.toString());
                }
            });

            e.closeHandler(new Handler<Void>() {

                @Override
                public void handle(Void x) {
                    NotificationDisplayer.getDefault().notify("Refresh", new MetalIconFactory.FileIcon16() , "Browser disconnected "+e.remoteAddress().getHostName(), null);
                    
                    v.sharedData().getSet(refresh).remove(textHandlerID);
                }
            });
        }

    }
}
