/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.zz.refresh;

import org.netbeans.api.actions.Savable;
import org.openide.modules.ModuleInstall;
import org.openide.util.Lookup;
import pl.zz.refreshservice.ZZMessageService;

public class Installer extends ModuleInstall {

    Lookup.Result<Savable> lookupResult;
    
    @Override
    public void restored() {
        ZZMessageService lookup = Lookup.getDefault().lookup(ZZMessageService.class);
    }

    @Override
    public boolean closing() {
        
        return super.closing(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
