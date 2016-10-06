/**
 * (c) 2008-2016 by G. Weirich
 * All rights reserved
 * 
 */
package ch.elexis.laborimport.sftp;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;

import com.jcraft.jsch.UserInfo;

import ch.elexis.core.data.activator.CoreHub;
import ch.elexis.core.ui.UiDesk;
import ch.elexis.core.ui.util.SWTHelper;

public class JschUserInfo implements UserInfo {
	
	public String getPassphrase(){
		return CoreHub.localCfg.get(Preferences.SFTP_PWD, null);
	}
	
	public String getPassword(){
		return CoreHub.localCfg.get(Preferences.SFTP_PWD, null);
	}
	
	public boolean promptPassphrase(String message){
		InputDialog input =
			new InputDialog(UiDesk.getTopShell(), "Passworteingabe", message, "", null);
		if (input.open() == Dialog.OK) {
			CoreHub.localCfg.set(Preferences.SFTP_PWD, input.getValue());
			return true;
		}
		return false;
	}
	
	public boolean promptPassword(String message){
		return true;
	}
	
	public boolean promptYesNo(String message){
		return SWTHelper.askYesNo("Laborimport Badena", message);
	}
	
	public void showMessage(String message){
		SWTHelper.showInfo("Laborimport Badena", message);
		
	}
	
}
