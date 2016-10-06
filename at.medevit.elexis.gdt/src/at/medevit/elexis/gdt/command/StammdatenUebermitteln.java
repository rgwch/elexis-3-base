/*******************************************************************************
 * Copyright (c) 2011, MEDEVIT OG and MEDELEXIS AG
 * All rights reserved.
 *******************************************************************************/
package at.medevit.elexis.gdt.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;

import at.medevit.elexis.gdt.constants.GDTConstants;
import at.medevit.elexis.gdt.constants.GDTPreferenceConstants;
import at.medevit.elexis.gdt.handler.GDTOutputHandler;
import at.medevit.elexis.gdt.interfaces.IGDTCommunicationPartner;
import at.medevit.elexis.gdt.messages.GDTSatzNachricht6301;
import at.medevit.elexis.gdt.tools.GDTSatzNachrichtHelper;
import at.medevit.elexis.gdt.ui.dialog.StammdatenUebermittelnDialog;
import ch.elexis.core.data.activator.CoreHub;
import ch.elexis.core.data.events.ElexisEventDispatcher;
import ch.elexis.data.Patient;

public class StammdatenUebermitteln extends AbstractHandler {
	
	public static final String ID = "at.medevit.elexis.gdt.command.StammdatenUebermitteln";
	public static final String PARAM_ID = "at.medevit.elexis.gdt.cmd.parameter.partnerClassname";
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException{
		Patient pat = null;
		String commPartnerClass = event.getParameter(PARAM_ID);
		
		ISelection selection =
			HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		IStructuredSelection strucSelection = (IStructuredSelection) selection;
		if (selection != null & selection instanceof IStructuredSelection) {
			pat = (Patient) strucSelection.getFirstElement();
		}
		
		String configuredGDTId = CoreHub.localCfg.get(GDTPreferenceConstants.CFG_GDT_ID, "");
		if (pat == null) {
			pat = ElexisEventDispatcher.getSelectedPatient();
		}
		if (pat == null) {
			return null;
		}
		
		GDTSatzNachricht6301 gdt6301 = new GDTSatzNachricht6301(pat.get(Patient.FLD_PATID),
			pat.getName(), pat.getVorname(), pat.getGeburtsdatum(), null, pat.get(Patient.TITLE),
			null, pat.get(Patient.FLD_ZIP) + " " + pat.get(Patient.FLD_PLACE),
			pat.get(Patient.FLD_STREET), null,
			GDTSatzNachrichtHelper.bestimmeGeschlechtsWert(pat.get(Patient.FLD_SEX)), null, null,
			null, null, configuredGDTId, GDTConstants.ZEICHENSATZ_IBM_CP_437 + "",
			GDTConstants.GDT_VERSION);
			
		StammdatenUebermittelnDialog sud = new StammdatenUebermittelnDialog(
			Display.getCurrent().getActiveShell(), gdt6301, commPartnerClass);
		int retVal = sud.open();
		
		if (retVal == TitleAreaDialog.CANCEL)
			return null;
			
		IGDTCommunicationPartner cp = sud.getGDTCommunicationPartner();
		GDTOutputHandler.handleOutput(gdt6301, cp);
		
		return null;
	}
	
}
