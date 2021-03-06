package swingjs.a2s;

import java.awt.Component;
import java.awt.Font;
import java.awt.MenuComponent;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

/**
 * Note that PopupMenu extends JPopupMenu, not java.awt.Menu
 * 
 * @author hansonr
 *
 */
public class PopupMenu extends JPopupMenu implements AWTPopupMenu {

	public void isAWT() {}
	
	public PopupMenu() {
		this(null);
	}

	public PopupMenu(String string) {
		super(string);
	}

	@Override
	public Font getFont() {
		// AWT default fonts are set by the peer (native OS)
		if (font == null && parent == null)
	    	font = new Font(Font.DIALOG, Font.PLAIN, 12);
		return super.getFont();

	}
	@Override
	public int countItems() {
		return super.getComponentCount();
	}

	@Override
	public java.awt.MenuItem add(java.awt.MenuItem mi) {
		return (MenuItem) super.add((JComponent) mi);
	}

	@Override
	public int getItemCount() {
		return super.getComponentCount();
	}

	@Override
	public java.awt.MenuItem getItem(int index) {
		return (MenuItem) super.getComponent(index);
	}

	@Override
	public void insert(java.awt.MenuItem menuitem, int index) {
		super.insert((Component) menuitem, index);		
	}

	@Override
	public void insert(String label, int index) {
		super.insert(new JLabel(label), index);
	}

	@Override
	public void insertSeparator(int index) {
		Separator sep = new JPopupMenu.Separator();
    	sep.秘j2sInvalidateOnAdd = false;
		insert(sep, index);
	}

	@Override
	public void remove(MenuComponent comp) {
		super.remove((Component) comp);		
	}

	@Override
	public boolean isTearOff() {
		// artifact of Menu superclass
		return false;
	}

	@Override
	public MenuShortcut getShortcut() {
		// artifact of Menu superclass
		return null;
	}

	@Override
	public void setShortcut(MenuShortcut s) {
		// artifact of Menu superclass
	}
	
	@Override
	public void deleteShortcut() {
		// artifact of Menu superclass
	}

	@Override
	public void setActionCommand(String command) {
		// artifact of Menu superclass
	}

	@Override
	public String getActionCommand() {
		// artifact of Menu superclass
		return null;
	}

	@Override
	public void addActionListener(ActionListener l) {
		// artifact of Menu superclass
	}

	@Override
	public void removeActionListener(ActionListener l) {
		// artifact of Menu superclass
	}

	@Override
	public ActionListener[] getActionListeners() {
		// artifact of Menu superclass
		return null;
	}

}
