package at.medevit.elexis.gdt.ui.table.util;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class SortColumnComparator extends ViewerComparator {
	public static final String SORT_BY = SortColumnComparator.class.getName() + ".sortBy";
	
	@SuppressWarnings("unchecked")
	@Override
	public int compare(Viewer viewer, Object e1, Object e2){
		
		Table table = (Table) viewer.getControl();
		
		// exchange values for descending sort direction
		if (table.getSortDirection() == SWT.DOWN) {
			Object tmp = e1;
			e1 = e2;
			e2 = tmp;
		}
		
		// resolve SORT_BY data value
		TableColumn column = table.getSortColumn();
		if (column == null)
			return super.compare(viewer, e1, e2);
		
		IValue sortBy = (IValue) column.getData(SORT_BY);
		
		if (sortBy == null)
			return super.compare(viewer, e1, e2);
		
		// compare using the SORT_BY value
		Object v1 = sortBy.getValue(e1);
		Object v2 = sortBy.getValue(e2);
		if (v1 instanceof Comparable && v2 instanceof Comparable) {
			return ((Comparable) v1).compareTo(v2);
		}
		
		// fallback on default ViewerComparator behaviour
		return super.compare(viewer, e1, e2);
		
	}
}
