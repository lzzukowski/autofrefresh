/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.zz.refresh;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.editor.document.OnSaveTask;
import org.openide.util.Lookup;
import org.openide.util.NbPreferences;
import pl.zz.refreshservice.ZZMessageService;

/**
 *
 * @author zulk
 */
public class RefreshSaveTask implements OnSaveTask {

    @Override
    public void performTask() {
        if (NbPreferences.forModule(AutoRefreshPanel.class).getBoolean("refreshOnSave", true)) {
            ZZMessageService lookup = Lookup.getDefault().lookup(ZZMessageService.class);
            lookup.pushMessage("pleaseRefresh");
        }
    }

    @Override
    public void runLocked(Runnable run) {
        run.run();
    }

    @Override
    public boolean cancel() {
        return true;
    }

    @MimeRegistration(mimeType = "", service = OnSaveTask.Factory.class, position = 1500)
    public static final class SaveTaskFactory implements OnSaveTask.Factory {

        @Override
        public OnSaveTask createTask(Context context) {
            return new RefreshSaveTask();
        }
    }

}
