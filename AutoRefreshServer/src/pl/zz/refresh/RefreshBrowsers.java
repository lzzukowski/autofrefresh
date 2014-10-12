/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.zz.refresh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.netbeans.api.actions.Savable;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import pl.zz.refreshservice.ZZMessageService;

@ActionID(
        category = "Bugtracking",
        id = "pl.zz.refresh.RefreshBrowsers"
)
@ActionRegistration(
        displayName = "#CTL_RefreshBrowsers"
)
@ActionReferences({
    @ActionReference(path = "Menu/RunProject", position = 100, separatorBefore = 50),
    @ActionReference(path = "Shortcuts", name = "A-D")
})
@Messages("CTL_RefreshBrowsers=Refresh Browsers")
public final class RefreshBrowsers implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Savable lookup = Savable.REGISTRY.lookup(Savable.class);
            if (lookup != null) {
                    lookup.save();
            }
            ZZMessageService service = Lookup.getDefault().lookup(ZZMessageService.class);
            service.pushMessage("pleaseRefresh");

        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
