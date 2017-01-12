package at.medevit.elexis.ehc.ui.vacdoc.wizard.service;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import at.medevit.elexis.ehc.vacdoc.service.VacdocService;

@Component
public class VacdocServiceComponent {
	private static VacdocService vacdocService;
	
	@Reference
	public void setVacdocService(VacdocService vacdocService){
		VacdocServiceComponent.vacdocService = vacdocService;
	}
	
	public void unsetVacdocService(VacdocService vacdocService){
		VacdocServiceComponent.vacdocService = null;
	}
	
	public static VacdocService getService(){
		if (vacdocService == null) {
			throw new IllegalStateException("No VacdocService available");
		}
		return vacdocService;
	}
}
